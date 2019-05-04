package com.cn.thinkx.wecard.facade.telrecharge.model;

import java.math.BigDecimal;

import com.cn.thinkx.common.base.core.domain.BaseEntity;

public class TelChannelOrderInf extends BaseEntity {

	private static final long serialVersionUID = -1203013799941780945L;

	private String channelOrderId;
	private String channelId;
	private String outerTid;
	private String rechargePhone;
	private String rechargeType;
	private BigDecimal rechargeValue; // 充值金额，面额
	private String itemNum;
	private BigDecimal payAmt; // 订单支付的金额
	private String orderStat;
	private String notifyStat;
	private String notifyFlag;
	private String notifyUrl;
	private String appVersion;
	private String productId;
	private String channelRate;
	private String resv1;
	private String resv2;
	private String resv3;
	private String resv4;
	private String resv5;
	private String resv6;

	private String channelName; // 分销商名称
	private String operName;//商品名称
	private String phoneNo;
	private String startDate;
	private String endDate;
	private String rechargeState;
	private String orderCount;
	private BigDecimal orderRechargeAmt;
	private BigDecimal orderPayAmt;
	private String mchntCode;

	public String getChannelOrderId() {
		return channelOrderId;
	}

	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getOuterTid() {
		return outerTid;
	}

	public void setOuterTid(String outerTid) {
		this.outerTid = outerTid;
	}

	public String getRechargePhone() {
		return rechargePhone;
	}

	public void setRechargePhone(String rechargePhone) {
		this.rechargePhone = rechargePhone;
	}

	public String getRechargeType() {
		return rechargeType;
	}

	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}

	public BigDecimal getRechargeValue() {
		return rechargeValue;
	}

	public void setRechargeValue(BigDecimal rechargeValue) {
		this.rechargeValue = rechargeValue;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	public String getNotifyStat() {
		return notifyStat;
	}

	public void setNotifyStat(String notifyStat) {
		this.notifyStat = notifyStat;
	}

	public String getNotifyFlag() {
		return notifyFlag;
	}

	public void setNotifyFlag(String notifyFlag) {
		this.notifyFlag = notifyFlag;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannelRate() {
		return channelRate;
	}

	public void setChannelRate(String channelRate) {
		this.channelRate = channelRate;
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

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	
	public String getRechargeState() {
		return rechargeState;
	}

	public void setRechargeState(String rechargeState) {
		this.rechargeState = rechargeState;
	}

	public String getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(String orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getOrderRechargeAmt() {
		return orderRechargeAmt;
	}

	public void setOrderRechargeAmt(BigDecimal orderRechargeAmt) {
		this.orderRechargeAmt = orderRechargeAmt;
	}

	public BigDecimal getOrderPayAmt() {
		return orderPayAmt;
	}

	public void setOrderPayAmt(BigDecimal orderPayAmt) {
		this.orderPayAmt = orderPayAmt;
	}
	
	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	@Override
	public String toString() {
		return "TelChannelOrderInf [channelOrderId=" + channelOrderId + ", channelId=" + channelId + ", outerTid="
				+ outerTid + ", rechargePhone=" + rechargePhone + ", rechargeType=" + rechargeType + ", rechargeValue="
				+ rechargeValue + ", itemNum=" + itemNum + ", payAmt=" + payAmt + ", orderStat=" + orderStat
				+ ", notifyStat=" + notifyStat + ", notifyFlag=" + notifyFlag + ", notifyUrl=" + notifyUrl
				+ ", appVersion=" + appVersion + ", productId=" + productId + ", channelRate=" + channelRate
				+ ", resv1=" + resv1 + ", resv2=" + resv2 + ", resv3=" + resv3 + ", resv4=" + resv4 + ", resv5=" + resv5
				+ ", resv6=" + resv6 + ", channelName=" + channelName + "]";
	}

}
