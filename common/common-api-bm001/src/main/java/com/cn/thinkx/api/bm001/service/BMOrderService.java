package com.cn.thinkx.api.bm001.service;


import com.cn.thinkx.api.bm001.domain.BaseResult;

public interface BMOrderService {

	/**
	 * 话费充值
	 * @param mobileNo
	 * @param rechargeAmount ：充值金额 单位元
	 * @param orderId
	 * @param callBack
	 * @param accessToken
	 * @return
	 */
	BaseResult handleHbkToBMPayBill(String mobileNo, String rechargeAmount, String orderId, String callBack, String accessToken);
	
}
