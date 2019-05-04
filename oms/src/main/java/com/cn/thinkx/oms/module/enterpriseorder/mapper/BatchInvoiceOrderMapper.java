package com.cn.thinkx.oms.module.enterpriseorder.mapper;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchInvoiceOrder;

public interface BatchInvoiceOrderMapper {
	
	BatchInvoiceOrder getBatchOrderByOrderId(String orderId);
	
	int insertBatchInvoiceOrder(BatchInvoiceOrder entity);
	
	BatchInvoiceOrder getBatchInvoiceOrderByOrderId(String orderId);
	
	int getBatchInvoiceCountByOrderId(String orderId);
}
