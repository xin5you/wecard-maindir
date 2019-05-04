package com.cn.thinkx.oms.module.customer.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.oms.module.common.model.TxnResp;
import com.cn.thinkx.oms.module.common.service.CtrlSystemService;
import com.cn.thinkx.oms.module.common.service.WxTransLogService;
import com.cn.thinkx.oms.module.customer.mapper.UserMerchantAcctMapper;
import com.cn.thinkx.oms.module.customer.model.UserMerchantAcct;
import com.cn.thinkx.oms.module.customer.service.PersonInfService;
import com.cn.thinkx.oms.module.customer.service.UserInfService;
import com.cn.thinkx.oms.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;

@Service("userMerchantAcctService")
public class UserMerchantAcctServiceImpl implements UserMerchantAcctService {

	private Logger logger = LoggerFactory.getLogger(UserMerchantAcctServiceImpl.class);
	
	@Autowired
	@Qualifier("userMerchantAcctMapper")
	private UserMerchantAcctMapper userMerchantAcctMapper;
	
	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;

	@Autowired
	@Qualifier("userInfService")
	private UserInfService userInfService;
	
	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;
	
	@Autowired
	private Java2TxnBusinessFacade java2TxnBusinessFacade;

	/**
	 * 获取客户的卡产品信息 所属的机构和 商户信息
	 * @param openid 微信openid
	 * @return
	 */
	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct entity) {
		entity.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
		List<UserMerchantAcct> list = userMerchantAcctMapper.getMerchantCardByMchnt(entity);
	
		return list;
	}
	

	
	/***
	 * 客户开卡
	 * @param userId 用户Id
	 * @param personId
	 * @param productCode 产品号
	 * @return TxnResp
	 */
	public TxnResp doCustomerCardOpeningITF(String orderListId,String userId,String personId,String productCode)throws Exception{
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		if((StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(personId)) || StringUtil.isNullOrEmpty(productCode)){
			resp.setInfo("请求参数不正确");
			return resp;
		}
		UserMerchantAcct userMerchantAcct=new UserMerchantAcct();
		userMerchantAcct.setProductCode(productCode);
		
		if(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_PROD_NO").equals(productCode)){
			userMerchantAcct.setInsCode(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_INS_CODE"));
			userMerchantAcct.setMchntCode(RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_MCHNT_NO"));
		}else{
			List<UserMerchantAcct> productlist=this.getMerchantCardByMchnt(userMerchantAcct);
			
			if(productlist==null || productlist.size()<=0){
				resp.setInfo("未查找到卡产品对应的商户信息");
				return resp;
			}
			userMerchantAcct=productlist.get(0);
		}
		
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
//				    log= new WxTransLog();
//				    String wxPrimaryKey="";
//					log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
//					log.setSettleDate(cs.getSettleDate());// 交易日期
//					log.setTransId(TransCode.CW80.getCode());// 交易类型 开户
//					log.setTransSt(0);// 插入时为0，报文返回时更新为1
//					log.setInsCode(userMerchantAcct.getInsCode());// 商户所属的机构code
//					log.setMchntCode(userMerchantAcct.getMchntCode());
//					log.setUserInfUserName(userId);
//					log.setProductCode(productCode);
					
					
					TxnPackageBean txnBean = new TxnPackageBean();
					Date currDate=DateUtil.getCurrDate();
					txnBean.setSwtFlowNo(orderListId); //接口平台交易流水号
					txnBean.setTxnType(TransCode.CW80.getCode()+"0");// 0: 同步 1:异步
					txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_YYYYMMDD));
					txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_HHMMSS));
					txnBean.setSwtSettleDate(cs.getSettleDate());
					txnBean.setChannel(ChannelCode.CHANNEL0.toString()); // 商户开户、客户开户、密码重置、消费 (从微信公众号发起)
					txnBean.setIssChannel(userMerchantAcct.getInsCode()); //机构渠道号
					txnBean.setInnerMerchantNo(userMerchantAcct.getMchntCode()); //商户号
					txnBean.setResv3(userId);
					txnBean.setResv4(personId);
					txnBean.setCardNo(productCode);
	
					// 远程调用消费接口
					String json ="";
					try{
						json=java2TxnBusinessFacade.customerAccountOpeningITF(txnBean); //客户开卡接口
						resp = JSONArray.parseObject(json, TxnResp.class);
					} catch (Exception ex) {
						logger.info("客户开户----->远程调用接口异常{}",ex);
					}
					if(resp==null){
						json = java2TxnBusinessFacade.transExceptionQueryITF(orderListId);
						resp = JSONArray.parseObject(json, TxnResp.class);
					}
					logger.info("resp  is -------------->" + json);
					return resp;
			} else {
				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
				logger.info("客户开户----->日切信息交易允许状态：日切中");
			}
		 }
	
		return resp;
	}
	
	/***
	 * 客户充值
	 * @param orderListId 订单明细Id
	 * @param userId 用户Id
	 * @param externalId 外部渠道用户主键
	 * @param transAmt 交易金额（分）
	 * @param mchntCode 商户号
	 * @param insCode  机构号
	 * @return TxnResp
	 */
	public TxnResp doCustomerRechargeTransactionITF(String orderListId,String userId,String externalId,String transAmt,String mchntCode,String insCode)throws Exception{
		TxnResp resp = new TxnResp();
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入流水
//				log=new WxTransLog();
//				String wxPrimaryKey="";
//				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
//				log.setSettleDate(cs.getSettleDate());// 交易日期
//				log.setTransId(TransCode.CW20.getCode()+ "0");//客户充值
//				log.setTransSt(0);// 插入时为0，报文返回时更新为1
//				log.setInsCode(insCode);// 商户所属的机构code
//				log.setMchntCode(mchntCode);
//				log.setUserInfUserName(userId);
//				log.setTransAmt(transAmt);// 实际交易金额 
//				log.setUploadAmt(transAmt);// 上送金额
//				log.setUserInfUserName(userId);
//				log.setCardNo(externalId);
//				log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
//				log.setTransChnl(ChannelCode.CHANNEL0.toString());
		
				TxnPackageBean txnBean = new TxnPackageBean();
				txnBean.setTxnType(TransCode.CW20.getCode()+ "0");// 交易类型，发送报文时补0
				txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
				txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
				txnBean.setSwtSettleDate(cs.getSettleDate());// 清算日期
				txnBean.setChannel(ChannelCode.CHANNEL0.toString());// 渠道号
				txnBean.setSwtFlowNo(orderListId);
				txnBean.setIssChannel(insCode);// 机构渠道号
				txnBean.setInnerMerchantNo(mchntCode);// 商户号

				txnBean.setTxnAmount(transAmt);// 交易金额
				txnBean.setOriTxnAmount(transAmt);// 原交易金额
				txnBean.setCardNo(externalId);// 用户外部ID
				String jsonStr="";
				try {
					jsonStr = java2TxnBusinessFacade.rechargeTransactionITF(txnBean);// 远程调用充值接口
					resp = JSONArray.parseObject(jsonStr, TxnResp.class);
				} catch(Exception e) {
					logger.error("充值交易返回通知--->远程调用充值接口异常，流水号：[{}]",e);
				}
				try {
					if(resp==null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())){
						jsonStr=java2TxnBusinessFacade.transExceptionQueryITF(orderListId);
						resp = JSONArray.parseObject(jsonStr, TxnResp.class);
					}
				} catch(Exception e) {
					logger.error("充值交易返回通知--->远程调用查询接口异常，流水号：[{}]",e);
				}
				logger.info("resp  is -------------->" + jsonStr);
				// 更新微信端流水
			}
		}
		return resp;
	}

	/**
	 * 获取所有卡的余额
	 */
	public List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct userMerchantAcct) {
		return userMerchantAcctMapper.getUserMerchantAcctByUser(userMerchantAcct);
	}
}
