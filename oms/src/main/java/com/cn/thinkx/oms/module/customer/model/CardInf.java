package com.cn.thinkx.oms.module.customer.model;

public class CardInf {

	private String cardNo;	//卡号
	private String cardStat;	//卡状态
	private String cancelStat;		//注销状态
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardStat() {
		return cardStat;
	}
	public void setCardStat(String cardStat) {
		this.cardStat = cardStat;
	}
	public String getCancelStat() {
		return cancelStat;
	}
	public void setCancelStat(String cancelStat) {
		this.cancelStat = cancelStat;
	}
	
}
