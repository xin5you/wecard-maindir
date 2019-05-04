package com.cn.thinkx.customer.service;

import com.cn.thinkx.customer.domain.UserInf;

public interface UserInfService {

	public UserInf getUserInfById(String userId);
	
	public UserInf getUserInfByOpenId(String openId);
	
	public int insertUserInf(UserInf entity);

	public int updateUserInf(UserInf entity);
}
