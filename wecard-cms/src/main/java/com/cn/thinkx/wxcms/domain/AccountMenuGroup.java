package com.cn.thinkx.wxcms.domain;

import com.cn.thinkx.core.domain.BaseEntity;

public class AccountMenuGroup extends BaseEntity {

	private String name;
	private Integer enable;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

}
