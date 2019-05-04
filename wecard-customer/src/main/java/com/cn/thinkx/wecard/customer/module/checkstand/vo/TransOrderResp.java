package com.cn.thinkx.wecard.customer.module.checkstand.vo;

/**
 * 交易订单通知vo
 * @author zqy
 *
 */
public class TransOrderResp extends OrderBaseTxnResp {

	private static final long serialVersionUID = -4218083226025958914L;
	private String innerMerchantNo;
	private String innerShopNo;
	private String userId;
	private String orderId;
	private String settleDate;
	private String txnFlowNo;
	private String oriTxnAmount;
	private String txnAmount;
	private String attach;
	
	private String redirectType; //重定向标记
	private String redirectUrl; //重定向URL

	private String sign;
	
	public String getInnerMerchantNo() {
		return innerMerchantNo;
	}
	public String getInnerShopNo() {
		return innerShopNo;
	}
	public String getUserId() {
		return userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public String getTxnFlowNo() {
		return txnFlowNo;
	}
	public String getOriTxnAmount() {
		return oriTxnAmount;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public String getAttach() {
		return attach;
	}
	public String getSign() {
		return sign;
	}
	public void setInnerMerchantNo(String innerMerchantNo) {
		this.innerMerchantNo = innerMerchantNo;
	}
	public void setInnerShopNo(String innerShopNo) {
		this.innerShopNo = innerShopNo;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}
	public void setOriTxnAmount(String oriTxnAmount) {
		this.oriTxnAmount = oriTxnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getRedirectType() {
		return redirectType;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
