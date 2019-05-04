package com.cn.thinkx.wecard.customer.module.customer.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;
import com.cn.thinkx.common.wecard.domain.user.UserInf;

public interface UserInfService {

	UserInf getUserInfById(String userId);
	
	UserInf getUserInfByOpenId(String openId);
	
	int insertUserInf(UserInf entity);

	int updateUserInf(UserInf entity);
	
	/**
	 * 查找用户渠道信息
	 * @param channelCode
	 * @param userId
	 * @return
	 */
	UserInf getUserChannelInfByUserId(String channelCode,String userId);
	
	/**
	 * 查找所有知了企服渠道用户
	 * 
	 * @return
	 */
	List<ChannelUserInf> getAllChannelUsers();
	
	/**
	 * 更新渠道用户
	 * 
	 * @param entity
	 * @return
	 */
	int updateChannelUserInf(ChannelUserInf entity);
}
