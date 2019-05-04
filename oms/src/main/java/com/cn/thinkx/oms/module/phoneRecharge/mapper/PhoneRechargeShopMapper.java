package com.cn.thinkx.oms.module.phoneRecharge.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeShop;

public interface PhoneRechargeShopMapper {

	List<PhoneRechargeShop> getPhoneRechargeShopList(PhoneRechargeShop pps);
	
	int insertPhoneRechargeShop(PhoneRechargeShop pps);
	
	int updatePhoneRechargeShop(PhoneRechargeShop pps);
	
	int deletePhoneRechargeShop(String id);
	
	PhoneRechargeShop getPhoneRechargeShopById(String id);
	
	PhoneRechargeShop getPhoneRechargeShopByPhoneRechargeShop(PhoneRechargeShop pps);
	
	List<PhoneRechargeShop> getShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
	
	List<PhoneRechargeShop> getYDShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
	
	List<PhoneRechargeShop> getLTShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
	
	List<PhoneRechargeShop> getDXShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
}
