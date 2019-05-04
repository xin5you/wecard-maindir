package com.cn.thinkx.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.merchant.domain.IntfaceTransLog;
import com.cn.thinkx.merchant.domain.TransLog;
import com.cn.thinkx.merchant.mapper.TransLogDao;
import com.cn.thinkx.merchant.service.TransLogService;

@Service("transLogService")
public class TransLogSerivceImpl implements TransLogService {

	@Autowired
	private TransLogDao transLogDao;

	@Override
	public TransLog getTransLogByLogId(String logId) {
		return transLogDao.getTransLogByLogId(logId);
	}

	@Override
	public IntfaceTransLog getIntfaceTransLogByLogId(String logId) {
		return transLogDao.getIntfaceTransLogByLogId(logId);
	}

	@Override
	public Pagination<TransLog> listEntityQuery(TransLog tl, Pagination<TransLog> pagination) {
		Integer totalItemsCount = transLogDao.getTotalItemsCount(tl);
		List<TransLog> items = transLogDao.paginationEntity(tl, pagination);
		if (pagination != null) {
			pagination.setItems(items);
			pagination.setTotalItemsCount(totalItemsCount);
		}
		return pagination;
	}

	@Override
	public long querySumTransAmount(TransLog tl) {
		return transLogDao.querySumTransAmount(tl);
	}

	@Override
	public Pagination<TransLog> queryTransByCARList(TransLog tl, Pagination<TransLog> pagination) {
		Integer totalItemsCount = transLogDao.getTotalItemsByCARCount(tl);
		List<TransLog> items = transLogDao.queryTransByCARList(tl, pagination);
		pagination.setItems(items);
		pagination.setTotalItemsCount(totalItemsCount);
		return pagination;
	}

	@Override
	public Pagination<TransLog> querymchntTransDetailList(TransLog tl, Pagination<TransLog> pagination) {
		Integer totalItemsCount = transLogDao.getTotalTransDetailByCARList(tl);
		List<TransLog> items = transLogDao.queryTransDetailByCARList(tl, pagination);
		pagination.setItems(items);
		pagination.setTotalItemsCount(totalItemsCount);
		return pagination;
	}

	@Override
	public List<TransLog> getTransLogListBySettleDate(TransLog transLog) {
		return transLogDao.getTransLogListBySettleDate(transLog);
	}

	@Override
	public TransLog queryTransCountBySettleDate(TransLog transLog) {
		return transLogDao.queryTransCountBySettleDate(transLog);
	}

	@Override
	public TransLog queryHisTransCountByMchntId(TransLog transLog) {
		return transLogDao.queryHisTransCountByMchntId(transLog);
	}
}
