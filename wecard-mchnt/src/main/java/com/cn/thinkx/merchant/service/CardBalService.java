package com.cn.thinkx.merchant.service;

import com.cn.thinkx.merchant.domain.CardBal;

public interface CardBalService {
	
	/**
	 * 查询商户下的会员卡数量和会员卡余额
	 * @param cb
	 * @return
	 */
	public CardBal getCardBalByInsCodeAndProductCode(CardBal cb);
}
