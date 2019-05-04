package com.cn.thinkx.wecard.api.module.withdraw.vo;

public class WelfaremartResellResp {
	
	/*
	 * true/false
	 */
	private boolean status;
	
	/*
	 * 返回码
	 */
	private String code;

	/*
	 * 返回信息
	 */
	private String msg;
	
	/*
	 * 订单号
	 */
	private String orderId;
	
	/*
	 * 标志
	 */
	private String check;
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}
}
