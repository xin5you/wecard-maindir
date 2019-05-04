package com.cn.thinkx.oms.module.diy.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class DiyRole extends BaseDomain{

	private String id;
	private String roleName; // 角色名称
	private String description; // 描述
	private String seq; // 排序号
	private String dataStat; // 是否默认
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
	
	
}
