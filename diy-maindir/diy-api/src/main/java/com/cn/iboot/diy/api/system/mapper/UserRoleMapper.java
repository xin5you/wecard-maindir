package com.cn.iboot.diy.api.system.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.system.domain.UserRole;

@Mapper
public interface UserRoleMapper extends BaseDao<UserRole> {

	/**
	 * 通过用户id查询用户角色表信息
	 * 
	 * @param userId
	 */
	List<UserRole> getUserRoleByUserId(String userId);
	
	/**
	 * 根据用户编号删除用户角色信息
	 * 
	 * @param userId
	 * @return
	 */
	int deleteUserRoleByUserId(String userId);
	
	/**
	 * 修改用户角色
	 * 
	 * @param userRole
	 * @return
	 */
	int updateUserRole(UserRole userRole);
}
