package com.cn.thinkx.wecard.facade.telrecharge.vo;

public class FrtPhoneRechargeResp  implements java.io.Serializable{

	private static final long serialVersionUID = -3223934674635004197L;
	
	private String orderId;
	private String channelOrderNo;
	private String userId;
	private String phone;
	private String telephoneFace;
	private String orderType;
	private String attach;
	private String reqChannel;
	private String	respTime;
	private String sign;
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

