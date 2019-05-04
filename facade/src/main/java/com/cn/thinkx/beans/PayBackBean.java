package com.cn.thinkx.beans;

import java.io.Serializable;

/**
 * 知了企服交易撤销bean
 * 
 * @author pucker
 *
 */
public class PayBackBean implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 外部订单号
	 */
	private String extOrderId;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 时间戳
	 */
	private String timestamp;
	/**
	 * 签名
	 */
	private String sign;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getExtOrderId() {
		return extOrderId;
	}

	public void setExtOrderId(String extOrderId) {
		this.extOrderId = extOrderId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

}
