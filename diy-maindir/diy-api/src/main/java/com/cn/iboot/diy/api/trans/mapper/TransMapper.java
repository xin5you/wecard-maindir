package com.cn.iboot.diy.api.trans.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.trans.domain.TransLog;

@Mapper
public interface TransMapper {

	/**
	 * 当日交易流水查询
	 * @param map
	 * @return
	 */
	List<TransLog> getTransLogCur(Map<String,Object> map);
	
	
	List<TransLog> getTransLogCur(TransLog log);
	
	List<TransLog> getTransLogHis(TransLog log);
	
	/**
	 * 根据dmsRelatedKey查询交易流水
	 * @param dmsRelatedKey
	 * @return
	 */
	TransLog getTransLogByDmsRelatedKey(TransLog transLog);

}
