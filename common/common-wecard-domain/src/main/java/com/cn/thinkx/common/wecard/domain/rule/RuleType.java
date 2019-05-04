package com.cn.thinkx.common.wecard.domain.rule;

import com.cn.thinkx.common.wecard.domain.base.BaseDomain;

/**
 *  规则类型
 * @author zqy
 *
 */
public class RuleType extends BaseDomain {

	private String  ruleTypeCode;
	private String 	ruleName;
	private String 	ruleType;
	private String  templateCode;
	private String  dataStat;
	
	public String getRuleTypeCode() {
		return ruleTypeCode;
	}

	public String getRuleType() {
		return ruleType;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setRuleTypeCode(String ruleTypeCode) {
		this.ruleTypeCode = ruleTypeCode;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
}
