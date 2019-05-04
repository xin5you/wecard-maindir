package com.cn.thinkx.oms.module.sys.model;

public class Role implements java.io.Serializable {

	private Integer id;
	private String name; // 角色名称
	private Integer seq; // 排序号
	private String isdefault; // 是否默认
	private String description; // 备注
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getSeq() {
		return seq;
	}
	public String getIsdefault() {
		return isdefault;
	}
	public String getDescription() {
		return description;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
