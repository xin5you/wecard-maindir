package com.cn.thinkx.oms.module.customer.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.customer.model.UserMerchantAcct;


public interface UserMerchantAcctMapper {

	/**
	 * 获取商户下所有的卡
	 * @param entity
	 * @return
	 */
	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct userMerchantAcct);
	
	/**
	 * 获取所有卡下的余额
	 * 
	 * @param userMerchantAcct
	 * @return
	 */
	List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct userMerchantAcct);
}