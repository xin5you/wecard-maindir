package com.cn.thinkx.merchant.mapper;

import com.cn.thinkx.merchant.domain.MerchantInfList;

public interface MerchantInfListDao {

	/**
	 * 根据邀请码查询商户信息明细表
	 * @param inviteCode
	 * @return
	 */
	public MerchantInfList getMerchantInfListByCode(String inviteCode);
	
	/***更改状态为不可用**/
	public int updateMerchantInfListDateStat(String insId);
}