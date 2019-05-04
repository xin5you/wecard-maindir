package com.cn.thinkx.oms.module.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.oms.module.customer.model.PersonInf;

public interface PersonInfMapper {

	/**
	 * 查询用户信息
	 * 
	 * @param personId
	 * @return
	 */
	public PersonInf getPersonInfById(String personId);

	/**
	 * 插入用户信息
	 * 
	 * @param entity
	 * @return
	 */
	public int insertPersonInf(PersonInf entity);

	/**
	 * 修改用户信息
	 * 
	 * @param entity
	 * @return
	 */
	public int updatePersonInf(PersonInf entity);

	/**
	 * 根据手机号和渠道查找用户
	 * 
	 * @param phoneNo
	 * @param channel
	 * @return
	 */
	public PersonInf getPersonInfByPhoneAndChnl(@Param("phoneNo") String phoneNo, @Param("channel") String channel);

	/**
	 * 根据手机号查找用户
	 * 
	 * @param phoneNo
	 * @return
	 */
	public PersonInf getPersonInfByPhoneNo(String phoneNo);

	/**
	 * 用户信息
	 * 
	 * @return
	 */
	public List<PersonInf> getPersonInfList(PersonInf personInf);

	/**
	 * 通过用户id查看用户
	 * 
	 * @param userId
	 * @return
	 */
	public PersonInf getPersonInfByUserId(String userId);

}