package com.cn.thinkx.wecard.api.module.trans.service.impl;

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
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.customer.model.PersonInf;
import com.cn.thinkx.wecard.api.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.api.module.trans.mapper.UserMerchantAcctMapper;
import com.cn.thinkx.wecard.api.module.trans.model.UserMerchantAcct;
import com.cn.thinkx.wecard.api.module.trans.model.WxTransLog;
import com.cn.thinkx.wecard.api.module.trans.service.CtrlSystemService;
import com.cn.thinkx.wecard.api.module.trans.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.api.module.trans.service.WxTransLogService;

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
	public List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct entity) {
		entity.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
		List<UserMerchantAcct> list = userMerchantAcctMapper.getUserMerchantAcctByUser(entity);
		if (list != null && list.size() > 0) {
			for (UserMerchantAcct acc : list) {
				acc.setAccBal(NumberUtils.RMBCentToYuan(acc.getAccBal()));
			}
		}
		return list;
	}
	
	/**
	 * 获取商户下所有的卡
	 * @param entity
	 * @return
	 */
	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct entity){
		return userMerchantAcctMapper.getMerchantCardByMchnt(entity);
	}
	
	/***
	 * 客户开卡
	 * @param productCode 卡产品Code
	 * @param personId 用户Id
	 * @param openid 
	 * @param mchntCode 商户号
	 * @param insCode  机构号
	 * @return 1:开户成功 ； 0：开户不成功
	 */
	public TxnResp doCustomerCardOpeningITF(String productCode,String personId,String openid,String mchntCode,String insCode)throws Exception{
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		if((StringUtil.isNullOrEmpty(personId) && StringUtil.isNullOrEmpty(openid))|| StringUtil.isNullOrEmpty(mchntCode) || StringUtil.isNullOrEmpty(insCode)){
			resp.setInfo("请求参数不正确");
			return resp;
		}
		PersonInf personInf=null;
		UserMerchantAcct userMerchantAcct=new UserMerchantAcct();
		userMerchantAcct.setExternalId(openid);
		userMerchantAcct.setMchntCode(mchntCode);
		userMerchantAcct.setInsCode(insCode);
		List<UserMerchantAcct> productlist=this.getUserMerchantAcctByUser(userMerchantAcct);
		
		if(productlist !=null && productlist.size()>0){ //如果用户有这个商户下的卡 就说明以及开户成功的 直接返回
			resp.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
			return resp;
		}
		
		if(!StringUtil.isNullOrEmpty(personId)){
			personInf=personInfService.findPersonInfById(personId);
		}else{
			personInf=personInfService.getPersonInfByOpenId(openid);
		}
		
		if(StringUtil.isNullOrEmpty(productCode)){
			userMerchantAcct=new UserMerchantAcct();
			userMerchantAcct.setMchntCode(mchntCode);
			userMerchantAcct.setInsCode(insCode);
			List<UserMerchantAcct> cardlist=this.getMerchantCardByMchnt(userMerchantAcct);
			
			if(cardlist==null || cardlist.size()<=0){
				resp.setInfo("您当前所属的商户还没开始销售卡，暂时不能办卡");
				return resp;
			}
			productCode=cardlist.get(0).getProductCode();
		}

		/**客户开卡**/
		WxTransLog log=null;
		try{
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs != null) {
			if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
				    log= new WxTransLog();
					String id = wxTransLogService.getPrimaryKey();
					log.setWxPrimaryKey(id);
					log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
					log.setSettleDate(cs.getSettleDate());// 交易日期
					log.setTransId(TransCode.CW80.getCode());// 交易类型 开户
					log.setTransSt(0);// 插入时为0，报文返回时更新为1
					log.setInsCode(insCode);// 商户所属的机构code
					log.setMchntCode(mchntCode);
					log.setOperatorOpenId(openid);
					log.setUserInfUserName(personInf.getUserId());
					int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
					if (i != 1) {
						resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
						logger.info("客户开户----->insertIntfaceTransLog微信端插入流水记录数量不为1");
					}
					TxnPackageBean txnBean = new TxnPackageBean();
					Date currDate=DateUtil.getCurrDate();
					txnBean.setSwtFlowNo(log.getWxPrimaryKey()); //接口平台交易流水号
					txnBean.setTxnType(log.getTransId()+"0");// 0: 同步 1:异步
					txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_YYYYMMDD));
					txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_HHMMSS));
					txnBean.setSwtSettleDate(log.getSettleDate());
					txnBean.setChannel(ChannelCode.CHANNEL1.toString()); // 商户开户、客户开户、密码重置、消费 (从微信公众号发起)
					txnBean.setIssChannel(log.getInsCode()); //机构渠道号
					txnBean.setInnerMerchantNo(log.getMchntCode()); //商户号
					txnBean.setResv3(personInf.getUserId());
					txnBean.setResv4(personInf.getPersonalId());
					txnBean.setCardNo(productCode);
	
					// 远程调用消费接口
					String json = java2TxnBusinessFacade.customerAccountOpeningITF(txnBean); //客户开户接口
					resp = JSONArray.parseObject(json, TxnResp.class);
					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					wxTransLogService.updateWxTransLog(log,resp); 
					if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
						logger.info("客户开户----->"+resp.getInfo());
					}
			} else {
				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
				logger.info("客户开户----->日切信息交易允许状态：日切中");
			}
		 }
		} catch (Exception e) {
			if(log !=null){
				log.setTransSt(1);// 插入时为0，报文返回时更新为1
				log.setRespCode(resp.getCode());
				wxTransLogService.updateWxTransLog(log,resp); 
		    }
			resp.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
			resp.setInfo("socket通信异常");
			logger.error("socket通信异常", e.getMessage());
		}
		return resp;
	}
}
