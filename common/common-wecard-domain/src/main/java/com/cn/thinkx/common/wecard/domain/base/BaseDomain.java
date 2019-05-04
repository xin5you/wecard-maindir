package com.cn.thinkx.common.wecard.domain.base;

import java.io.Serializable;
import java.util.Date;

public class BaseDomain implements Serializable {

	private String remarks; // 备注
	private String createUser;
	private String updateUser;
	private Date createTime;
	private Date updateTime;
	private long lockVersion; // 乐观锁版本

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public long getLockVersion() {
		return lockVersion;
	}

	public void setLockVersion(long lockVersion) {
		this.lockVersion = lockVersion;
	}

}
