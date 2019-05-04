package com.cn.thinkx.pub.mapper;

import java.util.Map;

import com.cn.thinkx.pub.domain.DetailBizInfo;

public interface PublicDao {

	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	DetailBizInfo getDetailBizInfo(DetailBizInfo detail);
	
}