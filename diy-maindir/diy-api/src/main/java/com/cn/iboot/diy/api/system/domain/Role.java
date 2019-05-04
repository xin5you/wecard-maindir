package com.cn.iboot.diy.api.system.domain;
import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class Role extends BaseEntity{

	private static final long serialVersionUID = -4688626183004316392L;
	
	private String id;

	private String roleName;

	private String description;

	private Long seq;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}
	
}
