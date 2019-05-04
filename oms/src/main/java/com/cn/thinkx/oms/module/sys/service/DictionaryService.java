package com.cn.thinkx.oms.module.sys.service;

import java.util.List;

import com.cn.thinkx.oms.module.sys.model.Dictionary;

public interface DictionaryService {
	
	Dictionary getDictionaryById(String dictionaryId);
	
	Dictionary getDictionaryByCode(String code);
	
	int insertDictionary(Dictionary entity);
	
	int updateDictionary(Dictionary entity);
	
	int deleteDictionary(String dictionaryId);
	
	List<Dictionary> getDictionaryList(Dictionary entity);
	
	/**
	 * 查询字典数据
	 * @param code
	 * @return
	 */
	List<Dictionary> getDictionaryListByPCode(String code);
}
