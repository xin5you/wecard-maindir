package com.cn.thinkx.wecard.customer.module.checkstand.vo;

public class TranslogRefundReq extends OrderBaseTxnResp {

	private static final long serialVersionUID = -5006836897103585981L;
	
	private String txnPrimaryKey;
	private String phoneNumber;
	private String phoneCode;
	private String sign;
	public String getTxnPrimaryKey() {
		return txnPrimaryKey;
	}
	public void setTxnPrimaryKey(String txnPrimaryKey) {
		this.txnPrimaryKey = txnPrimaryKey;
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
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
