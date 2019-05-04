package com.cn.thinkx.wecard.key.api.cardkeysproduct.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.key.api.cardkeysproduct.domain.CardKeysProduct;
import com.cn.thinkx.wecard.key.api.cardkeysproduct.mappper.CardKeysProductMapper;
import com.cn.thinkx.wecard.key.api.cardkeysproduct.service.CardKeysProductService;

@Service("cardKeysProductService")
public class CardKeysProductServiceImpl implements CardKeysProductService {

	@Autowired
	private CardKeysProductMapper cardKeysProductMapper;
	
	public String getProductCode(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.getProductCode(cardKeysProduct);
	}

	@Override
	public List<CardKeysProduct> getCardKeysProductList(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.getCardKeysProductList(cardKeysProduct);
	}

}
