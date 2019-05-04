package com.cn.thinkx.oms.module.key.service;

import com.cn.thinkx.oms.module.key.model.KeyVersion;

public interface KeyVersionService {

	/**
	 * 获取秘钥版本信息
	 * @param versionType
	 * @return
	 */
	public KeyVersion getDefaultKeyVersionByViesionType(String versionType);
}
