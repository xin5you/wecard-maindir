package com.cn.thinkx.oms.module.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.sys.model.Resource;


@Repository("resourceMapper")
public interface ResourceMapper {

	Resource getResourceById(String resourceId);
	
	Resource getResourceByKey(String key);
	
	int insertResource(Resource entity);

	int updateResource(Resource entity);
	
	int deleteResource(String resourceId);

	List<Resource> getResourceList(Resource entity);
	
	//<!-- 根据用户Id获取该用户的权限-->
	public List<Resource> getRoleResourceByRoleId(String roleId);
	
	/***
	 * 根据用户Id 查询对应的所有资源
	 * @param userId
	 * @return
	 */
	public List<Resource> getUserResourceByUserId(String userId);
	
	int deleteRoleResourceByResId(String resId);
	

}