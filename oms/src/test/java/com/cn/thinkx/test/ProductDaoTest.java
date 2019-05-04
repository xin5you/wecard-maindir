package com.cn.thinkx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.oms.module.merchant.mapper.ProductMapper;
import com.cn.thinkx.oms.module.merchant.model.Product;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring-mybatis.xml"})
public class ProductDaoTest {

	@Autowired
    @Qualifier("productMapper")
	private ProductMapper productMapper;
	
	@Autowired
	@Test
	public void Test() throws Exception{
		
		Product product = new Product();
		product.setProductName("开发测试产品");
		
		productMapper.insertProduct(product);
		
	}
}
