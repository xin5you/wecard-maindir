package com.cn.thinkx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.oms.module.cardkeys.mapper.CardKeysProductMapper;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysProduct;
import com.cn.thinkx.oms.module.margin.mapper.MerchantMarginListMapper;
import com.cn.thinkx.oms.module.merchant.mapper.InsInfMapper;
import com.cn.thinkx.oms.module.merchant.mapper.MerchantInfListMapper;
import com.cn.thinkx.pms.base.utils.DateUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/conf/spring-mybatis.xml"})
public class MchntDaoTest {

	@Autowired
    @Qualifier("insInfMapper")
	private InsInfMapper insInfMapper;
	
	@Autowired
    @Qualifier("merchantInfListMapper")
	private MerchantInfListMapper merchantInfListMapper;
	
	
	@Autowired
    @Qualifier("merchantMarginListMapper")
	private MerchantMarginListMapper merchantMarginListMapper;
	
	@Autowired
    @Qualifier("cardKeysProductMapper")
	private CardKeysProductMapper cardKeysProductMapper;
	
	@Autowired
	@Test
	public void insert() throws Exception{
		CardKeysProduct ckp = new CardKeysProduct();
		ckp.setProductCode("1001009");
		ckp.setProductName("name");
		ckp.setProductType("11");
		ckp.setOrgAmount("11");
		ckp.setAmount("11");
		ckp.setTotalNum("1");
		ckp.setAvailableNum("1");
		ckp.setSupplier("aa");
		ckp.setProductDesc("aaa");
		ckp.setCreateTime(DateUtil.getDateTimeFromString("20180910121212"));
		ckp.setUpdateTime(DateUtil.getDateTimeFromString("20180910121212"));
		cardKeysProductMapper.insertCardKeysProduct(ckp);
		/*MerchantMarginList m1=new MerchantMarginList();

		merchantMarginListMapper.getMerchantMarginList(m1);*/
		
	}
}
