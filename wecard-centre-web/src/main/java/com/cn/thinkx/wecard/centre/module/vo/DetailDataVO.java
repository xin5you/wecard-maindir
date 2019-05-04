package com.cn.thinkx.wecard.centre.module.vo;

/**
 * 付款详细数据
 * 
 * @author pucker
 *
 */
public class DetailDataVO {

	private String serialNo;
	private String receiverType;
	private String receiverCurrency;
	private String receiverName;
	private String amount;
	private String orderName;
	private String bankName;
	private String receiverCardNo;
	private String bankCode;
	private String bankProvince;
	private String bankCity;
	private String payeeBankLinesNo;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getReceiverCurrency() {
		return receiverCurrency;
	}

	public void setReceiverCurrency(String receiverCurrency) {
		this.receiverCurrency = receiverCurrency;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getReceiverCardNo() {
		return receiverCardNo;
	}

	public void setReceiverCardNo(String receiverCardNo) {
		this.receiverCardNo = receiverCardNo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getPayeeBankLinesNo() {
		return payeeBankLinesNo;
	}

	public void setPayeeBankLinesNo(String payeeBankLinesNo) {
		this.payeeBankLinesNo = payeeBankLinesNo;
	}

}
