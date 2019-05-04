package com.cn.thinkx.common.wecard.domain.trans;

import java.util.Date;

public class TransRuleDeclare {
	private String transRuleId;
	private String insId;
	private String platformId;
	private String mchntId;
	private String shopId;
	private Date startTime;
	private Date endTime;
	private String isSuperposed; //是否叠加 0：不叠加  1：叠加
	private String salience; //启用顺序  顺序 可随意，优惠叠加类型请设置0
	private String dataStat;
	private String transRuleDesp;
	private String templateCode;
	private String templateName;
	private String templateDesp;
	private String templateFrom;
	private String ruleTypeCode;
	private String ruleName;
	private String ruleType;
	private String ruleId;
	private String ruleFactorNum;
	private String ruleFactorId;
	private double ruleFactor;
	private double ruleParam;
	
	private String mchntName;
	private String shopName;
	public String getTransRuleId() {
		return transRuleId;
	}
	public String getInsId() {
		return insId;
	}
	public String getPlatformId() {
		return platformId;
	}
	public String getMchntId() {
		return mchntId;
	}
	public String getShopId() {
		return shopId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getIsSuperposed() {
		return isSuperposed;
	}
	public String getSalience() {
		return salience;
	}
	public String getDataStat() {
		return dataStat;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public String getTemplateName() {
		return templateName;
	}
	public String getTemplateDesp() {
		return templateDesp;
	}
	public String getTemplateFrom() {
		return templateFrom;
	}
	public String getRuleTypeCode() {
		return ruleTypeCode;
	}
	public String getRuleName() {
		return ruleName;
	}
	public String getRuleType() {
		return ruleType;
	}
	public String getRuleId() {
		return ruleId;
	}
	public String getRuleFactorNum() {
		return ruleFactorNum;
	}
	public String getRuleFactorId() {
		return ruleFactorId;
	}
	public double getRuleFactor() {
		return ruleFactor;
	}
	public double getRuleParam() {
		return ruleParam;
	}
	public void setTransRuleId(String transRuleId) {
		this.transRuleId = transRuleId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setIsSuperposed(String isSuperposed) {
		this.isSuperposed = isSuperposed;
	}
	public void setSalience(String salience) {
		this.salience = salience;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public void setTemplateDesp(String templateDesp) {
		this.templateDesp = templateDesp;
	}
	public void setTemplateFrom(String templateFrom) {
		this.templateFrom = templateFrom;
	}
	public void setRuleTypeCode(String ruleTypeCode) {
		this.ruleTypeCode = ruleTypeCode;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public void setRuleFactorNum(String ruleFactorNum) {
		this.ruleFactorNum = ruleFactorNum;
	}
	public void setRuleFactorId(String ruleFactorId) {
		this.ruleFactorId = ruleFactorId;
	}
	public void setRuleFactor(double ruleFactor) {
		this.ruleFactor = ruleFactor;
	}
	public void setRuleParam(double ruleParam) {
		this.ruleParam = ruleParam;
	}
	public String getTransRuleDesp() {
		return transRuleDesp;
	}
	public void setTransRuleDesp(String transRuleDesp) {
		this.transRuleDesp = transRuleDesp;
	}
	public String getMchntName() {
		return mchntName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
