package com.cn.thinkx.oms.module.cardkeys.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.cardkeys.model.CardKeysOrderInf;

public interface CardKeysOrderInfMapper {
	
	CardKeysOrderInf getCardKeysOrderInfByOrderId(String orderId);
	
	List<CardKeysOrderInf> getCardKeysOrderInfList(CardKeysOrderInf cko);
	
	CardKeysOrderInf getFailOrderByOrderInf(CardKeysOrderInf cko);
	
	int updateCardKeysOrderInf(CardKeysOrderInf cko);
}
