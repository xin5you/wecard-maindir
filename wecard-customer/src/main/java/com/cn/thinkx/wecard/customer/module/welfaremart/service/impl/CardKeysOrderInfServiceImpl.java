package com.cn.thinkx.wecard.customer.module.welfaremart.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeys;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;
import com.cn.thinkx.common.wecard.module.cardkeys.mapper.CardKeysMapper;
import com.cn.thinkx.common.wecard.module.cardkeys.mapper.CardKeysOrderInfMapper;
import com.cn.thinkx.common.wecard.module.cardkeys.mapper.CardKeysTransLogMapper;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.CardKeysTransLogService;

@Service("cardKeysOrderInfService")
public class CardKeysOrderInfServiceImpl implements CardKeysOrderInfService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CardKeysOrderInfMapper cardKeysOrderInfMapper;
	
	@Autowired
	private CardKeysTransLogMapper cardKeysTransLogMapper;
	
	@Autowired
	private CardKeysMapper cardKeysMapper;
	
	@Autowired
	private CardKeysTransLogService cardKeysTransLogService;

	@Override
	public int insertCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf) {
		return this.cardKeysOrderInfMapper.insertCardKeysOrderInf(cardKeysOrderInf);
	}

	@Override
	public int updateCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf) {
		return this.cardKeysOrderInfMapper.updateCardKeysOrderInf(cardKeysOrderInf);
	}

	@Override
	public CardKeysOrderInf getOrderNumByOrderId(CardKeysOrderInf cko) {
		return this.cardKeysOrderInfMapper.getOrderNumByOrderId(cko);
	}

	@Override
	public int getMonthResellNum(String userId) {
		return this.cardKeysOrderInfMapper.getMonthResellNum(userId);
	}

	@Override
	public List<CardKeysOrderInf> getOrderInfListByUserId(String userId) {
		return this.cardKeysOrderInfMapper.getOrderInfListByUserId(userId);
	}

	@Override
	public boolean insertCardRechargeOrder(CardKeysOrderInf order, List<CardKeysTransLog> cktList) {
		boolean isRecharge = false;
		if (order == null || cktList.size() < 1) {
			logger.error("## 卡券集市--->充值接口，reuqest CardKeysOrderInf or CardKeysTransLog is null");
			return isRecharge;
		}
		if (cardKeysOrderInfMapper.insertCardKeysOrderInf(order) < 1) {
			logger.error("## 卡券集市--->充值接口，userID[{}]insertCardKeysOrderInf失败", order.getUserId());
			return isRecharge;
		}
		
		int transLogNum = 0;
		int cardKeysNum = 0;
		for (CardKeysTransLog ckt : cktList) {
			CardKeysTransLog transLog = cardKeysTransLogMapper.getCardKeysTransLogByCard(ckt);
			if (transLog != null) {
				logger.error("## 卡券集市--->充值接口，userID[{}]已存在卡密[{}]充值流水[{}]信息", order.getUserId(), ckt.getCardKey(), transLog.getTxnPrimaryKey());
				break;
			}
			String id = cardKeysTransLogService.getPrimaryKey();
			ckt.setTxnPrimaryKey(id);
			
			//新增卡密交易流水信息
			if (cardKeysTransLogMapper.insertCardKeysTransLog(ckt) < 1) {
				logger.error("## 卡券集市--->充值接口，新增用户[{}]卡密流水信息失败", order.getUserId());
				break;
			} else {
				transLogNum = transLogNum + 1;
				//核销卡密信息
				CardKeys card = new CardKeys();
				card.setCardKey(ckt.getCardKey());
				if (cardKeysMapper.updateCardKeys(card) < 1) {
					logger.error("## 卡券集市--->充值接口，核销userID[{}]卡密[{}]失败", order.getUserId(), ckt.getCardKey());
					break;
				} else {
					cardKeysNum = cardKeysNum + 1;
				}
			}
		}
		if (transLogNum != cardKeysNum) {
			logger.error("## 卡券集市--->充值接口，新增卡密交易流水数[{}]与更新卡密信息数[{}]不想等", transLogNum, cardKeysNum);
			return isRecharge;
		}
		return true;
	}

}
