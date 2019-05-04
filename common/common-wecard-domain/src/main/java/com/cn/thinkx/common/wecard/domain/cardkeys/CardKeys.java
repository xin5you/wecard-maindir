package com.cn.thinkx.common.wecard.domain.cardkeys;

import com.cn.thinkx.pms.base.domain.BaseDomain;

public class CardKeys extends BaseDomain {

	/**
	 * 卡密
	 */
	private String cardKey;
	/**
	 * 产品号
	 */
	private String productCode;
	/**
	 * 卡密持有人ID
	 */
	private String accountId;
	/**
	 * 起始有效期
	 */
	private String activeStartDate;
	/**
	 * 结束有效期
	 */
	private String activeEndDate;
	
	/**
	 * 状态
	 */
	private String dataStat;
	
	/**
	 * 有效张数
	 */
	private String validNum;

	public String getCardKey() {
		return cardKey;
	}

	public void setCardKey(String cardKey) {
		this.cardKey = cardKey;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getActiveStartDate() {
		return activeStartDate;
	}

	public void setActiveStartDate(String activeStartDate) {
		this.activeStartDate = activeStartDate;
	}

	public String getActiveEndDate() {
		return activeEndDate;
	}

	public void setActiveEndDate(String activeEndDate) {
		this.activeEndDate = activeEndDate;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getValidNum() {
		return validNum;
	}

	public void setValidNum(String validNum) {
		this.validNum = validNum;
	}

}
