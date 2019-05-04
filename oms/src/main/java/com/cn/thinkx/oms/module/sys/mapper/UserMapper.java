package com.cn.thinkx.oms.module.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.model.UserRole;

@Repository("userMapper")
public interface UserMapper{
	
	int insertUser(User user);

	int updateUser(User user);
	
	int deleteUserById(String userId);
	
	User getUserById(String userId);
	
	User getUserByLoginName(String loginname);

	List<User> getUserList(User order);
	
	
	/**
	 * 增加用户角色
	 * @param entity
	 */
	void saveUserRole(UserRole entity);
	
	/**
	 * 删除用户角色
	 * @param userId
	 */
	void deleteUserRoleByUserId(String userId);
	

	
}
