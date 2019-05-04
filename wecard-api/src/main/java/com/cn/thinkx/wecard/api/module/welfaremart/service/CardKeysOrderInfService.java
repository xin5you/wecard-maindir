package com.cn.thinkx.wecard.api.module.welfaremart.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeys;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysOrderInf;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysTransLog;

public interface CardKeysOrderInfService {
	
	int getCardKeysOrderByCardKeys(@Param("cardKey")String cardKey);
	
	CardKeysOrderInf getCardKeysOrderByOrderId(String orderId);
	
	CardKeysOrderInf getOrderNumByOrderId(CardKeysOrderInf cko);
	
	int getMonthResellNum(String userId);
	
	CardKeysOrderInf getOrderFailByUserIdAndOrderId(CardKeysOrderInf cko);
	
	CardKeysOrderInf getCardKeysOrderInfByOrderInf(CardKeysOrderInf cko);
	
	int insertCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	int updateCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	int updateCardKeysOrderInfAndCardKeys(CardKeysOrderInf cardKeysOrderInf, List<CardKeys> cardKeyList) throws Exception;
	
	boolean updateUserNegotiation(CardKeysOrderInf order, List<CardKeysTransLog> cktList) throws Exception;
	
	boolean updateUserRechargeOrder(CardKeysOrderInf order, List<CardKeysTransLog> cktList) throws Exception;
	
	boolean updateUserBugCardOrder(CardKeysOrderInf order, List<CardKeysTransLog> cktList) throws Exception;
	
}
