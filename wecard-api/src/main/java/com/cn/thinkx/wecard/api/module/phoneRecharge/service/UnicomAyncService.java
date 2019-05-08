package com.cn.thinkx.wecard.api.module.phoneRecharge.service;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.wecard.api.module.phoneRecharge.model.PhoneRechargeOrder;

public interface UnicomAyncService {

	/**
	 * 购买请求
	 * 
	 * @param vo 充值参数
	 * @return
	 */
	String buy(PhoneRechargeOrder flowOrder);
	
	/**
	 * 查询方法
	 * 
	 * @param req
	 * @return
	 */
	String query(HttpServletRequest req);
	
	/**
	 * 薪无忧回调方法(提供鼎驰调用)
	 * 
	 * @param request
	 * @return
	 */
	String notify(HttpServletRequest request);
	
}
