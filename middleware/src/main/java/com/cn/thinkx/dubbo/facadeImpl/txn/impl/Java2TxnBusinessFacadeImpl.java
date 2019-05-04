package com.cn.thinkx.dubbo.facadeImpl.txn.impl;

import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.PayBackBean;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.biz.core.service.CtrlSystemService;
import com.cn.thinkx.biz.translog.model.AccountLog;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.biz.translog.model.TransLog;
import com.cn.thinkx.biz.translog.service.IntfaceTransLogService;
import com.cn.thinkx.biz.user.service.BizUserService;
import com.cn.thinkx.common.redis.util.BaseKeyUtil;
import com.cn.thinkx.dubbo.entity.TxnResp;
import com.cn.thinkx.dubbo.facadeImpl.txn.utils.HKBTxnUtil;
import com.cn.thinkx.dubbo.facadeImpl.txn.valid.Java2TxnBusinessValidUtil;
import com.cn.thinkx.dubbo.hsm.HsmUtil;
import com.cn.thinkx.itf.base.MessageUtils;
import com.cn.thinkx.itf.base.SignUtil;
import com.cn.thinkx.itf.service.TxnSendMessageITF;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ACC_TYPE;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DES3Util;
import com.cn.thinkx.pms.base.utils.RandomUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;

@Service("java2TxnBusinessFacade")
public class Java2TxnBusinessFacadeImpl implements Java2TxnBusinessFacade {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("txnSendMessageITF")
	private TxnSendMessageITF txnSendMessageITF;
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;
	
	@Autowired
	@Qualifier("intfaceTransLogService")
	private IntfaceTransLogService intfaceTransLogService;

	@Autowired
	@Qualifier("bizUserService")
	private BizUserService bizUserService;
	
	@Override
	public String merchantAccountOpeningITF(TxnPackageBean txn) {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		try{
			logger.info("Java2TxnBusinessFacadeImpl.merchantAccountOpeningITF txn param jsonStr is -------------->" + JSONArray.toJSONString(txn));
		}catch (Exception e){
			logger.error(" Java2TxnBusinessFacadeImpl-->merchantAccountOpeningITF--> : Exception");
		}
		
		if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
			resp.setInfo("机构渠道号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return JSONArray.toJSONString(resp);
		}
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		IntfaceTransLog log = new IntfaceTransLog();
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.MB80.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setMchntCode(txn.getInnerMerchantNo());
				log.setShopCode(txn.getInnerShopNo());
				if(txn.getCardNo() !=null){
					log.setUserInfUserName(txn.getCardNo().substring(1));
				}
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				
				try {
					int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
					if (i != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("merchantAccountOpeningITF--->中间层插入流水记录失败");
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("merchantAccountOpeningITF--->中间层插入流水异常：", e);
					return JSONArray.toJSONString(resp);
				}
				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
				
				BizMessageObj obj=null;
				try{
					obj = txnSendMessageITF.sendMessage(txn);
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("merchantAccountOpeningITF--->远程交易异常:", e);
				}
				if (obj == null) {
					obj=new BizMessageObj();
					obj.setRespCode(BaseConstants.TXN_TRANS_ERROR);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.info("merchantAccountOpeningITF--->客户端流水号[{}]交易报文没有应答", log.getInterfacePrimaryKey());
				}
				try{
					resp.setCode(obj.getRespCode());
					ITFRespCode respCodeEnum=ITFRespCode.findByCode(obj.getRespCode());
					if(respCodeEnum !=null){
						resp.setInfo(respCodeEnum.getValue());
					}else{
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					}
					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
					if (u != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("merchantAccountOpeningITF--->中间层更新流水失败，id=[{}]",log.getInterfacePrimaryKey());
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("merchantAccountOpeningITF--->更新流水异常:", e);
				}
			} else {
				resp.setInfo("日切信息交易允许状态：日切中");
				logger.info("merchantAccountOpeningITF--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} else {
			resp.setInfo("日切信息为空");
			logger.info("merchantAccountOpeningITF--->日切信息为空");
			return JSONArray.toJSONString(resp);
		}

		return JSONArray.toJSONString(resp);
	}

	@Override
	public String customerAccountOpeningITF(TxnPackageBean txn) {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		try{
			logger.info("Java2TxnBusinessFacadeImpl.customerAccountOpeningITF txn param jsonStr is -------------->" + JSONArray.toJSONString(txn));
		}catch (Exception e){
			logger.error(" Java2TxnBusinessFacadeImpl-->customerAccountOpeningITF--> : Exception");
		}
		
		if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
			resp.setInfo("机构渠道号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
			resp.setInfo("卡号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getResv3())) {
			resp.setInfo("用户ID为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getResv4())) {
			resp.setInfo("个人信息ID为空");
			return JSONArray.toJSONString(resp);
		}
		
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		IntfaceTransLog log = new IntfaceTransLog();
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW80.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setMchntCode(txn.getInnerMerchantNo());
				log.setShopCode(txn.getInnerShopNo());
				if(txn.getCardNo() !=null){
					log.setUserInfUserName(txn.getCardNo().substring(1));
				}
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				
				try{
					int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
					if (i != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("customerAccountOpeningITF--->中间层插入流水记录数量失败");
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("customerAccountOpeningITF--->中间层插入流水记录异常：", ex);
					return JSONArray.toJSONString(resp);
				}
				
				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
			    BizMessageObj obj=null;
				try{
					obj = txnSendMessageITF.sendMessage(txn);// 发送报文
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("customerAccountOpeningITF远程交易异常：", ex);
				}
				if (obj == null) {
					obj=new BizMessageObj();
					obj.setRespCode(BaseConstants.TXN_TRANS_ERROR);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.info("customerAccountOpeningITF--->客户端流水号[{}]交易报文没有应答", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				try{
					resp.setCode(obj.getRespCode());
					ITFRespCode respCodeEnum=ITFRespCode.findByCode(obj.getRespCode());
					if(respCodeEnum !=null){
						resp.setInfo(respCodeEnum.getValue());
					}else{
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					}
					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
					if (u != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("customerAccountOpeningITF--->中间层更新流水失败，id={}",log.getInterfacePrimaryKey());
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("customerAccountOpeningITF--->更新流水异常:", e);
					return JSONArray.toJSONString(resp);
				}
			} else {
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				logger.info("customerAccountOpeningITF--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} else {
			resp.setInfo("日切信息为空");
			logger.info("customerAccountOpeningITF--->日切信息为空");
			return JSONArray.toJSONString(resp);
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String customerPasswordResetITF(TxnPackageBean txn) {
		logger.info("会员卡密码重置请求参数：[{}]", JSONArray.toJSONString(txn));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		if (StringUtil.isNullOrEmpty(txn.getAccType())) {
			resp.setInfo("账户类型为空");
			return JSONArray.toJSONString(resp);
		}
		// 商户管理员重置密码时必填
		if (StringUtil.isNullOrEmpty(txn.getIssChannel()) && ACC_TYPE.MERCHANT.toString().equals(txn.getAccType())) {
			resp.setInfo("机构渠道号为空");
			return JSONArray.toJSONString(resp);
		}
		// 商户管理员重置密码时必填
		if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo()) && ACC_TYPE.MERCHANT.toString().equals(txn.getAccType())) {
			resp.setInfo("商户号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
			resp.setInfo("卡号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getPinTxn())) {
			resp.setInfo("交易密码为空");
			return JSONArray.toJSONString(resp);
		}
		String bizUserId = null;
		if(BaseConstants.PWDAcctType.CUSTOMER_PWD_100.getCode().equals(txn.getAccType())){ //获取用户Id
			bizUserId = bizUserService.getUserIdByUserName(txn.getCardNo().substring(1),txn.getChannel());
		}
		
		if(BaseConstants.PWDAcctType.MERCHANT_PWD_200.getCode().equals(txn.getAccType())){//获取管理员Id
			bizUserId = bizUserService.getManagerIdByOpenId(txn.getCardNo().substring(1));
		}
		if (StringUtil.isNullOrEmpty(bizUserId)) {
			resp.setInfo("没查找到用户信息");
			return JSONArray.toJSONString(resp);
		}
		if (!MessageUtils.validateTxnBeanSignature(txn)) { //密码重置签名验证
			resp.setInfo("签名验证失败");
			return JSONArray.toJSONString(resp);
		}
		try {
			txn.setPinTxn(DES3Util.Decrypt3DES(txn.getPinTxn(), BaseKeyUtil.getEncodingAesKey())); //密码转换为明文
		} catch (Exception e) {
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			logger.error("## 会员卡密码重置--->密码转换为明文异常", e);
			return JSONArray.toJSONString(resp);
		}
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		IntfaceTransLog log = new IntfaceTransLog();
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				String pinQuiry = RandomUtils.getRandomNumbernStr(16);
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				txn.setPinQuiry(pinQuiry);
				try {
					String mac = HsmUtil.encodeMac(pinQuiry);
					txn.setMac(mac.substring(0,8));
					txn.setPinTxn(HsmUtil.encodePassword(bizUserId, txn.getPinTxn()));
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("## 会员卡密码重置--->密码转拼异常：", e);
					return JSONArray.toJSONString(resp);
				}
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW81.getCode());// 请求类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				if (txn.getCardNo() != null) {
					log.setUserInfUserName(txn.getCardNo().substring(1));
				}
				
				try {
					int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
					if (i != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.error("## 会员卡密码重置--->中间层插入流水记录数量失败，wx_trans_log_id[{}]", txn.getSwtFlowNo());
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("## 会员卡密码重置--->中间层插入流水记录异常：", ex);
					return JSONArray.toJSONString(resp);
				}
				
				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
			    BizMessageObj obj = null;
				try {
					obj = txnSendMessageITF.sendMessage(txn);// 发送报文
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("## 会员卡密码重置--->远程交易异常", ex);
				}
				if (obj == null) {
					obj = new BizMessageObj();
					obj.setRespCode(BaseConstants.TXN_TRANS_ERROR);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("## 会员卡密码重置--->客户端流水号[{}]交易报文没有应答", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				try {
					resp.setCode(obj.getRespCode());
					ITFRespCode respCodeEnum = ITFRespCode.findByCode(obj.getRespCode());
					if (respCodeEnum != null) {
						resp.setInfo(respCodeEnum.getValue());
					} else {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					}
					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
					if (u != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.error("## 会员卡密码重置--->中间层更新流水失败，id[{}]", log.getInterfacePrimaryKey());
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("## 会员卡密码重置--->更新流水异常:", e);
					return JSONArray.toJSONString(resp);
				}
			} else {
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				logger.error("## 会员卡密码重置--->日切信息交易允许状态：日切中，用户id[{}]", bizUserId);
				return JSONArray.toJSONString(resp);
			}
		} else {
			resp.setInfo("日切信息为空");
			logger.error("## 会员卡密码重置--->日切信息为空，用户id[{}]", bizUserId);
			return JSONArray.toJSONString(resp);
		}
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String rechargeTransactionITF(TxnPackageBean txn) {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		try{
			logger.info("Java2TxnBusinessFacadeImpl.rechargeTransactionITF txn param jsonStr is -------------->" + JSONArray.toJSONString(txn));
		}catch (Exception e){
			logger.error("Java2TxnBusinessFacadeImpl-->rechargeTransactionITF--> : Exception");
		}
		
		if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
			resp.setInfo("机构渠道号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
			resp.setInfo("卡号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getOriTxnAmount())) {
			resp.setInfo("原交易金额为空");
			return JSONArray.toJSONString(resp);
		}
		txn.setCardNo("U" + txn.getCardNo());// 卡号 U开头为客户端交易，C开头则为刷卡交易
		
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		IntfaceTransLog log = new IntfaceTransLog();
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				
				String interfacePrimaryKey = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(interfacePrimaryKey);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW20.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setMchntCode(txn.getInnerMerchantNo());
				log.setShopCode(txn.getInnerShopNo());
				log.setUserInfUserName(txn.getCardNo().substring(1));
				log.setTransAmt(txn.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(txn.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				
				try{
					int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
					if (i != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("rechargeTransactionITF--->中间层插入流水记录数量失败");
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("rechargeTransactionITF--->中间层插入流水记录异常：", ex);
					return JSONArray.toJSONString(resp);
				}

				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
				BizMessageObj obj=null;
				try{
					obj = txnSendMessageITF.sendMessage(txn);// 发送报文
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("rechargeTransactionITF--->中间层插入流水记录异常：", ex);
				}
				if (obj == null) {
					obj=new BizMessageObj();
					obj.setRespCode(BaseConstants.TXN_TRANS_ERROR);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.info("rechargeTransactionITF--->客户端流水号[{}]交易报文没有应答", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				try{
					resp.setInterfacePrimaryKey(interfacePrimaryKey);
					resp.setCode(obj.getRespCode());
					ITFRespCode respCodeEnum=ITFRespCode.findByCode(obj.getRespCode());
					if(respCodeEnum !=null){
						resp.setInfo(respCodeEnum.getValue());
					}else{
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					}
					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
					if (u != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("rechargeTransactionITF--->insertIntfaceTransLog中间层更新流水失败，id:" + log.getInterfacePrimaryKey());
						return JSONArray.toJSONString(resp);
					}
					resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("rechargeTransactionITF--->insertIntfaceTransLog中间层插入流水记录异常：", ex);
				}
			} else {
				resp.setInfo("系统日切中");
				logger.info("rechargeTransactionITF--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} else {
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			logger.info("充值交易--->日切信息为空");
			return JSONArray.toJSONString(resp);
		}
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String consumeTransactionITF(TxnPackageBean txn) {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		try{
			logger.info("Java2TxnBusinessFacadeImpl.consumeTransactionITF txn param jsonStr is -------------->" + JSONArray.toJSONString(txn));
		}catch (Exception e){
			logger.error("Java2TxnBusinessFacadeImpl-->consumeTransactionITF--> : Exception");
		}

		/****参数校验**/
		if (Java2TxnBusinessValidUtil.consumeTransactionITFVaild(txn,resp)) {
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			return JSONArray.toJSONString(resp);
		}
		
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		IntfaceTransLog log = new IntfaceTransLog();
		if (cs != null) {
			// TODO 此处检查外部流水号swtFlowNo是否有重复记录
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				String interfacePrimaryKey = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(interfacePrimaryKey);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW10.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setMchntCode(txn.getInnerMerchantNo());
				log.setShopCode(txn.getInnerShopNo());
				if(txn.getCardNo() !=null){
					log.setUserInfUserName(txn.getCardNo().substring(1));
				}
				log.setTransAmt(txn.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(txn.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				log.setTermCode(txn.getInnerPosNo()); //设备号
				
				try{
					int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
					if (i != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("consumeTransactionITF--->中间层插入流水记录数量失败");
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("consumeTransactionITF--->insertIntfaceTransLog中间层插入流水记录异常：", ex);
					return JSONArray.toJSONString(resp);
				}

				if (!StringUtil.isNullOrEmpty(txn.getPinTxn())) {// 密码不为空则转pin
					try{
						txn.setPinTxn(HsmUtil.encodePassword(txn.getUserId(), txn.getPinTxn()));
					} catch (Exception ex) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.error("consumeTransactionITF--->中间层插入流水记录异常：", ex);
						return JSONArray.toJSONString(resp);
					}
				}
				resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
				BizMessageObj obj = null;
				try {
					obj = txnSendMessageITF.sendMessage(txn);
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("consumeTransactionITF--->远程调用核心交易异常", e);
				}
				
				if (obj == null) {
					obj=new BizMessageObj();
					obj.setRespCode(BaseConstants.TXN_TRANS_ERROR);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.info("consumeTransactionITF--->客户端流水号[{}]交易报文没有应答", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				try{
					resp.setInterfacePrimaryKey(interfacePrimaryKey);
					resp.setCode(obj.getRespCode());
					ITFRespCode respCodeEnum=ITFRespCode.findByCode(obj.getRespCode());
					if(respCodeEnum !=null){
						resp.setInfo(respCodeEnum.getValue());
					}else{
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					}
					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
					if (u != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("consumeTransactionITF--->中间层更新流水失败，id=[{}]",log.getInterfacePrimaryKey());
					}
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("consumeTransactionITF--->中间层更新流水异常", e);
				}
			} else {
				resp.setInfo("系统日切中");
				logger.info("消费交易--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} else {
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			logger.info("consumeTransactionITF--->日切信息为空");
			return JSONArray.toJSONString(resp);
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String HKBPayBackToJF(PayBackBean bean) throws Exception {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);

		try{
			logger.info("Java2TxnBusinessFacadeImpl.HKBPayBackToJF bean param jsonStr is -------------->" + JSONArray.toJSONString(bean));
		}catch (Exception e){
			logger.error(" Java2TxnBusinessFacadeImpl-->HKBPayBackToJF--> : Exception");
		}
		
		if (StringUtil.isNullOrEmpty(bean.getOrderId())) {
			resp.setInfo("订单号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(bean.getExtOrderId())) {
			resp.setInfo("外部订单号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(bean.getUserId())) {
			resp.setInfo("用户ID为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(bean.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return JSONArray.toJSONString(resp);
		}
		try {
			SortedMap<String, String> map = new TreeMap<String, String>();
			map.put("orderId", bean.getOrderId());
			map.put("extOrderId", bean.getExtOrderId());
			map.put("userId", bean.getUserId());
			map.put("timestamp", bean.getTimestamp());
			map.put("sign", SignUtil.sign(map, "PAYBACK"));
			return HKBTxnUtil.hkbPayBackToJF(map);
		} catch (Exception e) {
			logger.error(BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String consumeRefundITF(TxnPackageBean txn) {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		
		try{
			logger.info("Java2TxnBusinessFacadeImpl.consumeRefundITF txn param jsonStr is -------------->" + JSONArray.toJSONString(txn));
		}catch (Exception e){
			logger.error(" Java2TxnBusinessFacadeImpl-->consumeRefundITF--> : Exception");
		}
		
		if (StringUtil.isNullOrEmpty(txn.getSwtFlowNo())) {
			resp.setInfo("流水号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
			resp.setInfo("机构渠道号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
			resp.setInfo("卡号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getOriSwtFlowNo())) {
			resp.setInfo("原交易流水号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getOriTxnAmount())) {
			resp.setInfo("原交易金额为空");
			return JSONArray.toJSONString(resp);
		}
		if (!MessageUtils.validRefundSign(txn)) {
			resp.setInfo("签名验证失败");
			return JSONArray.toJSONString(resp);
		}
		try {
			// TODO 日切信息改为从缓存中读取!!!
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
			if (cs == null) {
				logger.info("consumeRefundITF--->日切信息为空");
				return JSONArray.toJSONString(resp);
			}
			// 检查外部流水号swtFlowNo是否有重复记录
			IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), txn.getSwtFlowNo());
			if (log != null) {
				logger.info("consumeRefundITF--->客户端流水号[{}]已有交易撤销流水记录", txn.getSwtFlowNo());
				resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
				return JSONArray.toJSONString(resp);
			} else {
				log = new IntfaceTransLog();
			}
			// 检查原交易流水是否存在
			IntfaceTransLog oriLog = intfaceTransLogService.getIntfaceTransLogBylogId(cs.getCurLogNum(), txn.getOriSwtFlowNo());
			if (oriLog == null) {
				logger.info("consumeRefundITF--->客户端原交易流水号[{}]无记录", txn.getOriSwtFlowNo());
				return JSONArray.toJSONString(resp);
			}
			// 检查原交易流水返回码是否为成功00
			if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(oriLog.getRespCode())) {
				logger.info("consumeRefundITF--->客户端原交易流水号[{}]respCode不等于00", txn.getOriSwtFlowNo());
				return JSONArray.toJSONString(resp);
			}
			
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setOrgInterfacePrimaryKey(txn.getOriSwtFlowNo());
				
				// 原交易为会员卡消费交易撤销
				if (TransCode.CW10.getCode().equals(oriLog.getTransId())) {
					log.setTransId(TransCode.CW11.getCode());// 交易类型
					txn.setTxnType(TransCode.CW11.getCode() + "0");
				}
				// 原交易为快捷消费交易撤销
				if (TransCode.CW71.getCode().equals(oriLog.getTransId())) {
					log.setTransId(TransCode.CW74.getCode());// 交易类型
					txn.setTxnType(TransCode.CW74.getCode() + "0");
				}
				
				log.setTransSt(0);// 交易前，状态初始值为0
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setMchntCode(txn.getInnerMerchantNo());
				log.setShopCode(txn.getInnerShopNo());
				log.setUserInfUserName(oriLog.getUserInfUserName());
				log.setTransAmt(txn.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(txn.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
				if (i != 1) {
					logger.info("consumeRefundITF--->insertIntfaceTransLog中间层插入流水记录数量不为1");
					return JSONArray.toJSONString(resp);
				}
				if (!StringUtil.isNullOrEmpty(txn.getPinTxn())) {// 密码不为空则转pin
					txn.setPinTxn(HsmUtil.encodePassword(txn.getUserId(), txn.getPinTxn()));
				}
				
				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
				BizMessageObj obj = txnSendMessageITF.sendMessage(txn);
				resp.setCode(obj.getRespCode());
				ITFRespCode respCodeEnum=ITFRespCode.findByCode(obj.getRespCode());
				if(respCodeEnum !=null){
					resp.setInfo(respCodeEnum.getValue());
				}else{
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				}
				
				log.setTransSt(1);// 交易成功，报文返回时状态更新为1
				log.setRespCode(obj.getRespCode());
				int u1 = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
				if (u1 != 1) {
					logger.info("消费交易撤销--->中间层更新流水[{}]失败", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
				
				oriLog.setTransSt(3);// 更新原交易流水状态为3：交易撤销
				oriLog.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				int u2 = intfaceTransLogService.updateIntfaceTransLog(oriLog);
				if (u2 != 1) {
					logger.info("消费交易撤销--->中间层更新原交易流水[{}]失败", oriLog.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
			} else {
				logger.info("消费交易撤销--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} catch (Exception e) {
			logger.error(BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String transRefundITF(TxnPackageBean txn) {
		logger.info("消费交易退款请求参数：{}", JSONArray.toJSONString(txn));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		
		if (StringUtil.isNullOrEmpty(txn.getSwtFlowNo())) {
			resp.setInfo("流水号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
			resp.setInfo("机构渠道号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
			resp.setInfo("卡号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getOriSwtFlowNo())) {
			resp.setInfo("原交易流水号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getOriTxnAmount())) {
			resp.setInfo("原交易金额为空");
			return JSONArray.toJSONString(resp);
		}
		if (!MessageUtils.validRefundSign(txn)) {
			resp.setInfo("签名验证失败");
			return JSONArray.toJSONString(resp);
		}
		try {
			// TODO 日切信息改为从缓存中读取!!!
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
			if (cs == null) {
				logger.info("consumeRefundITF流水号[{}]--->日切信息为空", txn.getSwtFlowNo());
				return JSONArray.toJSONString(resp);
			}
			// 检查外部流水号swtFlowNo是否有重复记录(当日记录)
			IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), txn.getSwtFlowNo());
			if (log != null) {
				logger.info("transRefundITF--->客户端流水号[{}]已有消费交易退款流水记录", txn.getSwtFlowNo());
				resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
				return JSONArray.toJSONString(resp);
			} else {
				// 检查外部流水号swtFlowNo是否有重复记录(历史记录)
				log = intfaceTransLogService.getIntfaceTransLogHisByDmsRelatedKey(txn.getSwtFlowNo());
				if (log != null) {
					logger.info("transRefundITF--->客户端流水号[{}]已有消费交易退款流水记录", txn.getSwtFlowNo());
					resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
					return JSONArray.toJSONString(resp);
				}
				log = new IntfaceTransLog();
			}
			// 检查当前流水表原交易流水是否存在
			IntfaceTransLog oriLog = intfaceTransLogService.getIntfaceTransLogBylogId(cs.getCurLogNum(), txn.getOriSwtFlowNo());
			boolean isCurrentLog = true;// 是否为当前交易记录
			if (oriLog == null) {
				isCurrentLog = false;
				oriLog = intfaceTransLogService.getIntfaceTransLogHisBylogId(txn.getOriSwtFlowNo());// 检查历史流水表原交易流水是否存在
				if (oriLog == null) {
					logger.info("transRefundITF--->客户端原交易流水号[{}]无记录", txn.getOriSwtFlowNo());
					return JSONArray.toJSONString(resp);
				}
			}
			// 检查原交易流水返回码是否为成功00
			if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(oriLog.getRespCode())) {
				logger.info("transRefundITF--->客户端原交易流水号[{}]respCode不等于00", txn.getOriSwtFlowNo());
				return JSONArray.toJSONString(resp);
			}
			
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setOrgInterfacePrimaryKey(txn.getOriSwtFlowNo());
				
				// 原交易为会员卡消费
				if (TransCode.CW10.getCode().equals(oriLog.getTransId())) {
					log.setTransId(TransCode.CW30.getCode());// 交易类型
					txn.setTxnType(TransCode.CW30.getCode() + "0");
				}
				// 原交易为快捷消费
				if (TransCode.CW71.getCode().equals(oriLog.getTransId())) {
					log.setTransId(TransCode.CW75.getCode());// 交易类型
					txn.setTxnType(TransCode.CW75.getCode() + "0");
				}
				
				log.setTransSt(0);// 交易前，状态初始值为0
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setMchntCode(txn.getInnerMerchantNo());
				log.setShopCode(txn.getInnerShopNo());
				log.setUserInfUserName(oriLog.getUserInfUserName());
				log.setTransAmt(txn.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(txn.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
				if (i != 1) {
					logger.info("transRefundITF--->insertIntfaceTransLog中间层插入流水记录数量不为1");
					return JSONArray.toJSONString(resp);
				}
				if (!StringUtil.isNullOrEmpty(txn.getPinTxn())) {// 密码不为空则转pin
					txn.setPinTxn(HsmUtil.encodePassword(txn.getUserId(), txn.getPinTxn()));
				}
				
				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
				BizMessageObj obj = txnSendMessageITF.sendMessage(txn);
				resp.setCode(obj.getRespCode());
				ITFRespCode respCodeEnum = ITFRespCode.findByCode(obj.getRespCode());
				if (respCodeEnum != null) {
					resp.setInfo(respCodeEnum.getValue());
				} else {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				}
				
				log.setTransSt(1);// 交易成功，报文返回时状态更新为1
				log.setRespCode(obj.getRespCode());
				int u1 = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
				if (u1 != 1) {
					logger.info("transRefundITF--->中间层更新流水[{}]失败", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
				
				oriLog.setTransSt(17);// 更新原交易流水状态为17：退货
				
				int u2 = 0;
				if (isCurrentLog) {
					oriLog.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
					u2 = intfaceTransLogService.updateIntfaceTransLog(oriLog);
				} else {
					u2 = intfaceTransLogService.updateIntfaceHisTransLog(oriLog);
				}
				if (u2 != 1) {
					logger.info("transRefundITF--->中间层更新原交易流水[{}]失败", oriLog.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
			} else {
				logger.info("transRefundITF--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} catch (Exception e) {
			logger.error(BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String merchantWithdrawITF(TxnPackageBean txn) {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		try{
			logger.info("Java2TxnBusinessFacadeImpl.merchantWithdrawITF txn param jsonStr is -------------->" + JSONArray.toJSONString(txn));
		}catch (Exception e){
			logger.error(" Java2TxnBusinessFacadeImpl-->merchantWithdrawITF--> : Exception");
		}
		
		if (StringUtil.isNullOrEmpty(txn.getIssChannel())) {
			resp.setInfo("机构渠道号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getCardNo())) {
			resp.setInfo("卡号为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return JSONArray.toJSONString(resp);
		}
		if (StringUtil.isNullOrEmpty(txn.getPinTxn())) {
			resp.setInfo("交易密码为空");
			return JSONArray.toJSONString(resp);
		}
		if (!MessageUtils.validateTxnBeanSignature(txn)) { //密码重置签名验证
			resp.setInfo("签名验证失败");
			return JSONArray.toJSONString(resp);
		}
		try {
			txn.setPinTxn(DES3Util.Decrypt3DES(txn.getPinTxn(), BaseKeyUtil.getEncodingAesKey())); //密码转换为明文
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
			IntfaceTransLog log = new IntfaceTransLog();
			if (cs != null) {
				if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
					txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
					
					String interfacePrimaryKey = intfaceTransLogService.getPrimaryKey();
					log.setInterfacePrimaryKey(interfacePrimaryKey);
					log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
					log.setSettleDate(cs.getSettleDate());// 交易日期
					log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
					log.setTransId(TransCode.MB10.getCode());// 交易类型
					log.setTransSt(0);// 插入时为0，报文返回时更新为1
					log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
					log.setMchntCode(txn.getInnerMerchantNo());
					if(txn.getCardNo() !=null){
						log.setUserInfUserName(txn.getCardNo().substring(1));
					}
					log.setTransAmt(txn.getTxnAmount());//交易金额
					log.setUploadAmt(txn.getOriTxnAmount());// 上送金额
					log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
					if (!StringUtil.isNullOrEmpty(txn.getPinTxn())) {// 密码不为空则转pin
						txn.setPinTxn(HsmUtil.encodePassword(txn.getUserId(), txn.getPinTxn()));
					}
					int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
					if (i != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("merchantWithdrawITF--->insertIntfaceTransLog中间层插入流水记录失败");
						return JSONArray.toJSONString(resp);
					}
					
					txn.setSwtFlowNo(log.getInterfacePrimaryKey());
					BizMessageObj obj = null;
					try{
						obj = txnSendMessageITF.sendMessage(txn);
					} catch (Exception ex) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.error("merchantWithdrawITF--->远程交易没有报文响应：", ex);
					}
					if (obj == null) {
						obj=new BizMessageObj();
						obj.setRespCode(BaseConstants.TXN_TRANS_ERROR);
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("merchantWithdrawITF--->客户端流水号[{}]交易报文没有应答", log.getInterfacePrimaryKey());
						return JSONArray.toJSONString(resp);
					}
					try{
						resp.setInterfacePrimaryKey(interfacePrimaryKey);
						resp.setCode(obj.getRespCode());
						ITFRespCode respCodeEnum=ITFRespCode.findByCode(obj.getRespCode());
						if(respCodeEnum !=null){
							resp.setInfo(respCodeEnum.getValue());
						}else{
							resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						}
						resp.setCardHolderFee(obj.getCardHolderFee());
						log.setTransSt(1);// 插入时为0，报文返回时更新为1
						log.setRespCode(resp.getCode());
						int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
						if (u != 1) {
							resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
							logger.info("merchantWithdrawITF--->中间层更新流水失败,id=[{}]",log.getInterfacePrimaryKey());
							return JSONArray.toJSONString(resp);
						}
						resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
					} catch (Exception ex) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("merchantWithdrawITF--->中间层更新流水失败异常");
					}
				} else {
					resp.setInfo("系统日切中");
					logger.info("merchantWithdrawITF--->日切信息交易允许状态：日切中");
					return JSONArray.toJSONString(resp);
				}
			} else {
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				logger.info("merchantWithdrawITF--->日切信息为空");
				return JSONArray.toJSONString(resp);
			}
		} catch (Exception e) {
			logger.error(BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String transExceptionQueryITF(String wxPrimaryKey) throws Exception {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		try{
			logger.info("Java2TxnBusinessFacadeImpl.transExceptionQueryITF wxPrimaryKey param jsonStr is -------------->" + JSONArray.toJSONString(wxPrimaryKey));
		}catch (Exception e){
			logger.error("Java2TxnBusinessFacadeImpl-->transExceptionQueryITF--> : Exception");
		}
		
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
				/**	Middleware查询接口逻辑*/
				//a)	根据wecard流水号，查询Middleware的流水(tb_intface_trans_log),三种结果
				IntfaceTransLog itfLog = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), wxPrimaryKey);
				//1 没有查到middleware流水：直接返回交易失败
				if (itfLog == null) { 
					resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					return JSONArray.toJSONString(resp);
				//2 查询到middleware流水,并且从核心有返回结果(trans_st!=0,resp_code有返回码)：直接返回结果，返回码为查询到的middleware流水的返回码
				} else if(itfLog.getTransSt() != 0 && !StringUtil.isNullOrEmpty(itfLog.getRespCode())) {
					resp.setInterfacePrimaryKey(itfLog.getInterfacePrimaryKey());
					resp.setCode(itfLog.getRespCode());
					resp.setTransAmt(itfLog.getTransAmt());
					resp.setCardHolderFee(itfLog.getTransFee());
					resp.setInfo(ITFRespCode.findByCode(itfLog.getRespCode()).getValue());
					logger.info("##异常处理，中间层流水号：" + itfLog.getDmsRelatedKey() + "，返回码：" + itfLog.getRespCode());
					return JSONArray.toJSONString(resp);
				}
				//3 查询到流水,并且没有从交易系统返回(trans_st=0,resp_code无返回码)：执行下面的)
				if(0 == itfLog.getTransSt() && StringUtil.isNullOrEmpty(itfLog.getRespCode())) {
					TransLog transLog = intfaceTransLogService.getTransLogByRelatedKey(cs.getCurLogNum(), itfLog.getInterfacePrimaryKey());
					//4 没有查到交易流水：更新middleware流水，并且返回交易失败，错误码为96系统异常
					if(transLog == null){
						intfaceTransLogService.updateIntfaceTransLogRespCode(BaseConstants.RESPONSE_EXCEPTION_CODE, cs.getCurLogNum(), wxPrimaryKey);
						resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						return JSONArray.toJSONString(resp);
					//5 查询到交易流水,并且从核心有返回结果(trans_st!=0，resp_code有返回码)：更新middleware流水，并且返回结果，返回码为查询到的交易系统流水的返回码
					} else if (transLog.getTransSt() != 0 && !StringUtil.isNullOrEmpty(transLog.getRespCode())) {
						intfaceTransLogService.updateIntfaceTransLogRespCode(transLog.getRespCode(), cs.getCurLogNum(), wxPrimaryKey);
						if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(transLog.getRespCode()) && 
								(TransCode.CW11.equals(transLog.getTransId())) || TransCode.CW74.equals(transLog.getTransId())) {
							intfaceTransLogService.updateOriITFLogRespCode(cs.getCurLogNum(), itfLog.getOrgDmsRelatedKey());
						}
						resp.setInterfacePrimaryKey(itfLog.getInterfacePrimaryKey());
						resp.setCode(transLog.getRespCode());
						resp.setTransAmt(transLog.getTransAmt());
						resp.setCardHolderFee(itfLog.getTransFee());
						resp.setInfo(ITFRespCode.findByCode(transLog.getRespCode()).getValue());
						logger.info("##异常处理，中间层流水号：" + itfLog.getDmsRelatedKey() + "，返回码：" + itfLog.getRespCode());
						return JSONArray.toJSONString(resp);
					}
					//6  查询账户流水,并且没有从账户系统返回(trans_st=0,resp_code无返回码)
					if (0 == transLog.getTransSt() && StringUtil.isNullOrEmpty(transLog.getRespCode())) {
						AccountLog accountLog = intfaceTransLogService.getAccountLogByRelatedKey(cs.getCurLogNum(), transLog.getTxnPrimaryKey());
						//7  没有查到账户流水：更新middleware和交易系统流水，并且返回交易失败，错误码为96系统异常
						String respCode = null;
						if (accountLog == null) {
							respCode = BaseConstants.RESPONSE_EXCEPTION_CODE;
							resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						} else {
							respCode = accountLog.getRespCode();
							resp.setTransAmt(itfLog.getTransAmt());
							resp.setCardHolderFee(itfLog.getTransFee());
							resp.setInfo(ITFRespCode.findByCode(accountLog.getRespCode()).getValue());
							logger.info("##异常处理，中间层流水号：" + itfLog.getDmsRelatedKey() + "，返回码：" + itfLog.getRespCode());
						}
						intfaceTransLogService.updateTransLogRespCode(respCode, cs.getCurLogNum(), transLog.getDmsRelatedKey());
						intfaceTransLogService.updateIntfaceTransLogRespCode(respCode, cs.getCurLogNum(),wxPrimaryKey);
						
						// 返回code为00并且交易类型为交易撤销时更新原交易流水
						if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(respCode) && 
								(TransCode.CW11.equals(transLog.getTransId())) || TransCode.CW74.equals(transLog.getTransId())) {
							intfaceTransLogService.updateOriTransLog(cs.getCurLogNum(), transLog.getOrgDmsRelatedKey());
							intfaceTransLogService.updateOriITFLogRespCode(cs.getCurLogNum(), itfLog.getOrgDmsRelatedKey());
						}
						resp.setInterfacePrimaryKey(itfLog.getInterfacePrimaryKey());
						resp.setCode(respCode);
						ITFRespCode respCodeEnum=ITFRespCode.findByCode(respCode);
						if(respCodeEnum !=null){
							resp.setInfo(respCodeEnum.getValue());
						}else{
							resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						}
						return JSONArray.toJSONString(resp);
					}
				}
			} else {
				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
				resp.setInfo("系统日切中");
				logger.info("交易记录查询--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} else {
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			logger.info("交易记录查询--->日切信息为空");
			return JSONArray.toJSONString(resp);
		}
		return null;
	}
	
	/**
	 * 快捷消费交易接口 W710
	 */
	@Override
	public String quickConsumeTransactionITF(TxnPackageBean txn) throws Exception{
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		try{
			logger.info("Java2TxnBusinessFacadeImpl.quickConsumeTransactionITF txn param jsonStr is -------------->" + JSONArray.toJSONString(txn));
		}catch (Exception e){
			logger.error("Java2TxnBusinessFacadeImpl-->quickConsumeTransactionITF--> : Exception");
		}
		
		/****参数校验**/
		if (Java2TxnBusinessValidUtil.quickConsumeTransactionITFVaild(txn,resp)) {
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			return JSONArray.toJSONString(resp);
		}
		// TODO 日切信息改为从缓存中读取!!!
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		IntfaceTransLog log = new IntfaceTransLog();
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				txn.setSwtSettleDate(cs.getSettleDate());// 交易日期
				String interfacePrimaryKey = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(interfacePrimaryKey);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(txn.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW71.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(txn.getIssChannel());// 客户端传过来的机构code 客户端根据商户号查TB_INS_INF的code
				log.setMchntCode(txn.getInnerMerchantNo());
				log.setShopCode(txn.getInnerShopNo());
				if(txn.getCardNo() !=null){
					log.setUserInfUserName(txn.getCardNo().substring(1));
				}
				log.setTransAmt(txn.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(txn.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(txn.getChannel());// 客户端传过来的渠道号
				try {
					int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
					if (i != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("merchantAccountOpeningITF--->insertIntfaceTransLog中间层插入流水记录失败");
						return JSONArray.toJSONString(resp);
					}
				} catch (Exception e) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("merchantAccountOpeningITF--->insertIntfaceTransLog中间层插入流水异常：", e);
					return JSONArray.toJSONString(resp);
				}
				
				txn.setSwtFlowNo(log.getInterfacePrimaryKey());
				BizMessageObj obj = null;
				try{
					obj = txnSendMessageITF.sendMessage(txn);
				} catch (Exception ex) {
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.error("quickConsumeTransactionITF--->快捷消费远程交易没有报文响应：", ex);
					
				}
				if (obj == null) {
					obj=new BizMessageObj();
					obj.setRespCode(BaseConstants.TXN_TRANS_ERROR);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					logger.info("quickConsumeTransactionITF--->客户端流水号[{}]交易报文没有应答", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				try{
					resp.setInterfacePrimaryKey(interfacePrimaryKey);
					resp.setCode(obj.getRespCode());
					ITFRespCode respCodeEnum=ITFRespCode.findByCode(obj.getRespCode());
					if(respCodeEnum !=null){
						resp.setInfo(respCodeEnum.getValue());
					}else{
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					}
					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
					if (u != 1) {
						resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
						logger.info("quickConsumeTransactionITF--->中间层更新流水[{}]失败", log.getInterfacePrimaryKey());
					}
					resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
				} catch (Exception ex) {
					logger.info("quickConsumeTransactionITF--->中间层更新流水异常",ex);
					resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
					return JSONArray.toJSONString(resp);
				}
			} else {
				resp.setInfo("系统日切中");
				logger.info("quickConsumeTransactionITF--->日切信息交易允许状态：日切中");
				return JSONArray.toJSONString(resp);
			}
		} else {
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			logger.info("quickConsumeTransactionITF--->日切信息为空");
			return JSONArray.toJSONString(resp);
		}
		return JSONArray.toJSONString(resp);
	}
}
