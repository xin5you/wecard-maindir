package com.cn.thinkx.wecard.customer.module.checkstand.vo;

public class TransOrderReq extends OrderBaseTxnResp  {

	private static final long serialVersionUID = 5982125449011111672L;
	
	private String userId; //用户ID
	private String orderId; //商户订单号
	private String txnFlowNo;//交易流水号
	private String innerMerchantNo; //商户Code
	private String innerShopNo;//门店号
	private String commodityName;//商品名称
	private String commodityNum;//商品数量
	private String txnAmount;//交易金额
	private String attach;//附加信息
	private String notify_type;
	private String notify_url;
	private String redirect_type;
	private String redirect_url;
	private String signType;
	private String sign;
	
	public String getUserId() {
		return userId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getTxnFlowNo() {
		return txnFlowNo;
	}
	public String getInnerMerchantNo() {
		return innerMerchantNo;
	}
	public String getInnerShopNo() {
		return innerShopNo;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public String getCommodityNum() {
		return commodityNum;
	}
	public String getTxnAmount() {
		return txnAmount;
	}
	public String getAttach() {
		return attach;
	}
	public String getNotify_type() {
		return notify_type;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public String getRedirect_type() {
		return redirect_type;
	}
	public String getRedirect_url() {
		return redirect_url;
	}
	public String getSignType() {
		return signType;
	}
	public String getSign() {
		return sign;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}
	public void setInnerMerchantNo(String innerMerchantNo) {
		this.innerMerchantNo = innerMerchantNo;
	}
	public void setInnerShopNo(String innerShopNo) {
		this.innerShopNo = innerShopNo;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public void setCommodityNum(String commodityNum) {
		this.commodityNum = commodityNum;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public void setNotify_type(String notify_type) {
		this.notify_type = notify_type;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public void setRedirect_type(String redirect_type) {
		this.redirect_type = redirect_type;
	}
	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
