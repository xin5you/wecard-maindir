package com.cn.thinkx.common.wecard.module.person.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.person.PersonInf;

public interface PersonInfDao {

	public PersonInf getPersonInfByUserId(String userId);
	
	public PersonInf getPersonInfById(String personId);
	
	public int insertPersonInf(PersonInf entity);
	
	public int updatePersonInf(PersonInf entity);
	
	public String getPhoneNumberByOpenId(String openid);
	
	public PersonInf getPersonInfByAccountNo(String accountNo);
	
	/**
	 * 根据手机号和渠道查找用户
	 * @param phoneNo
	 * @param channel
	 * @return
	 */
	public PersonInf getPersonInfByPhoneAndChnl(@Param("phoneNo")String phoneNo,@Param("channel")String channel);
	
	
	/**
	 * 根据手机号查找用户
	 * @param phoneNo
	 * @return
	 */
	public PersonInf getPersonInfByPhoneNo(String phoneNo);
	
	/**
	 * 根据openid查询用户（渠道号为40001010）
	 * @param openid
	 * @return
	 */
	PersonInf getPersonInfByOpenId(String openid);
	
}