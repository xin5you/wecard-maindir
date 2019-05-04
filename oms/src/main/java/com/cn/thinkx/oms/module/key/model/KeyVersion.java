package com.cn.thinkx.oms.module.key.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 秘钥版本
 * @author HQW
 *
 */
public class KeyVersion extends BaseDomain {

	private String versionId;
	private String versionCode;
	private String versionType;
	private String dftStat;
	
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionType() {
		return versionType;
	}
	public void setVersionType(String versionType) {
		this.versionType = versionType;
	}
	public String getDftStat() {
		return dftStat;
	}
	public void setDftStat(String dftStat) {
		this.dftStat = dftStat;
	}	
}
