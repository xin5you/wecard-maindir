package com.cn.thinkx.merchant.domain;

import java.sql.Date;

public class CardBal {
	private String selectDate;
	private String insCode;
	private String productCode;
	private String outAmt;
	private String cardBal;
	private String createUser;
	private String updateUser;
	private Date createTime;
	private Date updateTime;
	private long cardNum;
	
	private double cardSumBal;//会员卡余额
	private int cardSumNum;//会员卡数量
	
	
	public String getSelectDate() {
		return selectDate;
	}
	public void setSelectDate(String selectDate) {
		this.selectDate = selectDate;
	}
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getOutAmt() {
		return outAmt;
	}
	public void setOutAmt(String outAmt) {
		this.outAmt = outAmt;
	}
	public String getCardBal() {
		return cardBal;
	}
	public void setCardBal(String cardBal) {
		this.cardBal = cardBal;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public long getCardNum() {
		return cardNum;
	}
	public void setCardNum(long cardNum) {
		this.cardNum = cardNum;
	}
	public double getCardSumBal() {
		return cardSumBal;
	}
	public void setCardSumBal(double cardSumBal) {
		this.cardSumBal = cardSumBal;
	}
	public int getCardSumNum() {
		return cardSumNum;
	}
	public void setCardSumNum(int cardSumNum) {
		this.cardSumNum = cardSumNum;
	}
	
}
