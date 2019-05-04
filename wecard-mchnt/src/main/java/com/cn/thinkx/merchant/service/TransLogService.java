package com.cn.thinkx.merchant.service;

import java.util.List;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.merchant.domain.IntfaceTransLog;
import com.cn.thinkx.merchant.domain.TransLog;

/**
 * 交易流水查询
 * 
 * @author zqy
 *
 */
public interface TransLogService {

	/**
	 * 根据主键获取TRANS_LOG表信息
	 * 
	 * @param logId
	 * @return
	 */
	TransLog getTransLogByLogId(String logId);

	/**
	 * 根据主键获取INTFACE_TRANS_LOG表信息
	 * 
	 * @param logId
	 * @return
	 */
	IntfaceTransLog getIntfaceTransLogByLogId(String logId);

	/***
	 * 交易记录分页查询
	 * 
	 * @param searchEntity
	 * @param pagination
	 * @return
	 */
	Pagination<TransLog> listEntityQuery(TransLog tl, Pagination<TransLog> pagination);

	/** 查询统计的金额 */
	long querySumTransAmount(TransLog tl);

	/***
	 * 消费 和 充值 按照日期统计 ByCAR is by consume and rechargeable
	 * 
	 * @param tl
	 * @param pagination
	 * @return
	 */
	Pagination<TransLog> queryTransByCARList(TransLog tl, Pagination<TransLog> pagination);

	Pagination<TransLog> querymchntTransDetailList(TransLog tl, Pagination<TransLog> pagination);

	/**
	 * 按照日期查询交易记录
	 * 
	 * @param tl
	 * @return
	 */
	List<TransLog> getTransLogListBySettleDate(TransLog tl);

	TransLog queryTransCountBySettleDate(TransLog tl);

	/**
	 * 查找商户统计记录 历史记录
	 * 
	 * @param transLog
	 * @return
	 */
	TransLog queryHisTransCountByMchntId(TransLog transLog);
}
