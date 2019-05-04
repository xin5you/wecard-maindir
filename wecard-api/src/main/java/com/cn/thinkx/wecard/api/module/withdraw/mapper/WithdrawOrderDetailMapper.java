package com.cn.thinkx.wecard.api.module.withdraw.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrderDetail;

public interface WithdrawOrderDetailMapper {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	int getCountBySerialNo(@Param("serialNo")String serialNo);
	
	int insertWithdrawOrderDetail(WithdrawOrderDetail withdrawOrderDetail);
	
	int updateWithdrawOrderDetail(WithdrawOrderDetail withdrawOrderDetail);
	
}
