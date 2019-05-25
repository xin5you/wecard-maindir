package com.cn.thinkx.oms.module.cardkeys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pay.core.KeyUtils;
import com.cn.thinkx.pay.domain.UnifyQueryVO;
import com.cn.thinkx.pay.service.ZFPaymentServer;
import com.cn.thinkx.pms.base.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.cardkeys.mapper.CardKeysMapper;
import com.cn.thinkx.oms.module.cardkeys.mapper.CardKeysOrderInfMapper;
import com.cn.thinkx.oms.module.cardkeys.mapper.CardKeysTransLogMapper;
import com.cn.thinkx.oms.module.cardkeys.mapper.WithdrawOrderMapper;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeys;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysOrderInf;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysTransLog;
import com.cn.thinkx.oms.module.cardkeys.model.WithdrawOrder;
import com.cn.thinkx.oms.module.cardkeys.service.CardKeysOrderInfService;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderStat;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("cardKeysOrderInfService")
public class CardKeysOrderInfServiceImpl implements CardKeysOrderInfService {

	Logger logger = LoggerFactory.getLogger(CardKeysOrderInfServiceImpl.class);

	@Autowired
	private CardKeysOrderInfMapper cardKeysOrderInfMapper;

	@Autowired
	private CardKeysTransLogMapper cardKeysTransLogMapper;

	@Autowired
	private CardKeysMapper cardKeysMapper;

	@Autowired
	private WithdrawOrderMapper withdrawOrderMapper;

	@Override
	public PageInfo<CardKeysOrderInf> getCardKeysOrderInfPage(int startNum, int pageSize, CardKeysOrderInf entity) {
		PageHelper.startPage(startNum, pageSize);
		List<CardKeysOrderInf> list = this.cardKeysOrderInfMapper.getCardKeysOrderInfList(entity);
		if (list != null && list.size() > 0) {
			for (CardKeysOrderInf cko : list) {
				cko.setType(orderType.findByCode(cko.getType()).getValue());
				cko.setOrderStat(cko.getStat());
				cko.setStat(orderStat.findByCode(cko.getStat()).getValue());
				cko.setAmount(NumberUtils.RMBCentToYuan(cko.getAmount()));
				if (cko.getPaidAmount() != "0" && !"0".equals(cko.getPaidAmount())) {
					cko.setPaidAmount(NumberUtils.RMBCentToYuan(cko.getPaidAmount()));
				}
			}
		}
		PageInfo<CardKeysOrderInf> page = new PageInfo<CardKeysOrderInf>(list);
		return page;
	}

	@Override
	public boolean searchCardTicketTransOrderFail(String orderId) {
		CardKeysOrderInf order = cardKeysOrderInfMapper.getCardKeysOrderInfByOrderId(orderId);
		if (order == null) {
			logger.error("## 重置卡券交易信息失败，查询卡券交易订单[{}]信息为空", orderId);
			return false;
		}
		CardKeysOrderInf cko = new CardKeysOrderInf();
		cko.setOrderId(orderId);
		if (order.getType().equals(BaseConstants.orderType.O2.getCode())) {
			cko.setType(orderType.O2.getCode());
			cko.setStat(orderStat.OS21.getCode());
		} else if (order.getType().equals(BaseConstants.orderType.O3.getCode())) {
			cko.setType(orderType.O3.getCode());
			cko.setStat(orderStat.OS31.getCode());
		}
		cko.setDataStat("0");
		CardKeysOrderInf orderInf = cardKeysOrderInfMapper.getFailOrderByOrderInf(cko);
		if (orderInf == null) {
			logger.error("## 重置卡券交易信息失败，查询卡券交易订单[{}]信息为空", orderId);
			return false;
		} else {
			if (order.getType().equals(BaseConstants.orderType.O2.getCode())) {
				orderInf.setStat(orderStat.OS24.getCode());
			} else if (order.getType().equals(BaseConstants.orderType.O3.getCode())) {
				orderInf.setStat(orderStat.OS35.getCode());
			}
		}
		CardKeysTransLog ckt = new CardKeysTransLog();
		ckt.setOrderId(orderId);
		ckt.setDataStat("0");
		List<CardKeysTransLog> cktList = cardKeysTransLogMapper.getCardKeysTransLogByTransLog(ckt);
		if (cktList.size() < 1) {
			logger.error("## 重置卡券交易信息失败，查询卡券交易流水信息为空，订单号[{}]", orderId);
			return false;
		}
		List<CardKeys> cardList = new ArrayList<CardKeys>();
		List<CardKeysTransLog> transLogList = new ArrayList<CardKeysTransLog>();
		for (CardKeysTransLog log : cktList) {
			//卡密流水信息
			CardKeysTransLog transLog = new CardKeysTransLog();
			transLog.setTxnPrimaryKey(log.getTxnPrimaryKey());
			transLog.setCardKey(log.getCardKey());
			transLog.setTransResult(BaseConstants.TXN_TRANS_ERROR);
			transLog.setDataStat("1");
			transLogList.add(transLog);
			//卡密信息
			CardKeys ck = new CardKeys();
			ck.setCardKey(log.getCardKey());
			ck.setDataStat("1");
			CardKeys card = cardKeysMapper.getCardKeysByCardKey(ck);
			if (card != null) {
				cardList.add(card);
			} else {
				logger.error("## 重置卡券交易信息失败，查询用户[{}]订单[{}]已核销的卡密[{}]信息为空", orderInf.getUserId(), orderId, log.getCardKey());
			}
		}
		if (cardList.size() < 1 || cktList.size() != cardList.size()) {
			logger.error("## 重置卡券交易信息失败，查询用户[{}]订单[{}]已核销的卡密数量和卡密交易流水数量不一致", orderInf.getUserId(), orderId);
			return false;
		}
		boolean flag = editCardTicketTransOrder(orderInf, transLogList);
		if (!flag) {
			logger.error("## 重置卡券交易信息失败，更新用户[{}]相关订单[{}]信息失败", orderInf.getUserId(), orderId);
		}
		return flag;
	}

	@Override
	public boolean editCardTicketTransOrder(CardKeysOrderInf cko, List<CardKeysTransLog> cktList) {
		boolean isReset = true;
		if (cardKeysOrderInfMapper.updateCardKeysOrderInf(cko) < 1) {
			logger.error("## 重置卡券交易信息失败，更新卡密订单[{}]信息失败", cko.getOrderId());
			return false;
		}

		//中付代付 订单查询
		String sessionId = ZFPaymentServer.getPayForAnotherSessionId();
		UnifyQueryVO queryVO = new UnifyQueryVO();
		queryVO.setInTradeOrderNo(cko.getOrderId());;
		queryVO.setTradeTime(DateUtil.getCurrentDateStr2());
		queryVO.setSessionId(sessionId);
		JSONObject result=ZFPaymentServer.doPayForAnotherQuery(queryVO);

		//中付代付成功，不需要更改卡密状态
		if(KeyUtils.responseCode.equals(result.getString("responseCode"))){
			return  false;
		}

		WithdrawOrder withdrawOrder = withdrawOrderMapper.getWithdrawOrderByPaidId(cko.getOrderId());
		if (withdrawOrder == null) {
			logger.info("用户[{}]订单[{}]未进入代付，根据paid_id[{}]查询出款订单信息不存在", cko.getUserId(), cko.getOrderId(), cko.getOrderId());
		} else {
			withdrawOrder.setStat(Constants.withdrawStat.S04.getCode());
			if (withdrawOrderMapper.updateWithdrawOrder(withdrawOrder) < 1) {
				logger.error("## 重置用户[{}]代付失败，更新出款订单[{}]信息失败", withdrawOrder.getUserId(), cko.getOrderId());
				return false;
			}
		}

		for (CardKeysTransLog log : cktList) {
			if (cardKeysTransLogMapper.updateCardKeysTransLog(log) < 1) {
				logger.error("## 重置卡券交易信息失败，更新用户[{}]卡密流水[{}]信息失败", withdrawOrder.getUserId(), log.getTxnPrimaryKey());
				isReset = false;
				break;
			} else {
				CardKeys card = new CardKeys();
				card.setCardKey(log.getCardKey());
				card.setDataStat("0");
				if (cardKeysMapper.updateCardKeys(card) < 1) {
					logger.error("## 重置卡券交易信息失败，更新卡密[{}]信息失败", card.getCardKey());
					isReset = false;
					break;
				}
			}
		}
		return isReset;
	}
}
