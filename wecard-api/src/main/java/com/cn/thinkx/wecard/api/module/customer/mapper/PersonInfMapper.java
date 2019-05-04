package com.cn.thinkx.wecard.api.module.customer.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wecard.api.module.customer.model.PersonInf;

@Repository("personInfMapper")
public interface PersonInfMapper {
	
	public PersonInf getPersonInfById(String personId);
	
	/**
	 * 根据openid查询用户
	 * @param openid
	 * @return
	 */
	PersonInf getPersonInfByOpenId(String openid);
}