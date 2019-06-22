package com.cn.thinkx.oms.module.enterpriseorder.mapper;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;

import java.util.List;

public interface BatchWithdrawOrderMapper {

	BatchWithdrawOrder getById(String id);
	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public int insertBatchWithdrawOrder(BatchWithdrawOrder entity);

	/**
	 * 修改产品信息
	 * @param entity
	 * @return
	 */
	public int updateBatchWithdrawOrder(BatchWithdrawOrder entity);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteBatchWithdrawOrder(String id);


	List<BatchWithdrawOrder> getBatchWithdrawOrderList(BatchWithdrawOrder entity);
	
}
