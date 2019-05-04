package com.cn.thinkx.oms.module.phoneRecharge.service;

import java.util.List;

import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeShop;
import com.github.pagehelper.PageInfo;

public interface PhoneRechargeShopService {

	List<PhoneRechargeShop> getPhoneRechargeShopList(PhoneRechargeShop pps);
	
	int insertPhoneRechargeShop(PhoneRechargeShop pps);
	
	int updatePhoneRechargeShop(PhoneRechargeShop pps);
	
	int deletePhoneRechargeShop(String id);
	
	PageInfo<PhoneRechargeShop> getPhoneRechargeShopPage(int startNum, int pageSize, PhoneRechargeShop entity);
	
	PhoneRechargeShop getPhoneRechargeShopById(String id);
	
	List<PhoneRechargeShop> getShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
	
	List<PhoneRechargeShop> getYDShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
	
	List<PhoneRechargeShop> getLTShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
	
	List<PhoneRechargeShop> getDXShopFaceByPhoneRechargeShop(PhoneRechargeShop pps);
	
	PhoneRechargeShop getPhoneRechargeShopByPhoneRechargeShop(PhoneRechargeShop pps);
}
