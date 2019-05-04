package com.cn.iboot.diy.api.operate.domain;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class PositOprStatistics extends BaseEntity {

	private static final long serialVersionUID = -5804506437271169268L;

	private String id;
	private String shopStatisticsOprId;
	private String shopCode;
	private String shopName;
	private String mchntName;
	private String mchntCode;
	private String settleDate;
	private String cardPayAmt;
	private Integer cardPayNum;
	private String quickPayAmt;
	private Integer quickPayNum;
	private String  payAmt;
	
	private String  uPayAmt;	 //修改金额
	
	private String  totalPay;    //消费总金额
	private Integer totalNum;
	
	private String num;
	
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	/**
	 * 开始结束时间参数
	 */
	private String startDate;
	private String endDate;
	
	private String stat; 
	
	private String stat2; //状态(已格式化)

	
	
	
	public String getuPayAmt() {
		return uPayAmt;
	}
	public void setuPayAmt(String uPayAmt) {
		this.uPayAmt = uPayAmt;
	}

	public String getStat2() {
		return stat2;
	}
	public void setStat2(String stat2) {
		this.stat2 = stat2;
	}
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

	public String getShopStatisticsOprId() {
		return shopStatisticsOprId;
	}
	public void setShopStatisticsOprId(String shopStatisticsOprId) {
		this.shopStatisticsOprId = shopStatisticsOprId;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
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
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
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


	@Override
	public String toString() {
		return "PositOprStatistics [id=" + id + ", shopStatisticsOprId=" + shopStatisticsOprId + ", shopCode="
				+ shopCode + ", shopName=" + shopName + ", mchntName=" + mchntName + ", mchntCode=" + mchntCode
				+ ", settleDate=" + settleDate + ", cardPayAmt=" + cardPayAmt + ", cardPayNum=" + cardPayNum
				+ ", quickPayAmt=" + quickPayAmt + ", quickPayNum=" + quickPayNum + ", payAmt=" + payAmt + ", uPayAmt="
				+ uPayAmt + ", totalPay=" + totalPay + ", startDate=" + startDate + ", endDate=" + endDate + ", stat="
				+ stat + ", stat2=" + stat2 + "]";
	}
	public String getDateSub(){
		String date = "201712";
		if(this.getSettleDate()!=null)
			date = this.getSettleDate().substring(0, 6);
		return date;
	}
	public String getTotalPay() {
		return totalPay;
	}
	public void setTotalPay(String totalPay) {
		this.totalPay = totalPay;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

}
