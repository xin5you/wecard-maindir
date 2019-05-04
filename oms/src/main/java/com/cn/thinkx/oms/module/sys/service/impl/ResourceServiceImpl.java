package com.cn.thinkx.oms.module.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.sys.mapper.ResourceMapper;
import com.cn.thinkx.oms.module.sys.model.Resource;
import com.cn.thinkx.oms.module.sys.service.ResourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {


	@Autowired
	@Qualifier("resourceMapper")
	private ResourceMapper resourceMapper;

	
	/**
	 * 根据角色ID查询资源
	 */
	public List<Resource> getRoleResourceByRoleId(String roleId) {
		return resourceMapper.getRoleResourceByRoleId(roleId);
	}
	public Resource getResourceByKey(String key){
		return resourceMapper.getResourceByKey(key);
	}
	public List<Resource> getResourceList(Resource entity){
		return resourceMapper.getResourceList(entity);
	}
	
    public PageInfo<Resource> getResourcePage(int startNum, int pageSize, Resource entity){
		PageHelper.startPage(startNum, pageSize);
		List<Resource> roleList = getResourceList(entity);
		PageInfo<Resource> userPage = new PageInfo<Resource>(roleList);
		return userPage;
	}

	
	public Resource getResourceById(String resourceId) {
	
		return resourceMapper.getResourceById(resourceId);
	}

	
	public int insertResource(Resource resource) {
	
		return resourceMapper.insertResource(resource);
	}

	
	public int updateResource(Resource resource) {
	
		return resourceMapper.updateResource(resource);
	}

	
	public int deleteResource(String resourceId) {
		resourceMapper.deleteRoleResourceByResId(resourceId);
		return resourceMapper.deleteResource(resourceId);
	}

	@Override
	public List<Resource> getUserResourceByUserId(String userId) {
		// TODO Auto-generated method stub
		return resourceMapper.getUserResourceByUserId(userId);
	}
	

}
