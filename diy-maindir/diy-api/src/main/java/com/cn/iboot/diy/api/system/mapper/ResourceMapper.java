package com.cn.iboot.diy.api.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.system.domain.Resource;

@Mapper
public interface ResourceMapper extends BaseDao<Resource> {

	/**
	 * 查询所有资源信息
	 * 
	 * @return
	 */
	public List<Resource> getList();

	/**
	 * 根据用户Id获取该用户的权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Resource> getRoleResourceByRoleId(String roleId);

}
