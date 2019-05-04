package com.cn.thinkx.oms.module.cardkeys.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.oms.module.cardkeys.model.WithdrawOrder;

public interface WithdrawOrderMapper {
	
	int updateWithdrawOrder(WithdrawOrder withdrawOrder);
	
	WithdrawOrder getWithdrawOrderByPaidId(@Param("paidId")String paidId);
	
}
