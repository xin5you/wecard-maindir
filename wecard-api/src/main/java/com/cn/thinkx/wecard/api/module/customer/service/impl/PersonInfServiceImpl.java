package com.cn.thinkx.wecard.api.module.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.api.module.customer.mapper.PersonInfMapper;
import com.cn.thinkx.wecard.api.module.customer.model.PersonInf;
import com.cn.thinkx.wecard.api.module.customer.service.PersonInfService;

@Service("personInfService")
public class PersonInfServiceImpl implements PersonInfService {

	@Autowired
	@Qualifier("personInfMapper")
	private PersonInfMapper personInfMapper;

	/**
	 * 获取用户个人信息
	 * @param userId
	 * @return
	 */
	public PersonInf findPersonInfById(String personId){
		return personInfMapper.getPersonInfById(personId);
	}



	@Override
	public PersonInf getPersonInfByOpenId(String openid) {
		return personInfMapper.getPersonInfByOpenId(openid);
	}
}
