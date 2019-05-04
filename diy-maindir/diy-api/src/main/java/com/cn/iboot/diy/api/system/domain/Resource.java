package com.cn.iboot.diy.api.system.domain;
import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class Resource extends BaseEntity{

	private static final long serialVersionUID = -4688626183004316392L;
	
	private String id;

	private String description;

	private String icon;

	private String resourceName;

	private Long resourceType;

	private String key;

	private Long seq;

	private String url;

	private String pid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Long getResourceType() {
		return resourceType;
	}

	public void setResourceType(Long resourceType) {
		this.resourceType = resourceType;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
}
