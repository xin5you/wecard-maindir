package com.cn.thinkx.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.oms.module.sys.mapper.OrganizationMapper;
import com.cn.thinkx.oms.module.sys.model.Organization;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring-mybatis.xml"})
public class OrganizationDaoTest {

	@Autowired
    @Qualifier("organizationMapper")
	private OrganizationMapper organizationMapper;
	
	

	
	@Autowired
	@Test
	public void Test() throws Exception{
		
		List<Organization> list=organizationMapper.getOrganizationList(new Organization());
	
		System.out.println(list.size());
	}
}
