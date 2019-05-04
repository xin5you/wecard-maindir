package com.cn.thinkx.oms.module.customer.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class UserInf extends BaseDomain {
	
	private String userId; //用户信息_id
	private String userName; //用户名  微信渠道 openid
	private String userType; //用户类型
	private String userPasswd; //密码
	private String productCode; //产品号
	private String dataStat;
	
	/**
	 * 外部渠道ID
	 */
	private String externalId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserPasswd() {
		return userPasswd;
	}
	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
}
