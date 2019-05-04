package com.cn.thinkx.wecard.centre.module.biz.mapper;

import java.util.List;
import java.util.Map;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;

public interface CardKeysTransLogMapper {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	List<CardKeysTransLog> getCardKeysTransLogList(CardKeysTransLog cardKeysTransLog);
	
	CardKeysTransLog getCardKeysTransLogByCard(CardKeysTransLog cardKeysTransLog);
	
	int insertCardKeysTransLog(CardKeysTransLog cardKeysTransLog);
	
	int updateCardKeysTransLog(CardKeysTransLog cardKeysTransLog);
	
	int getCountCardKeys(CardKeysTransLog cardKeysTransLog);
}
