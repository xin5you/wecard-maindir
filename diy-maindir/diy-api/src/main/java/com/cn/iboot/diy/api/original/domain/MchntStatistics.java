package com.cn.iboot.diy.api.original.domain;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class MchntStatistics extends BaseEntity {

	private static final long serialVersionUID = -4568477616253521740L;
	
	private String id;
	private String  mchntCode;
	private String cardPayAmt;
	private String quickPayAmt;
	private String olRechargeDen;
	private String olRechargeAmt;
	private String pfRechargeDen;
	private String pfRechargeAmt;
	private Integer pfSubsidyNum;
	private String pfSubsidyAmt;
	private String cardBal;
	private String fee;
	private String settleAmt;
	private String settleDate;
	
	private String mchntName;
	private String startDate;
	private String endDate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
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
	public String getOlRechargeDen() {
		return olRechargeDen;
	}
	public void setOlRechargeDen(String olRechargeDen) {
		this.olRechargeDen = olRechargeDen;
	}
	public String getOlRechargeAmt() {
		return olRechargeAmt;
	}
	public void setOlRechargeAmt(String olRechargeAmt) {
		this.olRechargeAmt = olRechargeAmt;
	}
	public String getPfRechargeDen() {
		return pfRechargeDen;
	}
	public void setPfRechargeDen(String pfRechargeDen) {
		this.pfRechargeDen = pfRechargeDen;
	}
	public String getPfRechargeAmt() {
		return pfRechargeAmt;
	}
	public void setPfRechargeAmt(String pfRechargeAmt) {
		this.pfRechargeAmt = pfRechargeAmt;
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
	public String getCardBal() {
		return cardBal;
	}
	public void setCardBal(String cardBal) {
		this.cardBal = cardBal;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getSettleAmt() {
		return settleAmt;
	}
	public void setSettleAmt(String settleAmt) {
		this.settleAmt = settleAmt;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
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
}
