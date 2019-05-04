package com.cn.thinkx.wecard.customer.module.merchant.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.merchant.MerchantCashManager;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.common.wecard.module.merchant.mapper.MerchantInfDao;
import com.cn.thinkx.common.wecard.module.merchant.mapper.MerchantManagerDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.customer.module.customer.service.CtrlSystemService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfListService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

@Service("merchantInfService")
public class MerchantInfServiceImpl implements MerchantInfService {
	private Logger logger = LoggerFactory.getLogger(MerchantInfServiceImpl.class);

	@Autowired
	@Qualifier("merchantInfDao")
	private MerchantInfDao merchantInfDao;

	@Autowired
	@Qualifier("merchantManagerDao")
	private MerchantManagerDao merchantManagerDao;

	@Autowired
	@Qualifier("merchantInfListService")
	private MerchantInfListService merchantInfListService;

	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;

	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;

	@Autowired
	@Qualifier("userMerchantAcctService")
	private UserMerchantAcctService userMerchantAcctService;

	public MerchantInf getMerchantInfById(String mId) {
		return merchantInfDao.getMerchantInfById(mId);
	}

	public MerchantInf getMerchantInfByCode(String mchntCode) {
		return merchantInfDao.getMerchantInfByCode(mchntCode);
	}

	public MerchantCashManager getMerchantCashManagerByMchntId(String mchntId) {
		return merchantInfDao.getMerchantCashManagerByMchntId(mchntId);
	}

	public MerchantInf getMchntAndInsInfBymchntId(String mchntId) {
		return merchantInfDao.getMchntAndInsInfBymchntId(mchntId);
	}

	public String getInsCodeByInsId(String insId) {
		return merchantInfDao.getInsCodeByInsId(insId);
	}

	@Override
	public String getCheckMerchantInf(String mchntCode, String openid) {
		/*** 判断用户是否已经是某商户会员 **/
		MerchantInf merchantInf = merchantInfDao.getMerchantInfByCode(mchntCode);
		String userMchntAccBal = "0";
		if (merchantInf != null) {
			UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
			userMerchantAcct.setExternalId(openid);
			userMerchantAcct.setMchntCode(mchntCode);
			List<UserMerchantAcct> productlist = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
			if (productlist == null || productlist.size() <= 0) {
				try {
					// 请求开卡
					TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null, openid,
							mchntCode, merchantInf.getInsCode());
					if (cardCheckResp != null
							&& !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
						userMerchantAcctService.doCustomerAccountOpening(null, null, openid, mchntCode,
								merchantInf.getInsCode());
					}
				} catch (Exception ex) {
					logger.error("## 扫码快捷支付，用户开卡失败", ex);

				}
			}
			// productlist=userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
			if (productlist != null && productlist.size() > 0) {
				userMchntAccBal = productlist.get(0).getAccBal();
			}
		}
		return userMchntAccBal;
	}
}
