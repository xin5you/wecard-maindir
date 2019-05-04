package com.cn.thinkx.common.wecard.module.user.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.user.UserInf;

public interface UserInfDao {

	UserInf getUserInfById(String userId);

	UserInf getUserInfByOpenId(String openId);

	int insertUserInf(UserInf entity);

	int updateUserInf(UserInf entity);

	UserInf getUserChannelInfByUserId(@Param("channelCode") String channelCode, @Param("userId") String userId);

}