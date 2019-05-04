package com.cn.thinkx.oms.module.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.sys.mapper.UserMapper;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.model.UserRole;
import com.cn.thinkx.oms.module.sys.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


@Service("userService")
public class UserServiceImpl implements UserService {
	
    @Autowired
    @Qualifier("userMapper")
    private UserMapper userMapper;
    
    
	/**
	 * 根据登录名得到用户对象
	 * @param loginName
	 * @return User
	 */
	public User getUserByLoginName(String loginName){
		return userMapper.getUserByLoginName(loginName);
	}

	/**
	 * 根据Id得到用户对象
	 * @param userId
	 * @return User
	 */
	public User getUserById(String userId) throws Exception {
		return userMapper.getUserById(userId);
	}
	
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	public int updateUser(User user) throws Exception{
		return userMapper.updateUser(user);
	}


	public int updateUser(User user,String [] rolesId)throws Exception {
		int operNum=userMapper.updateUser(user);
		this.deleteUserRoleByUserId(user.getId().toString());
		
		if(rolesId !=null && rolesId.length>0){
			UserRole userRole=null;
			for(int i=0;i<rolesId.length;i++){
				userRole=new UserRole();
				userRole.setUserId(user.getId());
				userRole.setRoleId(Integer.parseInt(rolesId[i]));
				userMapper.saveUserRole(userRole);
			}
		}
		return operNum;
	}


	public int deleteUser(String userId) throws Exception{
		User user=userMapper.getUserById(userId);
		user.setState("1");
		return userMapper.updateUser(user);
	}

	/**
	 * 添加用户
	 * @param user
	 * @param rolesId
	 * @return
	 * @throws Exception
	 */
	public int saveUser(User user,String [] rolesId) throws Exception {
		int operNum=userMapper.insertUser(user);
	
		if(rolesId !=null && rolesId.length>0){
			UserRole userRole=null;
			for(int i=0;i<rolesId.length;i++){
				userRole=new UserRole();
				userRole.setUserId(user.getId());
				userRole.setRoleId(Integer.parseInt(rolesId[i]));
				userMapper.saveUserRole(userRole);
			}
		}
		return operNum;
	}
	/**
	 * 查询所有用户
	 */
	public List<User> getUserList(User entity){
		return userMapper.getUserList(entity);
	}
	/**
	 * 用户列表
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
    public PageInfo<User> getUserPage(int startNum, int pageSize, User user){
		PageHelper.startPage(startNum, pageSize);
		List<User> userList = getUserList(user);
		PageInfo<User> userPage = new PageInfo<User>(userList);
		return userPage;
    }
    
	/**
	 * 增加用户角色
	 * @param entity
	 */
	public void saveUserRole(UserRole entity) {
		userMapper.saveUserRole(entity);
	}


	/**
	 * 删除用户角色
	 * @param userId
	 */
	public void deleteUserRoleByUserId(String userId) {
		userMapper.deleteUserRoleByUserId(userId);
		
	}

}
