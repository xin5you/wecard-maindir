package com.cn.thinkx.wecard.api.module.welfaremart.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.BaseConstants.orderStat;
import com.cn.thinkx.wecard.api.module.welfaremart.mapper.CardKeysMapper;
import com.cn.thinkx.wecard.api.module.welfaremart.mapper.CardKeysOrderInfMapper;
import com.cn.thinkx.wecard.api.module.welfaremart.mapper.CardKeysTransLogMapper;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeys;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysOrderInf;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysTransLog;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrder;
import com.cn.thinkx.wecard.api.module.withdraw.mapper.WithdrawOrderMapper;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.Constants;

@Service("cardKeysOrderInfService")
public class CardKeysOrderInfServiceImpl implements CardKeysOrderInfService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CardKeysOrderInfMapper cardKeysOrderInfMapper;
	
	@Autowired
	private CardKeysMapper cardKeysMapper;
	
	@Autowired
	private CardKeysTransLogMapper cardKeysTransLogMapper;
	
	@Autowired
	private WithdrawOrderMapper withdrawOrderMapper;

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
	public CardKeysOrderInf getCardKeysOrderByOrderId(String orderId) {
		return this.cardKeysOrderInfMapper.getCardKeysOrderByOrderId(orderId);
	}

	@Override
	public int getCardKeysOrderByCardKeys(String cardKey) {
		return this.cardKeysOrderInfMapper.getCardKeysOrderByCardKeys(cardKey);
	}

	@Override
	public CardKeysOrderInf getOrderFailByUserIdAndOrderId(CardKeysOrderInf cko) {
		return this.cardKeysOrderInfMapper.getOrderFailByUserIdAndOrderId(cko);
	}
	
	@Override
	public CardKeysOrderInf getCardKeysOrderInfByOrderInf(CardKeysOrderInf cko) {
		return this.cardKeysOrderInfMapper.getOrderFailByUserIdAndOrderId(cko);
	}
	
	@Override
	public int updateCardKeysOrderInfAndCardKeys(CardKeysOrderInf cardKeysOrderInf, List<CardKeys> cardKeyList) throws Exception{
		if (cardKeysOrderInf == null && cardKeyList == null) {
			logger.error("## 卡券集市--->更新卡密交易订单及卡密核销失败，参数cardKeysOrderInf和cardKey为空");
			return 0;
		}
		int updateCardKeysNum = 0;
		for (CardKeys card : cardKeyList) {
			card.setDataStat("1");
			int i = this.cardKeysMapper.updateCardKeys(card);
			if (i >= 1) {
				updateCardKeysNum = updateCardKeysNum + 1;
			}
		}
		if (cardKeyList.size() != updateCardKeysNum) {
			logger.error("## 卡券集市--->更新卡密交易订单及卡密核销失败，应核销卡密数量[{}]与已核销的卡密数量[{}]不一致", cardKeyList.size(), updateCardKeysNum);
			return 0;
		}
		
		int i = this.cardKeysOrderInfMapper.updateCardKeysOrderInf(cardKeysOrderInf);
		if (i < 1) {
			logger.error("## 卡券集市--->更新卡密交易订单[{}]失败", cardKeysOrderInf.getOrderId());
			return 0;
		}
		return i;
	}

	@Override
	public boolean updateUserNegotiation(CardKeysOrderInf order, List<CardKeysTransLog> cktList) throws Exception {
		boolean isUpdateUserNegotiation = true;
		if (order == null || cktList.size() < 1) {
			logger.error("## 用户[{}]代付失败，更新卡密订单，流水及卡密信息失败，参数为空", order.getUserId());
			return false;
		}
		
		if (cardKeysOrderInfMapper.updateCardKeysOrderInf(order) < 1) {
			logger.error("## 用户[{}]代付失败，更新卡密订单[{}]信息失败", order.getOrderId());
			return false;
		}
		
		WithdrawOrder withdrawOrder = withdrawOrderMapper.getWithdrawOrderByPaidId(order.getOrderId());
		if (withdrawOrder == null) {
			logger.error("## 用户[{}]代付失败，根据paid_id[{}]查询出款订单信息不存在失败", order.getOrderId());
			return false;
		}
		withdrawOrder.setStat(Constants.withdrawStat.S04.getCode());
		if (withdrawOrderMapper.updateWithdrawOrder(withdrawOrder) < 1) {
			logger.error("## 用户[{}]代付失败，更新出款订单信息失败", order.getOrderId());
			return false;
		}
		
		for (CardKeysTransLog log : cktList) {
			if (cardKeysTransLogMapper.updateCardKeysTransLog(log) < 1) {
				logger.error("## 用户[{}]代付失败，更新卡密流水[{}]信息失败", log.getTxnPrimaryKey());
				isUpdateUserNegotiation = false;
				break;
			} else {
				CardKeys card = new CardKeys();
				card.setCardKey(log.getCardKey());
				card.setDataStat("0");
				if (cardKeysMapper.updateCardKeys(card) < 1) {
					logger.error("## 用户[{}]代付失败，更新卡密[{}]信息失败", card.getCardKey());
					isUpdateUserNegotiation = false;
					break;
				}
			}
		}
		
		return isUpdateUserNegotiation;
	}

	@Override
	public boolean updateUserRechargeOrder(CardKeysOrderInf order, List<CardKeysTransLog> cktList) throws Exception {
		boolean isUpdateUserNegotiation = true;
		if (order == null || cktList.size() < 1) {
			logger.error("## 卡密充值--->更新用户[{}]卡密订单，流水及卡密信息失败，参数为空", order.getUserId());
			return false;
		}
		
		if (cardKeysOrderInfMapper.updateCardKeysOrderInf(order) < 1) {
			logger.error("## 卡密充值--->更新用户[{}]卡密订单[{}]信息失败", order.getOrderId());
			return false;
		}
		
		for (CardKeysTransLog log : cktList) {
			if (cardKeysTransLogMapper.updateCardKeysTransLog(log) < 1) {
				logger.error("## 卡密充值--->更新用户[{}]卡密流水[{}]信息失败", log.getTxnPrimaryKey());
				isUpdateUserNegotiation = false;
				break;
			} else {
				CardKeys card = new CardKeys();
				card.setCardKey(log.getCardKey());
				if (!order.getStat().equals(orderStat.OS22.getCode())) {
					card.setDataStat("0");
					if (cardKeysMapper.updateCardKeys(card) < 1) {
						logger.error("## 卡密充值--->更新用户[{}]卡密[{}]信息失败", card.getCardKey());
						isUpdateUserNegotiation = false;
						break;
					}
				}
			}
		}
		return isUpdateUserNegotiation;
	}

	@Override
	public boolean updateUserBugCardOrder(CardKeysOrderInf order, List<CardKeysTransLog> cktList) throws Exception {
		boolean isUpdateUserBugCardOrder = true;
		if (order == null || cktList.size() < 1) {
			logger.error("## 卡券购买--->更新用户[{}]卡密订单，流水及卡密信息失败，参数为空", order.getUserId());
			return false;
		}
		
		if (cardKeysOrderInfMapper.updateCardKeysOrderInf(order) < 1) {
			logger.error("## 卡券购买--->更新用户[{}]卡密订单[{}]信息失败", order.getOrderId());
			return false;
		}
		
		if (order.getStat().equals(orderStat.OS12.getCode())) {
			for (CardKeysTransLog log : cktList) {
				if (cardKeysTransLogMapper.insertCardKeysTransLog(log) < 1) {
					logger.error("## 卡券购买--->新增用户[{}]卡密流水[{}]信息失败", log.getTxnPrimaryKey());
					isUpdateUserBugCardOrder = false;
					break;
				} else {
					CardKeys card = new CardKeys();
					card.setCardKey(log.getCardKey());
					card.setAccountId(order.getUserId());
					card.setProductCode(order.getProductCode());
					if (cardKeysMapper.insertCardKeys(card) < 1) {
						logger.error("## 卡券购买--->核销用户[{}]卡密[{}]信息失败", card.getCardKey());
						isUpdateUserBugCardOrder = false;
						break;
					}
				}
			}
		}
		return isUpdateUserBugCardOrder;
	}

}
