package com.cn.thinkx.dubbo.facadeImpl.txn.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.biz.core.model.ImageManager;
import com.cn.thinkx.biz.core.service.CtrlSystemService;
import com.cn.thinkx.biz.core.service.ImageManagerService;
import com.cn.thinkx.biz.core.service.MQProducerService;
import com.cn.thinkx.biz.mchnt.model.CardTransInf;
import com.cn.thinkx.biz.mchnt.model.MchtCommodities;
import com.cn.thinkx.biz.mchnt.model.MerchantInf;
import com.cn.thinkx.biz.mchnt.model.ShopDetailInf;
import com.cn.thinkx.biz.mchnt.model.ShopInf;
import com.cn.thinkx.biz.mchnt.model.ShopListInf;
import com.cn.thinkx.biz.mchnt.service.BizMchtService;
import com.cn.thinkx.biz.salesactivity.service.SalesActivityListService;
import com.cn.thinkx.biz.translog.model.AccountLog;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.biz.translog.model.TransLog;
import com.cn.thinkx.biz.translog.service.IntfaceTransLogService;
import com.cn.thinkx.biz.user.model.CustomerAccount;
import com.cn.thinkx.biz.user.model.PersonInf;
import com.cn.thinkx.biz.user.model.UserInf;
import com.cn.thinkx.biz.user.model.UserMerchantAcct;
import com.cn.thinkx.biz.user.service.BizUserService;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.dubbo.entity.TxnResp;
import com.cn.thinkx.dubbo.facadeImpl.txn.utils.HKBTxnUtil;
import com.cn.thinkx.dubbo.facadeImpl.txn.valid.HKBTxnReqValid;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.bean.CardTransDetailQueryRequest;
import com.cn.thinkx.facade.bean.CusAccListQueryRequest;
import com.cn.thinkx.facade.bean.CusAccOpeningRequest;
import com.cn.thinkx.facade.bean.CusAccQueryRequest;
import com.cn.thinkx.facade.bean.CusCardOpeningRequest;
import com.cn.thinkx.facade.bean.CustomerBuyCardStocksRequest;
import com.cn.thinkx.facade.bean.MchntInfQueryRequest;
import com.cn.thinkx.facade.bean.RechargeTransRequest;
import com.cn.thinkx.facade.bean.ShopInfQueryRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.itf.service.TxnSendMessageITF;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.GeoConstants.GeoConstantsEnum;
import com.cn.thinkx.pms.base.utils.GeoHash;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.service.wecard.WecardWXService;
import com.github.pagehelper.PageInfo;

@Service("HKBTxnFacade")
public class HKBTxnFacadeImpl implements HKBTxnFacade {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("txnSendMessageITF")
	private TxnSendMessageITF txnSendMessageITF;
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;
	
	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;
	
	@Autowired
	@Qualifier("intfaceTransLogService")
	private IntfaceTransLogService intfaceTransLogService;
	
	@Autowired
	@Qualifier("bizUserService")
	private BizUserService bizUserService;
	
	@Autowired
	@Qualifier("bizMchtService")
	private BizMchtService bizMchtService;
	
	@Autowired
	@Qualifier("mqProducerService")
	private MQProducerService mqProducerService;
	
    @Qualifier("wecardWXService")
	private WecardWXService wecardWXService;
	
	@Autowired
	@Qualifier("salesActivityListService")
	private SalesActivityListService salesActivityListService;
	
	public String customerAccountOpeningITF(CusAccOpeningRequest req) {
		logger.info("客户开户请求参数：[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.cusAccOpeningValid(req, resp)) 
			return JSONArray.toJSONString(resp);
		
		try {
			UserInf user = bizUserService.getUserInfByUserName(req.getUserId(), req.getChannel());
			if (user != null) {
				resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
				return JSONArray.toJSONString(resp);
			}
			resp = bizUserService.addPersonInf(req);
			return JSONArray.toJSONString(resp);
		} catch (Exception e) {
			logger.error("## 客户开户异常", e);
		}
		return JSONArray.toJSONString(resp);
	}	
	
	@Override
	public String customerCardOpeningITF(CusCardOpeningRequest req) {
		logger.info("客户开卡请求参数：[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.cusCardOpeningValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		/**检查用户是否开卡*/
		try {
			UserMerchantAcct acc = new UserMerchantAcct();
			acc.setExternalId(req.getUserId());  //外部渠道ID
			acc.setChannelCode(req.getChannel()); //外部渠道号
			acc.setMchntCode(req.getInnerMerchantNo());
			String cardNo = bizUserService.getCardNo(acc);
			if (!StringUtil.isNullOrEmpty(cardNo)) {
				resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
				resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
				return JSONArray.toJSONString(resp);
			}
		} catch (Exception e) {
			logger.error("## 客户开卡异常", e);
		}
		
		try {
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();//TODO 得到日切信息
			if (cs == null) {
				logger.error("## 客户开卡--->日切信息为空");
				return JSONArray.toJSONString(resp);
			}
			// 检查外部流水号swtFlowNo是否有重复记录
			IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLog(cs.getCurLogNum(), req.getUserId(), 
					req.getInnerMerchantNo());
			if (log != null && BaseConstants.RESPONSE_SUCCESS_CODE.equals(log.getRespCode())) {
				logger.error("## 客户开卡--->客户端流水号[{}]已有成功开卡流水记录", req.getUserId());
				resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
				return JSONArray.toJSONString(resp);
			} else {
				log = new IntfaceTransLog();
			}
			
			MerchantInf merInf = bizMchtService.getMerchantInfByCode(req.getInnerMerchantNo());
			if (merInf == null || StringUtil.isNullOrEmpty(merInf.getInsCode())) {
				logger.error("## 客户开卡--->客户端商户号[{}]没有记录或者机构号为空", req.getInnerMerchantNo());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			req.setIssChannel(merInf.getInsCode());// 机构号
			
			String productNo = bizMchtService.getProCodeByMchntCode(req.getInnerMerchantNo());
			if (StringUtil.isNullOrEmpty(productNo)) {
				logger.error("## 客户开卡--->客户端商户号[{}]没有产品", req.getInnerMerchantNo());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			req.setCardNo(productNo);
			
			PersonInf personInf = bizUserService.getPersonInfByUserName(req.getUserId(),req.getChannel());
			if (personInf == null || StringUtil.isNullOrEmpty(personInf.getPersonalId())) {
				logger.error("## 客户开卡--->客户用户ID[{}]没有personInf记录", req.getUserId());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			req.setResv3(personInf.getUserId());
			req.setResv4(personInf.getPersonalId());
			
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(req.getUserId());// 客户端传过来的流水号
				log.setTransId(TransCode.CW80.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(req.getIssChannel());
				log.setMchntCode(req.getInnerMerchantNo());
				log.setShopCode(req.getInnerShopNo());
				log.setUserInfUserName(req.getCardNo());
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(req.getChannel());// 客户端传过来的渠道号
				int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
				if (i != 1) {
					logger.error("## 客户开卡--->insertIntfaceTransLog中间层插入流水记录数量不为1");
					return JSONArray.toJSONString(resp);
				}
				
				BizMessageObj obj = txnSendMessageITF.sendMessage(HKBTxnUtil.customerCardOpening(req, id));
				
				// 报文没有应答
				if (obj == null) {
					logger.error("## 客户开卡--->客户端流水号[{}]交易报文没有应答", req.getSwtFlowNo());
					resp.setCode(BaseConstants.TXN_TRANS_ERROR);
					return JSONArray.toJSONString(resp);
				}
				resp.setCode(obj.getRespCode());
				resp.setInfo(ITFRespCode.findByCode(obj.getRespCode()).getValue());
				if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(obj.getRespCode()))
					logger.error("## 客户开卡--->客户端流水号[{}]" + resp.getInfo(), req.getUserId());
				
				log.setTransSt(1);// 插入时为0，报文返回时更新为1
				log.setRespCode(obj.getRespCode());
				int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
				if (u != 1) {
					logger.error("## 客户开卡--->insertIntfaceTransLog更新流水失败，id[{}]", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
			} else {
				logger.error("## 客户开卡--->日切信息交易允许状态：日切中");
			}
		} catch (Exception e) {
			logger.error("## 客户开卡--->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String customerHKBCardOpeningITF(CusCardOpeningRequest req) {
		logger.info("客户通卡开卡请求参数：[{}]", JSONArray.toJSONString(req));
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		
		// 参数校验
		if (HKBTxnReqValid.cusHKBCardOpeningValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		req.setInnerMerchantNo(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_MCHNT_NO"));
		req.setIssChannel(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_INS_CODE"));
		req.setCardNo(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_PROD_NO"));
		
		/**检查用户是否开通卡*/
		try {
			UserMerchantAcct acc = new UserMerchantAcct();
			acc.setExternalId(req.getUserId());  //外部渠道ID
			acc.setChannelCode(req.getChannel()); //外部渠道号
			acc.setMchntCode(req.getInnerMerchantNo());
			String cardNo = bizUserService.getCardNo(acc);
			if (!StringUtil.isNullOrEmpty(cardNo)) {
				resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
				resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
				return JSONArray.toJSONString(resp);
			}
		} catch (Exception e) {
			logger.error("## 客户通卡开卡异常", e);
		}
		
		try {
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();//TODO 得到日切信息
			if (cs == null) {
				logger.error("## 客户通卡开卡--->日切信息为空");
				return JSONArray.toJSONString(resp);
			}
			// 检查外部流水号swtFlowNo是否有重复记录
			IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLog(cs.getCurLogNum(), req.getUserId(), 
					req.getInnerMerchantNo());
			if (log != null && BaseConstants.RESPONSE_SUCCESS_CODE.equals(log.getRespCode())) {
				logger.error("## 客户通卡开卡--->客户端流水号[{}]已有成功开卡流水记录", req.getUserId());
				resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
				return JSONArray.toJSONString(resp);
			} else {
				log = new IntfaceTransLog();
			}
			
			MerchantInf merInf = bizMchtService.getMerchantInfByCode(req.getInnerMerchantNo());
			if (merInf == null || StringUtil.isNullOrEmpty(merInf.getInsCode())) {
				logger.error("## 客户通卡开卡--->客户端商户号[{}]没有记录或者机构号为空", req.getInnerMerchantNo());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			
			PersonInf personInf = bizUserService.getPersonInfByUserName(req.getUserId(),req.getChannel());
			if (personInf == null || StringUtil.isNullOrEmpty(personInf.getPersonalId())) {
				logger.error("## 客户通卡开卡--->客户用户ID[{}]没有personInf记录", req.getUserId());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			req.setResv3(personInf.getUserId());
			req.setResv4(personInf.getPersonalId());
			
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(req.getUserId());// 客户端传过来的流水号
				log.setTransId(TransCode.CW80.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(req.getIssChannel());
				log.setMchntCode(req.getInnerMerchantNo());
				log.setShopCode(req.getInnerShopNo());
				log.setUserInfUserName(req.getCardNo());
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(req.getChannel());// 客户端传过来的渠道号
				int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
				if (i != 1) {
					logger.error("## 客户通卡开卡--->insertIntfaceTransLog中间层插入流水记录数量不为1");
					return JSONArray.toJSONString(resp);
				}
				
				BizMessageObj obj = txnSendMessageITF.sendMessage(HKBTxnUtil.customerCardOpening(req, id));
				
				// 报文没有应答
				if (obj == null) {
					logger.error("## 客户通卡开卡--->客户端流水号[{}]交易报文没有应答", req.getSwtFlowNo());
					resp.setCode(BaseConstants.TXN_TRANS_ERROR);
					return JSONArray.toJSONString(resp);
				}
				resp.setCode(obj.getRespCode());
				resp.setInfo(ITFRespCode.findByCode(obj.getRespCode()).getValue());
				if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(obj.getRespCode()))
					logger.error("## 客户通卡开卡--->客户端流水号[{}]:{}", req.getUserId(), resp.getInfo());
				
				log.setTransSt(1);// 插入时为0，报文返回时更新为1
				log.setRespCode(obj.getRespCode());
				int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
				if (u != 1) {
					logger.error("## 客户通卡开卡--->insertIntfaceTransLog更新流水失败，id[{}]", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				resp.setTransAmt(log.getTransAmt());// 往客户端返回实际交易金额
			} else {
				logger.error("## 客户通卡开卡--->日切信息交易允许状态：日切中");
			}
		} catch (Exception e) {
			logger.error("## 客户通卡开卡异常--->", e);
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String customerAccountQueryITF(CusAccQueryRequest req) {
		logger.info("客户账户查询请求参数：[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
		resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		// 参数校验
		if (HKBTxnReqValid.cusAccQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		try {
			String userId = bizUserService.getUserIdByUserName(req.getUserId(),req.getChannel());
			if (StringUtil.isNullOrEmpty(userId)) {
				resp.setAccountFlag("0");// 未开户
				resp.setCardFlag("0");// 未开卡
			} else {
				UserMerchantAcct acc = new UserMerchantAcct();
				acc.setExternalId(req.getUserId());  //外部渠道ID
				acc.setChannelCode(req.getChannel()); //外部渠道号
				acc.setMchntCode(req.getInnerMerchantNo());
				String cardNo = bizUserService.getCardNo(acc);
				if (StringUtil.isNullOrEmpty(cardNo)) {
					resp.setAccountFlag("1");// 已开户
					resp.setCardFlag("0");// 未开卡
				} else {
					resp.setAccountFlag("1");// 已开户
					resp.setCardFlag("1");// 已开卡
				}
			}
		} catch (Exception e) {
			logger.error("## 客户账户查询异常", e);
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		}
		return JSONArray.toJSONString(resp);
	}	
	
	@Override
	public String rechargeTransactionITF(RechargeTransRequest req) {
		logger.info("充值交易请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.rechargeTransValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		try {
			//当前充值购卡的用户Id查询
			String userId = bizUserService.getUserIdByUserName(req.getCardNo(), req.getChannel());
			if (StringUtil.isNullOrEmpty(userId)) {
				logger.error("## 客户端流水号[{}]：用户表中没查找到当前用户", req.getSwtFlowNo());
				resp.setInfo("当前用户查询失败");
				return JSONArray.toJSONString(resp);
			}
			// 售卡充值检查实际交易金额与买卡金额是否一致
			String productCode = "";
			if (!StringUtil.isNullOrEmpty(req.getCommodityCode()) && !StringUtil.isNullOrEmpty(req.getCommodityNum())) {
				MchtCommodities mc = new MchtCommodities();
				mc.setCommodityCode(req.getCommodityCode());
				mc.setMchntCode(req.getInnerMerchantNo());
				mc = bizMchtService.getSellingCard(mc);
				
				if (mc == null || StringUtil.isNullOrEmpty(mc.getCommodityPrice())) {
					logger.error("## 客户端流水号[{}]：活动表中没有找到对应商品记录", req.getSwtFlowNo());
					return JSONArray.toJSONString(resp);
				}
				productCode = mc.getProductCode();
				int commPrice = Integer.parseInt(mc.getCommodityPrice()) * Integer.parseInt(req.getCommodityNum());
				int oriTxnAmount = Integer.parseInt(req.getOriTxnAmount());
				if (commPrice != oriTxnAmount) {// 价格不一致
					logger.error("## 客户端流水号[{}]：实际交易金额[{}]与购卡总金额[{}]不一致" , req.getSwtFlowNo(), oriTxnAmount, commPrice);
					resp.setInfo(BaseConstants.RESPONSE_COMMAMOUNT_ERROR);
					return JSONArray.toJSONString(resp);
				}
			}
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
			if (cs == null) {
				logger.error("## 充值交易--->日切信息为空");
				return JSONArray.toJSONString(resp);
			}
			// 检查外部流水号swtFlowNo是否有重复记录
			IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), req.getSwtFlowNo());
			if (log != null) {
				logger.error("## 充值交易--->客户端流水号[{}]已有充值流水记录", req.getSwtFlowNo());
				resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
				return JSONArray.toJSONString(resp);
			} else {
				log = new IntfaceTransLog();
			}
			
			MerchantInf merInf = bizMchtService.getMerchantInfByCode(req.getInnerMerchantNo());
			if (merInf == null || StringUtil.isNullOrEmpty(merInf.getInsCode())) {
				logger.error("## 充值交易--->客户端商户号[{}]没有记录或者机构号为空", req.getInnerMerchantNo());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			req.setIssChannel(merInf.getInsCode());// 机构号
			
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(req.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW20.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(req.getIssChannel());
				log.setMchntCode(req.getInnerMerchantNo());
				log.setShopCode(req.getInnerShopNo());
				log.setUserInfUserName(req.getCardNo());
				log.setTransAmt(req.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(req.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(req.getChannel());// 客户端传过来的渠道号
				log.setProductCode(productCode);
				int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
				if (i != 1) {
					logger.error("## 充值交易--->insertIntfaceTransLog中间层插入流水记录数量不为1");
					return JSONArray.toJSONString(resp);
				}
				// 发送报文
				BizMessageObj obj = txnSendMessageITF.sendMessage(HKBTxnUtil.rechargeTrans(req, id));
				// 报文没有应答
				if (obj == null) {
					logger.error("## 充值交易--->客户端流水号[{}]交易报文没有应答", req.getSwtFlowNo());
					resp.setCode(BaseConstants.TXN_TRANS_ERROR);
					return JSONArray.toJSONString(resp);
				}
				resp.setCode(obj.getRespCode());
				resp.setInfo(ITFRespCode.findByCode(obj.getRespCode()).getValue());
				if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(obj.getRespCode())){
					logger.error("## 充值交易--->客户端流水号[{}]" + resp.getInfo(), req.getSwtFlowNo());
				}
				log.setTransSt(1);// 插入时为0，报文返回时更新为1
				log.setRespCode(obj.getRespCode());
				int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
				if (u != 1) {
					logger.error("## 充值交易--->insertIntfaceTransLog更新流水失败，id[{}]", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				resp.setSwtFlowNo(req.getSwtFlowNo());// 返回原交易流水
				if (StringUtil.trim(obj.getBalance()).length() > 11) 
					resp.setBalance(HKBTxnUtil.transMsgAmount(StringUtil.trim(obj.getBalance()), 0, 12));// 返回余额
				resp.setTxnFlowNo(obj.getSwtFlowNo());// 返回中间层交易流水
				resp.setSettleDate(obj.getSwtSettleDate());// 返回中间层交易日期
				
				if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(obj.getRespCode()) && 
						(BaseConstants.ChannelCode.CHANNEL1.toString().equals(req.getChannel()) || 
								BaseConstants.ChannelCode.CHANNEL2.toString().equals(req.getChannel()) || 
								BaseConstants.ChannelCode.CHANNEL4.toString().equals(req.getChannel()))) {
					//充值送活动
					salesActivityListService.doRechargeSpecialActivity(req, userId, log);
				}
				
			} else {
				logger.error("## 充值交易--->日切信息交易允许状态：日切中");
			}
		} catch (Exception e) {
			logger.error("## 充值交易异常", e);
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String cardBalanceQueryITF(CardBalQueryRequest req) throws Exception {
		logger.info("会员卡余额查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.cardBalQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		try {
			UserMerchantAcct acc = new UserMerchantAcct();
			acc.setExternalId(req.getUserId());  //外部渠道ID
			acc.setChannelCode(req.getChannel()); //外部渠道号
			acc.setMchntCode(req.getInnerMerchantNo());
			String balance = bizUserService.getAccBalance(acc);
			
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
			resp.setBalance(balance);
		} catch (Exception e) {
			logger.error("## 会员卡余额查询查询VIEW_USER_MERCHANT_ACCT视图失败", e);
		}
		return JSONArray.toJSONString(resp);
	}

	/**
	 * 消费交易  暂时不做用户体系校验
	 */
	public String hkbConsumeTransactionITF(BaseTxnReq req) {
		logger.info("会员卡消费交易请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.hkbConsumeTransValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		try {
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();//TODO 得到日切信息
			if (cs == null) {
				logger.error("## 会员卡消费交易--->日切信息为空");
				return JSONArray.toJSONString(resp);
			}
			// 检查外部流水号swtFlowNo是否有重复记录
			IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), req.getSwtFlowNo());
			if (log != null) {
				logger.error("## 会员卡消费交易--->客户端流水号[{}]已有流水记录", req.getSwtFlowNo());
				resp.setInfo(BaseConstants.RESPONSE_REPEAT_TRANS);
				return JSONArray.toJSONString(resp);
			} else {
				log = new IntfaceTransLog();
			}
			
			MerchantInf merInf = bizMchtService.getMerchantInfByCode(req.getInnerMerchantNo());
			if (merInf == null || StringUtil.isNullOrEmpty(merInf.getInsCode())) {
				logger.error("## 会员卡消费交易--->客户端商户号[{}]没有记录或者机构号为空", req.getInnerMerchantNo());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			req.setIssChannel(merInf.getInsCode());// 机构号
			
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(req.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW10.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(req.getIssChannel());
				log.setMchntCode(req.getInnerMerchantNo());
				log.setShopCode(req.getInnerShopNo());
				log.setUserInfUserName(req.getCardNo());
				log.setTransAmt(req.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(req.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(req.getChannel());// 客户端传过来的渠道号
				int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
				if (i != 1) {
					logger.error("## 会员卡消费交易--->insertIntfaceTransLog中间层插入流水记录数量不为1");
					return JSONArray.toJSONString(resp);
				}
				/*if (!StringUtil.isNullOrEmpty(req.getPinTxn())) {// 密码不为空则转pin
					req.setPinTxn(HsmUtil.encodePassword(req.getUserId(), req.getPinTxn()));
				}*/
				
				BizMessageObj obj = txnSendMessageITF.sendMessage(HKBTxnUtil.hkbConsumeTrans(req, id));
				// 报文没有应答
				if (obj == null) {
					logger.error("## 会员卡消费交易--->客户端流水号[{}]交易报文没有应答", req.getSwtFlowNo());
					resp.setCode(BaseConstants.TXN_TRANS_ERROR);
					return JSONArray.toJSONString(resp);
				}
				resp.setCode(obj.getRespCode());
				resp.setInfo(ITFRespCode.findByCode(obj.getRespCode()).getValue());

				log.setTransSt(1);// 插入时为0，报文返回时更新为1
				log.setRespCode(obj.getRespCode());
				int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
				if (u != 1) {
					logger.error("## 会员卡消费交易--->insertIntfaceTransLog更新流水失败，id[{}]", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				
				if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(obj.getRespCode())) {
					logger.error("## 会员卡消费交易失败--->客户端流水号[{}] 交易返回[{}]", req.getSwtFlowNo(), resp.getInfo());
					return JSONArray.toJSONString(resp);
				}
				
				// 发送客服消息
				try {
					String txnTime = DateUtil.getFullFormatStr(req.getSwtTxnDate() + req.getSwtTxnTime());
					String userId = bizUserService.getUserIdByUserName(log.getUserInfUserName(), req.getChannel());
					String phoneNo = bizUserService.getPhoneNoByUserId(userId);
					String phoneNoLast4 = phoneNo == null ? "无" : StringUtil.getPhoneNumberFormatLast4(phoneNo);
					
					boolean flag = mqProducerService.sendCustomTextMsg(TransCode.CW10.getCode(), 
							log.getInterfacePrimaryKey(), txnTime, req.getInnerMerchantNo(), req.getInnerShopNo(), 
							req.getTxnAmount(), null, phoneNoLast4);
					if (!flag) 
						logger.error("## 会员卡消费交易--->发送客服消息失败，流水id[{}]", log.getInterfacePrimaryKey());
					
				} catch (Exception e) {
					logger.error("## 会员卡消费支付交易--->发送客服消息失败，流水id[{}]", log.getInterfacePrimaryKey());
				}
				
				resp.setSwtFlowNo(req.getSwtFlowNo());// 返回原交易流水
				if (StringUtil.trim(obj.getBalance()).length() > 11) 
					resp.setBalance(HKBTxnUtil.transMsgAmount(StringUtil.trim(obj.getBalance()), 0, 12));
				
				resp.setTransAmt(StringUtil.trim(obj.getTxnAmount()));// 返回实际交易金额
				resp.setOriTxnAmount(StringUtil.trim(obj.getOriTxnAmount()));// 返回活动前交易金额
				resp.setTxnFlowNo(StringUtil.trim(obj.getSwtFlowNo()));// 返回中间层交易流水
				resp.setSettleDate(StringUtil.trim(obj.getSwtSettleDate()));// 返回中间层交易日期
			} else {
				logger.error("## 会员卡消费交易--->日切信息交易允许状态：日切中");
			}
		} catch (Exception e) {
			logger.error("## 会员卡消费交易异常", e);
		}
		return JSONArray.toJSONString(resp);
	}
	
	/**
	 * 快捷消费交易  暂时不做用户体系校验
	 */
	public String quickPaymentTransactionITF(BaseTxnReq req) {
		logger.info("快捷消费交易请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.quickPayTransValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		try {
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();//TODO 得到日切信息
			if (cs == null) {
				logger.error("## 快捷支付交易--->日切信息为空");
				return JSONArray.toJSONString(resp);
			}
			// 检查外部流水号swtFlowNo是否有重复记录
			IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), req.getSwtFlowNo());
			if (log != null) {
				logger.error("## 快捷支付交易--->客户端流水号[{}]已有流水记录", req.getSwtFlowNo());
				return JSONArray.toJSONString(resp);
			} else {
				log = new IntfaceTransLog();
			}
			
			MerchantInf merInf = bizMchtService.getMerchantInfByCode(req.getInnerMerchantNo());
			if (merInf == null || StringUtil.isNullOrEmpty(merInf.getInsCode())) {
				logger.error("## 快捷支付交易--->客户端商户号[{}]没有记录或者机构号为空", req.getInnerMerchantNo());
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				return JSONArray.toJSONString(resp);
			}
			req.setIssChannel(merInf.getInsCode());// 机构号
			
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入中间层流水
				String id = intfaceTransLogService.getPrimaryKey();
				log.setInterfacePrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setDmsRelatedKey(req.getSwtFlowNo());// 客户端传过来的流水号
				log.setTransId(TransCode.CW71.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(req.getIssChannel());
				log.setMchntCode(req.getInnerMerchantNo());
				log.setShopCode(req.getInnerShopNo());
				log.setUserInfUserName(req.getCardNo());
				log.setTransAmt(req.getTxnAmount());// 规则引擎处理后的实际交易金额
				log.setUploadAmt(req.getOriTxnAmount());// 上送金额
				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
				log.setTransChnl(req.getChannel());// 客户端传过来的渠道号
				int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
				if (i != 1) {
					logger.error("## 快捷支付交易--->insertIntfaceTransLog中间层插入流水记录数量不为1");
					return JSONArray.toJSONString(resp);
				}
				
				BizMessageObj obj = txnSendMessageITF.sendMessage(HKBTxnUtil.quickPaymentTrans(req, id));
				// 报文没有应答
				if (obj == null) {
					logger.error("## 快捷支付交易--->客户端流水号[{}]交易报文没有应答", req.getSwtFlowNo());
					resp.setCode(BaseConstants.TXN_TRANS_ERROR);
					return JSONArray.toJSONString(resp);
				}
				resp.setCode(obj.getRespCode());
				resp.setInfo(ITFRespCode.findByCode(obj.getRespCode()).getValue());
				
				log.setTransSt(1);// 插入时为0，报文返回时更新为1
				log.setRespCode(obj.getRespCode());
				int u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
				if (u != 1) {
					logger.error("## 快捷支付交易--->insertIntfaceTransLog更新流水失败，id[{}]", log.getInterfacePrimaryKey());
					return JSONArray.toJSONString(resp);
				}
				
				if (!BaseConstants.RESPONSE_SUCCESS_CODE.equals(obj.getRespCode())) {
					logger.error("## 快捷消费交易失败--->客户端流水号[{}] 交易返回[{}]", req.getSwtFlowNo(), resp.getInfo());
					return JSONArray.toJSONString(resp);
				}
				try {
					// 发送客服消息
					String txnTime = DateUtil.getFullFormatStr(req.getSwtTxnDate() + req.getSwtTxnTime());
					String userId = bizUserService.getUserIdByUserName(log.getUserInfUserName(), req.getChannel());
					String phoneNo = bizUserService.getPhoneNoByUserId(userId);
					String phoneNoLast4 = phoneNo == null ? "无" : StringUtil.getPhoneNumberFormatLast4(phoneNo);
					
					boolean flag = mqProducerService.sendCustomTextMsg(TransCode.CW71.getCode(), 
							log.getInterfacePrimaryKey(), txnTime, req.getInnerMerchantNo(), req.getInnerShopNo(), 
							req.getTxnAmount(), null, phoneNoLast4);
					if (!flag)
						logger.error("## 快捷支付交易--->发送客服消息失败，id[{}]", log.getInterfacePrimaryKey());
					
				} catch (Exception e) {
					logger.error("## 快捷支付交易--->发送客服消息失败，id[{}]", log.getInterfacePrimaryKey());
				}
				resp.setSwtFlowNo(req.getSwtFlowNo());// 返回原交易流水
				if (StringUtil.trim(obj.getBalance()).length() > 11) 
					resp.setBalance(HKBTxnUtil.transMsgAmount(StringUtil.trim(obj.getBalance()), 0, 12));// 返回余额
				
				resp.setTransAmt(StringUtil.trim(obj.getTxnAmount()));// 返回实际交易金额
				resp.setOriTxnAmount(StringUtil.trim(obj.getOriTxnAmount()));// 返回活动前交易金额
				resp.setTxnFlowNo(StringUtil.trim(obj.getSwtFlowNo()));// 返回中间层交易流水
				resp.setSettleDate(StringUtil.trim(obj.getSwtSettleDate()));// 返回中间层交易日期
			} else {
				logger.error("## 快捷支付交易--->日切信息交易允许状态：日切中");
			}
		} catch (Exception e) {
			logger.error("## 快捷支付交易异常--->", e);
		}
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String mchtSellingCardListQueryITF(BaseTxnReq req) throws Exception {
		logger.info("商户在售卡列表查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.mchtSelListQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		try {
			List<MchtCommodities> list = bizMchtService.getSellingCardList(req.getInnerMerchantNo());
			
			String [] activityBodys = null;
			if (list != null && list.size() > 0) {
				activityBodys=new String[list.size()];
				
				for( int i=0;i <list.size();i++ ){
					activityBodys[i]=list.get(i).getCommodityCode();
				}
				ImageManager im = new ImageManager();
				im.setApplicationId(list.get(0).getProductCode());
				im.setApplication(BaseConstants.Application.APP_PROD.getCode());
				im.setApplicationType(BaseConstants.AppType.A3001.getCode());
				List<String> urlList = imageManagerService.getImagesUrl(im);
				if (urlList != null && urlList.size() > 0) {
					resp.setProductImage(urlList.get(0));// 产品卡面目前只有一张
				} else {
					resp.setProductImage(null);
				}
				urlList = null;// 释放内存
			}
			resp.setCardList(list);
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch (Exception e) {
			logger.error("## 商户在售卡列表查询异常", e);
		}
		return JSONArray.toJSONString(resp);
	}
	

	@Override
	public String customerAccListQueryITF(CusAccListQueryRequest req) throws Exception {
		logger.info("客户会员卡列表查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.cusAccListQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		UserMerchantAcct acc = new UserMerchantAcct();
		acc.setExternalId(req.getUserId());  //外部渠道ID
		acc.setChannelCode(req.getChannel());
		acc.setMchntCode(req.getInnerMerchantNo());
		acc.setRemarks(req.getRemarks());
		try {
			List<CustomerAccount> list = bizUserService.getCusAccList(acc);
			resp.setProductList(list);
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch(Exception e) {
			logger.error("## 客户会员卡列表查询异常", e);
		}
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String shopInfoQueryITF(ShopInfQueryRequest req) throws Exception {
		logger.info("门店明细查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.shopInfoQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		if (StringUtil.isNullOrEmpty(req.getDetailFlag()))
			req.setDetailFlag("0");// 默认不查询明细
		
		ShopInf shop = new ShopInf();
		shop.setMchntCode(req.getInnerMerchantNo());
		shop.setShopCode(req.getInnerShopNo());
		shop.setDetailFlag(req.getDetailFlag());
		
		try {
			ShopDetailInf detail = null;
			if ("1".equals(req.getDetailFlag())) {
				detail = bizMchtService.getShopDetailInfo(shop);
			} else {
				detail = bizMchtService.getShopSimpleInfo(shop);
			}
			if (detail == null)
				detail = new ShopDetailInf();
			
			ImageManager im = new ImageManager();
			im.setApplicationId(detail.getShopCode());
			im.setApplication(BaseConstants.Application.APP_SHOP.getCode());
			im.setApplicationType(BaseConstants.AppType.A2001.getCode());
			List<String> urlList = imageManagerService.getImagesUrl(im);
			if (urlList != null && urlList.size() > 0) {
				detail.setBrandLogo(urlList.get(0));// 商户LOGO目前只有一张
			} else {
				detail.setBrandLogo(null);
			}
			urlList = null;// 释放内存
			resp.setShopInfo(detail);
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch(Exception e) {
			logger.error("## 门店明细查询异常", e);
		}	
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String shopListQueryITF(ShopInfQueryRequest req) throws Exception {
		logger.info("门店列表查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.shopListQueryITF(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		ShopInf shop = new ShopInf();
		try{
			shop.setLatitude(req.getLatitude());
			shop.setLongitude(req.getLongitude());
			shop.setDistance(req.getDistance());
			
			//根据传递的距离查询获取geohash 生成的长度
			int geoLength = GeoConstantsEnum.findEnumByDistance(Integer.parseInt(req.getDistance())).getGeoLength();
			GeoHash g = new GeoHash(Double.parseDouble(req.getLatitude()), Double.parseDouble(req.getLongitude()));
			g.sethashLength(geoLength);
	    	String[] geohashs = (String[]) g.getGeoHashBase32For9().toArray(new String[g.getGeoHashBase32For9().size()]);
			shop.setGeohashs(geohashs);
			shop.setGeoLength(geoLength);
			shop.setSort(req.getSort());
			shop.setIndustryType(req.getIndustryType());
			
			int startNum = StringUtil.parseInt(req.getPageNum(), 1);
			int pageSize = StringUtil.parseInt(req.getItemSize(), 10);
	
			PageInfo<ShopListInf> pageList = bizMchtService.getShopListInfoPage(startNum, pageSize, shop);
			
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
			resp.setShopList(pageList.getList());
			resp.setCurrPageSize(String.valueOf(pageList.getPageNum()));
			resp.setPageSize(String.valueOf(pageList.getPages()));
		} catch(Exception e) {
			logger.error("## 门店列表查询异常", e);
		}	
		
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String merchantInfoQueryITF(MchntInfQueryRequest req) throws Exception {
		logger.info("商户明细查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.merchantInfoQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		try {
			MerchantInf detail = bizMchtService.getMerchantInfByCode(req.getInnerMerchantNo());
			if (detail == null) {
				detail = new MerchantInf();
			} else {
				ImageManager im = new ImageManager();
				im.setApplicationId(detail.getMchntCode());
				im.setApplication(BaseConstants.Application.APP_MCHNT.getCode());
				im.setApplicationType(BaseConstants.AppType.A1001.getCode());
				List<String> urlList = imageManagerService.getImagesUrl(im);
				if (urlList != null && urlList.size() > 0) {
					detail.setBrandLogo(urlList.get(0));// 商户LOGO目前只有一张
				} else {
					detail.setBrandLogo(null);
				}
				urlList = null;// 释放内存
			}
			resp.setMerchantInfo(detail);
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch(Exception e) {
			logger.error("## 商户明细查询异常", e);
		}	
		
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String cardTransDetailQueryITF(CardTransDetailQueryRequest req) throws Exception {
		logger.info("会员卡交易明细查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.cardTransDetailQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		try {
			UserMerchantAcct acc = new UserMerchantAcct();
			acc.setExternalId(req.getUserId());
			acc.setMchntCode(req.getInnerMerchantNo());
			acc.setChannelCode(req.getChannel());
			//查找用户主账户号
			String accountNo = bizUserService.getAccountNoByExternalId(acc);
			
			acc.setAccountNo(accountNo);
			int startNum = StringUtil.parseInt(req.getPageNum(), 1);
			int pageSize = StringUtil.parseInt(req.getItemSize(), 10);
	
			PageInfo<CardTransInf> pageList = bizMchtService.getCardTransDetailListPage(startNum, pageSize, acc);
			
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
			resp.setTransList(pageList.getList());
			resp.setCurrPageSize(String.valueOf(pageList.getPageNum()));
			resp.setPageSize(String.valueOf(pageList.getPages()));
		} catch(Exception e) {
			logger.error("## 会员卡交易明细查询异常", e);
		}	
		
		return JSONArray.toJSONString(resp);
	}

	@Override
	public String transExceptionQueryITF(BaseTxnReq req) throws Exception {
		logger.info("交易异常查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.transExceptionQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs == null) {
			logger.error("## 交易记录查询--->日切信息为空");
			return JSONArray.toJSONString(resp);
		} 
		if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
			/**	Middleware查询接口逻辑*/
			//a)	根据wecard流水号，查询Middleware的流水(tb_intface_trans_log),三种结果
			IntfaceTransLog itfLog = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), req.getSwtFlowNo());
			//1 没有查到middleware流水：直接返回交易失败
			if (itfLog == null) { 
				return JSONArray.toJSONString(resp);
			//2 查询到middleware流水,并且从核心有返回结果(trans_st!=0,resp_code有返回码)：直接返回结果，返回码为查询到的middleware流水的返回码
			} else if(itfLog.getTransSt() != 0 && !StringUtil.isNullOrEmpty(itfLog.getRespCode())) {
				resp.setCode(itfLog.getRespCode());
				resp.setInfo(ITFRespCode.findByCode(itfLog.getRespCode()).getValue());
				resp.setTransAmt(itfLog.getTransAmt());
				resp.setCardHolderFee(itfLog.getTransFee());
				resp.setTxnFlowNo(itfLog.getInterfacePrimaryKey());// 中间层交易流水号
				resp.setSwtFlowNo(itfLog.getDmsRelatedKey());// 原交易流水号
				logger.info("##2 异常处理，中间层流水号[{}]，返回码[{}]", itfLog.getDmsRelatedKey(), itfLog.getRespCode());
				return JSONArray.toJSONString(resp);
			} else {
				resp.setTxnFlowNo(itfLog.getInterfacePrimaryKey());// 中间层交易流水号
				resp.setSwtFlowNo(itfLog.getDmsRelatedKey());// 原交易流水号
			}
			//3 查询到流水,并且没有从交易系统返回(trans_st=0,resp_code无返回码)：执行下面的)
			if (0 == itfLog.getTransSt() && StringUtil.isNullOrEmpty(itfLog.getRespCode())) {
				TransLog transLog = intfaceTransLogService.getTransLogByRelatedKey(cs.getCurLogNum(), itfLog.getInterfacePrimaryKey());
				//4 没有查到交易流水：更新middleware流水，并且返回交易失败，错误码为96系统异常
				if(transLog == null){
					intfaceTransLogService.updateIntfaceTransLogRespCode(BaseConstants.RESPONSE_EXCEPTION_CODE, cs.getCurLogNum(), req.getSwtFlowNo());
					return JSONArray.toJSONString(resp);
				//5 查询到交易流水,并且从核心有返回结果(trans_st!=0，resp_code有返回码)：更新middleware流水，并且返回结果，返回码为查询到的交易系统流水的返回码
				} else if (transLog.getTransSt() != 0 && !StringUtil.isNullOrEmpty(transLog.getRespCode())) {
					intfaceTransLogService.updateIntfaceTransLogRespCode(transLog.getRespCode(), cs.getCurLogNum(), req.getSwtFlowNo());
					resp.setCode(transLog.getRespCode());
					resp.setInfo(ITFRespCode.findByCode(transLog.getRespCode()).getValue());
					resp.setTransAmt(transLog.getTransAmt());
					resp.setCardHolderFee(transLog.getTransFee());
					logger.info("##5 异常处理，中间层流水号[{}]，返回码[{}]", itfLog.getDmsRelatedKey(), itfLog.getRespCode());
					return JSONArray.toJSONString(resp);
				}
				//6  查询账户流水,并且没有从账户系统返回(trans_st=0,resp_code无返回码)
				if (0 == transLog.getTransSt() && StringUtil.isNullOrEmpty(transLog.getRespCode())) {
					AccountLog accountLog = intfaceTransLogService.getAccountLogByRelatedKey(cs.getCurLogNum(), transLog.getTxnPrimaryKey());
					//7  没有查到账户流水：更新middleware和交易系统流水，并且返回交易失败，错误码为96系统异常
					if (accountLog != null) {
						resp.setCode(accountLog.getRespCode());
						resp.setInfo(ITFRespCode.findByCode(accountLog.getRespCode()).getValue());
						resp.setTransAmt(itfLog.getTransAmt());
						resp.setCardHolderFee(itfLog.getTransFee());
						logger.info("##7 异常处理，中间层流水号[{}]，返回码[{}]", itfLog.getDmsRelatedKey(), itfLog.getRespCode());
					}
					intfaceTransLogService.updateTransLogRespCode(BaseConstants.RESPONSE_EXCEPTION_CODE, cs.getCurLogNum(), transLog.getDmsRelatedKey());
					intfaceTransLogService.updateIntfaceTransLogRespCode(BaseConstants.RESPONSE_EXCEPTION_CODE, cs.getCurLogNum(), req.getSwtFlowNo());
					return JSONArray.toJSONString(resp);
				}
			}
		} else {
			logger.error("## 交易记录查询--->日切信息交易允许状态：日切中");
		}
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String customerBuyCardStocksQueryITF(CustomerBuyCardStocksRequest req) throws Exception {
		logger.info("客户可购卡数量查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.customerBuyCardStocksQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		resp.setCommodityCode(req.getCommodityCode());
		
		try {
			//当前充值购卡的用户Id查询
			String userId = bizUserService.getUserIdByUserName(req.getUserId(), req.getChannel());
			//查找用户 剩余可购买数量
			int commodityStocks = salesActivityListService.getCommodityStocksByCommodityId(req.getCommodityCode(), userId);
			resp.setCommodityStocks(String.valueOf(commodityStocks));
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch (Exception e) {
			logger.error("## 客户可购卡数量查询异常", e);
		}	
		
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String getMchtSellingCardListQueryITF(BaseTxnReq req) throws Exception {
		logger.info("商户在售卡列表(新版)查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.getMchtSelListQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		try {
			List<MchtCommodities> list = bizMchtService.getSellingCardList(req.getInnerMerchantNo());
			String [] activityBodys = null;
			if (list != null && list.size() > 0) {
				activityBodys=new String[list.size()];
				
				for( int i=0;i <list.size();i++ ){
					activityBodys[i]=list.get(i).getCommodityCode();
				}
				ImageManager im = new ImageManager();
				im.setApplicationId(list.get(0).getProductCode());
				im.setApplication(BaseConstants.Application.APP_PROD.getCode());
				im.setApplicationType(BaseConstants.AppType.A3001.getCode());
				List<String> urlList = imageManagerService.getImagesUrl(im);
				if (urlList != null && urlList.size() > 0) {
					resp.setProductImage(urlList.get(0));// 产品卡面目前只有一张
				} else {
					resp.setProductImage(null);
				}
				urlList = null;// 释放内存
			}
			resp.setCardList(list);
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch (Exception e) {
			logger.error("## 商户在售卡列表(新版)查询异常", e);
		}
		return JSONArray.toJSONString(resp);
	
	}
	
	@Override
	public String getShopInfoQueryITF(ShopInfQueryRequest req) throws Exception {
		logger.info("商户门店明细(新版)查询请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.getShopInfoQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		
		if (StringUtil.isNullOrEmpty(req.getDetailFlag()))
			req.setDetailFlag("0");// 默认不查询明细
		
		ShopInf shop = new ShopInf();
		shop.setMchntCode(req.getInnerMerchantNo());
		shop.setShopCode(req.getInnerShopNo());
		shop.setDetailFlag(req.getDetailFlag());
		
		try {
			ShopDetailInf detail = null;
			if ("1".equals(req.getDetailFlag())) {
				detail = bizMchtService.getShopDetailInfo(shop);
			} else {
				detail = bizMchtService.getShopSimpleInfo(shop);
			}
			if (detail == null)
				detail = new ShopDetailInf();
			
			ImageManager im = new ImageManager();
			im.setApplicationId(detail.getShopCode());
			im.setApplication(BaseConstants.Application.APP_SHOP.getCode());
			im.setApplicationType(BaseConstants.AppType.A2001.getCode());
			List<String> urlList = imageManagerService.getImagesUrl(im);
			if (urlList != null && urlList.size() > 0) {
				detail.setBrandLogo(urlList.get(0));// 商户LOGO目前只有一张
			} else {
				detail.setBrandLogo(null);
			}
			urlList = null;// 释放内存
			resp.setShopInfo(detail);
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch(Exception e) {
			logger.error("## 商户门店明细(新版)查询异常", e);
		}	
		
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String getShopListQueryITF(ShopInfQueryRequest req) throws Exception {
		logger.info("商户门店列表查询(含分页)请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.getShopListQueryITF(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		ShopInf shop = new ShopInf();
		try {
			shop.setLatitude(req.getLatitude());
			shop.setLongitude(req.getLongitude());
			shop.setDistance(req.getDistance());
			
			//根据传递的距离查询获取geohash 生成的长度
			int geoLength = GeoConstantsEnum.findEnumByDistance(Integer.parseInt(req.getDistance())).getGeoLength();
			GeoHash g = new GeoHash(Double.parseDouble(req.getLatitude()), Double.parseDouble(req.getLongitude()));
			g.sethashLength(geoLength);
	    	String[] geohashs = (String[]) g.getGeoHashBase32For9().toArray(new String[g.getGeoHashBase32For9().size()]);
			shop.setGeohashs(geohashs);
			shop.setGeoLength(geoLength);
			shop.setSort(req.getSort());
			shop.setIndustryType(req.getIndustryType());
			
			int startNum = StringUtil.parseInt(req.getPageNum(), 1);
			int pageSize = StringUtil.parseInt(req.getItemSize(), 10);
	
			PageInfo<ShopListInf> pageList = bizMchtService.getShopListInfoPage(startNum, pageSize, shop);
			
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
			resp.setShopList(pageList.getList());
			resp.setCurrPageSize(String.valueOf(pageList.getPageNum()));
			resp.setPageSize(String.valueOf(pageList.getPages()));
		} catch(Exception e) {
			logger.error("## 商户门店列表查询(含分页)异常", e);
		}	
		
		return JSONArray.toJSONString(resp);
	}
	
	@Override
	public String  getMerchantInfoQueryITF(BaseTxnReq req) throws Exception{
		logger.info("商户信息查询(新版)请求参数[{}]", JSONArray.toJSONString(req));
		
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
		// 参数校验
		if (HKBTxnReqValid.getMerchantInfoQueryValid(req, resp)) {
			return JSONArray.toJSONString(resp);
		}
		try {
			MerchantInf detail = bizMchtService.getMerchantInfByCode(req.getInnerMerchantNo());
			if (detail == null) {
				detail = new MerchantInf();
			} else {
				ImageManager im = new ImageManager();
				im.setApplicationId(detail.getMchntCode());
				im.setApplication(BaseConstants.Application.APP_MCHNT.getCode());
				im.setApplicationType(BaseConstants.AppType.A1001.getCode());
				List<String> urlList = imageManagerService.getImagesUrl(im);
				if (urlList != null && urlList.size() > 0) {
					detail.setBrandLogo(urlList.get(0));// 商户LOGO目前只有一张
				} else {
					detail.setBrandLogo(null);
				}
				urlList = null;// 释放内存
			}
			resp.setMerchantInfo(detail);
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
		} catch(Exception e) {
			logger.error("## 商户信息查询(新版)异常", e);
		}	
		return JSONArray.toJSONString(resp);
	}
	
}
