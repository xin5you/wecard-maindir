package com.cn.thinkx.wecard.api.module.mchnt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.api.module.mchnt.mapper.MerchantInfMapper;
import com.cn.thinkx.wecard.api.module.mchnt.model.MerchantInf;
import com.cn.thinkx.wecard.api.module.mchnt.service.MerchantInfService;

@Service("merchantInfService")
public class MerchantInfServiceImpl implements MerchantInfService {

	@Autowired
	@Qualifier("merchantInfMapper")
	private MerchantInfMapper merchantInfMapper;
	
	@Override
	public MerchantInf getMerchantInfByMchntCode(String mchntCode) {
		return this.merchantInfMapper.getMerchantInfByMchntCode(mchntCode);
	}

	@Override
	public String getAccountStatByMchntCode(String mchntCode) {
		return this.merchantInfMapper.getAccountStatByMchntCode(mchntCode);
	}

}
