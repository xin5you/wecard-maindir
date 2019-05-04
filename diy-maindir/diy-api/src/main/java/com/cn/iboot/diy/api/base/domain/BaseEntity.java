package com.cn.iboot.diy.api.base.domain;

import java.io.Serializable;

public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String createUser;
	private String createTime;
	private String updateUser;
	private String updateTime;
	private String dataStat;
	private String remarks;
	private String lockVersion;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(String lockVersion) {
		this.lockVersion = lockVersion;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

}
