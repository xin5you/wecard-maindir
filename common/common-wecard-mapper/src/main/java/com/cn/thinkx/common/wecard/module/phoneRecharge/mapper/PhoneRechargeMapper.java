package com.cn.thinkx.common.wecard.module.phoneRecharge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeOrder;

public interface PhoneRechargeMapper {
	
	PhoneRechargeOrder getPhoneRechargeOrderById(@Param("rId")String rId);
	
	int insertPhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder);
	
	int updatePhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder);
	
	List<PhoneRechargeOrder> getPhoneRechargeOrderList(PhoneRechargeOrder phoneRechargeOrder);
	
}
