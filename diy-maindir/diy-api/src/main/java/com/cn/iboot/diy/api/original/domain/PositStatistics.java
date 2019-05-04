package com.cn.iboot.diy.api.original.domain;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class PositStatistics extends BaseEntity {

	private static final long serialVersionUID = 7840957872918594185L;

	private String id;
	private String shopStatisticsId;
	private String shopCode;  //档口code
	private String settleDate;
	private String cardPayAmt;
	private Integer cardPayNum;
	private String  quickPayAmt;
	private Integer quickPayNum;
	private String  payAmt;
	
	private String shopName;  //档口名称
	private String shCode; //门店code
	private String startDate;
	private String endDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShopStatisticsId() {
		return shopStatisticsId;
	}
	public void setShopStatisticsId(String shopStatisticsId) {
		this.shopStatisticsId = shopStatisticsId;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getCardPayAmt() {
		return cardPayAmt;
	}
	public void setCardPayAmt(String cardPayAmt) {
		this.cardPayAmt = cardPayAmt;
	}
	public Integer getCardPayNum() {
		return cardPayNum;
	}
	public void setCardPayNum(Integer cardPayNum) {
		this.cardPayNum = cardPayNum;
	}
	public String getQuickPayAmt() {
		return quickPayAmt;
	}
	public void setQuickPayAmt(String quickPayAmt) {
		this.quickPayAmt = quickPayAmt;
	}
	public Integer getQuickPayNum() {
		return quickPayNum;
	}
	public void setQuickPayNum(Integer quickPayNum) {
		this.quickPayNum = quickPayNum;
	}
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getShCode() {
		return shCode;
	}
	public void setShCode(String shCode) {
		this.shCode = shCode;
	}
}
