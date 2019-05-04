package com.cn.thinkx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantContractService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring-mybatis.xml"})
public class MerchantContractDaoTest {

	@Autowired
	@Qualifier("merchantContractService")
	private MerchantContractService merchantContractService;
	
	@Autowired
	@Test
	public void Test() throws Exception{	
		MerchantInf merchantInf = new  MerchantInf();
		merchantInf.setMchntCode("123123123123123");
		merchantInf.setMchntId("2016111613230000000002");
		merchantContractService.insertDefaultMerchantContract(merchantInf);
	}
}
