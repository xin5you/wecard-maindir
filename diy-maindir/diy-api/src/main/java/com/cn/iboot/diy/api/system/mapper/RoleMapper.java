package com.cn.iboot.diy.api.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.system.domain.Role;

@Mapper
public interface RoleMapper extends BaseDao<Role> {

	/**
	 * 获取某个用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> getUserRoleByUserId(String userId);
	
	/**
	 * 根据用户编号查询角色名称
	 * @param userId
	 * @return
	 */
	String getRoleNameByUserId(String userId);
	
	/**
	 * 查询所有的角色信息(不包含最高管理员)
	 * 
	 * @return
	 */
	List<Role> getRoleList();
	
}
