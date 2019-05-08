package com.cn.thinkx.oms.module.merchant.model;

import java.util.Arrays;

public class TransInf {

	private String tableNum;
	private String shopCode;
	private String shopName;
	private String mchntName;
	private String mchntCode;
	private String [] channelCode; //渠道号
	private String omschannelCode;
	private String userName;
	private String transType;
	private String queryType;
	private String startTime;
	private String endTime;
	private String accHkbMchntNo;	//薪无忧通卡商户号
	private String accHkbInsCode;	//薪无忧通卡机构号

	public String getAccHkbMchntNo() {
		return accHkbMchntNo;
	}

	public void setAccHkbMchntNo(String accHkbMchntNo) {
		this.accHkbMchntNo = accHkbMchntNo;
	}

	public String getAccHkbInsCode() {
		return accHkbInsCode;
	}

	public void setAccHkbInsCode(String accHkbInsCode) {
		this.accHkbInsCode = accHkbInsCode;
	}

	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String[] getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String[] channelCode) {
		this.channelCode = channelCode;
	}

	public String getOmschannelCode() {
		return omschannelCode;
	}

	public void setOmschannelCode(String omschannelCode) {
		this.omschannelCode = omschannelCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "TransInf [tableNum=" + tableNum + ", shopCode=" + shopCode + ", shopName=" + shopName + ", mchntName="
				+ mchntName + ", mchntCode=" + mchntCode + ", channelCode=" + Arrays.toString(channelCode)
				+ ", omschannelCode=" + omschannelCode + ", userName=" + userName + ", transType=" + transType
				+ ", queryType=" + queryType + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
