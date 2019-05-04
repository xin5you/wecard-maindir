package com.cn.thinkx.oms.module.diy.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class DiyRoleResource extends BaseDomain{

	private String roleId;
	private String resourceId;
	private String dataStat;
	
	public String getRoleId() {
		return roleId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
