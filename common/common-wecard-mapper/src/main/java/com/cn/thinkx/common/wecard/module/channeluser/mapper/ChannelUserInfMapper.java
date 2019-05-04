package com.cn.thinkx.common.wecard.module.channeluser.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;

public interface ChannelUserInfMapper {

	int insertChannelUserInf(ChannelUserInf channelUserInf);

	/**
	 * 查找所有知了企服渠道用户
	 * 
	 * @return
	 */
	List<ChannelUserInf> getAllChannelUsers();

	int updateChannelUserInf(ChannelUserInf entity);
	
	/**
	 * 根据渠道号跟userID查询最新用户信息
	 * 
	 * @param entity
	 * @return
	 */
	String getExternalId(ChannelUserInf entity);
	
}