package com.cn.thinkx.oms.module.sys.model;

import java.util.Date;


public class Resource implements java.io.Serializable {

	private String id;
	private Date createdatetime; // 创建时间
	private String name; // 名称
	private String url; // 菜单路径
	private String description; // 描述
	private String icon; // 图标
	private Integer seq; // 排序号
	private String resourcetype; // 资源类型, 0菜单 1功能
	private String state; // 状态 0启用 1停用
	private String pid; // 父级
	private String key;
	
	private boolean checked;
	
	public String getId() {
		return id;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public String getDescription() {
		return description;
	}
	public String getIcon() {
		return icon;
	}
	public Integer getSeq() {
		return seq;
	}
	public String getResourcetype() {
		return resourcetype;
	}
	public String getState() {
		return state;
	}
	public String getPid() {
		return pid;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}