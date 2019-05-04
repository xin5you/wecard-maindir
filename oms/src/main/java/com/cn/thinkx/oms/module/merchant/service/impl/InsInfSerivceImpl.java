package com.cn.thinkx.oms.module.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.InsInfMapper;
import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.service.InsInfService;



@Service("insInfService")
public class InsInfSerivceImpl implements InsInfService {

	@Autowired
	@Qualifier("insInfMapper")
	private InsInfMapper insInfMapper;

	
	public int addInsInf(InsInf insInf) {
		
		return insInfMapper.insertInsInf(insInf);
	}

	
	public int updateInsInf(InsInf insInf) {
		
		return insInfMapper.updateInsInf(insInf);
	}

	
	public InsInf getInsInfById(String id) {
		return insInfMapper.getInsInfById(id);
	}


}
