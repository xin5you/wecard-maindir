package com.cn.thinkx.oms.module.sys.model;

import java.util.Date;

public class Organization implements java.io.Serializable {

	private String id;
	private String name;
	private String address;
	private String code;
	private String icon;
	private String seq;
	private String pid;
	private Date createdatetime;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getCode() {
		return code;
	}
	public String getIcon() {
		return icon;
	}
	public String getSeq() {
		return seq;
	}
	public String getPid() {
		return pid;
	}
	public Date getCreatedatetime() {
		return createdatetime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setCreatedatetime(Date createdatetime) {
		this.createdatetime = createdatetime;
	}
}
