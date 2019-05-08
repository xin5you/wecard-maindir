package com.cn.thinkx.common.service.module.jiafupay.vo;

/**
 * 请求嘉福支付封装方法 入参参数
 * 
 * @author xiaomei
 * @date 2018/4/16
 */
public class JFChnlReq {

	private String txnAmount;	//支付金额
	private String jfUserId;	//嘉福用户编号
	private String swtFlowNo;	//薪无忧流水
	private String payType;		//支付方式
	
	public String getTxnAmount() {
		return txnAmount;
	}
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	public String getJfUserId() {
		return jfUserId;
	}
	public void setJfUserId(String jfUserId) {
		this.jfUserId = jfUserId;
	}
	public String getSwtFlowNo() {
		return swtFlowNo;
	}
	public void setSwtFlowNo(String swtFlowNo) {
		this.swtFlowNo = swtFlowNo;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
}
