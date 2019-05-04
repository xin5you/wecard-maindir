package com.cn.iboot.diy.api.system.domain;

import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class User extends BaseEntity{

	private static final long serialVersionUID = -4688626183004316392L;

	private String id;

	private String loginName;

	private String password;

	private String userName;

	private String phoneNo;

	private String isdefault;
	
	private String roleId;

	private String roleName;

	private String shopCode;

	private String shopName;
	
	private String mchntCode;
	
	private String mchntName;
	
	private String seq;
	
	private String sessionId;
	
	private Integer countNumber;

	public Integer getCountNumber() {
		return countNumber;
	}

	public void setCountNumber(Integer countNumber) {
		this.countNumber = countNumber;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", loginName=" + loginName + ", password=" + password + ", userName=" + userName
				+ ", phoneNo=" + phoneNo + ", isdefault=" + isdefault + ", roleId=" + roleId + ", roleName=" + roleName
				+ ", shopCode=" + shopCode + ", shopName=" + shopName + ", mchntCode=" + mchntCode + ", mchntName="
				+ mchntName + ", seq=" + seq + "]";
	}
	

}