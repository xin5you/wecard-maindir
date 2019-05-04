package com.cn.thinkx.common.wecard.domain.cardkeys;

import com.cn.thinkx.pms.base.domain.BaseDomain;

/**
 * 提现黑名单信息
 * 
 * @author xiaomei
 *
 */
public class WithdrawBlacklistInf extends BaseDomain {
	
	/**
	 * 主键
	 */
	private String id;

	/**
	 * 用户人ID
	 */
	private String userId;
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 用户手机号
	 */
	private String userPhone;
	
	/**
	 * openID
	 */
	private String openId;
	
	/**
	 * 状态
	 */
	private String dataStat;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
