package com.cn.thinkx.oms.module.enterpriseorder.mapper;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;

import java.util.List;

public interface BatchWithdrawOrderDetailMapper {


	BatchWithdrawOrderDetail getByDetailId(String id);

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public int insertBatchWithdrawOrderDetail(BatchWithdrawOrderDetail entity);


	/**
	 * 修改产品信息
	 * @param entity
	 * @return
	 */
	public int updateBatchWithdrawOrderDetail(BatchWithdrawOrderDetail entity);

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteBatchWithdrawOrderDetail(String id);

	List<BatchWithdrawOrderDetail> getList(BatchWithdrawOrderDetail entity);

}
