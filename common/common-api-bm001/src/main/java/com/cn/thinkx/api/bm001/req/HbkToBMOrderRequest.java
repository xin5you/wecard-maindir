package com.cn.thinkx.api.bm001.req;


public class HbkToBMOrderRequest implements  java.io.Serializable {

	private static final long serialVersionUID = -1259608733245370258L;

	private String userId;
	private String orderId;
	private String mobileNo;
	private String rechargeAmount;
	private String callback;
	private String channl;
	private String timestamp;
	private String sign;

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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getChannl() {
		return channl;
	}

	public void setChannl(String channl) {
		this.channl = channl;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "HbkToBMOrderRequest{" +
				"userId='" + userId + '\'' +
				", orderId='" + orderId + '\'' +
				", mobileNo='" + mobileNo + '\'' +
				", rechargeAmount='" + rechargeAmount + '\'' +
				", callback='" + callback + '\'' +
				", channl='" + channl + '\'' +
				", timestamp='" + timestamp + '\'' +
				", sign='" + sign + '\'' +
				'}';
	}
}
