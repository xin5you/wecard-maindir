package com.cn.thinkx.wecard.key.api.cardkey.service;

import java.util.List;
import java.util.Set;

import com.cn.thinkx.wecard.key.api.cardkey.domain.CardKeys;

public interface CardKeysService {
	
	List<CardKeys> getCardKeysList(CardKeys cardKeys);

	int insertCardKeys(Set<CardKeys> cardKeys);
	
	int updateCardKeys(CardKeys cardKeys);
	
}
