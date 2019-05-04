package com.cn.thinkx.wecard.api.module.core.domain;

/**
 * 交易返回对象，用于接收交易返回时转换json字符串
 * 
 * @author pucker
 *
 */
public class TxnResp {

	private String code;
	private String info;
	private String transAmt;
	private String userId;
	private String balance;

	/** 返回接口层流水主键 **/
	private String txnFlowNo;
	
	/** 通卡流水主键*/
	private String accWxPrimaryKey;

	public String getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public String getUserId() {
		return userId;
	}

	public String getTxnFlowNo() {
		return txnFlowNo;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getAccWxPrimaryKey() {
		return accWxPrimaryKey;
	}

	public void setAccWxPrimaryKey(String accWxPrimaryKey) {
		this.accWxPrimaryKey = accWxPrimaryKey;
	}

}