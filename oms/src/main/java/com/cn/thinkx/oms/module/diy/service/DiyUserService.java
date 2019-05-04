package com.cn.thinkx.oms.module.diy.service;

import com.cn.thinkx.oms.module.diy.model.DiyDataAuth;
import com.cn.thinkx.oms.module.diy.model.DiyUser;
import com.cn.thinkx.oms.module.diy.model.DiyUserDataAuth;
import com.cn.thinkx.oms.module.diy.model.DiyUserRole;
import com.cn.thinkx.oms.module.sys.model.User;
import com.github.pagehelper.PageInfo;

public interface DiyUserService {
	/**
	 * 根据用户名名得到用户对象
	 * 
	 * @param userName
	 * @return DiyUser
	 */
	DiyUser getDiyUserByUserName(DiyUser diyUser);
	
	/**
	 * 根据Id获取用户对象
	 * @param userId
	 * @return
	 */
	DiyUser getDiyUserById(String userId) throws Exception;
	
	/**
	 * 保存用户信息
	 * @param DiyUser
	 * @param rolesIds
	 * @return
	 */
	int saveDiyUser(DiyUser diyUser,String rolesId,User user) throws Exception;
	
	/**
	 * 修改用户信息
	 * @param DiyUser
	 * @return
	 */
	int updateDiyUser(DiyUser diyUser,String rolesId,User user) throws Exception;
	
	/**
	 * 删除用户信息
	 * @param userId
	 * @return
	 */
	void deleteDiyUser(String userId) throws Exception;
	
	/**
	 * 用户列表
	 * @param startNum
	 * @param pageSize
	 * @param DiyUser
	 * @return
	 */
    public PageInfo<DiyUser> getDiyUserPage(int startNum, int pageSize, DiyUser diyUser);
	
	
	/**
	 * 增加用户角色
	 * @param entity
	 */
	void saveDiyUserRole(DiyUserRole entity);
	
	/**
	 * 删除用户角色
	 * @param userId
	 */
	void deleteDiyUserRoleByUserId(String userId);
	
	/**
	 * 增加用户和数据权限的中间表
	 * 
	 * @param entity
	 */
	void saveDiyUserDataAuth(DiyUserDataAuth entity);
	
	/**
	 * 删除用户和数据权限的中间表
	 * 
	 * @param userId
	 */
	
	int deleteDiyUserDataAuthByUserId(String userId);
	
	/**
	 * 添加用户数据权限
	 * 
	 * @param entity
	 */
	void saveDiyDataAuth(DiyDataAuth entity);
	
	/**
	 * 修改用户数据权限
	 * 
	 * @param entity
	 */
	int updateDiyDataAuth(DiyDataAuth entity);
	
	DiyDataAuth getDiyDataAuthById(String id);
	
	/**
	 * 通过id产出数据权限
	 * 
	 * @param id
	 */
	void deleteDiyDataAuthByAuthId(String id);
	
	DiyUser getDiyUserByPhoneNo(DiyUser diyUser);
	
	/**
	 * 查看同一商户下的门店财务的用户数
	 * 
	 * @param diyUser
	 * @return
	 */
	DiyUser getRoleByMchntCodeAndShopCode(DiyUser diyUser);
}
