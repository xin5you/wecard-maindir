package com.cn.thinkx.oms.module.sys.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.sys.model.Dictionary;

@Repository("dictionaryMapper")
public interface DictionaryMapper{
	
	Dictionary getDictionaryById(String dictionaryId);
	
	Dictionary getDictionaryByCode(String code);
	
	int insertDictionary(Dictionary entity);

	int updateDictionary(Dictionary entity);
	
	int deleteDictionary(String dictionaryId);

	List<Dictionary> getDictionaryList(Dictionary entity);
	
	List<Dictionary> getDictionaryListByPCode(String code);
	
}
