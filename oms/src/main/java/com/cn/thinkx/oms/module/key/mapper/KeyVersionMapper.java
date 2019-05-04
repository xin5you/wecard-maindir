package com.cn.thinkx.oms.module.key.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.key.model.KeyVersion;

@Repository("keyVersionMapper")
public interface KeyVersionMapper {
	
	public KeyVersion getDefaultKeyVersionByViesionType(String versionType);
}