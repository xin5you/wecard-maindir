package com.cn.thinkx.oms.module.sys.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.sys.mapper.DictionaryMapper;
import com.cn.thinkx.oms.module.sys.model.Dictionary;
import com.cn.thinkx.oms.module.sys.service.DictionaryService;

/**
 * 数据字典
 * @author zqy
 *
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {
	
    @Autowired
    @Qualifier("dictionaryMapper")
    private DictionaryMapper dictionaryMapper;

	/**
	 *	
	 */
	public Dictionary getDictionaryById(String dictionaryId) {
		
		return dictionaryMapper.getDictionaryById(dictionaryId);
	}
	
	public Dictionary getDictionaryByCode(String code){
	
		return dictionaryMapper.getDictionaryByCode(code);
	}

	/**
	 * 新增
	 */
	public int insertDictionary(Dictionary entity) {
		
		return dictionaryMapper.insertDictionary(entity);
	}

	/**
	 * 修改
	 */
	public int updateDictionary(Dictionary entity) {
		
		return dictionaryMapper.updateDictionary(entity);
	}

	/**
	 * 删除
	 */
	public int deleteDictionary(String dictionaryId) {
		
		return dictionaryMapper.deleteDictionary(dictionaryId);
	}

	/**
	 * 查询列表
	 */
	public List<Dictionary> getDictionaryList(Dictionary entity) {
		
		return dictionaryMapper.getDictionaryList(entity);
	}
	
	/**
	 * 查询字典数据
	 * @param code
	 * @return
	 */
	public List<Dictionary> getDictionaryListByPCode(String code){
		return dictionaryMapper.getDictionaryListByPCode(code);
	}

    
}
