package com.cn.thinkx.wecard.api.module.mchnt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.api.module.mchnt.mapper.ShopInfMapper;
import com.cn.thinkx.wecard.api.module.mchnt.model.ShopInf;
import com.cn.thinkx.wecard.api.module.mchnt.service.ShopInfService;

@Service("shopInfService")
public class ShopInfServiceImpl implements ShopInfService {

	@Autowired
	@Qualifier("shopInfMapper")
	private ShopInfMapper shopInfMapper;
	
	public ShopInf getShopInfByCode(String shopCode) {
		return shopInfMapper.getShopInfByCode(shopCode);
	}

}
