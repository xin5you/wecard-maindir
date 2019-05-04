package com.cn.thinkx.oms.module.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.customer.mapper.UserInfMapper;
import com.cn.thinkx.oms.module.customer.model.UserInf;
import com.cn.thinkx.oms.module.customer.service.UserInfService;

@Service("userInfService")
public class UserInfServiceImpl implements UserInfService {

	
	@Autowired
	private UserInfMapper userInfMapper;

	public UserInf getUserInfById(String userId) {
		return userInfMapper.getUserInfById(userId);
	}


	public int insertUserInf(UserInf entity) {
		return userInfMapper.insertUserInf(entity);
	}

	public int updateUserInf(UserInf entity) {
		return userInfMapper.updateUserInf(entity);
	}
	
	/**
	 * 查找用户渠道信息
	 * @param channelCode
	 * @param userId
	 * @return
	 */
	public UserInf getUserChannelInfByUserId(String channelCode,String userId){
		return userInfMapper.getUserChannelInfByUserId(channelCode, userId);
	}

}
