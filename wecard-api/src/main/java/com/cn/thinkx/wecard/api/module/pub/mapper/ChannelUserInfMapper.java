package com.cn.thinkx.wecard.api.module.pub.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wecard.api.module.pub.model.ChannelUserInf;

@Repository("channelUserInfMapper")
public interface ChannelUserInfMapper {

	/**
	 * 根据渠道号跟userID查询最新用户信息
	 * 
	 * @param entity
	 * @return
	 */
	String getExternalId (ChannelUserInf entity);
	
}