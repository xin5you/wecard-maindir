package com.cn.thinkx.wecard.centre.module.biz.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;

public interface CardKeysOrderInfMapper {
	
	List<CardKeysOrderInf> getCardKeysOrderInfList(CardKeysOrderInf cko);

	List<CardKeysOrderInf> getCardKeysOrderInfs(CardKeysOrderInf cko);
	
	CardKeysOrderInf getOrderNumByOrderId(CardKeysOrderInf cko);
	
	int insertCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	int updateCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	int updateCkoByLockversion(CardKeysOrderInf cardKeysOrderInf);
	
}
