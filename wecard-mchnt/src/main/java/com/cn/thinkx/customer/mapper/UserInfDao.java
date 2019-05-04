package com.cn.thinkx.customer.mapper;

import com.cn.thinkx.customer.domain.UserInf;

public interface UserInfDao {

	
	public UserInf getUserInfById(String userId);
	
	public UserInf getUserInfByOpenId(String openId);
	
	public int insertUserInf(UserInf entity);

	public int updateUserInf(UserInf entity);
	
}