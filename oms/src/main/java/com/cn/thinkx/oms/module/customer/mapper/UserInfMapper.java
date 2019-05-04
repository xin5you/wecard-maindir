package com.cn.thinkx.oms.module.customer.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.oms.module.customer.model.UserInf;

public interface UserInfMapper {

	
	public UserInf getUserInfById(String userId);
	
	public int insertUserInf(UserInf entity);

	public int updateUserInf(UserInf entity);
	
	/**
	 * 查找用户渠道信息
	 * @param channelCode
	 * @param userId
	 * @return
	 */
	UserInf getUserChannelInfByUserId(@Param("channelCode")String channelCode,@Param("userId") String userId);
	
}