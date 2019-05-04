package com.cn.thinkx.oms.module.cardkeys.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 卡密交易流水
 * 
 * @author xiaomei
 *
 */
public class CardKeysTransLog extends BaseDomain {

	/**
	 * 交易流水号
	 */
	private String txnPrimaryKey;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 交易类型
	 */
	private String transId;
	/**
	 * 卡密
	 */
	private String cardKey;
	/**
	 * 产品号
	 */
	private String productCode;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 转入账户
	 */
	private String tfrInAcctNo;
	
	/**
	 * 转出账户
	 */
	private String tfrOutAcctNo;
	/**
	 * 实际交易金额
	 */
	private String transAmt;
	/**
	 * 原交易金额
	 */
	private String orgTransAmt;
	/**
	 * 手续费
	 */
	private String transFee;
	/**
	 * 手续费类型
	 */
	private String transFeeType;
	/**
	 * 交易结果
	 */
	private String transResult;
	/**
	 * 附加信息
	 */
	private String additionalInfo;
	
	/**
	 * 状态
	 */
	private String dataStat;
	
	private String productName;	//卡密产品名称
	private String personalName;	//用户名

	public String getTxnPrimaryKey() {
		return txnPrimaryKey;
	}

	public void setTxnPrimaryKey(String txnPrimaryKey) {
		this.txnPrimaryKey = txnPrimaryKey;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getCardKey() {
		return cardKey;
	}

	public void setCardKey(String cardKey) {
		this.cardKey = cardKey;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTfrInAcctNo() {
		return tfrInAcctNo;
	}

	public void setTfrInAcctNo(String tfrInAcctNo) {
		this.tfrInAcctNo = tfrInAcctNo;
	}

	public String getTfrOutAcctNo() {
		return tfrOutAcctNo;
	}

	public void setTfrOutAcctNo(String tfrOutAcctNo) {
		this.tfrOutAcctNo = tfrOutAcctNo;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getOrgTransAmt() {
		return orgTransAmt;
	}

	public void setOrgTransAmt(String orgTransAmt) {
		this.orgTransAmt = orgTransAmt;
	}

	public String getTransFee() {
		return transFee;
	}

	public void setTransFee(String transFee) {
		this.transFee = transFee;
	}

	public String getTransFeeType() {
		return transFeeType;
	}

	public void setTransFeeType(String transFeeType) {
		this.transFeeType = transFeeType;
	}

	public String getTransResult() {
		return transResult;
	}

	public void setTransResult(String transResult) {
		this.transResult = transResult;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additonalInfo) {
		this.additionalInfo = additonalInfo;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}

}
