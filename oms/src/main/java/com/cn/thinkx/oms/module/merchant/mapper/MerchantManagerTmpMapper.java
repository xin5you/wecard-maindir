package com.cn.thinkx.oms.module.merchant.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.MerchantManagerTmp;

@Repository("merchantManagerTmpMapper")
public interface MerchantManagerTmpMapper {
	
	public MerchantManagerTmp getMerchantManagerTmpById(String mId);
	
	public int insertMerchantManagerTmp(MerchantManagerTmp entity);
	
	public int updateMerchantManagerTmp(MerchantManagerTmp entity);
	
	public int deleteMerchantManagerTmp(String entityId);
	
	public List<MerchantManagerTmp> getMerchantManagerTmpList(MerchantManagerTmp entity);
	
	public MerchantManagerTmp getMchntManagerTmpByPhoneNumber(String phoneNumber);
}