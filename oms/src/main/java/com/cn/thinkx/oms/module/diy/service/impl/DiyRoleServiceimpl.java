package com.cn.thinkx.oms.module.diy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.diy.mapper.DiyRoleMapper;
import com.cn.thinkx.oms.module.diy.model.DiyRole;
import com.cn.thinkx.oms.module.diy.model.DiyRoleResource;
import com.cn.thinkx.oms.module.diy.service.DiyRoleService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("diyRoleService")
public class DiyRoleServiceimpl implements DiyRoleService{
	
	@Autowired
	@Qualifier("diyRoleMapper")
	private DiyRoleMapper diyRoleMapper;

	
	/**
	 * 通过id查看角色信息
	 * 
	 */
	public DiyRole getDiyRoleById(String id) {
		return diyRoleMapper.getDiyRoleById(id);
	}
	
	/**
	 * 添加商户角色
	 * 
	 */
	public void insertDiyRole(DiyRole entity) {
		diyRoleMapper.insertDiyRole(entity);
	}

	/**
	 * 修改商户角色
	 * 
	 */
	public void updateDiyRole(DiyRole entity) {
		diyRoleMapper.updateDiyRole(entity);
	}

	/**
	 * 删除角色
	 * 
	 */
	public void deleteDiyRoleById(String id) {
		//删除资源角色表中的信息
		diyRoleMapper.deleteDiyRoleResourceByRoleId(id);
		diyRoleMapper.deleteDiyUserRoleByRoleId(id);
		diyRoleMapper.deleteDiyRoleById(id);
	}

	/**
	 * 查询所有商户角色
	 * 
	 */
	public List<DiyRole> getDiyRoleList(DiyRole diyRole) {
		return diyRoleMapper.getDiyRoleList(diyRole);
	}

	public List<DiyRole> getDiyUserRoleByUserId(String userId) {
		return diyRoleMapper.getDiyUserRoleByUserId(userId);
	}

	@Override
	public PageInfo<DiyRole> getDiyRolePage(int startNum, int pageSize, DiyRole diyRole) {
		PageHelper.startPage(startNum, pageSize);
		List<DiyRole> diyRoleList = getDiyRoleList(diyRole);
		PageInfo<DiyRole> diyRolePage = new PageInfo<DiyRole>(diyRoleList);
		return diyRolePage;
	}

	/**
	 * 授权保存提交
	 * 
	 */
	public void editDiyRoleResource(String id, String[] resourceIds,User user) {
		
		diyRoleMapper.deleteDiyRoleResourceByRoleId(id);
    	
    	if(resourceIds !=null && resourceIds.length>0){
    		DiyRoleResource diyRoleResource=null;
    		for(int i=0;i<resourceIds.length;i++){
    			diyRoleResource=new DiyRoleResource();
    			diyRoleResource.setRoleId(id);
    			diyRoleResource.setResourceId(resourceIds[i]);
    			diyRoleResource.setDataStat("0");
    			diyRoleResource.setCreateUser(user.getId().toString());
    			diyRoleResource.setUpdateUser(user.getId().toString());
    			diyRoleMapper.saveDiyRoleResource(diyRoleResource);
    		}
    	}
	}

}
