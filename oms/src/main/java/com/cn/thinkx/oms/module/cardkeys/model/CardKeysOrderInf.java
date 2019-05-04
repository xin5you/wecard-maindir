package com.cn.thinkx.oms.module.cardkeys.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 卡密交易订单表
 * 
 * @author xiaomei
 *
 */
public class CardKeysOrderInf extends BaseDomain {

	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 产品号
	 */
	private String productCode;
	/**
	 * 银行卡号
	 */
	private String bankNo;
	/**
	 * 订单金额
	 */
	private String amount;
	/**
	 * 出款金额
	 */
	private String paidAmount;
	/**
	 * 订单类型
	 */
	private String type;

	/**
	 * 订单状态
	 */
	private String stat;

	/**
	 * 订单数量
	 */
	private String num;

	/**
	 * 状态
	 */
	private String dataStat;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 开户行
	 */
	private String accountBank;

	/**
	 * 开户行所在地
	 */
	private String accountBankAddr;
	
	private String productName;	//卡密产品名称
	private String productType;	//卡密产品类型
	private String personalName; //用户名称
	private String queryType;	//查询时间类型（当天或历史）
	private String startTime;	//开始时间
	private String endTime;	//结束时间
	
	private String orderStat;	//订单状态

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountBankAddr() {
		return accountBankAddr;
	}

	public void setAccountBankAddr(String accountBankAddr) {
		this.accountBankAddr = accountBankAddr;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
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

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

}
