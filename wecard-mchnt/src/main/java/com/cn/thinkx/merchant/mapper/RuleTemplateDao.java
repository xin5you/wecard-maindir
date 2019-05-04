package com.cn.thinkx.merchant.mapper;

import java.util.List;

import com.cn.thinkx.merchant.domain.RuleTemplate;

/**
 * 规则模板
 * @author zqy
 *
 */
public interface RuleTemplateDao {
	
	public RuleTemplate getRuleTemplateById();
	
	public List<RuleTemplate> getAllRuleTemplate(RuleTemplate entity);
}