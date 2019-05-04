package com.cn.thinkx.wecard.api.module.phoneRecharge.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.wecard.api.module.phoneRecharge.model.PhoneRechargeOrder;

public interface PhoneRechargeMapper {
	
	PhoneRechargeOrder getPhoneRechargeOrderById(@Param("rId")String rId);
	
	int insertPhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder);
	
	int updatePhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder);	
	
}
