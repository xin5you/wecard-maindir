package com.cn.thinkx.wecard.key.api.cardkeysproduct.service;

import java.util.List;

import com.cn.thinkx.wecard.key.api.cardkeysproduct.domain.CardKeysProduct;

public interface CardKeysProductService {
	
	/**
	 * 根据orgAmount与productType查询productCode
	 * 
	 * @param cardKeysProduct
	 * @return
	 */
	String getProductCode(CardKeysProduct cardKeysProduct);
	
	List<CardKeysProduct> getCardKeysProductList(CardKeysProduct cardKeysProduct);
	
}
