package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseDomain;

public class CardTransDetailQueryITFVo  extends BaseDomain {

	private static final long serialVersionUID = 1L;
	/**
	 * 原交易流水号
	 */
	private String swtFlowNo;
	/**
	 * 交易流水号
	 */
	private String txnFlowNo;
	/**
	 * 商户号
	 */
	private String mchntCode;
	/**
	 * 门店号
	 */
	private String shopCode;
	/**
	 * 商户名称
	 */
	private String mchntName;
	/**
	 * 门店名称
	 */
	private String shopName;
	/**
	 * 交易金额
	 */
	private String txnAmount;
	/**
	 * 余额
	 */
	private String balance;
	/**
	 * 交易类型
	 */
	private String transId;
	/**
	 * 交易类型描述
	 */
	private String transIdDesc;
	/**
	 * 交易时间
	 */
	private String transTime;
	
	//业务字段
	
	private String transIdStr;//
	
	private String dateStr;
	
	private String timeStr;

	public String getSwtFlowNo() {
		return swtFlowNo;
	}

	public void setSwtFlowNo(String swtFlowNo) {
		this.swtFlowNo = swtFlowNo;
	}

	public String getTxnFlowNo() {
		return txnFlowNo;
	}

	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
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

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getTransIdDesc() {
		return transIdDesc;
	}

	public void setTransIdDesc(String transIdDesc) {
		this.transIdDesc = transIdDesc;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTransIdStr() {
		return transIdStr;
	}

	public void setTransIdStr(String transIdStr) {
		this.transIdStr = transIdStr;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getTimeStr() {
		return timeStr;
	}

	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}

}
