package com.cn.thinkx.common.wecard.module.rule.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.rule.RuleTemplate;

/**
 * 规则模板
 * @author zqy
 *
 */
public interface RuleTemplateDao {
	
	public RuleTemplate getRuleTemplateById();
	
	public List<RuleTemplate> getAllRuleTemplate(RuleTemplate entity);
}