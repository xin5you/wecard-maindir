package com.cn.thinkx.oms.module.cardkeys.service;

import java.util.List;

import com.cn.thinkx.oms.module.cardkeys.model.CardKeysOrderInf;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysTransLog;
import com.github.pagehelper.PageInfo;

public interface CardKeysOrderInfService {
	
	PageInfo<CardKeysOrderInf> getCardKeysOrderInfPage(int startNum, int pageSize, CardKeysOrderInf entity);
	
	boolean searchCardTicketTransOrderFail(String orderId);
	
	boolean editCardTicketTransOrder(CardKeysOrderInf cko, List<CardKeysTransLog> cktList);
}
