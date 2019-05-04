package com.cn.iboot.diy.api.base.service;

import java.io.Serializable;

public interface BaseService<T extends Serializable> {
	
	T selectByPrimaryKey(String primaryKey) throws Exception;

	int insert(T record) throws Exception;

	int insertSelective(T record) throws Exception;

	int updateByPrimaryKeySelective(T record) throws Exception;

	int updateByPrimaryKey(T record) throws Exception;

	int deleteByPrimaryKey(String primaryKey) throws Exception;

}
