package com.cn.thinkx.wecard.centre.module.biz.service;

import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.user.UserInf;

public interface PersonInfService {

	/**
	 * 获取用户个人信息
	 * @param userId userId
	 * @return PersonInf
	 */
	PersonInf getPersonInfByUserId(String userId);

}
