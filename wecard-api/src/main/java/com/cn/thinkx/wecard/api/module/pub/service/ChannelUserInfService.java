package com.cn.thinkx.wecard.api.module.pub.service;

import com.cn.thinkx.wecard.api.module.pub.model.ChannelUserInf;

public interface ChannelUserInfService {

	/**
	 * 根据渠道号跟userID查询最新用户信息
	 * 
	 * @param entity
	 * @return
	 */
	String getExternalId (ChannelUserInf entity);
}
