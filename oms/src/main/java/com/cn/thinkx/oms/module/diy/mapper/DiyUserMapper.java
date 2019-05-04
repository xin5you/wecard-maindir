package com.cn.thinkx.oms.module.diy.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.diy.model.DiyDataAuth;
import com.cn.thinkx.oms.module.diy.model.DiyUser;
import com.cn.thinkx.oms.module.diy.model.DiyUserDataAuth;
import com.cn.thinkx.oms.module.diy.model.DiyUserRole;

@Repository("diyUserMapper")
public interface DiyUserMapper{
	
	int insertDiyUser(DiyUser diyUser);

	int updateDiyUser(DiyUser diyUser);
	
	int deleteDiyUserById(String userId);
	
	DiyUser getDiyUserById(String userId);
	
	DiyUser getDiyUserByUserName(DiyUser diyUser);

	List<DiyUser> getDiyUserList(DiyUser diyUser);
	
	
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
	 * 修改用户角色
	 * @param entity
	 */
	void updateDiyUserRole(DiyUserRole entity);

	/**
	 * 新增用户数据权限
	 * 
	 * @param entity
	 */
	void saveDiyUserDataAuth(DiyUserDataAuth entity);
	
	/**
	 * 删除用户数据权限通过用户id
	 * 
	 * @param userId
	 */
	int deleteDiyUserDataAuthByUserId(String userId);
	
	/**
	 * 添加数据权限
	 * 
	 * @param entity
	 */
	void saveDiyDataAuth(DiyDataAuth entity);
	
	/**
	 * 修改数据权限
	 * 
	 * @param entity
	 * @return
	 */
	int updateDiyDataAuth(DiyDataAuth entity);
	
	/**
	 * 删除数据权限
	 * 
	 * @param id
	 */
	void deleteDiyDataAuthByAuthId(String id);
	
	
	DiyDataAuth getDiyDataAuthById(String id);
	
	/**
	 * 通过手机号码查询用户是否存在
	 * 
	 * @param diyUser
	 * @return
	 */
	DiyUser getDiyUserByPhoneNo(DiyUser diyUser);
	
	/**
	 * 查看同一商户下的门店财务的用户数
	 * 
	 * @param diyUser
	 * @return
	 */
	DiyUser getRoleByMchntCodeAndShopCode(DiyUser diyUser);
	
}
