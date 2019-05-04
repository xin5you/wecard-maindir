package com.cn.thinkx.common.wecard.module.pub.mapper;

import java.util.Map;

import com.cn.thinkx.common.wecard.domain.detail.DetailBizInfo;

public interface PublicDao {
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	DetailBizInfo getDetailBizInfo(DetailBizInfo detail);
	
}