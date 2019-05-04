package com.cn.thinkx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.oms.module.sys.mapper.RoleMapper;
import com.cn.thinkx.oms.module.sys.mapper.UserMapper;
import com.cn.thinkx.oms.module.sys.model.Role;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.service.RoleService;
import com.cn.thinkx.oms.module.sys.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring-mybatis.xml"})
public class UserDaoTest {

	@Autowired
    @Qualifier("userMapper")
	private UserMapper userMapper;
	
	
	@Autowired
    @Qualifier("roleMapper")
	private RoleMapper roleMapper;
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Test
	public void UserTest() throws Exception{
		User user=userService.getUserByLoginName("admin");
		
		Role role =roleService.getRoleById("1");
		System.out.println(user.getName());
		System.out.println("Role=="+role.getName());
	}
}
