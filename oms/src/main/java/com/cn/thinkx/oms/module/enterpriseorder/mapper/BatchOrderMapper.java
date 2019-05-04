package com.cn.thinkx.oms.module.enterpriseorder.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.github.pagehelper.PageInfo;

public interface BatchOrderMapper {
	List<BatchOrder> getBatchOrderList(BatchOrder order);
	
	int addBatchOrder(BatchOrder order);
	
	int updateBatchOrder(BatchOrder order);
	
	int deleteBatchOrder(String orderId);
	
	PageInfo<BatchOrder> getBatchOrderPage(int startNum, int pageSize, BatchOrder order);
	
		BatchOrder getBatchOrderByOrderId(String orderId);

	BatchOrder getBatchOrderById(String orderId);
	
	String getOpenIdByPhoneNo(String phoneNo);
}
