package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.redis.vo.ShopInfVO;
import com.cn.thinkx.wecard.centre.module.biz.mapper.ShopInfMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.ShopInfService;

@Service("shopInfService")
public class ShopInfServiceImpl implements ShopInfService{

	@Autowired
	private ShopInfMapper shopInfMapper;
	
	@Override
	public List<ShopInfVO> getShopInfList() {
		return shopInfMapper.getShopInfList();
	}

	
}
