package com.cn.thinkx.oms.module.key.service;

import com.cn.thinkx.oms.module.key.model.KeyIndex;

public interface KeyIndexService {

	/**
	 * 查找秘钥信息
	 * @param keyName
	 * @param versionId
	 * @return
	 */
	public KeyIndex getKeyIndexByKeyNameAndVersionId(String keyName,String versionId);
}
