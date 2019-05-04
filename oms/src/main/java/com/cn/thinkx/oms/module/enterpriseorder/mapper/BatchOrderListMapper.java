package com.cn.thinkx.oms.module.enterpriseorder.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.github.pagehelper.PageInfo;

public interface BatchOrderListMapper {
	List<BatchOrderList> getBatchOrderListList(String orderId);
	
	int addBatchOrderList(List<BatchOrderList> list);
	
	PageInfo<BatchOrderList> getBatchOrderListPage(int startNum, int pageSize, String orderId);
	
	int addOrderList(BatchOrderList orderList);
	
	int deleteBatchOrderList(String orderListId);
	
	int updateBatchOrderList(BatchOrderList orderList);
	
	List<BatchOrderList> getBatchOrderListFailList(String orderId);
	
	BatchOrderList getBatchOrderListByOrderIdAndPhoneNo(BatchOrderList orderList);
	
	BatchOrderList getBatchOrderListByOrderListId(String orderListId);
}
