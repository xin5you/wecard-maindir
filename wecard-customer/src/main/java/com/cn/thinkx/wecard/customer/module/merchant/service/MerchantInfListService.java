package com.cn.thinkx.wecard.customer.module.merchant.service;

import com.cn.thinkx.common.wecard.domain.merchant.MerchantInfList;

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
