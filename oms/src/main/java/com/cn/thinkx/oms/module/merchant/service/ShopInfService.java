package com.cn.thinkx.oms.module.merchant.service;

import java.util.List;
import java.util.Map;

import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.github.pagehelper.PageInfo;


public interface ShopInfService {


	public String addShopInf(ShopInf shop);

	public int updateShopInf(ShopInf shop);

	public ShopInf getShopInfById(String id);

	public List<ShopInf> findShopInfList(ShopInf shopInf);
	

	public int deleteShopInf(String shopInfId);
	
	public int deleteShopInfByMchntId(String mchntId);
	
	/**
	 * 门店列表
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
    public PageInfo<ShopInf> getShopInfPage(int startNum, int pageSize, ShopInf entity);
    
    
    
	public ShopInf getShopInfByCode(String shopCode);

    /**
     * 一级商店
     * @return
     */
	public Map<String, ShopInf> findShopInfListFirstLevel(String id);
	
	/**
	 * 获取旗下分店
	 */
	public List<ShopInf> getShopInfListByPShopCode(String pShopCode);
	
	/**
	 * 根据商户code查询门店信息
	 * @param entity
	 * @return
	 */
	List<ShopInf> getShopInfListByMchntCode(String mchntCode);
	
}
