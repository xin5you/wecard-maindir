package com.cn.thinkx.oms.module.sys.service;

import java.util.List;

import com.cn.thinkx.oms.module.sys.model.Role;
import com.cn.thinkx.oms.module.sys.model.UserRole;
import com.github.pagehelper.PageInfo;

public interface RoleService{
	
	/***
	 * 获取角色
	 * @param roleId
	 * @return
	 */
	Role getRoleById(String roleId);
	
	/**
	 * 添加角色
	 * @param entity
	 */
	void insertRole(Role entity);

	/**
	 * 修改角色
	 * @param entity
	 */
	void updateRole(Role entity);
	
	/**
	 * 删除角色
	 * @param roleId
	 */
	void deleteRoleById(String roleId);

	/**
	 * 查询所有角色
	 * @param role
	 * @return
	 */
	List<Role> getRoleList(Role role);
	
	/**
	 * 获取某个用户的角色
	 * @param userId
	 * @return
	 */
	List<Role> getUserRoleByUserId(String userId);
	

	/**
	 * 查询角色分页查询
	 * @param startNum
	 * @param pageSize
	 * @param role
	 * @return
	 */
    public PageInfo<Role> getRolePage(int startNum, int pageSize, Role role);
    
    /**
     * 角色授权
     * @param roleId
     * @param resourceIds
     * @return
     */
    public void editRoleResource(String roleId ,String[] resourceIds);
 
}
