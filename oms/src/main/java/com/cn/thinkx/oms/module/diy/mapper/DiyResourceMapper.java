package com.cn.thinkx.oms.module.diy.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.diy.model.DiyResource;



@Repository("diyResourceMapper")
public interface DiyResourceMapper {

	DiyResource getDiyResourceById(String resourceId);
	
	DiyResource getDiyResourceByKey(String key);
	
	int insertDiyResource(DiyResource entity);

	int updateDiyResource(DiyResource entity);
	
	int deleteDiyResource(String resourceId);
	
	int deleteDiyRoleResourceByResId(String resId);

	List<DiyResource> getDiyResourceList(DiyResource entity);
	
	//<!-- 根据角色Id获取该用户的权限-->
	public List<DiyResource> getDiyRoleResourceByRoleId(String roleId);
	
	
	public List<DiyResource> getDiyUserResourceByUserId(String userId);
	
}