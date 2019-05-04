package com.cn.thinkx.oms.module.customer.service;

import com.cn.thinkx.oms.module.customer.model.UserInf;

public interface UserInfService {

	public UserInf getUserInfById(String userId);
	
	public int insertUserInf(UserInf entity);

	public int updateUserInf(UserInf entity);
	
	/**
	 * 查找用户渠道信息
	 * @param channelCode
	 * @param userId
	 * @return
	 */
	UserInf getUserChannelInfByUserId(String channelCode,String userId);
}
