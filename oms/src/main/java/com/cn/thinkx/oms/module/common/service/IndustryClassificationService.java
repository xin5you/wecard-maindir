package com.cn.thinkx.oms.module.common.service;

import java.util.List;

import com.cn.thinkx.oms.module.common.model.IndustryClassification;

/**
 * 行业类型service
 * @author zqy
 *
 */
public interface IndustryClassificationService {


	/**
	 * 查找行业类型
	 * @param entity
	 * @return
	 */
	public List<IndustryClassification> findIndustryClassificationList(IndustryClassification entity);
	

}
