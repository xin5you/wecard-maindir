package com.cn.iboot.diy.api.operate.domain;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class ShopOprStatistics extends BaseEntity {

	private static final long serialVersionUID = -1809403158606714275L;

	private String id;
	private String mchntStatisticsOprId;
	private String shopCode;
	private String cardPayAmt;
	private String cardPayNum;
	private String quickPayAmt;
	private String quickPayNum;
	private String olRechargeAmt;
	private String olRechargeDen;
	private String pfRechargeAmt;
	private String pfRechargeDen;
	private String pfSubsidyNum;
	private String pfSubsidyAmt;
	private String cardBal;
	private String fee;
	private String settleAmt;
	private String settleDate;
	private String stat;
	
	
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMchntStatisticsOprId() {
		return mchntStatisticsOprId;
	}
	public void setMchntStatisticsOprId(String mchntStatisticsOprId) {
		this.mchntStatisticsOprId = mchntStatisticsOprId;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getCardPayAmt() {
		return cardPayAmt;
	}
	public void setCardPayAmt(String cardPayAmt) {
		this.cardPayAmt = cardPayAmt;
	}
	public String getCardPayNum() {
		return cardPayNum;
	}
	public void setCardPayNum(String cardPayNum) {
		this.cardPayNum = cardPayNum;
	}
	public String getQuickPayAmt() {
		return quickPayAmt;
	}
	public void setQuickPayAmt(String quickPayAmt) {
		this.quickPayAmt = quickPayAmt;
	}
	public String getQuickPayNum() {
		return quickPayNum;
	}
	public void setQuickPayNum(String quickPayNum) {
		this.quickPayNum = quickPayNum;
	}
	public String getOlRechargeAmt() {
		return olRechargeAmt;
	}
	public void setOlRechargeAmt(String olRechargeAmt) {
		this.olRechargeAmt = olRechargeAmt;
	}
	public String getOlRechargeDen() {
		return olRechargeDen;
	}
	public void setOlRechargeDen(String olRechargeDen) {
		this.olRechargeDen = olRechargeDen;
	}
	public String getPfRechargeAmt() {
		return pfRechargeAmt;
	}
	public void setPfRechargeAmt(String pfRechargeAmt) {
		this.pfRechargeAmt = pfRechargeAmt;
	}
	public String getPfRechargeDen() {
		return pfRechargeDen;
	}
	public void setPfRechargeDen(String pfRechargeDen) {
		this.pfRechargeDen = pfRechargeDen;
	}
	public String getPfSubsidyNum() {
		return pfSubsidyNum;
	}
	public void setPfSubsidyNum(String pfSubsidyNum) {
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
	
	
}
