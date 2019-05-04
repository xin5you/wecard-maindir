package com.cn.iboot.diy.api.system.service;

import java.util.List;
import com.cn.iboot.diy.api.base.service.BaseService;
import com.cn.iboot.diy.api.system.domain.Resource;

public interface ResourceService extends BaseService<Resource> {

	/**
	 * 获取所有资源信息
	 * 
	 * @return
	 */
	List<Resource> getList();

	/**
	 * 根据角色Id获取该用户的权限
	 * 
	 * @param roleId
	 * @return
	 */
	List<Resource> getRoleResourceByRoleId(String roleId);

}
