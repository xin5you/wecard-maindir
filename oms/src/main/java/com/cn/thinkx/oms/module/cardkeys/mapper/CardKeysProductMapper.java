package com.cn.thinkx.oms.module.cardkeys.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.cardkeys.model.CardKeysProduct;

@Repository("cardKeysProductMapper")
public interface CardKeysProductMapper {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);

	/**
	 * 根据产品号查询卡密产品信息
	 * 
	 * @param productCode
	 * @return
	 */
	public CardKeysProduct getCardKeysProductById(String productCode);
	
	public List<CardKeysProduct> getCardKeysProductList(CardKeysProduct cardKeysProduct);
	
	public int insertCardKeysProduct(CardKeysProduct cardKeysProduct);
	
	public int updateCardKeysProduct(CardKeysProduct cardKeysProduct);
	
	public int updateStandUpAndDown(CardKeysProduct cardKeysProduct);
	
	public int deleteCardKeysProduct(String productCode);
}
