package com.cn.thinkx.customer.mapper;

import com.cn.thinkx.customer.domain.AccountInf;

public interface AccountInfDao {

	/**
	 * 获取主账号信息 
	 * @param openid 微信openid
	 * @return
	 */
	public AccountInf getAccountInfByOpenId(String openid);

}