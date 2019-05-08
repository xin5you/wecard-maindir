package com.cn.thinkx.wecard.customer.module.jfpay.vo;

/**
 * 嘉福调用薪无忧二维码页面 入参参数
 * 
 * @author xiaomei
 *
 */
public class JFQrCodeReq {

	private String hkbUserID;	//薪无忧userID
	private String jfUserID;	//嘉福UserID
	private String timestamp;	//时间戳
	private String sign;		//签名
	
	public String getHkbUserID() {
		return hkbUserID;
	}
	public void setHkbUserID(String hkbUserID) {
		this.hkbUserID = hkbUserID;
	}
	public String getJfUserID() {
		return jfUserID;
	}
	public void setJfUserID(String jfUserID) {
		this.jfUserID = jfUserID;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return "JFQrCodeReq [hkbUserID=" + hkbUserID + ", jfUserID=" + jfUserID + ", timestamp=" + timestamp + ", sign="
				+ sign + "]";
	}
	
}
