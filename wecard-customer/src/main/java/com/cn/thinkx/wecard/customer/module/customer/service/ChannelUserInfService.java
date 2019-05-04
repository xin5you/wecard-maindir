package com.cn.thinkx.wecard.customer.module.customer.service;

import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;

public interface ChannelUserInfService {

	/**
	 * 根据渠道号跟userID查询最新用户信息
	 * 
	 * @param entity
	 * @return
	 */
	String getExternalId (ChannelUserInf entity);
}
