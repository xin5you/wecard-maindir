package com.cn.thinkx.customer.mapper;

import com.cn.thinkx.customer.domain.PersonInf;

public interface PersonInfDao {

	public PersonInf getPersonInfByUserId(String userId);
	
	public PersonInf getPersonInfById(String personId);
	
	public int insertPersonInf(PersonInf entity);
	
	public int updatePersonInf(PersonInf entity);
	
	public String getPhoneNumberByOpenId(String openid);
	
	public PersonInf getPersonInfByAccountNo(String accountNo);
}