package com.cn.thinkx.oms.module.common.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.common.model.IndustryClassification;

@Repository("industryClassificationMapper")
public interface IndustryClassificationMapper {

	/**
	 * 查找行业类型
	 * @param entity
	 * @return
	 */
	List<IndustryClassification> findIndustryClassificationList(IndustryClassification entity);
}