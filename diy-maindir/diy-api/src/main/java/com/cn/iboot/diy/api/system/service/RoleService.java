package com.cn.iboot.diy.api.system.service;
import java.util.List;

import com.cn.iboot.diy.api.base.service.BaseService;
import com.cn.iboot.diy.api.system.domain.Role;

public interface RoleService extends BaseService<Role> {

	/**
	 * 根据用户编号查询用户角色信息
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
	 * 查询所有的角色信息
	 * 
	 * @return
	 */
	List<Role> getRoleList();

}
