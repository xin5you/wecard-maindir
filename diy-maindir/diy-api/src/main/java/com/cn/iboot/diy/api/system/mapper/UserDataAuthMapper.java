package com.cn.iboot.diy.api.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.system.domain.UserDataAuth;

@Mapper
public interface UserDataAuthMapper extends BaseDao<UserDataAuth> {

	/**
	 * 根据用户编号查询用户数据权限信息
	 * 
	 * @param userId
	 * @return
	 */
	UserDataAuth getUserDataAuthByUserId(String userId);
	
	/**
	 * 根据权限编号删除用户数据权限信息
	 * 
	 * @param authId
	 * @return
	 */
	int deleteUserDataAuthByAuthId(String authId);
}
