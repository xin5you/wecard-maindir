package com.cn.thinkx.api.bm001.service;

import com.cn.thinkx.api.bm001.req.PayBillReq;
import com.qianmi.open.api.response.*;

public interface BMOpenApiService {

	
	/**
	 * @param mobileNo
	 * @param rechargeAmount
	 * @param accessToken
	 * @return
	 */
	BmRechargeMobileGetItemInfoResponse handleGetItemInfo(String mobileNo, String rechargeAmount, String accessToken);
	
	/**
	 * @param payBillReq
	 * @param accessToken
	 * @return
	 */
	BmRechargeMobilePayBillResponse handlePayBill(PayBillReq payBillReq, String accessToken);
	
	/**
	 * @param billId
	 * @param accessToken
	 * @return
	 */
	BmRechargeOrderInfoResponse handleGetOrderInfo(String billId, String accessToken);
	
	/**
	 * @param outerTid
	 * @param accessToken
	 * @return
	 */
	BmOrderCustomGetResponse handleGetCustomOrder(String outerTid, String accessToken);
	
	/**
	 * @param mobileNo
	 * @param accessToken
	 * @return
	 */
	BmRechargeMobileGetPhoneInfoResponse handleGetPhoneInfo(String mobileNo, String accessToken);
	
}
