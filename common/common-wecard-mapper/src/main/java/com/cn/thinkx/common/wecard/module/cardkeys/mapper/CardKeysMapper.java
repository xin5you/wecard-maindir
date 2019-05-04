package com.cn.thinkx.common.wecard.module.cardkeys.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeys;

public interface CardKeysMapper {
	
	/**
	 * 查询卡密表信息
	 * 
	 * @param cardKeys
	 * @return
	 */
	List<CardKeys> getCardKeysList(CardKeys cardKeys);
	
	/**
	 * 统计用户持有卡劵可用张数
	 * 
	 * @param accountId
	 * @return
	 */
	String getCardKeysCount(String accountId);
	
	/**
	 * 可转卖张数
	 * 
	 * @param cardKeys
	 * @return
	 */
	int getLoseNumByAccountId(CardKeys cardKeys);
	
	/**
	 * 根据用户ID，按产品号分组查询用户卡密数量及产品号
	 * 
	 * @param accountId
	 * @return
	 */
	List<CardKeys> getCardKeysByAccountId(String accountId);
	
	/**
	 * 查询已核销的卡密（已转卖）
	 * 
	 * @return
	 */
	List<CardKeys> getCardKeysByDataStat();

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
