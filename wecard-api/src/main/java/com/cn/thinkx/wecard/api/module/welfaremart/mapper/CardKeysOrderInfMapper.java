package com.cn.thinkx.wecard.api.module.welfaremart.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysOrderInf;

public interface CardKeysOrderInfMapper {
	
	int getCardKeysOrderByCardKeys(@Param("cardKey")String cardKey);
	
	CardKeysOrderInf getCardKeysOrderByOrderId(@Param("orderId")String orderId);
	
	CardKeysOrderInf getOrderNumByOrderId(CardKeysOrderInf cko);
	
	int getMonthResellNum(@Param("userId")String userId);
	
	int insertCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	int updateCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	CardKeysOrderInf getOrderFailByUserIdAndOrderId(CardKeysOrderInf cko);
	
}
