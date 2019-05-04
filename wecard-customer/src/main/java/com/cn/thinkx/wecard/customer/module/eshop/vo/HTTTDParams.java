package com.cn.thinkx.wecard.customer.module.eshop.vo;

/**
 * 海豚通通兑出入参数
 * 
 * @author xiaomei
 *
 */
public class HTTTDParams {
	/** 请求参数*/
	private String userId;
	private String nickName;
	private String institutionNo;
	private String mobile;
	private String appId;
	
	/** 接收参数*/
	private String sessionId;

	/** 公用参数*/
	private String randomKey;
	private String sign;
	private String timestamp;
	
	private String data;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getInstitutionNo() {
		return institutionNo;
	}
	public void setInstitutionNo(String institutionNo) {
		this.institutionNo = institutionNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getRandomKey() {
		return randomKey;
	}
	public void setRandomKey(String randomKey) {
		this.randomKey = randomKey;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
