package com.cn.thinkx.oms.module.key.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.key.mapper.KeyVersionMapper;
import com.cn.thinkx.oms.module.key.model.KeyVersion;
import com.cn.thinkx.oms.module.key.service.KeyVersionService;



@Service("keyVersionService")
public class KetVersionServiceImpl implements KeyVersionService {

	@Autowired
	@Qualifier("keyVersionMapper")
	private KeyVersionMapper keyVersionMapper;

	@Override
	public KeyVersion getDefaultKeyVersionByViesionType(String versionType) {
		return keyVersionMapper.getDefaultKeyVersionByViesionType(versionType);
	}
	
}
