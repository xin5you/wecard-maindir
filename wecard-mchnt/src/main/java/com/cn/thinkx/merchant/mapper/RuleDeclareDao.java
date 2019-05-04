package com.cn.thinkx.merchant.mapper;

import com.cn.thinkx.merchant.domain.RuleDeclare;

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