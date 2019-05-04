package com.cn.thinkx.wxclient.domain;

import com.cn.thinkx.core.domain.BaseDomain;

public class WxFansRole extends BaseDomain {

	private String fansId;
	private String roleId;
	private String dataStat;

	public String getFansId() {
		return fansId;
	}

	public void setFansId(String fansId) {
		this.fansId = fansId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
