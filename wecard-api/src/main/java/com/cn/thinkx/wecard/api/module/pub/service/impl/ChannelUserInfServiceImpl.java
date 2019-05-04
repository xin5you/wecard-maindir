package com.cn.thinkx.wecard.api.module.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.api.module.pub.mapper.ChannelUserInfMapper;
import com.cn.thinkx.wecard.api.module.pub.model.ChannelUserInf;
import com.cn.thinkx.wecard.api.module.pub.service.ChannelUserInfService;

@Service("channelUserInfService")
public class ChannelUserInfServiceImpl implements ChannelUserInfService {
	
	@Autowired
	private ChannelUserInfMapper channelUserInfMapper;

	@Override
	public String getExternalId(ChannelUserInf entity) {
		return this.channelUserInfMapper.getExternalId(entity);
	}

}