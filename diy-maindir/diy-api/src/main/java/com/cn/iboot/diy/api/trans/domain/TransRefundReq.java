package com.cn.iboot.diy.api.trans.domain;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class TransRefundReq extends BaseEntity{

	private String dmsRelatedKey;
	private String txnPrimaryKey;
	private String settleDate;
	private String transAmt;
	private String phoneNumber;
	private String phoneCode;
	
	private String channel;
	private String sign;
	
	private String shopCode;
	private String mchntCode;
	private String mchntName;// 商户名称
	private String shopName;// 门店名称
	private String transTime;// 交易时间
	
	private String invoiceInfo;	//开票详情
	
	public String getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
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
	public String getTransTime() {
		return transTime;
	}
	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}
	public String getDmsRelatedKey() {
		return dmsRelatedKey;
	}
	public void setDmsRelatedKey(String dmsRelatedKey) {
		this.dmsRelatedKey = dmsRelatedKey;
	}
	public String getTxnPrimaryKey() {
		return txnPrimaryKey;
	}
	public void setTxnPrimaryKey(String txnPrimaryKey) {
		this.txnPrimaryKey = txnPrimaryKey;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
