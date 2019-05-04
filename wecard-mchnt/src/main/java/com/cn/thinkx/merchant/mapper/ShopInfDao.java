package com.cn.thinkx.merchant.mapper;

import java.util.List;

import com.cn.thinkx.merchant.domain.ShopInf;

public interface ShopInfDao {

	
	public ShopInf getShopInfById(String shopInfId);
	
	public int insertShopInf(ShopInf entity);
	
	public int updateShopInf(ShopInf entity);
	
	public int updateShopInfUrl(ShopInf entity);
	
	public List<ShopInf> getShopInfList(ShopInf entity);
	
	public ShopInf getShopInfByCode(String shopCode);
}