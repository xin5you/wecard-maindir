package com.cn.thinkx.common.base.core.mapper;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T extends Serializable> {

	T getById(String id);

	int insert(T t);

	int update(T t);

	int deleteById(String Id);

	List<T> getList(T t);
}