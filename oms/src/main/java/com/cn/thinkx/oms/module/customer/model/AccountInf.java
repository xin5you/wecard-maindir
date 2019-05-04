package com.cn.thinkx.oms.module.customer.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class AccountInf extends BaseDomain {

	private String accountNo;
	private String userId;
	private String personalId;
	private String accountType;
	private String accountStat;
	private String accBal;
	private String accBalCode;
	private String maxTxnAmt1;
	private String maxDayTxnAmt1;
	private String dayTotalAmt1;
	private String maxTxnAmt2;
	private String maxDayTxnAmt2;
	private String dayTotalAmt2;
	private String freezeAmt;
	private String lastTxnDate;
	private String lastTxnTime;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getAccountStat() {
		return accountStat;
	}
	public void setAccountStat(String accountStat) {
		this.accountStat = accountStat;
	}
	public String getAccBal() {
		return accBal;
	}
	public void setAccBal(String accBal) {
		this.accBal = accBal;
	}
	public String getAccBalCode() {
		return accBalCode;
	}
	public void setAccBalCode(String accBalCode) {
		this.accBalCode = accBalCode;
	}
	public String getMaxTxnAmt1() {
		return maxTxnAmt1;
	}
	public void setMaxTxnAmt1(String maxTxnAmt1) {
		this.maxTxnAmt1 = maxTxnAmt1;
	}
	public String getMaxDayTxnAmt1() {
		return maxDayTxnAmt1;
	}
	public void setMaxDayTxnAmt1(String maxDayTxnAmt1) {
		this.maxDayTxnAmt1 = maxDayTxnAmt1;
	}
	public String getDayTotalAmt1() {
		return dayTotalAmt1;
	}
	public void setDayTotalAmt1(String dayTotalAmt1) {
		this.dayTotalAmt1 = dayTotalAmt1;
	}
	public String getMaxTxnAmt2() {
		return maxTxnAmt2;
	}
	public void setMaxTxnAmt2(String maxTxnAmt2) {
		this.maxTxnAmt2 = maxTxnAmt2;
	}
	public String getMaxDayTxnAmt2() {
		return maxDayTxnAmt2;
	}
	public void setMaxDayTxnAmt2(String maxDayTxnAmt2) {
		this.maxDayTxnAmt2 = maxDayTxnAmt2;
	}
	public String getDayTotalAmt2() {
		return dayTotalAmt2;
	}
	public void setDayTotalAmt2(String dayTotalAmt2) {
		this.dayTotalAmt2 = dayTotalAmt2;
	}
	public String getFreezeAmt() {
		return freezeAmt;
	}
	public void setFreezeAmt(String freezeAmt) {
		this.freezeAmt = freezeAmt;
	}
	public String getLastTxnDate() {
		return lastTxnDate;
	}
	public void setLastTxnDate(String lastTxnDate) {
		this.lastTxnDate = lastTxnDate;
	}
	public String getLastTxnTime() {
		return lastTxnTime;
	}
	public void setLastTxnTime(String lastTxnTime) {
		this.lastTxnTime = lastTxnTime;
	}
	
}
