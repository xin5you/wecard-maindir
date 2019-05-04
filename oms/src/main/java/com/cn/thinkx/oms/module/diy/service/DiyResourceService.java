package com.cn.thinkx.oms.module.diy.service;

import java.util.List;

import com.cn.thinkx.oms.module.diy.model.DiyResource;
import com.github.pagehelper.PageInfo;

public interface DiyResourceService {

	/**
	 * 通过资源id查询对应的资源信息
	 * 
	 * @param resourceId
	 * @return
	 */
	DiyResource getDiyResourceById(String resourceId);
	/**
	 * 通过资源的key查找对应的信息,key不允许重复
	 * 
	 * @param key
	 * @return
	 */
	DiyResource getDiyResourceByKey(String key);
	
	/**
	 * 插入资源信息
	 * 
	 * @param DiyResource
	 * @return
	 */
	int insertDiyResource(DiyResource diyResource);
	/**
	 * 修改资源信息
	 * 
	 * @param DiyResource
	 * @return
	 */
	int updateDiyResource(DiyResource diyResource);
	
	/**
	 * 删除资源信息
	 * 
	 * @param resourceId
	 * @return
	 */
	int deleteDiyResource(String resourceId);
	
	/**
	 * 查询资源信息列表
	 * 
	 * @param entity
	 * @return
	 */
	List<DiyResource> getDiyResourceList(DiyResource entity);
	
	/**
	 * 根据角色Id获取该用户的权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<DiyResource> getDiyRoleResourceByRoleId(String roleId);

}
