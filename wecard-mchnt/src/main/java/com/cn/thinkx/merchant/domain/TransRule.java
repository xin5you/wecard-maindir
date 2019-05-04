package com.cn.thinkx.merchant.domain;

import java.util.Date;

import com.cn.thinkx.core.domain.BaseDomain;

/**
 * 交易规则表
 * @author zqy
 *
 */
public class TransRule extends BaseDomain {

	private String transRuleId;
	private String insId;
	private String platformId;
	private String mchntId;
	private String shopId;
	
	private String mchntName;
	private String shopName;
	
	private Date startTime;
	private Date endTime;
	private String isSuperposed;
	private String salience; //启用顺序  顺序 可随意，优惠叠加类型请设置0
	private String dataStat;
	
	private String transRuleDesp;
	
	private String ruleName;
	private String ruleType;
	private String templateName;
	private String templateCode;
	
	
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

	public String getRuleName() {
		return ruleName;
	}

	public String getRuleType() {
		return ruleType;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
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
