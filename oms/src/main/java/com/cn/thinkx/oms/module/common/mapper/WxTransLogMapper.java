package com.cn.thinkx.oms.module.common.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.common.model.WxTransLog;

@Repository("wxTransLogMapper")
public interface WxTransLogMapper {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	
	WxTransLog getWxTransLogById(String id);

	int insertWxTransLog(WxTransLog log);

	int updateWxTransLog(WxTransLog log);
	
	int updateWxCurTransLog(WxTransLog log);
	
	int updateWxHisTransLog(WxTransLog log);

}
