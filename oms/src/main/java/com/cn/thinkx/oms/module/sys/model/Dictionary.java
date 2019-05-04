package com.cn.thinkx.oms.module.sys.model;


public class Dictionary  implements java.io.Serializable{
	
	private String id;
	private String code;
	private String isdefault;
	private String seq;
	private String state; // 状态 0启用 1停用
	private String name; // 是否默认
	private String type;
	private String value;
	private String pid;
	
	public String getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public String getSeq() {
		return seq;
	}
	public String getState() {
		return state;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getValue() {
		return value;
	}
	public String getPid() {
		return pid;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
