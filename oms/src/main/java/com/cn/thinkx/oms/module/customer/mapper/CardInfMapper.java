package com.cn.thinkx.oms.module.customer.mapper;

import com.cn.thinkx.oms.module.customer.model.CardInf;

public interface CardInfMapper {

	/**
	 * 会员卡注销卡产品
	 * 
	 * @return
	 */
	int updateCardInf(String cardNo);

	/**
	 * 通过卡号查询卡产品
	 * 
	 * @param cardNo
	 * @return
	 */
	CardInf getCardInfByCardNo(String cardNo);

}