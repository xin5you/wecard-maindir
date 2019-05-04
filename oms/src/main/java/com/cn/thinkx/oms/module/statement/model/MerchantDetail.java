package com.cn.thinkx.oms.module.statement.model;

public class MerchantDetail {
	private String shopName;
	private String memberCardConsumeAmt; 
	private Integer memberCardConsumeCount;
	private String speedyConsumeAmt;
	private Integer speedyConsumeCount;
	private String consumeAmt;
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getMemberCardConsumeAmt() {
		return memberCardConsumeAmt;
	}
	public void setMemberCardConsumeAmt(String memberCardConsumeAmt) {
		this.memberCardConsumeAmt = memberCardConsumeAmt;
	}
	public Integer getMemberCardConsumeCount() {
		return memberCardConsumeCount;
	}
	public void setMemberCardConsumeCount(Integer memberCardConsumeCount) {
		this.memberCardConsumeCount = memberCardConsumeCount;
	}
	public String getSpeedyConsumeAmt() {
		return speedyConsumeAmt;
	}
	public void setSpeedyConsumeAmt(String speedyConsumeAmt) {
		this.speedyConsumeAmt = speedyConsumeAmt;
	}
	public Integer getSpeedyConsumeCount() {
		return speedyConsumeCount;
	}
	public void setSpeedyConsumeCount(Integer speedyConsumeCount) {
		this.speedyConsumeCount = speedyConsumeCount;
	}
	public String getConsumeAmt() {
		return consumeAmt;
	}
	public void setConsumeAmt(String consumeAmt) {
		this.consumeAmt = consumeAmt;
	}
}
