package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseReq;

public class CusAccOpeningRequest extends BaseReq {

	private static final long serialVersionUID = 1L;
	/**
	 * 渠道号
	 */
	private String channel;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 手机号
	 */
	private String mobile;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
