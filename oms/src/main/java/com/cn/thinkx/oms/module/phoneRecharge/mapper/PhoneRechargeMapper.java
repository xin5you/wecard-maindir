package com.cn.thinkx.oms.module.phoneRecharge.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeOrder;
import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeOrderUpload;

public interface PhoneRechargeMapper {

	/**
	 * 查询手机充值订单信息
	 * 
	 * @return
	 */
	List<PhoneRechargeOrder> getPhoneRechargeList(PhoneRechargeOrder entity);

	List<PhoneRechargeOrderUpload> getPhoneRechargeListUpload(PhoneRechargeOrder entity);

	PhoneRechargeOrder getPhoneRechargeByRid(String rId);

	int updatePhoneRechargeOrder(PhoneRechargeOrder entity);
}
