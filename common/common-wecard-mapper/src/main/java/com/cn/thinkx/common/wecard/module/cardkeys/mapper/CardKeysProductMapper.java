package com.cn.thinkx.common.wecard.module.cardkeys.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysProduct;

public interface CardKeysProductMapper {
	
	CardKeysProduct getCardKeysProductByCode(CardKeysProduct product);
	
	/**
	 * 根据产品类型查询已上架的产品信息（按价格降序排列）
	 * 
	 * @param productCode
	 * @return
	 */
	List<CardKeysProduct> getCardKeysProductByType(String productType);
	
	/**
	 * 根据productCode跟dataStat查询卡产品信息
	 * 
	 * @param cardKeysProduct
	 * @return
	 */
	CardKeysProduct getCardKeysProductByCard(CardKeysProduct cardKeysProduct);
	
	/**
	 * 更新卡密产品信息（更新已发数量）
	 * 
	 * @param cardKeysProduct
	 * @return
	 */
	int updateCardKeysProduct(CardKeysProduct cardKeysProduct);
	
}
