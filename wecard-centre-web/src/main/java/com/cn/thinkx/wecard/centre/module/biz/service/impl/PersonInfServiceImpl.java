package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.wecard.centre.module.biz.mapper.PersonInfMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.PersonInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("personInfService")
public class PersonInfServiceImpl implements PersonInfService {

	@Autowired
	private PersonInfMapper personInfMapper;

	@Override
	public PersonInf getPersonInfByUserId(String userId) {
		return personInfMapper.getPersonInfByUserId(userId);
	}

}
