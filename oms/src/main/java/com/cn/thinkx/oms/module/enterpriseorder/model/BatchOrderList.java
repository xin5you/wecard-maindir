package com.cn.thinkx.oms.module.enterpriseorder.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class BatchOrderList extends BaseDomain {
	private String orderListId;                     	 //订单明细_id
	private String orderId;                              //订单号
	private String userName;                         //用户名
	private String phoneNo;                         //手机号
	private String userCardNo;                     //身份证号
	private String productCode;                  //产品号
	private String productName;                 //产品名称
	private String amount;                          //金额
	private String orderStat;                      //订单状态
	private String orderDesc;                    //订单描述
	private String dataStat;                      //数据状态
	
	private String puid;
	
	private String invoiceStat;		//开票状态
	
	public String getInvoiceStat() {
		return invoiceStat;
	}

	public void setInvoiceStat(String invoiceStat) {
		this.invoiceStat = invoiceStat;
	}

	public BatchOrderList() {
	}

	public String getOrderListId() {
		return orderListId;
	}

	public void setOrderListId(String orderListId) {
		this.orderListId = orderListId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getUserCardNo() {
		return userCardNo;
	}

	public void setUserCardNo(String userCardNo) {
		this.userCardNo = userCardNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getPuid() {
		return puid;
	}

	public void setPuid(String puid) {
		this.puid = puid;
	}
	
}
