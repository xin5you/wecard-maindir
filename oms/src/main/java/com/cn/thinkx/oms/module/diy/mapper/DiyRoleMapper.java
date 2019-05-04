package com.cn.thinkx.oms.module.diy.mapper;



import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.diy.model.DiyRole;
import com.cn.thinkx.oms.module.diy.model.DiyRoleResource;


@Repository("diyRoleMapper")
public interface DiyRoleMapper  {
	
	/**
	 * 通过id查找角色信息
	 * 
	 * @param id
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
	 * 
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
	 * @param role
	 * @return
	 */
	List<DiyRole> getDiyRoleList(DiyRole DiyRole);
	
	/**
	 * 获取某个用户的角色
	 * @param userId
	 * @return
	 */
	List<DiyRole> getDiyUserRoleByUserId(String userId);
	
	
	/**
	 * 删除用户角色
	 * @param entity
	 */
	void deleteDiyUserRoleByRoleId(String id);
	
	/**
	 * 删除该角色对应的资源
	 * 
	 * @param entity
	 */
	void deleteDiyRoleResourceByRoleId(String id);
	
	
	void saveDiyRoleResource(DiyRoleResource diyRoleResource);
}