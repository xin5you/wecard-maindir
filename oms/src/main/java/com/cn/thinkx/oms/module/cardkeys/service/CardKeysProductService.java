package com.cn.thinkx.oms.module.cardkeys.service;

import java.util.List;

import com.cn.thinkx.oms.module.cardkeys.model.CardKeysProduct;
import com.github.pagehelper.PageInfo;

public interface CardKeysProductService {
	
	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public String getPrimaryKey();
	
	/**
	 * 根据产品号查询卡密产品信息
	 * 
	 * @param productCode
	 * @return
	 */
	public CardKeysProduct getCardKeysProductById(String productCode);
	
	public List<CardKeysProduct> getCardKeysProductList(CardKeysProduct cardKeysProduct);
	
	public PageInfo<CardKeysProduct> getCardKeysProductPage(int startNum, int pageSize, CardKeysProduct entity);
	
	public int insertCardKeysProduct(CardKeysProduct cardKeysProduct);
	
	public int updateCardKeysProduct(CardKeysProduct cardKeysProduct);
	
	public int updateStandUpAndDown(CardKeysProduct cardKeysProduct);
	
	public int deleteCardKeysProduct(String productCode);
}
