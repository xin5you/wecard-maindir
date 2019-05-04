package com.cn.thinkx.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.customer.domain.UserInf;
import com.cn.thinkx.customer.mapper.UserInfDao;
import com.cn.thinkx.customer.service.UserInfService;

@Service("userInfService")
public class UserInfServiceImpl implements UserInfService {


	
	@Autowired
	private UserInfDao userInfDao;

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

}
