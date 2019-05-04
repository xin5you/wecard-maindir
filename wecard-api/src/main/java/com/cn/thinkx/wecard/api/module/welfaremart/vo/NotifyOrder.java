package com.cn.thinkx.wecard.api.module.welfaremart.vo;

public class NotifyOrder {

	private String channel;
	private String respResult;
	private String innerMerchantNo;
	private String innerShopNo;
	private String userId;
	private String orderId;
	private String settleDate;
	private String txnFlowNo;
	private String oriTxnAmount;
	private String txnAmount;
	private String attach;
	private String sign;
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getRespResult() {
		return respResult;
	}
	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}
	public String getInnerMerchantNo() {
		return innerMerchantNo;
	}
	public void setInnerMerchantNo(String innerMerchantNo) {
		this.innerMerchantNo = innerMerchantNo;
	}
	public String getInnerShopNo() {
		return innerShopNo;
	}
	public void setInnerShopNo(String innerShopNo) {
		this.innerShopNo = innerShopNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getTxnFlowNo() {
		return txnFlowNo;
	}
	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}
	public String getOriTxnAmount() {
		return oriTxnAmount;
	}
	public void setOriTxnAmount(String oriTxnAmount) {
		this.oriTxnAmount = oriTxnAmount;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
