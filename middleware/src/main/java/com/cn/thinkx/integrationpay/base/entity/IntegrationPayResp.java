package com.cn.thinkx.integrationpay.base.entity;

import com.cn.thinkx.dubbo.entity.BaseTransResp;

public class IntegrationPayResp extends BaseTransResp {
	
	/**申鑫退款返回参数说明*/
	private String version;// 版本号
	private String instId;// 机构号
	private String mId;//商户号
	private String tId;//终端号//
	private String requestStatus;//请求受理结果
	private String status;//请求处理结果
	private String traceId;//订单跟踪号
	private String refundSeq;//退款序列号
	
	/**申鑫查询返回参数说明*/
	private String respCode;
	private String respMsg;
	private String orderId;
	private String payStatus;
	private String payDesc;
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getInstId() {
		return instId;
	}
	public void setInstId(String instId) {
		this.instId = instId;
	}
	public String getmId() {
		return mId;
	}
	public void setmId(String mId) {
		this.mId = mId;
	}
	public String gettId() {
		return tId;
	}
	public void settId(String tId) {
		this.tId = tId;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getRefundSeq() {
		return refundSeq;
	}
	public void setRefundSeq(String refundSeq) {
		this.refundSeq = refundSeq;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayDesc() {
		return payDesc;
	}
	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}
}
