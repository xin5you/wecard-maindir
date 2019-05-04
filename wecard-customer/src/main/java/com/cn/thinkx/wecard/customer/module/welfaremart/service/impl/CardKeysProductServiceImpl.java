package com.cn.thinkx.wecard.customer.module.welfaremart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysProduct;
import com.cn.thinkx.common.wecard.module.cardkeys.mapper.CardKeysProductMapper;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.CardKeysProductService;

@Service("cardKeysProductService")
public class CardKeysProductServiceImpl implements CardKeysProductService {

	@Autowired
	private CardKeysProductMapper cardKeysProductMapper;
	
	@Override
	public CardKeysProduct getCardKeysProductByCard(CardKeysProduct cardKeysProduct) {
		return cardKeysProductMapper.getCardKeysProductByCard(cardKeysProduct);
	}

	@Override
	public List<CardKeysProduct> getCardKeysProductByType(String productType) {
		List<CardKeysProduct> ckpList = cardKeysProductMapper.getCardKeysProductByType(productType);
		for (CardKeysProduct ckp : ckpList) {
			ckp.setAmount(NumberUtils.RMBCentToYuan(ckp.getAmount()));
			ckp.setOrgAmount(NumberUtils.RMBCentToYuan(ckp.getOrgAmount()));
		}
		return ckpList;
	}

	@Override
	public int updateCardKeysProduct(CardKeysProduct cardKeysProduct) {
		return this.cardKeysProductMapper.updateCardKeysProduct(cardKeysProduct);
	}

	@Override
	public CardKeysProduct getCardKeysProductByCode(CardKeysProduct product) throws Exception{
		return this.cardKeysProductMapper.getCardKeysProductByCode(product);
	}

}
