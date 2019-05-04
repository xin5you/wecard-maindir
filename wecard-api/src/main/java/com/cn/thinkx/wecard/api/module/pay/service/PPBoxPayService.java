package com.cn.thinkx.wecard.api.module.pay.service;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.wecard.api.module.pay.req.PPScanCodeReq;

/**
 * paipai盒子支付 service
 * 
 * @author zqy
 *
 */
public interface PPBoxPayService {

	/**
	 * paipai盒子提交扫码
	 * 
	 * @param req
	 * @param request
	 * @return
	 */
	String doPPScanTrans(PPScanCodeReq req, HttpServletRequest request);
}
