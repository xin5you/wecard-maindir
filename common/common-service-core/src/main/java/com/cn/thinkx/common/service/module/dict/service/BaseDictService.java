package com.cn.thinkx.common.service.module.dict.service;

import java.util.List;

import com.cn.thinkx.common.service.module.dict.domain.BaseDict;

public interface BaseDictService {
	
	/**
	 * 查询所有的字典数据信息
	 * @return
	 */
	List<BaseDict> getAllBaseDictByKey();
	
	/**
	 *  查询字典数据信息
	 * @param dictCode
	 * @return
	 */
	List<BaseDict> getBaseDictByKList();
	
	/**
	 * 添加字典数据信息
	 * @param bd
	 * @return
	 */
	int innsertBaseDict(BaseDict bd);
}
