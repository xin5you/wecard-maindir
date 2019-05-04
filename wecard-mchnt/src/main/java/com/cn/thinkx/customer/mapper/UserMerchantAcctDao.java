package com.cn.thinkx.customer.mapper;

import java.util.List;

import com.cn.thinkx.customer.domain.UserMerchantAcct;

public interface UserMerchantAcctDao {

	/**
	 * 获取客户的卡产品信息 所属的机构和 商户信息
	 * @param entity 
	 * @return
	 */
	public List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct entity);
	
	
	/**
	 * 获取商户下所有的卡
	 * @param entity
	 * @return
	 */
	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct entity);
}