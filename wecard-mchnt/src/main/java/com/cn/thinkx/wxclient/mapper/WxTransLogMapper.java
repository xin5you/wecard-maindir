package com.cn.thinkx.wxclient.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wxclient.domain.WxTransLog;

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

}
