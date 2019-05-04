package com.cn.thinkx.wecard.customer.module.jfpay.service;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

public interface JfPayService {

	TxnResp scanCodeJava2JFBusiness(HttpServletRequest request);
	
	/**
	 * 插入微信端交易流水
	 * 
	 * @param httpServletRequest
	 * @return {@link WxTransLog}
	 * @author pucker
	 */
	WxTransLog insertWxTransLog(HttpServletRequest httpServletRequest);
}
