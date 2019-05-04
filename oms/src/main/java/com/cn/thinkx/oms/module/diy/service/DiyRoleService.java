package com.cn.thinkx.oms.module.diy.service;

import java.util.List;

import com.cn.thinkx.oms.module.diy.model.DiyRole;
import com.cn.thinkx.oms.module.sys.model.User;
import com.github.pagehelper.PageInfo;

public interface DiyRoleService{
	
	/***
	 * 获取角色
	 * 
	 * @param roleId
	 * @return
	 */
	DiyRole getDiyRoleById(String id);
	
	/**
	 * 添加角色
	 * 
	 * @param entity
	 */
	void insertDiyRole(DiyRole entity);

	/**
	 * 修改角色
	 * @param entity
	 */
	void updateDiyRole(DiyRole entity);
	
	/**
	 * 删除角色
	 * 
	 * @param roleId
	 */
	void deleteDiyRoleById(String id);

	/**
	 * 查询所有角色
	 * 
	 * @param role
	 * @return
	 */
	List<DiyRole> getDiyRoleList(DiyRole role);
	
	/**
	 * 获取某个用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	List<DiyRole> getDiyUserRoleByUserId(String userId);
	

	/**
	 * 查询角色分页查询
	 * @param startNum
	 * @param pageSize
	 * @param role
	 * @return
	 */
    public PageInfo<DiyRole> getDiyRolePage(int startNum, int pageSize, DiyRole diyRole);
    
    /**
     * 角色授权
     * @param roleId
     * @param resourceIds
     * @return
     */
    public void editDiyRoleResource(String id ,String[] resourceIds,User user);
 
}
