package com.cn.thinkx.oms.module.sys.model;

public class ZTreeResource implements java.io.Serializable {

	private String id;
	private String name; // 名称
	private String pId; // 父级
	private boolean checked;
	private String icon;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getpId() {
		return pId;
	}
	public boolean isChecked() {
		return checked;
	}
	public String getIcon() {
		return icon;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}