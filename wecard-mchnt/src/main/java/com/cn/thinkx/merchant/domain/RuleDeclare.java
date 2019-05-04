package com.cn.thinkx.merchant.domain;

import com.cn.thinkx.core.domain.BaseDomain;

/**
 * 规则申明表结构
 * @author zqy
 *
 */
public class RuleDeclare extends BaseDomain {

	private String ruleId;
	
	private String ruleTypeCode;
	
	private String ruleType;
	
	private String transRuleId;
	
	private String ruleFactorNum;
	
	private String dataStat;

	public String getRuleId() {
		return ruleId;
	}

	public String getRuleTypeCode() {
		return ruleTypeCode;
	}

	public String getRuleType() {
		return ruleType;
	}

	public String getTransRuleId() {
		return transRuleId;
	}

	public String getRuleFactorNum() {
		return ruleFactorNum;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setRuleTypeCode(String ruleTypeCode) {
		this.ruleTypeCode = ruleTypeCode;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}


	public void setTransRuleId(String transRuleId) {
		this.transRuleId = transRuleId;
	}

	public void setRuleFactorNum(String ruleFactorNum) {
		this.ruleFactorNum = ruleFactorNum;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
