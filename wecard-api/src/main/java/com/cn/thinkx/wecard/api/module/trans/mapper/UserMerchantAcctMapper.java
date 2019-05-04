package com.cn.thinkx.wecard.api.module.trans.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wecard.api.module.trans.model.UserMerchantAcct;

@Repository("userMerchantAcctMapper")
public interface UserMerchantAcctMapper {

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