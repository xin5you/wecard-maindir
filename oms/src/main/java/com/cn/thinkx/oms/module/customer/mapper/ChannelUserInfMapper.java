package com.cn.thinkx.oms.module.customer.mapper;

import com.cn.thinkx.oms.module.customer.model.ChannelUserInf;

public interface ChannelUserInfMapper {

	int insertChannelUserInf (ChannelUserInf channelUserInf);
	
	/**
	 * 删除渠道用户表（根据用户id）
	 * 
	 * @param userId
	 */
	void deldteChannelUserInfByUserId(ChannelUserInf channelUserInf);
	
}