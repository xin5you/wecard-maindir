package com.cn.thinkx.wecard.customer.module.welfaremart.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;

public interface CardKeysOrderInfService {
	
	/**
	 * 查询单笔订单交易数量
	 * 
	 * @param cko
	 * @return
	 */
	CardKeysOrderInf getOrderNumByOrderId(CardKeysOrderInf cko);
	
	/**
	 * 查询用户转让次数
	 * 0:代表没有转让过，1：转让次数为1
	 * 
	 * @param userId
	 * @return
	 */
	int getMonthResellNum(@Param("userId")String userId);
	
	/**
	 * 根据用户ID查询卡密交易订单信息
	 * 
	 * @param userId
	 * @return
	 */
	List<CardKeysOrderInf> getOrderInfListByUserId(@Param("userId")String userId);
	
	/**
	 * 新增卡密交易订单信息
	 * 
	 * @param cardKeysOrderInf
	 * @return
	 */
	int insertCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	/**
	 * 更新卡密交易订单信息
	 * 
	 * @param cardKeysOrderInf
	 * @return
	 */
	int updateCardKeysOrderInf(CardKeysOrderInf cardKeysOrderInf);
	
	/**
	 * 卡券充值新增卡密订单，卡密流水及核销卡密
	 * 
	 * @param cktList
	 * @param userId
	 * @return
	 */
	boolean insertCardRechargeOrder(CardKeysOrderInf order, List<CardKeysTransLog> cktList);
	
}
