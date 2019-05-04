package com.cn.thinkx.wecard.customer.module.phonerecharge.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeOrder;
import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeShop;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;

public interface PhoneRechargeService {
	
	PhoneRechargeOrder getPhoneRechargeOrderById(@Param("rId")String rId);
	
	PhoneRechargeOrder insertPhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder);
	
	int updatePhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder);
	
	List<PhoneRechargeOrder> getPhoneRechargeOrderList(PhoneRechargeOrder phoneRechargeOrder);
	
	/**
	 * 手机号校验
	 * 
	 * @param request
	 * @return
	 */
	List<PhoneRechargeShop> phoneRechargeMobileValid(HttpServletRequest request) throws Exception;
	
	/**
	 * 手机充值跳转收银台
	 * 
	 * @param request
	 * @throws Exception
	 */
	TransOrderReq toPhoneRechargeUnifiedOrder(HttpServletRequest request) throws Exception;
	
}
