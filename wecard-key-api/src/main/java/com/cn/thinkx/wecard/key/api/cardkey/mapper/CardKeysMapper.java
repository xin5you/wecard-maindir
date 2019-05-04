package com.cn.thinkx.wecard.key.api.cardkey.mapper;

import java.util.List;

import com.cn.thinkx.wecard.key.api.cardkey.domain.CardKeys;

public interface CardKeysMapper {
	
	/**
	 * 查询卡密表信息
	 * 
	 * @param cardKeys
	 * @return
	 */
	List<CardKeys> getCardKeysList(CardKeys cardKeys);

	/**
	 * 新增卡密信息
	 * 
	 * @param cardKeys
	 * @return
	 */
	int insertCardKeys(CardKeys cardKeys);
	
	/**
	 * 注销卡密信息
	 * 
	 * @param cardKeys
	 * @return
	 */
	int updateCardKeys(CardKeys cardKeys);
}
