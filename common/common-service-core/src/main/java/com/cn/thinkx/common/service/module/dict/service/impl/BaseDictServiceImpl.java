package com.cn.thinkx.common.service.module.dict.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.service.module.dict.domain.BaseDict;
import com.cn.thinkx.common.service.module.dict.mapper.BaseDictMapper;
import com.cn.thinkx.common.service.module.dict.service.BaseDictService;

@Service("baseDictService")
public class BaseDictServiceImpl implements BaseDictService {
	
	@Autowired
	private BaseDictMapper baseDictMapper;

	@Override
	public List<BaseDict> getAllBaseDictByKey() {
		return baseDictMapper.getAllBaseDictByKey();
	}

	@Override
	public List<BaseDict> getBaseDictByKList() {
		return baseDictMapper.getBaseDictByKList();
	}

	@Override
	public int innsertBaseDict(BaseDict bd) {
		return baseDictMapper.innsertBaseDict(bd);
	}


}
