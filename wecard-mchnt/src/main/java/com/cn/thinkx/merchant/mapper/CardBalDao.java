package com.cn.thinkx.merchant.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.merchant.domain.CardBal;

public interface CardBalDao {
	
	/**
	 * 查询商户下的会员卡数量和会员卡余额
	 * @param cb
	 * @return
	 */
	public CardBal getCardBalByInsCodeAndProductCode(@Param("cb") CardBal cb);
}
