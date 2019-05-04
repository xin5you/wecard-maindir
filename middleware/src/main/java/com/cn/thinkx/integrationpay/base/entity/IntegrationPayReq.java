package com.cn.thinkx.integrationpay.base.entity;

import com.cn.thinkx.facade.bean.IntegrationPayRequest;

public class IntegrationPayReq extends IntegrationPayRequest {
 
	private static final long serialVersionUID = -1333459011528909434L;
	
	public String settleDate;//交易时间
	public String orderNo;//第三方订单号
	public String swtFlowNo;//第三方流水号
	public String reqDate;//请求日期 yyyyMMdd
	public String reqTime;//请求时间 hh:mm:ss
	
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSwtFlowNo() {
		return swtFlowNo;
	}
	public void setSwtFlowNo(String swtFlowNo) {
		this.swtFlowNo = swtFlowNo;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getReqTime() {
		return reqTime;
	}
	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
	
}
