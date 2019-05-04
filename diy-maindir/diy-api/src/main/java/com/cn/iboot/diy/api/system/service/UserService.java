package com.cn.iboot.diy.api.system.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.iboot.diy.api.base.service.BaseService;
import com.cn.iboot.diy.api.system.domain.User;
import com.github.pagehelper.PageInfo;

public interface UserService extends BaseService<User> {

	/**
	 * 用户列表（含分页）
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
	public PageInfo<User> getUserPage(int startNum, int pageSize, User user);

	/**
	 * 查询所有用户信息
	 * 
	 * @return
	 */
	List<User> getList();

	/**
	 * 根据登录名得到用户对象
	 * 
	 * @param userName
	 * @return User
	 */
	User getUserByUserName(User user);
	
	/**
	 * 根据手机号查找用户
	 * 
	 * @param phoneNo
	 * @return
	 */
	User getUserByPhoneNo(String phoneNo);
	
	/**
	 * 根据当前用户编号查询门店列表
	 * @param userId
	 * @return
	 */
	List<User> getShopListByUserId(String userId);
	
	/**
	 * 新增用户信息
	 * 
	 * @param user
	 * @return
	 */
	void addUser(User user);
		
	/**
	 * 修改用户信息
	 * 
	 * @param user
	 */
	void updateUser(HttpServletRequest req,User user);
	
	/**
	 * 删除用户信息
	 * 
	 * @param userId
	 */
	int deleteUser(String userId);
	
	/**
	 * 通过手机号查询是否存在该用户
	 * 
	 * @param user
	 * @return
	 */
	User getUserCheckByPhoneNo(User user);
	
	/**
	 * 根据用户编号查询商户号和门店号
	 * 
	 * @param userId
	 * @return
	 */
	User getShopMchntCodeByUserId(String userId);
	
	/**
	 * 根据商户号查询商户名称
	 * 
	 * @param mchntCode
	 * @return
	 */
	String getMchntNameByMchntCode(String mchntCode);
	
	/**
	 * 根据门店号查询门店名称
	 * 
	 * @param shopCode
	 * @return
	 */
	String getShopNameByShopCode(String shopCode);
	
	/**
	 * 查看同一商户下的门店财务的用户数
	 * 
	 * @param user
	 * @return
	 */
	User getRoleByMchntCodeAndShopCode(User user);
}
