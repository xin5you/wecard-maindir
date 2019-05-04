package com.cn.thinkx.wecard.centre.module.biz.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;

public interface CardKeysTransLogService {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	String getPrimaryKey();
	
	List<CardKeysTransLog> getCardKeysTransLogList(CardKeysTransLog cardKeysTransLog);
	
	CardKeysTransLog getCardKeysTransLogByCard(CardKeysTransLog cardKeysTransLog);
	
	int insertCardKeysTransLog(CardKeysTransLog cardKeysTransLog);
	
	/**
	 * 更新卡密交易流水
	 * 
	 * @param cardKeysTransLog
	 * @return
	 */
	int updateCardKeysTransLog(CardKeysTransLog cardKeysTransLog);
	
	int getCountCardKeys(CardKeysTransLog cardKeysTransLog);
	
}
