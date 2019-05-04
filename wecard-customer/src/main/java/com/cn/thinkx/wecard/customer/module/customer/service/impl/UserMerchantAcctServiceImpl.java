package com.cn.thinkx.wecard.customer.module.customer.service.impl;

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
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.common.wecard.module.user.mapper.UserMerchantAcctDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.module.customer.service.CtrlSystemService;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

@Service("userMerchantAcctService")
public class UserMerchantAcctServiceImpl implements UserMerchantAcctService {
	private Logger logger = LoggerFactory.getLogger(UserMerchantAcctServiceImpl.class);

	@Autowired
	@Qualifier("userMerchantAcctDao")
	private UserMerchantAcctDao userMerchantAcctDao;

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

	public boolean doCustomerNeed2EnterPassword(UserMerchantAcct userMerchantAcct, int currTxnAmt) {
		boolean b = true;
		userMerchantAcct.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
		List<UserMerchantAcct> list = userMerchantAcctDao.getUserMerchantAcctByUser(userMerchantAcct);
		try {
			if (list != null && list.size() > 0) {
				userMerchantAcct = list.get(0);
				CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
				if (cs != null) {
					int noPinTxnAmt = Integer.parseInt(userMerchantAcct.getNopinTxnAmt());

					int dayTotalAmt2 = 0;
					if (cs.getSettleDate().equals(userMerchantAcct.getLastTxnDate())) {
						dayTotalAmt2 = Integer.parseInt(userMerchantAcct.getDayTotalAmt2()); // 日交易总额
					}
					if (noPinTxnAmt >= (currTxnAmt + (dayTotalAmt2))) {
						return false;
					} else {
						return true;
					}
				}
			}
		} catch (Exception e) {
			logger.error("getUserTransNopinTxnAmt-->异常：", e);
			return true;
		}
		return b;
	}

	public List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct entity) {
		entity.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
		List<UserMerchantAcct> list = userMerchantAcctDao.getUserMerchantAcctByUser(entity);
		if (list != null && list.size() > 0) {
			for (UserMerchantAcct acc : list) {
				acc.setAccBal(NumberUtils.RMBCentToYuan(acc.getAccBal()));
			}
		}
		return list;
	}

	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct entity) {
		return userMerchantAcctDao.getMerchantCardByMchnt(entity);
	}

	public TxnResp doCustomerAccountOpening(String productCode, String userId, String openid, String mchntCode,
			String insCode) throws Exception {
		TxnResp resp = new TxnResp();
		resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
		if ((StringUtil.isNullOrEmpty(userId) && StringUtil.isNullOrEmpty(openid))
				|| StringUtil.isNullOrEmpty(mchntCode) || StringUtil.isNullOrEmpty(insCode)) {
			resp.setInfo("请求参数不正确");
			return resp;
		}
		PersonInf personInf = null;
		UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
		userMerchantAcct.setUserId(userId);
		userMerchantAcct.setExternalId(openid);
		userMerchantAcct.setMchntCode(mchntCode);
		userMerchantAcct.setInsCode(insCode);
		List<UserMerchantAcct> productlist = this.getUserMerchantAcctByUser(userMerchantAcct);

		if (productlist != null && productlist.size() > 0) { // 如果用户有这个商户下的卡就说明以及开户成功，直接返回
			resp.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
			logger.info("用户userid[{}] openID[{}]已开卡，不能重复开卡", userId, openid);
			return resp;
		}
		if (!StringUtil.isNullOrEmpty(userId)) {
			personInf = personInfService.getPersonInfByUserId(userId);
		} else {
			UserInf userInf = userInfService.getUserInfByOpenId(openid);
			if (userInf == null) {
				resp.setInfo("对不起，您当前不是会员，请先注册成为公众号会员");
				return resp;
			}
			userId = userInf.getUserId();
			personInf = personInfService.getPersonInfByUserId(userInf.getUserId());
		}
		if (personInf == null) {
			resp.setInfo("对不起，您当前不是会员，请先注册成为公众号会员");
			return resp;
		}

		if (StringUtil.isNullOrEmpty(productCode)) {
			userMerchantAcct = new UserMerchantAcct();
			userMerchantAcct.setMchntCode(mchntCode);
			userMerchantAcct.setInsCode(insCode);
			List<UserMerchantAcct> cardlist = this.getMerchantCardByMchnt(userMerchantAcct);

			if (cardlist == null || cardlist.size() <= 0) {
				resp.setInfo("对不起，该商户暂未售卡");
				return resp;
			}
			productCode = cardlist.get(0).getProductCode();
		}
		/** 客户开户 **/
		WxTransLog log = null;
		try {
			CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息 TODO 加入redis
			if (cs != null) {
				if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
					log = new WxTransLog();
					String id = wxTransLogService.getPrimaryKey();
					log.setWxPrimaryKey(id);
					log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
					log.setSettleDate(cs.getSettleDate());// 交易日期
					log.setTransId(TransCode.CW80.getCode());// 交易类型 开户
					log.setTransSt(0);// 插入时为0，报文返回时更新为1
					log.setInsCode(insCode);// 商户所属的机构code
					log.setMchntCode(mchntCode);
					log.setOperatorOpenId(openid);
					log.setUserInfUserName(userId);
					int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
					if (i != 1) {
						resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
						logger.error("## 客户[{}]开户，insertIntfaceTransLog微信端插入流水记录数量不为1", openid);
					}
					TxnPackageBean txnBean = new TxnPackageBean();
					Date currDate = DateUtil.getCurrDate();
					txnBean.setSwtFlowNo(log.getWxPrimaryKey()); // 接口平台交易流水号
					txnBean.setTxnType(log.getTransId() + "0");// 0: 同步 1:异步
					txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_YYYYMMDD));
					txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_HHMMSS));
					txnBean.setSwtSettleDate(log.getSettleDate());
					txnBean.setChannel(ChannelCode.CHANNEL1.toString()); // 商户开户、客户开户、密码重置、消费
																			// (从微信公众号发起)
					txnBean.setIssChannel(log.getInsCode()); // 机构渠道号
					txnBean.setInnerMerchantNo(log.getMchntCode()); // 商户号
					txnBean.setResv3(userId);
					txnBean.setResv4(personInf.getPersonalId());
					txnBean.setCardNo(productCode);

					String json = java2TxnBusinessFacade.customerAccountOpeningITF(txnBean); // 客户开户接口
					resp = JSONArray.parseObject(json, TxnResp.class);
					logger.info("远程调用ITF开户接口返回{}", json);

					log.setTransSt(1);// 插入时为0，报文返回时更新为1
					log.setRespCode(resp.getCode());
					wxTransLogService.updateWxTransLog(log, resp);
				} else {
					resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
					logger.error("## 客户[{}]开户----->日切信息交易允许状态：日切中", openid);
				}
			}
		} catch (Exception e) {
			if (log != null) {
				log.setTransSt(1);// 插入时为0，报文返回时更新为1
				log.setRespCode(resp.getCode());
				wxTransLogService.updateWxTransLog(log, resp);
			}
			resp.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
			resp.setInfo("socket通信异常");
			logger.error("## socket通信异常", e);
		}
		return resp;
	}
}
