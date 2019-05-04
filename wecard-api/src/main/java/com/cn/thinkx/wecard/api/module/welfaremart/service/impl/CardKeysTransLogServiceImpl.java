package com.cn.thinkx.wecard.api.module.welfaremart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.BaseConstants.TransType;
import com.cn.thinkx.wecard.api.module.welfaremart.mapper.CardKeysTransLogMapper;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysTransLog;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysTransLogService;


@Service("cardKeysTransLogService")
public class CardKeysTransLogServiceImpl implements CardKeysTransLogService {
	private Logger logger = LoggerFactory.getLogger(getClass());

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
	public CardKeysTransLog getCardKeysTransLogByCard(CardKeysTransLog cardKeysTransLog) {
		return this.cardKeysTransLogMapper.getCardKeysTransLogByCard(cardKeysTransLog);
	}
	
	@Override
	public int insertCardKeysTransLog(CardKeysTransLog cardKeysTransLog) {
		return this.cardKeysTransLogMapper.insertCardKeysTransLog(cardKeysTransLog);
	}

	@Override
	public int updateCardKeysTransLog(CardKeysTransLog cardKeysTransLog) {
		return this.cardKeysTransLogMapper.updateCardKeysTransLog(cardKeysTransLog);
	}

	@Override
	public int insertBatchCardKeysTransLogList(List<CardKeysTransLog> cardKeysTransLogList) throws Exception {
		int batchRecords = 0;// 批量插入记录
		
		boolean canInsertTransLog = false;// 是否可以插入卡密流水
		
		CardKeysTransLog queryLog = new CardKeysTransLog();
		String cardKey = null;
		String orderId = null;
		
		// 根据卡密流水中的卡密判断是否可以插入（卡密流水无当前操作插入的记录）
		for (CardKeysTransLog log : cardKeysTransLogList) {
			queryLog.setCardKey(log.getCardKey());
			queryLog.setTransId(TransType.W30.getCode());
			int cardKeyNum = cardKeysTransLogMapper.getCountCardKeys(queryLog);
			if (cardKeyNum > 0) {
				cardKey = log.getCardKey();
				orderId = log.getOrderId();
				canInsertTransLog = false;
				break;
			}
			canInsertTransLog = true;
		}
		
		if (canInsertTransLog) {
			batchRecords = this.cardKeysTransLogMapper.insertBatchCardKeysTransLogList(cardKeysTransLogList);
		} else {
			logger.error("## 批量插入卡密流水失败：卡密[{}]已有卡密流水,代付卡密订单[{}]", cardKey, orderId);
		}
		return batchRecords;
	}

	@Override
	public List<CardKeysTransLog> getCardKeysTransLogByOrderId(CardKeysTransLog ckt) {
		return this.cardKeysTransLogMapper.getCardKeysTransLogByOrderId(ckt);
	}

}
