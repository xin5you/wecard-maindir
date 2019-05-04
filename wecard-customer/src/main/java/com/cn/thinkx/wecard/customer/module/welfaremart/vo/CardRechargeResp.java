package com.cn.thinkx.wecard.customer.module.welfaremart.vo;

/**
 * 卡券充值同步返回参数
 * 
 * @author xiaomei
 *
 */
public class CardRechargeResp {

	private String code;		
	private String msg;	
	private String orderId;
	
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
	
}
