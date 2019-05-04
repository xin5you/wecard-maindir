package com.cn.thinkx.merchant.service;

import com.cn.thinkx.merchant.domain.MerchantInfList;

public interface MerchantInfListService {

	/**
	 * 根据邀请码查询商户信息明细表
	 * @param inviteCode
	 * @return
	 */
	public MerchantInfList getMerchantInfListByCode(String inviteCode);
	
	/**
	 * 更改商户介绍明细为
	 * @param insId
	 * @return
	 */
	public int updateMerchantInfListDateStat(String insId);
}
