package com.cn.thinkx.oms.module.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.MerchantInfListMapper;
import com.cn.thinkx.oms.module.merchant.model.MerchantInfList;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfListService;

@Service("merchantInfListService")
public class MerchantInfListServiceImpl implements MerchantInfListService {

	@Autowired
	@Qualifier("merchantInfListMapper")
	private MerchantInfListMapper merchantInfListMapper;

	
	public MerchantInfList getMerchantInfoListByMchntId(String entityId) {
		return merchantInfListMapper.getMerchantInfoListByMchntId(entityId);
	}

	
	public int insertMerchantInfoList(MerchantInfList entity) {
		return merchantInfListMapper.insertMerchantInfoList(entity);
	}

	
	public int updateMerchantInfoList(MerchantInfList entity) {
		
		return merchantInfListMapper.updateMerchantInfoList(entity);
	}
	
	public MerchantInfList getMerchantInfListByCode(String inviteCode){
		return merchantInfListMapper.getMerchantInfListByCode(inviteCode);
	}


	@Override
	public int deleteMerchantInfList(String mchntId) {
		return merchantInfListMapper.deleteMerchantInfList(mchntId);
	}
	

}
