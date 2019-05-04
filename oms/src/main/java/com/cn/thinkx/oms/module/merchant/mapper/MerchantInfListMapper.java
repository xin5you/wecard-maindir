package com.cn.thinkx.oms.module.merchant.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.MerchantInfList;

@Repository("merchantInfListMapper")
public interface MerchantInfListMapper {

	
	public MerchantInfList getMerchantInfoListByMchntId(String entityId);
	
	public int insertMerchantInfoList(MerchantInfList entity);
	
	public int updateMerchantInfoList(MerchantInfList entity);
	
	public MerchantInfList getMerchantInfListByCode(String code);
	
	public int deleteMerchantInfList(String mchntId);
}