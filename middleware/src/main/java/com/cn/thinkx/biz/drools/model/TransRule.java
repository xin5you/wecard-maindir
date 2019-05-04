package com.cn.thinkx.biz.drools.model;

public class TransRule {
	
	private String transRuleId;
	private String insId;
	private String platformId;
	private String mchntId;
	private String shopId;
	private String templateCode;

	private String isSuperposed; //是否 叠加 0：叠加   1:不叠加  
	private String salience; 	//执行顺序
	private String ruleTypeCode; //规则类型
	private String ruleType;	 //
	private int ruleFactor;		 //因子
	private int ruleParam;		 //参数
	
	/**
	 * 录入金额
	 */
	private Integer inMoney;
	
	public String getIsSuperposed() {
		return isSuperposed;
	}
	public String getSalience() {
		return salience;
	}
	public String getRuleTypeCode() {
		return ruleTypeCode;
	}
	public String getRuleType() {
		return ruleType;
	}
	public int getRuleFactor() {
		return ruleFactor;
	}
	public int getRuleParam() {
		return ruleParam;
	}
	public void setIsSuperposed(String isSuperposed) {
		this.isSuperposed = isSuperposed;
	}
	public void setSalience(String salience) {
		this.salience = salience;
	}
	public void setRuleTypeCode(String ruleTypeCode) {
		this.ruleTypeCode = ruleTypeCode;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public void setRuleFactor(int ruleFactor) {
		this.ruleFactor = ruleFactor;
	}
	public void setRuleParam(int ruleParam) {
		this.ruleParam = ruleParam;
	}
	public Integer getInMoney() {
		return inMoney;
	}
	public void setInMoney(Integer inMoney) {
		this.inMoney = inMoney;
	}
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
	public String getTemplateCode() {
		return templateCode;
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
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
}
