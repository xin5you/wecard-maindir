package com.cn.thinkx.wxclient.domain;

import com.cn.thinkx.core.domain.BaseDomain;

/**
 * 角色表
 * @author zhuqy
 *
 */
public class WxRole extends BaseDomain {
	
	private String roleId;
	private String roleType; //000 客户会员 100 商户boss 200 商户财务 300 商户店长 400 商户收银员 500 商户服务员
	private String roleName ;
	private String dataStat;
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
}
