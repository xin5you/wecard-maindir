package com.cn.thinkx.wecard.customer.module.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.domain.trans.InterfaceTrans;
import com.cn.thinkx.common.wecard.domain.trans.TransLog;
import com.cn.thinkx.common.wecard.module.trans.mapper.TransLogDao;
import com.cn.thinkx.wecard.customer.module.merchant.service.TransLogService;

@Service("transLogService")
public class TransLogSerivceImpl implements TransLogService {

	
	@Autowired
	private TransLogDao transLogDao;
	
	 /**
	  * 
	  * @param relatedKey 交易流水表Id
	  * @return
	  */
	public TransLog getTransLogByRelatedKey(String relatedKey){
	
		 return transLogDao.getTransLogByRelatedKey(relatedKey);
	 }
	
	/***
	 * 交易记录分页查询
	 * @param searchEntity
	 * @param pagination
	 * @return
	 */
	public Pagination<TransLog> listEntityQuery(TransLog tl, Pagination<TransLog> pagination){
		Integer totalItemsCount = transLogDao.getTotalItemsCount(tl);
		List<TransLog> items = transLogDao.paginationEntity(tl, pagination);
		if (pagination != null) {
			pagination.setItems(items);
			pagination.setTotalItemsCount(totalItemsCount);
		}
		return pagination;
	}
	
	/**查询统计的金额*/
	public long querySumTransAmount(TransLog tl){
		 return transLogDao.querySumTransAmount(tl);
	}
	
	/***
	 * 消费 和 充值 按照日期统计  ByCAR is by consume and rechargeable
	 * @param tl
	 * @param pagination
	 * @return
	 */
	public Pagination<TransLog> queryTransByCARList(TransLog tl, Pagination<TransLog> pagination){
		Integer totalItemsCount = transLogDao.getTotalItemsByCARCount(tl);
		List<TransLog> items = transLogDao.queryTransByCARList(tl, pagination);
		pagination.setItems(items);
		pagination.setTotalItemsCount(totalItemsCount);
		return pagination;
	}
	
	
	/***
	 * 消费 和 充值 detail ByCAR is by consume and rechargeable
	 * @param tl
	 * @param pagination
	 * @return
	 */
	public Pagination<TransLog> querymchntTransDetailList(TransLog tl, Pagination<TransLog> pagination){
		Integer totalItemsCount = transLogDao.getTotalTransDetailByCARList(tl);
		List<TransLog> items = transLogDao.queryTransDetailByCARList(tl, pagination);
		pagination.setItems(items);
		pagination.setTotalItemsCount(totalItemsCount);
		return pagination;
	}
	
	/**
	 * 按照日期查询交易记录
	 * @param tl
	 * @return
	 */
	public List<TransLog> getTransLogListBySettleDate(TransLog transLog){
		return transLogDao.getTransLogListBySettleDate(transLog);
	}
	public TransLog queryTransCountBySettleDate(TransLog transLog){
		return transLogDao.queryTransCountBySettleDate(transLog);
	}
	
	/**
	 * 查找商户统计记录 历史记录
	 * @param transLog
	 * @return
	 */
	public TransLog queryHisTransCountByMchntId(TransLog transLog){
		return transLogDao.queryHisTransCountByMchntId(transLog);
	}

	@Override
	public InterfaceTrans getInterfaceTransByPrimaryKey(String primarykey) {
		return transLogDao.getInterfaceTransByPrimaryKey(primarykey);
	}

	@Override
	public InterfaceTrans getInterfaceTransByInterfaceTrans(InterfaceTrans interfaceTrans) {
		return transLogDao.getInterfaceTransByInterfaceTrans(interfaceTrans);
	}
}
