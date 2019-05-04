package com.cn.thinkx.oms.module.enterpriseorder.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchInvoiceOrder;

public interface BatchInvoiceOrderService {
	
	/**
	 * 批量充值开票，通过订单号，查询订单信息
	 * 
	 * @param orderId
	 * @return
	 */
	BatchInvoiceOrder getBatchOrderByOrderId(String orderId);
	
	/**
	 * 批量充值，确认开票提交
	 * 
	 * @param entity
	 * @return
	 */
	ModelMap addBatchInvoiceOrderCommit(HttpServletRequest req);
	
	/**
	 * 批量开票订单详情
	 * 
	 * @param orderId
	 * @return
	 */
	BatchInvoiceOrder getBatchInvoiceOrderByOrderId(String orderId);
	
	/**
	 * 通过订单号查询订单个数
	 * 
	 * @param orderId
	 * @return
	 */
	int getBatchInvoiceCountByOrderId(String orderId);
}
