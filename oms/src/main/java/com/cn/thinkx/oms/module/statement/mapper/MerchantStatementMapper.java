package com.cn.thinkx.oms.module.statement.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.statement.model.MerchantDetail;
import com.cn.thinkx.oms.module.statement.model.MerchantSummarizing;
import com.cn.thinkx.oms.module.statement.model.ShopDetail;
import com.cn.thinkx.oms.module.statement.util.Condition;

public interface MerchantStatementMapper {
	/**
	 * 门店明细列表
	 * @param conditions
	 * @return
	 */
	List<ShopDetail> getShopDetailList(Condition conditions);
	
	/**
	 * 商户明细列表
	 * @param conditions
	 * @return
	 */
	List<MerchantDetail> getMerchantDetailList(Condition conditions);
	
	/**
	 * 商户汇总列表
	 * @param conditions
	 * @return
	 */
	MerchantSummarizing getMerchantSummarizing(Condition conditions);
	
	/**
	 * 查询会员卡余额
	 * @param conditions
	 * @return
	 */
	String getCardBal(Condition conditions);
	
	MerchantDetail getMerchantDetailAmount(Condition condition);
	
	ShopDetail getShopDetailAmount(Condition condition);
}
