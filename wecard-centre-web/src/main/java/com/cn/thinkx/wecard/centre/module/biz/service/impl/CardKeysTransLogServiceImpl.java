package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;
import com.cn.thinkx.wecard.centre.module.biz.mapper.CardKeysTransLogMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysTransLogService;

@Service("cardKeysTransLogService")
public class CardKeysTransLogServiceImpl implements CardKeysTransLogService {

	@Autowired
	private CardKeysTransLogMapper cardKeysTransLogMapper;
	
	@Override
	public String getPrimaryKey() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "");
		cardKeysTransLogMapper.getPrimaryKey(paramMap);
		String id = (String) paramMap.get("id");
		return id;
	}

	@Override
	public List<CardKeysTransLog> getCardKeysTransLogList(CardKeysTransLog cardKeysTransLog) {
		return cardKeysTransLogMapper.getCardKeysTransLogList(cardKeysTransLog);
	}

	@Override
	public CardKeysTransLog getCardKeysTransLogByCard(CardKeysTransLog cardKeysTransLog) {
		return this.cardKeysTransLogMapper.getCardKeysTransLogByCard(cardKeysTransLog);
	}
	
	@Override
	public int insertCardKeysTransLog(CardKeysTransLog cardKeysTransLog) {
		return this.cardKeysTransLogMapper.insertCardKeysTransLog(cardKeysTransLog);
	}

	@Override
	public int updateCardKeysTransLog(CardKeysTransLog cardKeysTransLog) {
		return cardKeysTransLogMapper.updateCardKeysTransLog(cardKeysTransLog);
	}

	@Override
	public int getCountCardKeys(CardKeysTransLog cardKeysTransLog) {
		return cardKeysTransLogMapper.getCountCardKeys(cardKeysTransLog);
	}

}
