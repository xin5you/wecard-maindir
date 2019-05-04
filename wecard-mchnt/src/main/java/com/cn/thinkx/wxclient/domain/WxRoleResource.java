package com.cn.thinkx.wxclient.domain;

import com.cn.thinkx.core.domain.BaseDomain;

public class WxRoleResource extends BaseDomain {
	private String roleId;
	private String resourceId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
}
