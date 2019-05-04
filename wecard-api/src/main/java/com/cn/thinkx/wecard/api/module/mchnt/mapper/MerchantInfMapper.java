package com.cn.thinkx.wecard.api.module.mchnt.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wecard.api.module.mchnt.model.MerchantInf;

@Repository("merchantInfMapper")
public interface MerchantInfMapper {

	/**
	 * 查找商户信息
	 * @param mchntCode 商户号
	 * @return
	 */
	public MerchantInf getMerchantInfByMchntCode(String mchntCode);
	
	/**
	 * 查询商户是否已开户
	 * 
	 * @param mchntCode
	 * @return
	 */
	public String getAccountStatByMchntCode(String mchntCode);
	
}