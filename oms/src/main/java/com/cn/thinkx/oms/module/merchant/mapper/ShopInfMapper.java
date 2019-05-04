package com.cn.thinkx.oms.module.merchant.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.ShopInf;

@Repository("shopInfMapper")
public interface ShopInfMapper {

	
	public ShopInf getShopInfById(String shopInfId);
	
	public int insertShopInf(ShopInf entity);
	
	public int updateShopInf(ShopInf entity);
	
	
	public List<ShopInf> getShopInfList(ShopInf entity);
	
	public int deleteShopInf(String shopInfId);
	
	public int deleteShopInfByMchntId(String mchntId);
	
	public ShopInf getShopInfByCode(String shopCode);
	
	@MapKey("shopCode")
	public Map<String,ShopInf> getShopInfListFirstLevel(String id);

	/**
	 * 根据门店code查询档口信息
	 * @param shopCode
	 * @return
	 */
	List<ShopInf> getShopInfListByPShopCode(String shopCode);
	
	/**
	 * 根据商户code查询门店信息
	 * @param entity
	 * @return
	 */
	List<ShopInf> getShopInfListByMchntCode(String mchntCode);
	
}