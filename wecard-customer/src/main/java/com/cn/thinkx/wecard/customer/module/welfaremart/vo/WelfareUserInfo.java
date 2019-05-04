package com.cn.thinkx.wecard.customer.module.welfaremart.vo;

/**
 * 福利集市账户信息
 * 
 * @author xiaomei
 *
 */
public class WelfareUserInfo {

	private String userId;			//用户ID
	private String balance;			//福利余额
	private String availBalance;	//可卖额度
	private String cardNum;			//张数
	private String mchntCode;	//商户号
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getAvailBalance() {
		return availBalance;
	}
	public void setAvailBalance(String availBalance) {
		this.availBalance = availBalance;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	
}
