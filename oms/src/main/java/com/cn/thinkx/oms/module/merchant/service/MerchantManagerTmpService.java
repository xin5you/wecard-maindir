package com.cn.thinkx.oms.module.merchant.service;

import java.util.List;

import com.cn.thinkx.oms.module.merchant.model.MerchantManagerTmp;
import com.github.pagehelper.PageInfo;

public interface MerchantManagerTmpService {

	public MerchantManagerTmp getMerchantManagerTmpById(String mId);
	
	public int insertMerchantManagerTmp(MerchantManagerTmp entity);
	
	public int updateMerchantManagerTmp(MerchantManagerTmp entity);
	
	public int deleteMerchantManagerTmp(String entityId);
	
	public List<MerchantManagerTmp> getMerchantManagerTmpList(MerchantManagerTmp entity);
	
	public PageInfo<MerchantManagerTmp> getMerchantManagerTmpPage(int startNum, int pageSize, MerchantManagerTmp entity);
	 
	public MerchantManagerTmp getMchntManagerTmpByPhoneNumber(String phoneNumber);
}
