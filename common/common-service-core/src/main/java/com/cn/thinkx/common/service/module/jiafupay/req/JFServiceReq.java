package com.cn.thinkx.common.service.module.jiafupay.req;

/**
 * 嘉福业务请求参数
 * 
 * @author xiaomei
 * @date 2018/4/16
 */
public class JFServiceReq{

	private String amount;		//充值金额
	private String uid;			//用户编号
	private String bizid;		//业务编号
	private String payMethod;	//支付方式
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getBizid() {
		return bizid;
	}
	public void setBizid(String bizid) {
		this.bizid = bizid;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	
}
