package com.cn.thinkx.common.wecard.module.phoneRecharge.mapper;

import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeShop;

public interface PhoneRechargeShopMapper {

	
	PhoneRechargeShop getPhoneRechargeShopById(String id);
	
	PhoneRechargeShop getPhoneRechargeShopByRechargeShop(PhoneRechargeShop phoneRechargeShop);
	
}
