package com.cn.thinkx.oms.module.key.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 秘钥索引
 * @author HQW
 *
 */
public class KeyIndex extends BaseDomain {

	private String keyId;
	private String versionId;
	private String keyIndex;
	private String keyName;
	
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getKeyIndex() {
		return keyIndex;
	}
	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

}
