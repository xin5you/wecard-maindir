package com.cn.thinkx.oms.module.merchant.service;

import com.cn.thinkx.oms.module.merchant.model.MerchantInfList;

public interface MerchantInfListService {

	public MerchantInfList getMerchantInfoListByMchntId(String entityId);
	
	public int insertMerchantInfoList(MerchantInfList entity);
	
	public int updateMerchantInfoList(MerchantInfList entity);
	
	public MerchantInfList getMerchantInfListByCode(String inviteCode);
	
	public int deleteMerchantInfList(String mchntId);
}
