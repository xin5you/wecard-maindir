package com.cn.thinkx.common.redis.vo;

/**
 * 用户二维码属性vo类
 * @author kpplg
 *
 */
public class CustomerQrCodeVO {
	private String openid;
	private String payType;
	private long currTime;
	
	private String hkbUserID;
	private String jfUserID;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public long getCurrTime() {
		return currTime;
	}
	public void setCurrTime(long currTime) {
		this.currTime = currTime;
	}
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
	

}
