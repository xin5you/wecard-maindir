package com.cn.thinkx.wecard.key.api.cardkey.domain;

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
	 * 交易流水号
	 */
	private String txnPrimaryKey;
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
	 * 注销张数
	 */
	private String rowNum;

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

	public String getTxnPrimaryKey() {
		return txnPrimaryKey;
	}

	public void setTxnPrimaryKey(String txnPrimaryKey) {
		this.txnPrimaryKey = txnPrimaryKey;
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

	public String getRowNum() {
		return rowNum;
	}

	public void setRowNum(String rowNum) {
		this.rowNum = rowNum;
	}

}
