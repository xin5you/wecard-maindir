package com.cn.thinkx.wecard.customer.module.customer.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

public interface UserMerchantAcctService {

	/**
	 * 获取 用户交易 获取无pin 输入密码状态
	 * 
	 * @param userMerchantAcct
	 * @param currTxnAmt 当前交易金额 （分）
	 * @return boolean false:不需要输入密码，true: 需要输入密码
	 */
	public boolean doCustomerNeed2EnterPassword(UserMerchantAcct userMerchantAcct, int currTxnAmt);

	/**
	 * 获取客户的卡产品信息 所属的机构和 商户信息
	 * 
	 * @param openid 微信openid
	 * @return
	 */
	public List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct entity);

	/**
	 * 获取商户下所有的卡
	 * 
	 * @param entity
	 * @return
	 */
	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct entity);

	/***
	 * 客户开户
	 * 
	 * @param productCode 产品号
	 * @param userId 用户Id
	 * @param openid
	 * @param mchntCode 商户号
	 * @param insCode 机构号
	 * @return 1:开户成功 ； 0：开户不成功
	 */
	public TxnResp doCustomerAccountOpening(String productCode, String userId, String openid, String mchntCode,
			String insCode) throws Exception;
}
