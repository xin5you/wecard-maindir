package com.cn.iboot.diy.api.trans.service;

import java.util.List;
import java.util.Map;

import com.cn.iboot.diy.api.trans.domain.TransLog;
import com.github.pagehelper.PageInfo;

public interface TransService {

	/**
	 * 当日交易流水查询
	 * @param map
	 * @return
	 */
	List<TransLog> getTransLogCur(Map<String,Object> map);
	
	PageInfo<TransLog> getTransLogPage(int startNum, int pageSize,Map<String,Object> map);
	
	List<TransLog> getTransLogCur(TransLog log);
	
	List<TransLog> getTransLogHis(TransLog log);
	
	PageInfo<TransLog> getTransLogPage(int startNum, int pageSize,TransLog log);
	
	/**
	 * 根据dmsRelatedKey查询交易流水
	 * @param dmsRelatedKey
	 * @return
	 */
	TransLog getTransLogByDmsRelatedKey(TransLog transLog);

}
