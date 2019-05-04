package com.cn.iboot.diy.api.original.domain;

public class ShopStatisticsUpload {

	private String shopName;
	private String cardPayAmt;
	private String quickPayAmt;
	private Integer pfSubsidyNum;
	private String pfSubsidyAmt;
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
	public String getQuickPayAmt() {
		return quickPayAmt;
	}
	public void setQuickPayAmt(String quickPayAmt) {
		this.quickPayAmt = quickPayAmt;
	}
	public Integer getPfSubsidyNum() {
		return pfSubsidyNum;
	}
	public void setPfSubsidyNum(Integer pfSubsidyNum) {
		this.pfSubsidyNum = pfSubsidyNum;
	}
	public String getPfSubsidyAmt() {
		return pfSubsidyAmt;
	}
	public void setPfSubsidyAmt(String pfSubsidyAmt) {
		this.pfSubsidyAmt = pfSubsidyAmt;
	}
	
}
