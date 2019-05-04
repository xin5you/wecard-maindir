package com.cn.thinkx.oms.module.diy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.diy.mapper.DiyResourceMapper;
import com.cn.thinkx.oms.module.diy.model.DiyResource;
import com.cn.thinkx.oms.module.diy.service.DiyResourceService;

@Service("diyResourceService")
public class DiyResourceServiceImpl implements DiyResourceService{


	@Autowired
	@Qualifier("diyResourceMapper")
	private DiyResourceMapper diyResourceMapper;

	/**
	 * 通过资源id查找对应的资源信息
	 */
	public DiyResource getDiyResourceById(String resourceId) {
		return diyResourceMapper.getDiyResourceById(resourceId);
	}

	/**
	 * 通过key查找信息，key值不允许重复
	 */
	public DiyResource getDiyResourceByKey(String key) {
		return diyResourceMapper.getDiyResourceByKey(key);
	}

	/**
	 * 插入资源信息
	 */
	public int insertDiyResource(DiyResource diyResource) {
		return diyResourceMapper.insertDiyResource(diyResource);
	}

	/**
	 * 修改资源信息
	 */
	public int updateDiyResource(DiyResource diyResource) {
		return diyResourceMapper.updateDiyResource(diyResource);
	}

	/**
	 * 删除资源信息
	 */
	public int deleteDiyResource(String resourceId) {
		diyResourceMapper.deleteDiyRoleResourceByResId(resourceId);
		return diyResourceMapper.deleteDiyResource(resourceId);
	}


	/**
	 * 查询资源列表
	 */
	public List<DiyResource> getDiyResourceList(DiyResource entity) {
		return diyResourceMapper.getDiyResourceList(entity);
	}

	/**
	 * 通过角色id查找该角色所对应的资源
	 */
	public List<DiyResource> getDiyRoleResourceByRoleId(String roleId) {
		return diyResourceMapper.getDiyRoleResourceByRoleId(roleId);
	}

}
