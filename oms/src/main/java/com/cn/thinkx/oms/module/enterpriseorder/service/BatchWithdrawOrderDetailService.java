package com.cn.thinkx.oms.module.enterpriseorder.service;


import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.github.pagehelper.PageInfo;
import org.springframework.ui.ModelMap;

import java.util.List;

public interface BatchWithdrawOrderDetailService {
	
		BatchWithdrawOrderDetail getByDetailId(String id);

		/**
		 * 保存
		 * @param entity
		 * @return
		 */
		public int insertBatchWithdrawOrderDetail(BatchWithdrawOrderDetail entity);


		/**
		 * 修改信息
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

		/**
		 * 分页
		 * @param startNum
		 * @param pageSize
		 * @param entity
		 * @return
		 */
		public PageInfo<BatchWithdrawOrderDetail> getBatchWithdrawOrderDetailPage(int startNum, int pageSize, BatchWithdrawOrderDetail entity);

		/**
		 *  联行获取
		 * @param list
		 */
		ModelMap getCanps(List<BatchWithdrawOrderDetail>  list);

	   List<BatchWithdrawOrderDetail> getList(BatchWithdrawOrderDetail entity);
}
