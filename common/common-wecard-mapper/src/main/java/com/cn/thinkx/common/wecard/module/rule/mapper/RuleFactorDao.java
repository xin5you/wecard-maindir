package com.cn.thinkx.common.wecard.module.rule.mapper;

import com.cn.thinkx.common.wecard.domain.rule.RuleFactor;

/**
 * 规则因子表 DAO
 * @author zqy
 *
 */
public interface RuleFactorDao {
		
	public int insertRuleFactor(RuleFactor ruleFactor);
	
	public int updateRuleFactor(RuleFactor ruleFactor);

}