package com.cn.thinkx.merchant.mapper;

import com.cn.thinkx.merchant.domain.RuleFactor;

/**
 * 规则因子表 DAO
 * @author zqy
 *
 */
public interface RuleFactorDao {
		
	public int insertRuleFactor(RuleFactor ruleFactor);
	
	public int updateRuleFactor(RuleFactor ruleFactor);

}