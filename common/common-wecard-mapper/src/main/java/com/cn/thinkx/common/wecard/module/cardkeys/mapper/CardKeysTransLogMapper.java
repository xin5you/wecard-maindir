package com.cn.thinkx.common.wecard.module.cardkeys.mapper;

import java.util.Map;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;

public interface CardKeysTransLogMapper {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	/**
	 * 根据卡密产品交易类型查询卡密交易流水信息
	 * 
	 * @param cardKeysTransLog
	 * @return
	 */
	CardKeysTransLog getCardKeysTransLogByCard(CardKeysTransLog cardKeysTransLog);
	
	/**
	 * 新增卡密交易流水信息
	 * 
	 * @param cardKeysTransLog
	 * @return
	 */
	int insertCardKeysTransLog(CardKeysTransLog cardKeysTransLog);
	
	/**
	 * 更新卡密交易流水信息
	 * 
	 * @param cardKeysTransLog
	 * @return
	 */
	int updateCardKeysTransLog(CardKeysTransLog cardKeysTransLog);
	
}
