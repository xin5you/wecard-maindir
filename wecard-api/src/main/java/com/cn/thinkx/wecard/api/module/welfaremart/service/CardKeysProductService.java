package com.cn.thinkx.wecard.api.module.welfaremart.service;

import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysProduct;

public interface CardKeysProductService {
	
	CardKeysProduct getCardKeysProductByCode(CardKeysProduct cardKeysProduct);
	
	CardKeysProduct getCardKeysProductByCard(CardKeysProduct cardKeysProduct);
	
	int updateCardKeysProduct(CardKeysProduct cardKeysProduct);
	
}
