package com.cn.thinkx.oms.module.statement.service;

import java.util.List;

import com.cn.thinkx.oms.module.statement.model.FinancingSummarizing;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.github.pagehelper.PageInfo;

public interface FinancingStatementService {
	
	/**
	 * 财务结算汇总列表
	 * @param condition
	 * @return
	 */
	List<FinancingSummarizing> getFinancingSummarizingList(Condition condition);
	
	PageInfo<FinancingSummarizing> getFinancingSummarizingPage(int startNum, int pageSize, Condition condition);
}
