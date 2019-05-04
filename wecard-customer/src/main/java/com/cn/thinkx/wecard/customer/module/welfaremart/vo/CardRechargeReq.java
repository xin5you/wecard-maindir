package com.cn.thinkx.wecard.customer.module.welfaremart.vo;

/**
 * 卡券充值请求参数
 * 
 * @author xiaomei
 *
 */
public class CardRechargeReq {

	private String accessToken;			//accessToken
	private String channelOrderNo;		//渠道订单号
	private String userId;				//操作人ID
	private String phone;				//充值手机号
	private String telephoneFace;		//充值面额
	private String transAmt;		//交易金额
	private String orderType;		//订单类型
	private String reqChannel;		//请求渠道号
	private String callBack;		//回调地址
	private String attach;			//附加数据
	private long timestamp;			//时间戳
	private String sign;			//签名
	
	private String itemId;	//商品编号
	
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getReqChannel() {
		return reqChannel;
	}
	public void setReqChannel(String reqChannel) {
		this.reqChannel = reqChannel;
	}
	public String getCallBack() {
		return callBack;
	}
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
