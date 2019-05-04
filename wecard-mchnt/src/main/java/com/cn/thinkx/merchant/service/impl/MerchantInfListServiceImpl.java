package com.cn.thinkx.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.merchant.domain.MerchantInfList;
import com.cn.thinkx.merchant.mapper.MerchantInfListDao;
import com.cn.thinkx.merchant.service.MerchantInfListService;

@Service("merchantInfListService")
public class MerchantInfListServiceImpl implements MerchantInfListService {

	@Autowired
	private MerchantInfListDao wxMerchantInfListDao;
	
	
	/**
	 * 根据邀请码查询商户信息明细表
	 * @param inviteCode
	 * @return
	 */
	public MerchantInfList getMerchantInfListByCode(String inviteCode){
		return wxMerchantInfListDao.getMerchantInfListByCode(inviteCode);
	}


	@Override
	public int updateMerchantInfListDateStat(String insId) {
		return wxMerchantInfListDao.updateMerchantInfListDateStat(insId);
	}
}
