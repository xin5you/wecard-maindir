package com.cn.thinkx.merchant.domain;

import com.cn.thinkx.core.domain.BaseDomain;

/**
 * 商户账户信息
 * @author zqy
 *
 */
public class MchntAcctInf extends BaseDomain {

	private String accountNo;
	private String insId;	 	//机构_id
	private String manchtId;	//主商户_id
	private String accountType; //账户类型
	private String accountStat; //账户状态
	private String accBal;		//余额明文 单位（分）
	private String accBalCode;	//余额密文
	private String maxTxnAmt1;  //单笔Pos交易限额
	private String maxDayTxnAmt1; //每日Pos交易限额
	private String dayTotalAmt1; //当天Pos交易总金额
	private String maxTxnAmt2;  //单笔Web交易限额
	private String maxDayTxnAmt2; //每日Web交易限额
	private String dayTotalAmt2;  //当天Web交易总金额
	private String freezeAmt;	//冻结金额
	private String lastTxnDate; //最近交易日期
	private String lastTxnTime; //最近交易时间
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getInsId() {
		return insId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getManchtId() {
		return manchtId;
	}
	public void setManchtId(String manchtId) {
		this.manchtId = manchtId;
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
