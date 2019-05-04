package com.cn.thinkx.oms.module.merchant.service;

import java.util.List;

import com.cn.thinkx.oms.module.merchant.model.TransInf;
import com.cn.thinkx.oms.module.merchant.model.TransLog;
import com.cn.thinkx.oms.module.merchant.model.TransLogUpload;
import com.github.pagehelper.PageInfo;

public interface TransLogService {
	
	/**
	 * 获取交易记录列表
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	PageInfo<TransLog> getTransLogPage(int startNum, int pageSize, TransInf entity);
	
	/**
	 * 获取交易记录集合
	 * 
	 * @param entity
	 * @return
	 */
	List<TransLogUpload> getTransLogList(TransInf entity);

}
