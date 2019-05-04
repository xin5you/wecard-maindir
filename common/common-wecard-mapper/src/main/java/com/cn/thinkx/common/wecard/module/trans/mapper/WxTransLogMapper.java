package com.cn.thinkx.common.wecard.module.trans.mapper;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;

@Repository("wxTransLogMapper")
public interface WxTransLogMapper {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);
	
	WxTransLog getWxTransLogById(String id);
	
	int countWxTransLogByOrderId(String orderId);

	int insertWxTransLog(WxTransLog log);

	int updateWxTransLog(WxTransLog log);
	
	WxTransLog getWxTransLogByWxTransLog(WxTransLog log);

}
