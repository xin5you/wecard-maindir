package com.cn.thinkx.common.wecard.module.rule.mapper;

import com.cn.thinkx.common.wecard.domain.rule.RuleDeclare;

/**
 * 规则申明 DAO
 * @author zqy
 *
 */
public interface RuleDeclareDao {
	
	public RuleDeclare getRuleDeclareById(String ruleId);
		
	public int insertRuleDeclare(RuleDeclare ruleDeclare);
	
	public int updateRuleDeclare(RuleDeclare ruleDeclare);

}