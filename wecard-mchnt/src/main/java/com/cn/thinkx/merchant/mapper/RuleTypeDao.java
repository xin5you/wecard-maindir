package com.cn.thinkx.merchant.mapper;

import java.util.List;

import com.cn.thinkx.merchant.domain.RuleType;

/**
 * 规则类型表
 * @author zqy
 *
 */
public interface RuleTypeDao {
	
	
	public RuleType getRuleTypeById(String ruleTypeCode);
	
	public List<RuleType> getRuleTypeListByTempCode(String tempCode);

}