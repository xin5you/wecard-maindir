package com.cn.thinkx.wecard.customer.module.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;
import com.cn.thinkx.common.wecard.module.channeluser.mapper.ChannelUserInfMapper;
import com.cn.thinkx.wecard.customer.module.customer.service.ChannelUserInfService;

@Service("channelUserInfService")
public class ChannelUserInfServiceImpl implements ChannelUserInfService {
	
	@Autowired
	private ChannelUserInfMapper channelUserInfMapper;

	@Override
	public String getExternalId(ChannelUserInf entity) {
		return this.channelUserInfMapper.getExternalId(entity);
	}

}
