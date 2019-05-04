package com.cn.iboot.diy.api.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.system.domain.Resource;
import com.cn.iboot.diy.api.system.domain.Role;
import com.cn.iboot.diy.api.system.service.ResourceService;
import com.cn.iboot.diy.api.system.service.RoleService;
import com.cn.iboot.diy.api.system.service.UserService;


/**
 * User userdetail该类实现 UserDetails 接口，该类在验证成功后会被保存在当前回话的principal对象中
 * 
 * 获得对象的方式： WebUserDetails webUserDetails =
 * (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().
 * getPrincipal();
 * 
 * 或在JSP中： <sec:authentication property="principal.username"/>
 * 
 * 如果需要包括用户的其他属性，可以实现 UserDetails 接口中增加相应属性即可 权限验证类
 */
@Service
public class MyUserDetailServiceImpl implements UserDetailsService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;

	/**
	 * 登录验证
	 * 
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("登录验证方法");
		// 取得用户的权限
		com.cn.iboot.diy.api.system.domain.User users = null;
		try {
			users = userService.getUserByPhoneNo(username);
		} catch (Exception e) {
			logger.error("## 根据账号查询用户手机号为{}信息出错", username);
		}
		if (users == null)
			throw new UsernameNotFoundException(username + " not exist!");
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
		// 封装成spring security的user   grantedAuths： 用户的权限
		User userdetail = new User(users.getUserName(), users.getPassword(), true, true, true, true, grantedAuths);
		return userdetail;
	}

	/**
	 * 取得用户的权限
	 * @param user
	 * @return
	 */
	private Set<GrantedAuthority> obtionGrantedAuthorities(com.cn.iboot.diy.api.system.domain.User user) {
		logger.info("取得用户的权限方法");
		// 根据用户登录ID获得该用户所拥有的角色
		List<Role> roleList = roleService.getUserRoleByUserId(user.getId().toString());
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		// 循环用户所拥有的角色获得角色所拥有的资源
		if (roleList != null && roleList.size() > 0) {
			roleList.stream().forEach(r ->{
				List<Resource> resourceList = resourceService.getRoleResourceByRoleId(r.getId().toString());
				if (resourceList != null && resourceList.size() > 0) {
					resourceList.stream().forEach(res ->{  
						authSet.add(new SimpleGrantedAuthority("ROLE_" + res.getKey()));
					});
				}  
			});  
		}
		logger.info("取得用户为[{}]的权限大小为[{}]",user.getId(),authSet.size());
		return authSet;
	}
}