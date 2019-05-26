package com.cn.thinkx.wecard.centre.module.biz.service;

import java.util.List;
import java.util.Set;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;

public interface CardKeysOrderInfService {
	
	Set<CardKeysOrderInf> getCardKeysOrderInfList(CardKeysOrderInf cko);

	List<CardKeysOrderInf> getCardKeysOrderInfs(CardKeysOrderInf cko);

	CardKeysOrderInf getOrderNumByOrderId(CardKeysOrderInf cko);
	
	int insertCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	int updateCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	int updateCkoByLockversion(CardKeysOrderInf cardKeysOrderInf);
	
}
