package com.cn.thinkx.wecard.api.module.welfaremart.mapper;

import java.util.List;

import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysProduct;

public interface CardKeysProductMapper {
	
	CardKeysProduct getCardKeysProductByCode(CardKeysProduct cardKeysProduct);
	
	List<CardKeysProduct> getCardKeysProductByType(String productCode);
	
	CardKeysProduct getCardKeysProductByCard(CardKeysProduct cardKeysProduct);
	
	int updateCardKeysProduct(CardKeysProduct cardKeysProduct);
	
}
