package com.cn.thinkx.oms.module.statement.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.oms.module.statement.model.CustomerInfo;
import com.cn.thinkx.oms.module.statement.model.CustomerInfoDetail;
import com.cn.thinkx.oms.module.statement.model.MarketingDetail;
import com.cn.thinkx.oms.module.statement.model.OperationDetail;
import com.cn.thinkx.oms.module.statement.model.OperationSummarizing;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.github.pagehelper.PageInfo;

public interface OperationStatementService {
	
	/**
	 * 运营汇总列表
	 * @param condition
	 * @return
	 */
	OperationSummarizing getOperationSummarizing(Condition condition);
	
	/**
	 * 运营明细列表
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<OperationDetail> getOperationDetailList(Condition condition);
	
	/**
	 * 客户数据列表
	 * @param condition
	 * @return
	 */
	CustomerInfo getCustomerInfo(Condition condition);
	
	/**
	 * 客户数据明细列表
	 * @param condition
	 * @return
	 */
	CustomerInfoDetail getCustomerInfoDetail(Condition condition);
	
	/**
	 * 营销数据明细列表
	 * @param condition
	 * @return
	 */
	List<MarketingDetail> getMarketingDetailList(Condition condition);
	
	PageInfo<OperationDetail> getOperationDetailPage(int startNum, int pageSize, Condition condition);
	PageInfo<MarketingDetail> getMarketingDetailPage(int startNum, int pageSize, Condition condition);
	
	OperationDetail getOperationDetailAmount(Condition condition);
}
