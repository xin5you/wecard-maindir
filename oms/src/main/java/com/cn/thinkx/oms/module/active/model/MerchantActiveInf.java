package com.cn.thinkx.oms.module.active.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 活动基础类
 * 
 * @author pucker
 *
 */
public class MerchantActiveInf extends BaseDomain {
	/**
	 * 活动ID
	 */
	private String activeId;
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 活动名称
	 */
	private String activeName;
	/**
	 * 生效状态
	 */
	private String activeStat;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 活动说明
	 */
	private String activeExplain;

	/**
	 * 使用规则
	 */
	private String activeRule;
	/**
	 * 数据状态
	 */
	private String dataStat;

	private String mchntName;
	private String mchntCode;

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getActiveName() {
		return activeName;
	}

	public void setActiveName(String activeName) {
		this.activeName = activeName;
	}

	public String getActiveStat() {
		return activeStat;
	}

	public void setActiveStat(String activeStat) {
		this.activeStat = activeStat;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getActiveExplain() {
		return activeExplain;
	}

	public void setActiveExplain(String activeExplain) {
		this.activeExplain = activeExplain;
	}

	public String getActiveRule() {
		return activeRule;
	}

	public void setActiveRule(String activeRule) {
		this.activeRule = activeRule;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

}
