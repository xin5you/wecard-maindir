package com.cn.thinkx.common.wecard.module.shop.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.shop.ShopInf;

public interface ShopInfDao {

	
	public ShopInf getShopInfById(String shopInfId);
	
	public int insertShopInf(ShopInf entity);
	
	public int updateShopInf(ShopInf entity);
	
	public int updateShopInfUrl(ShopInf entity);
	
	public List<ShopInf> getShopInfList(ShopInf entity);
	
	public ShopInf getShopInfByCode(String shopCode);
}