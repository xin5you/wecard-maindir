package com.cn.thinkx.merchant.domain;

import com.cn.thinkx.core.domain.BaseDomain;

public class RuleFactor extends BaseDomain {

	private String ruleFactorId;
	private String ruleId;
	private int ruleFactor;
	private int ruleParam;  //金额百分比 费率万分比
	private String dataStat;
	public String getRuleFactorId() {
		return ruleFactorId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public long getRuleFactor() {
		return ruleFactor;
	}
	public long getRuleParam() {
		return ruleParam;
	}

	public void setRuleFactorId(String ruleFactorId) {
		this.ruleFactorId = ruleFactorId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public void setRuleFactor(int ruleFactor) {
		this.ruleFactor = ruleFactor;
	}
	public void setRuleParam(int ruleParam) {
		this.ruleParam = ruleParam;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
}
