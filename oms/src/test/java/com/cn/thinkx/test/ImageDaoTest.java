package com.cn.thinkx.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.oms.module.common.mapper.ImageManagerMapper;
import com.cn.thinkx.oms.module.common.model.ImageManager;
import com.cn.thinkx.oms.module.merchant.mapper.InsInfMapper;
import com.cn.thinkx.oms.module.merchant.mapper.MerchantInfListMapper;
import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInfList;
import com.cn.thinkx.oms.module.sys.mapper.RoleMapper;
import com.cn.thinkx.oms.module.sys.mapper.UserMapper;
import com.cn.thinkx.oms.module.sys.model.Role;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.service.RoleService;
import com.cn.thinkx.oms.module.sys.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring-mybatis.xml"})
public class ImageDaoTest {

	@Autowired
    @Qualifier("imageManagerMapper")
	private ImageManagerMapper imageManagerMapper;
	

	
	
	@Autowired
	@Test
	public void TTest() throws Exception{
		
		ImageManager imageManager=new ImageManager();
		imageManager.setApplication("20");
		imageManager.setApplicationId("00000109");
		imageManager.setApplicationType("2001");

		List<ImageManager> list=imageManagerMapper.getImageManagerList(imageManager);
		
		System.out.println(list.size());
	}
}
