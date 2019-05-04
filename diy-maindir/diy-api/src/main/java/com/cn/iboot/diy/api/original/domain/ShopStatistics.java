package com.cn.iboot.diy.api.original.domain;

import java.util.ArrayList;
import java.util.List;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class ShopStatistics extends BaseEntity {

	private static final long serialVersionUID = -2031762227486700449L;
	
	private String id;
	private String mchntStatisticsId;
	private String shopCode;   //门店code
	private String cardPayAmt;
	private Integer cardPayNum;
	private String quickPayAmt;
	private Integer quickPayNum;
	private String olRechargeAmt;
	private String olRechargeDen;
	private String pfRechargeAmt;
	private String pfRechargeDen;
	private Integer pfSubsidyNum;
	private String pfSubsidyAmt;
	private String cardBal;
	private String fee;
	private String settleAmt;
	private String settleDate;
	
	private String payCountAmt;
	private String shopName;
	private String mchntCode;
	private String startDate;
	private String endDate;
	private List<String> shopCodes = new ArrayList<String>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMchntStatisticsId() {
		return mchntStatisticsId;
	}
	public void setMchntStatisticsId(String mchntStatisticsId) {
		this.mchntStatisticsId = mchntStatisticsId;
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
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
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
	public List<String> getShopCodes() {
		return shopCodes;
	}
	public void setShopCodes(List<String> shopCodes) {
		this.shopCodes = shopCodes;
	}
	public String getPayCountAmt() {
		return payCountAmt;
	}
	public void setPayCountAmt(String payCountAmt) {
		this.payCountAmt = payCountAmt;
	}
}
