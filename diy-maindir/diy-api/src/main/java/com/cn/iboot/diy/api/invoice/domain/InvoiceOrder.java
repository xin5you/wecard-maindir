package com.cn.iboot.diy.api.invoice.domain;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class InvoiceOrder extends BaseEntity{
	
	private String id;	//订单号
	private String itfPrimaryKey;	//交易流水号
	private String invoiceUserName;	//开票人姓名
	private String invoiceMobile;	//开票人手机号
	private String invoiceAmt;		//开票金额
	private String invoiceType;	//开票类型
	private String mchntCode;	//充值商户code
	private String shopCode;	//开票门店code
	private String invoiceInfo;	//开票信息
	private String invoiceStat;	//开票状态
	
	private String mchntName;
	private String shopName;
	
	private String binvoiceAmtCount;	//批量充值开票总金额
	private String invoiceAmtCount;	//个人充值开票总金额
	private String amtCount;	//开票总金额
	
	private String startDate;
	private String endDate;
	
	public String getAmtCount() {
		return amtCount;
	}
	public void setAmtCount(String amtCount) {
		this.amtCount = amtCount;
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
	public String getBinvoiceAmtCount() {
		return binvoiceAmtCount;
	}
	public void setBinvoiceAmtCount(String binvoiceAmtCount) {
		this.binvoiceAmtCount = binvoiceAmtCount;
	}
	public String getInvoiceAmtCount() {
		return invoiceAmtCount;
	}
	public void setInvoiceAmtCount(String invoiceAmtCount) {
		this.invoiceAmtCount = invoiceAmtCount;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItfPrimaryKey() {
		return itfPrimaryKey;
	}
	public void setItfPrimaryKey(String itfPrimaryKey) {
		this.itfPrimaryKey = itfPrimaryKey;
	}
	public String getInvoiceUserName() {
		return invoiceUserName;
	}
	public void setInvoiceUserName(String invoiceUserName) {
		this.invoiceUserName = invoiceUserName;
	}
	public String getInvoiceMobile() {
		return invoiceMobile;
	}
	public void setInvoiceMobile(String invoiceMobile) {
		this.invoiceMobile = invoiceMobile;
	}
	public String getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
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
	public String getInvoiceInfo() {
		return invoiceInfo;
	}
	public void setInvoiceInfo(String invoiceInfo) {
		this.invoiceInfo = invoiceInfo;
	}
	public String getInvoiceStat() {
		return invoiceStat;
	}
	public void setInvoiceStat(String invoiceStat) {
		this.invoiceStat = invoiceStat;
	}
	
}
