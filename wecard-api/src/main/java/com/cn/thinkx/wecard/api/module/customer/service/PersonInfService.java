package com.cn.thinkx.wecard.api.module.customer.service;

import com.cn.thinkx.wecard.api.module.customer.model.PersonInf;

public interface PersonInfService {

	
	public PersonInf findPersonInfById(String personId);
	
	/**
	 * 根据openid查询用户
	 * @param openid
	 * @return
	 */
	PersonInf getPersonInfByOpenId(String openid);
}
