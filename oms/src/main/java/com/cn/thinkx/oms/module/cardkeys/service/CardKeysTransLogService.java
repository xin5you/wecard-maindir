package com.cn.thinkx.oms.module.cardkeys.service;

import java.util.List;

import com.cn.thinkx.oms.module.cardkeys.model.CardKeysTransLog;
import com.github.pagehelper.PageInfo;

public interface CardKeysTransLogService {
	
	List<CardKeysTransLog> getCardKeysTransLogByTransLog(CardKeysTransLog ckt);
	
	PageInfo<CardKeysTransLog> getCardKeysTransLogPage(int startNum, int pageSize, CardKeysTransLog entity);
}
