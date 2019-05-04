package com.cn.thinkx.oms.module.enterpriseorder.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.github.pagehelper.PageInfo;

public interface BatchOrderListService {
	
	List<BatchOrderList> getBatchOrderListList(String orderId);
	
	int addBatchOrderList(List<BatchOrderList> list);
	
	PageInfo<BatchOrderList> getBatchOrderListPage(int startNum, int pageSize, String orderId);
	
	int addOrderList(BatchOrderList orderList);
	
	int deleteBatchOrderList(HttpServletRequest req);
	
	int updateBatchOrderList(BatchOrderList orderList);
	
	List<BatchOrderList> getBatchOrderListFailList(String orderId);
	
	BatchOrderList getBatchOrderListByOrderIdAndPhoneNo(BatchOrderList orderList);
	
	BatchOrderList getBatchOrderListByOrderListId(String orderListId);
}
