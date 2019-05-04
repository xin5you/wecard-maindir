package com.cn.thinkx.oms.module.cardkeys.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.cardkeys.model.CardKeysTransLog;

public interface CardKeysTransLogMapper {
	
	List<CardKeysTransLog> getCardKeysTransLogByTransLog(CardKeysTransLog ckt);
	
	int updateCardKeysTransLog(CardKeysTransLog ckt);
	
}
