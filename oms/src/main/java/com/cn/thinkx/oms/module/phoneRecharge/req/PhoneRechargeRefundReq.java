package com.cn.thinkx.oms.module.phoneRecharge.req;

public class PhoneRechargeRefundReq {
	private String oriOrderId;
	private String refundOrderId;
	private String refundAmount;
	private String channel;
	private String refundFlag;
	private long timestamp;
	private String sign;

	public String getOriOrderId() {
		return oriOrderId;
	}

	public void setOriOrderId(String oriOrderId) {
		this.oriOrderId = oriOrderId;
	}

	public String getRefundOrderId() {
		return refundOrderId;
	}

	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
