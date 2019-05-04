package com.cn.thinkx.wecard.api.module.welfaremart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.api.module.welfaremart.mapper.CardKeysProductMapper;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysProduct;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysProductService;

@Service("cardKeysProductService")
public class CardKeysProductServiceImpl implements CardKeysProductService {

	@Autowired
	private CardKeysProductMapper cardKeysProductMapper;
	
	@Override
	public CardKeysProduct getCardKeysProductByCard(CardKeysProduct cardKeysProduct) {
		return cardKeysProductMapper.getCardKeysProductByCard(cardKeysProduct);
	}

	@Override
	public CardKeysProduct getCardKeysProductByCode(CardKeysProduct cardKeysProduct) {
		return cardKeysProductMapper.getCardKeysProductByCode(cardKeysProduct);
	}
	
	@Override
	public int updateCardKeysProduct(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.updateCardKeysProduct(cardKeysProduct);
	}

}
