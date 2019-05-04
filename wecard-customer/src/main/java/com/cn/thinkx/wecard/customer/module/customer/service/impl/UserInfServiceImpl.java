package com.cn.thinkx.wecard.customer.module.customer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.module.channeluser.mapper.ChannelUserInfMapper;
import com.cn.thinkx.common.wecard.module.user.mapper.UserInfDao;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;

@Service("userInfService")
public class UserInfServiceImpl implements UserInfService {

	@Autowired
	private UserInfDao userInfDao;
	
	@Autowired
	private ChannelUserInfMapper channelUserInfMapper;

	public UserInf getUserInfById(String userId) {
		return userInfDao.getUserInfById(userId);
	}

	public UserInf getUserInfByOpenId(String openId) {
		return userInfDao.getUserInfByOpenId(openId);
	}

	public int insertUserInf(UserInf entity) {
		return userInfDao.insertUserInf(entity);
	}

	public int updateUserInf(UserInf entity) {
		return userInfDao.updateUserInf(entity);
	}

	public UserInf getUserChannelInfByUserId(String channelCode, String userId) {
		return userInfDao.getUserChannelInfByUserId(channelCode, userId);
	}

	@Override
	public List<ChannelUserInf> getAllChannelUsers() {
		return channelUserInfMapper.getAllChannelUsers();
	}

	@Override
	public int updateChannelUserInf(ChannelUserInf entity) {
		return channelUserInfMapper.updateChannelUserInf(entity);
	}

}
