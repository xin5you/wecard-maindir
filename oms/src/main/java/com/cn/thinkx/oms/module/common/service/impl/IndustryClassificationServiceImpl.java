package com.cn.thinkx.oms.module.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.common.mapper.IndustryClassificationMapper;
import com.cn.thinkx.oms.module.common.model.IndustryClassification;
import com.cn.thinkx.oms.module.common.service.IndustryClassificationService;



@Service("industryClassificationService")
public class IndustryClassificationServiceImpl implements IndustryClassificationService {

	@Autowired
	@Qualifier("industryClassificationMapper")
	private IndustryClassificationMapper industryClassificationMapper;

	@Override
	public List<IndustryClassification> findIndustryClassificationList(IndustryClassification entity) {
		
		return industryClassificationMapper.findIndustryClassificationList(entity);
	}

	
}
