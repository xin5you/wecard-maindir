package com.cn.thinkx.common.wecard.domain.ecomorder;

import com.cn.thinkx.pms.base.domain.BaseDomain;

public class OrderInf extends BaseDomain {

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 渠道号
	 */
	private String channel;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 电商订单号
	 */
	private String routerOrderNo;
	/**
	 * 商户号
	 */
	private String merchantNo;
	/**
	 * 门店号
	 */
	private String shopNo;
	/**
	 * 商品名称
	 */
	private String commodityName;
	/**
	 * 商品数量
	 */
	private String commodityNum;
	/**
	 * 交易金额
	 */
	private String txnAmount;
	/**
	 * 订单状态   0未支付   1支付失败   2支付完成
	 */
	private String orderType;
	/**
	 * 支付通知地址
	 */
	private String notifyUrl;
	/**
	 * 重定向地址
	 */
	private String redirectUrl;
	/**
	 *薪无忧流水（itf主键）
	 */
	private String txnFlowNo;
	/**
	 * 电商原交易订单号
	 */
	private String orgID;
	/**
	 * 备用字段1
	 */
	private String resv1;
	/**
	 * 备用字段2
	 */
	private String resv2;
	/**
	 * 备用字段3
	 */
	private String resv3;
	/**
	 * 备用字段4
	 */
	private String resv4;
	/**
	 * 备用字段5
	 */
	private String resv5;
	/**
	 * 备用字段6
	 */
	private String resv6;
	
	private String dataStat;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRouterOrderNo() {
		return routerOrderNo;
	}

	public void setRouterOrderNo(String routerOrderNo) {
		this.routerOrderNo = routerOrderNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getShopNo() {
		return shopNo;
	}

	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(String commodityNum) {
		this.commodityNum = commodityNum;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getTxnFlowNo() {
		return txnFlowNo;
	}

	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
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

}
