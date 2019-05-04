package com.cn.thinkx.oms.module.margin.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.margin.model.MerchantCashManage;

@Repository("merchantCashManageMapper")
public interface MerchantCashManageMapper {

	
	public MerchantCashManage getMchntCashManagetById(String id);
	
	public int insertMchntCashManaget(MerchantCashManage entity);
	
	public int updateMchntCashManage(MerchantCashManage entity);
	
	public List<MerchantCashManage> getMerchantCashManageList(MerchantCashManage entity);
	
	public int deleteMerchantCashManage(String id);
	
	
	public MerchantCashManage getMerchantCashManageByMchntId(String mchntId);
}