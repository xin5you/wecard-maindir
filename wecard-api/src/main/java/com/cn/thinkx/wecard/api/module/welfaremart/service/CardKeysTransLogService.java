package com.cn.thinkx.wecard.api.module.welfaremart.service;

import java.util.List;

import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysTransLog;

public interface CardKeysTransLogService {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	String getPrimaryKey();
	
	CardKeysTransLog getCardKeysTransLogByCard(CardKeysTransLog cardKeysTransLog);
	
	int insertCardKeysTransLog(CardKeysTransLog cardKeysTransLog) throws Exception;
	
	int updateCardKeysTransLog(CardKeysTransLog cardKeysTransLog) throws Exception;
	
	int insertBatchCardKeysTransLogList(List<CardKeysTransLog> cardKeysTransLogList) throws Exception;
	
	List<CardKeysTransLog> getCardKeysTransLogByOrderId(CardKeysTransLog ckt);
	
}
