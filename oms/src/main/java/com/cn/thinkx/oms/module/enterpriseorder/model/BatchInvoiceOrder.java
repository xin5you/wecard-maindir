package com.cn.thinkx.oms.module.enterpriseorder.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class BatchInvoiceOrder extends BaseDomain{
	
	private String id;	//订单号
	private String orderId;	//交易流水号
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
