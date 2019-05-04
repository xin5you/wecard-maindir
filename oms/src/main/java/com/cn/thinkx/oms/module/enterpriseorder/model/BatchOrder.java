package com.cn.thinkx.oms.module.enterpriseorder.model;

import java.util.Date;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class BatchOrder extends BaseDomain {
	private String orderId;                                 //订单号
	private String productCode;                        //产品号
	private String productName;                      //卡产品名称
	private String orderName;                         //订单名称
	private String orderType;                          //订单类型
	private Date orderDate;                           //订单日期
	private String orderStat;                          //订单状态
	private String dataStat;                          //数据状态
	private String companyName;              //公司名称
	private String bizType;                          //业务类型
	private String resv1;                             //备用字段1
	private String resv2;                             //备用字段2
	private String resv3;                             //备用字段3
	private String resv4;                             //备用字段4
	private String resv5;                             //备用字段5
	private String resv6;                             //备用字段6
	
	private String orderCount;                     //订单数量
	private String sumAmount;                   //总金额
	private String startTime;                       //开始时间
	private String endTime;                       //结束时间
	private String disposeWait;                  //未处理
	private String disposeSuccess;             //处理成功
	private String disposeFail;                   //处理失败
	
	private String invoiceStat;			//开票状态
	
	public BatchOrder() {
		
	}

	public BatchOrder(String orderId, String productCode, String productName, String orderName, String orderType,
			Date orderDate, String orderStat, String dataStat, String companyName, String bizType, String resv1,
			String resv2, String resv3, String resv4, String resv5, String resv6, String orderCount, String sumAmount,
			String startTime, String endTime, String disposeWait, String disposeSuccess, String disposeFail,
			String invoiceStat) {
		super();
		this.orderId = orderId;
		this.productCode = productCode;
		this.productName = productName;
		this.orderName = orderName;
		this.orderType = orderType;
		this.orderDate = orderDate;
		this.orderStat = orderStat;
		this.dataStat = dataStat;
		this.companyName = companyName;
		this.bizType = bizType;
		this.resv1 = resv1;
		this.resv2 = resv2;
		this.resv3 = resv3;
		this.resv4 = resv4;
		this.resv5 = resv5;
		this.resv6 = resv6;
		this.orderCount = orderCount;
		this.sumAmount = sumAmount;
		this.startTime = startTime;
		this.endTime = endTime;
		this.disposeWait = disposeWait;
		this.disposeSuccess = disposeSuccess;
		this.disposeFail = disposeFail;
		this.invoiceStat = invoiceStat;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	public String getResv2() {
		return resv2;
	}

	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}

	public String getResv3() {
		return resv3;
	}

	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}

	public String getResv4() {
		return resv4;
	}

	public void setResv4(String resv4) {
		this.resv4 = resv4;
	}

	public String getResv5() {
		return resv5;
	}

	public void setResv5(String resv5) {
		this.resv5 = resv5;
	}

	public String getResv6() {
		return resv6;
	}

	public void setResv6(String resv6) {
		this.resv6 = resv6;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public String getSumAmount() {
		return sumAmount;
	}

	public void setSumAmount(String sumAmount) {
		this.sumAmount = sumAmount;
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

	public String getDisposeWait() {
		return disposeWait;
	}

	public void setDisposeWait(String disposeWait) {
		this.disposeWait = disposeWait;
	}

	public String getDisposeSuccess() {
		return disposeSuccess;
	}

	public void setDisposeSuccess(String disposeSuccess) {
		this.disposeSuccess = disposeSuccess;
	}

	public String getDisposeFail() {
		return disposeFail;
	}

	public void setDisposeFail(String disposeFail) {
		this.disposeFail = disposeFail;
	}

	public String getInvoiceStat() {
		return invoiceStat;
	}

	public void setInvoiceStat(String invoiceStat) {
		this.invoiceStat = invoiceStat;
	}
	
}
