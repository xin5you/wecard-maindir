package com.cn.thinkx.oms.module.key.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.key.model.KeyIndex;

@Repository("keyIndexMapper")
public interface KeyIndexMapper {
	
	public KeyIndex getKeyIndexByKeyNameAndVersionId(@Param("keyName")String keyName,@Param("versionId")String versionId);
}