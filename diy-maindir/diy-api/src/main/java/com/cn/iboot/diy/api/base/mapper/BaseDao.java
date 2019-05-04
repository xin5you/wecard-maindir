package com.cn.iboot.diy.api.base.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BaseDao<T extends Serializable> {
	
	T selectByPrimaryKey(@Param("primaryKey") String primaryKey);

	int insert(T record);

	int insertSelective(T record);

	int updateByPrimaryKeySelective(T record);

	int updateByPrimaryKey(T record);

	int deleteByPrimaryKey(String primaryKey);
	
	List<T> getList(T record);
}
