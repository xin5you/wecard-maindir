package com.cn.iboot.diy.api.original.domain;

public class PositStatisticsUpload {
	private String shopName;
	private String cardPayAmt;
	private Integer cardPayNum;
	private String  quickPayAmt;
	private Integer quickPayNum;
	private String  payAmt;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
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
}
