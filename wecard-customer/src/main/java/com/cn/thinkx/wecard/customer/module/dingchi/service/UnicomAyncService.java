package com.cn.thinkx.wecard.customer.module.dingchi.service;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.wecard.customer.module.dingchi.vo.UnicomAyncReq;
import com.cn.thinkx.wecard.customer.module.dingchi.vo.UnicomAyncResp;

public interface UnicomAyncService {

	/**
	 * 购买请求
	 * 
	 * @param vo 充值参数
	 * @return
	 */
	UnicomAyncResp buy(UnicomAyncReq vo, HttpServletRequest request);
	
	/**
	 * 支付方法
	 * 
	 * @param req
	 * @return
	 */
	String hkbPayment(HttpServletRequest req);
	
	/**
	 * 查询方法
	 * 
	 * @param req
	 * @return
	 */
	UnicomAyncResp hkbQuery(HttpServletRequest req);
	
	/**
	 * 知了企服回调方法(提供鼎驰调用)
	 * 
	 * @param request
	 * @return
	 */
	String hkbNotify(HttpServletRequest request);
	
	/**
	 * 知了企服退款接口（提供鼎驰调用）
	 * 
	 * @param refundId
	 * @param oriSwtFlowNo
	 * @return
	 */
	String hkbRefund(String refundId, String oriSwtFlowNo);
	
	/**
	 * 验证手机号
	 * 
	 * @param req
	 * @return
	 */
	/*List<CardKeysProduct> mobileSign(HttpServletRequest req);*/
	
}
