package com.cn.thinkx.biz.drools.service;

import java.util.List;
import java.util.Map;

import com.cn.thinkx.biz.drools.model.TransRule;

/**
 *  商户优惠
 * @author zqy
 *
 */
public interface TransRuleService {
	/**
	 * 优惠规则列表查询
	 * @param searchEntity
	 * @param pagination
	 * @return
	 */
	List<TransRule> getTransRuleDeclareList(TransRule trFilter)  throws Exception;
	
	
	/**
	 * 查询规则List 区分叠加和 不叠加的
	 * @param mchntId
	 * @param platformId
	 * @param oriTxnAmount
	 * @return
	 */
	Map<String, List<TransRule>> getTransRuleList(String mchntId,String platformId,int oriTxnAmount,String templateCode) throws Exception;
	
	/**
	 * 遍历规则 计算最大优惠
	 * @param list
	 * @return
	 */
	int maxFavourableConvert(List<TransRule> list);
}