package com.cn.thinkx.oms.module.enterpriseorder.service;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.sys.model.User;
import com.github.pagehelper.PageInfo;

public interface BatchOrderService {
	
	List<BatchOrder> getBatchOrderList(BatchOrder order);
	
	int addBatchOrder(BatchOrder order);
	
	int updateBatchOrder(BatchOrder order);
	
	int deleteBatchOrder(String orderId);
	
	PageInfo<BatchOrder> getBatchOrderPage(int startNum, int pageSize, BatchOrder order,HttpServletRequest req);
	
	int addBatchOrder(BatchOrder order ,LinkedList<BatchOrderList> personInfList);
	
	BatchOrder getBatchOrderByOrderId(String orderId);
	
	BatchOrder getBatchOrderById(String orderId);
	
	/**
	 * 批量开户
	 * @param orderId 订单
	 * @param user 用户 
	 */
	void batchOpenAccountITF(String orderId,User user,String commitStat);
	
	/**
	 * 批量开卡
	 * @param orderId 订单
	 * @param user 用户 
	 */
	void batchOpenCardITF(String orderId,User user,String commitStat);
	
	
	/**
	 * 批量充值
	 * @param orderId 订单
	 * @param user 用户 
	 */
	void batchRechargeCardITF(String orderId,User user,String commitStat);
	
	/**
	 * 批量修改限额
	 * @param orderId
	 * @param user
	 */
	void batchUpdateQuotaITF(String orderId ,String maxAmt, User user);
}
