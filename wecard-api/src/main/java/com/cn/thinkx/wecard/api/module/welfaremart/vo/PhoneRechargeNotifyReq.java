package com.cn.thinkx.wecard.api.module.welfaremart.vo;

public class PhoneRechargeNotifyReq {
	private String code;
	private String msg;
	private String orderId; //订单id
	private String channelOrderNo;//渠道订单号
	private String userId;//操作人id
	private String phone;//手机号
	private String telephoneFace;//话费面额
	private String orderType;//订单类型
	private String attach;//附加数据
	private String reqChannel; //请求渠道号
	private String respTime;//消息发送时间
	private String sign;//签名
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
	public String getChannelOrderNo() {
		return channelOrderNo;
	}
	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelephoneFace() {
		return telephoneFace;
	}
	public void setTelephoneFace(String telephoneFace) {
		this.telephoneFace = telephoneFace;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getReqChannel() {
		return reqChannel;
	}
	public void setReqChannel(String reqChannel) {
		this.reqChannel = reqChannel;
	}
	public String getRespTime() {
		return respTime;
	}
	public void setRespTime(String respTime) {
		this.respTime = respTime;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
