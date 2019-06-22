package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchWithdrawOrderMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("batchWithdrawOrderService")
public class BatchWithdrawOrderServiceImpl implements BatchWithdrawOrderService {

	@Autowired
	private BatchWithdrawOrderMapper batchWithdrawOrderMapper;

	@Autowired
	private BatchWithdrawOrderDetailService batchWithdrawOrderDetailService;

	@Override
	public BatchWithdrawOrder getById(String id) {
		return batchWithdrawOrderMapper.getById(id);
	}

  /**
	 * 保存
	 * @param entity
	 * @return
	 */
	public int insertBatchWithdrawOrder(BatchWithdrawOrder entity, List<BatchWithdrawOrderDetail> details){
		//批量订单Id
		Snowflake snowflake=IdUtil.getSnowflake(1,1);
		String batchOrderId=snowflake.nextIdStr();

		BatchWithdrawOrderDetail detail=null;
		for (int i=0;i<details.size();i++){
			detail=details.get(i);
			detail.setDetailId(snowflake.nextIdStr());
			detail.setOrderId(batchOrderId);
			//手续费先为0
			detail.setFee(new BigDecimal(0));
			detail.setDataStat("0");
			batchWithdrawOrderDetailService.insertBatchWithdrawOrderDetail(detail);
		}
		entity.setOrderId(batchOrderId);
		entity.setTotalNum(details.size());
		entity.setStat("00"); //初始化新建
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setLockVersion(0);
		return batchWithdrawOrderMapper.insertBatchWithdrawOrder(entity);
	}

	@Override
	public int updateBatchWithdrawOrder(BatchWithdrawOrder entity) {
		return batchWithdrawOrderMapper.updateBatchWithdrawOrder(entity);
	}

	@Override
	public int deleteBatchWithdrawOrder(String id) {
		return batchWithdrawOrderMapper.deleteBatchWithdrawOrder(id);
	}

	public List<BatchWithdrawOrder> getBatchWithdrawOrderList(BatchWithdrawOrder entity) {
		return batchWithdrawOrderMapper.getBatchWithdrawOrderList(entity);
	}
	@Override
	public PageInfo<BatchWithdrawOrder> getBatchWithdrawOrderPage(int startNum, int pageSize, BatchWithdrawOrder entity) {
		PageHelper.startPage(startNum, pageSize);
        List<BatchWithdrawOrder> list = getBatchWithdrawOrderList(entity);
        PageInfo<BatchWithdrawOrder> page = new PageInfo<BatchWithdrawOrder>(list);
        return page;
	}
}
