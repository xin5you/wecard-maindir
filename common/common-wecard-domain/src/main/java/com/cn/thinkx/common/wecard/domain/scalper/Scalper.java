package com.cn.thinkx.common.wecard.domain.scalper;

import com.cn.thinkx.pms.base.domain.BaseDomain;

public class Scalper extends BaseDomain {

	private String userId; // 用户id
	private String userName; // 用户名 微信渠道 openid
	private String userType; // 用户类型
	
	public Scalper(String userId, String userName, String userType) {
		this.userId = userId;
		this.userName = userName;
		this.userType = userType;
	}

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

	@Override
	public String toString() {
		return "Scalper [userId=" + userId + ", userName=" + userName + ", userType=" + userType + "]";
	}

}
