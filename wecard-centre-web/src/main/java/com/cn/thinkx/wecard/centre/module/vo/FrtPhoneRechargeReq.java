package com.cn.thinkx.wecard.centre.module.vo;

public class FrtPhoneRechargeReq  implements java.io.Serializable {
	
		private static final long serialVersionUID = 8963226701814420994L;
		
		private String channelOrderNo;//渠道订单号
		private String accessToken; //
		private String userId;//操作人id
		private String phone;//充值手机号
		private String telephoneFace;//话费面额
		private String transAmt;//交易金额
		private String orderType; //订单类型
		private String reqChannel; //请求渠道号
		private String callBack;//回调地址
		private String attach;//附加数据
		private long timestamp; //时间戳
		private String sign;//签名
		public String getChannelOrderNo() {
			return channelOrderNo;
		}
		public void setChannelOrderNo(String channelOrderNo) {
			this.channelOrderNo = channelOrderNo;
		}
		public String getAccessToken() {
			return accessToken;
		}
		public void setAccessToken(String accessToken) {
			this.accessToken = accessToken;
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
}

