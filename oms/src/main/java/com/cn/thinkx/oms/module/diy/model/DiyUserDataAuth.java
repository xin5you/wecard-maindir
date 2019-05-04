package com.cn.thinkx.oms.module.diy.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class DiyUserDataAuth extends BaseDomain{

	private String authId;
	private String userId;
	private String dataStat;
	
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
	
}
