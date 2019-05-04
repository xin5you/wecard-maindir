package com.cn.thinkx.oms.module.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.sys.mapper.RoleMapper;
import com.cn.thinkx.oms.module.sys.model.Role;
import com.cn.thinkx.oms.module.sys.model.RoleResource;
import com.cn.thinkx.oms.module.sys.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	@Qualifier("roleMapper")
	private RoleMapper roleMapper;
	

	/***
	 * 获取角色
	 * @param roleId
	 * @return
	 */
	public Role getRoleById(String roleId) {
		
		return roleMapper.getRoleById(roleId);
	}
	
	/**
	 * 查询所有角色
	 */
	public List<Role> getRoleList(Role entity){
		return roleMapper.getRoleList(entity);
	}
  /**
   * 添加角色
   */
	public void insertRole(Role entity) {
		roleMapper.insertRole(entity);
	}
	/**
	 * 修改角色
	 * @param entity
	 */
	public void updateRole(Role entity) {
		roleMapper.updateRole(entity);
	}
	/**
	 * 删除角色
	 * @param roleId
	 */
	public void deleteRoleById(String roleId) {
		
		/**删除用户与角色关系*/
		roleMapper.deleteUserRoleByRoleId(roleId);
		
		/**删除角色与资源关联关系表*/
		roleMapper.deleteRoleResourceByRoleId(roleId);
		
		/**删除角色*/
		roleMapper.deleteRoleById(roleId);
		
	}
	/**
	 * 获取某个用户的角色
	 * @param userId
	 * @return
	 */
   public List<Role> getUserRoleByUserId(String userId){
    	return roleMapper.getUserRoleByUserId(userId);
    }

	/**
	 * 查询角色分页查询
	 * @param startNum
	 * @param pageSize
	 * @param role
	 * @return
	 */
    public PageInfo<Role> getRolePage(int startNum, int pageSize, Role role){
		PageHelper.startPage(startNum, pageSize);
		List<Role> roleList = getRoleList(role);
		PageInfo<Role> rolePage = new PageInfo<Role>(roleList);
		return rolePage;
	}
    
    /**
     * 角色授权
     * @param roleId
     * @param resourceIds
     * @return
     */
    public void editRoleResource(String roleId ,String[] resourceIds){
    	
    	roleMapper.deleteRoleResourceByRoleId(roleId);
    	
    	if(resourceIds !=null && resourceIds.length>0){
    		RoleResource roleResource=null;
    		for(int i=0;i<resourceIds.length;i++){
    			roleResource=new RoleResource();
    			roleResource.setRoleId(roleId);
    			roleResource.setResourceId(resourceIds[i]);
    			roleMapper.saveRoleResource(roleResource);
    		}
    	}
    }
}
