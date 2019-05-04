package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class ScanBoxDeviceInf extends BaseDomain{
	

	private String deviceId;	
	private String deviceType;	//设备类型
	private String deviceNo;	//设备号
	private String insCode;		//机构号
	private String mchntCode;	//商户号
	private String shopCode;	//门店号
	private String fixedPayFlag;
	private String fixedPayAmt;
	private String print;
	private String printQr;
	private String printType;
	private String receipt;
	private String dataStat;
	
	private String mchntName;	//商户名称
	private String shopName;	//门店名称
	
	private int count;
	
	private String channelNo;	//通道号
	private String channelName;	//通道名称
	
	public String getChannelNo() {
		return channelNo;
	}
	public void setChannelNo(String channelNo) {
		this.channelNo = channelNo;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getFixedPayFlag() {
		return fixedPayFlag;
	}
	public void setFixedPayFlag(String fixedPayFlag) {
		this.fixedPayFlag = fixedPayFlag;
	}
	public String getFixedPayAmt() {
		return fixedPayAmt;
	}
	public void setFixedPayAmt(String fixedPayAmt) {
		this.fixedPayAmt = fixedPayAmt;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public String getPrintQr() {
		return printQr;
	}
	public void setPrintQr(String printQr) {
		this.printQr = printQr;
	}
	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
