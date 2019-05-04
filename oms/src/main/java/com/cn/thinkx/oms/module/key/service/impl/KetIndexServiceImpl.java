package com.cn.thinkx.oms.module.key.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.key.mapper.KeyIndexMapper;
import com.cn.thinkx.oms.module.key.model.KeyIndex;
import com.cn.thinkx.oms.module.key.service.KeyIndexService;



@Service("keyIndexService")
public class KetIndexServiceImpl implements KeyIndexService {

	@Autowired
	@Qualifier("keyIndexMapper")
	private KeyIndexMapper keyIndexMapper;
	
	@Override
	public KeyIndex getKeyIndexByKeyNameAndVersionId(String keyName, String versionId) {
		return keyIndexMapper.getKeyIndexByKeyNameAndVersionId(keyName, versionId);
	}

}
