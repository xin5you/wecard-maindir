package com.cn.thinkx.wecard.api.module.withdraw.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrder;

public interface WithdrawOrderMapper {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	WithdrawOrder getWithdrawOrderById(@Param("batchNo")String batchNo);
	
	int getCountByBatchNo(@Param("batchNo")String batchNo);
	
	int getCountBySerialNo(@Param("serialNo")String serialNo);
	
	int insertWithdrawOrder(WithdrawOrder withdrawOrder);
	
	int updateWithdrawOrder(WithdrawOrder withdrawOrder);
	
	WithdrawOrder getWithdrawOrderByPaidId(@Param("paidId")String paidId);
	
}
