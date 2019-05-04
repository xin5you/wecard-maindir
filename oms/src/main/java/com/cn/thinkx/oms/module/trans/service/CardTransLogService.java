package com.cn.thinkx.oms.module.trans.service;

import java.util.List;

import com.cn.thinkx.oms.module.trans.model.CardTransInf;
import com.cn.thinkx.oms.module.trans.model.CardTransLog;
import com.cn.thinkx.oms.module.trans.model.CardTransLogUpload;
import com.github.pagehelper.PageInfo;

public interface CardTransLogService {

	/**
	 * 获取交易记录列表
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	PageInfo<CardTransLog> getTransLogPage(int startNum, int pageSize, CardTransInf entity);

	/**
	 * 获取交易记录集合
	 * 
	 * @param entity
	 * @return
	 */
	List<CardTransLogUpload> getTransLogList(CardTransInf entity);

}
