package com.cn.thinkx.oms.module.statement.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.statement.model.FinancingSummarizing;
import com.cn.thinkx.oms.module.statement.util.Condition;

public interface FinancingStatementMapper {
	
	/**
	 * 财务结算汇总列表
	 * @param condition
	 * @return
	 */
	List<FinancingSummarizing> getFinancingSummarizingList(Condition condition);
}
