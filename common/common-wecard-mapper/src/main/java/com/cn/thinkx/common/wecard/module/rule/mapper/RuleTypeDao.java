package com.cn.thinkx.common.wecard.module.rule.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.rule.RuleType;

/**
 * 规则类型表
 * @author zqy
 *
 */
public interface RuleTypeDao {
	
	
	public RuleType getRuleTypeById(String ruleTypeCode);
	
	public List<RuleType> getRuleTypeListByTempCode(String tempCode);

}