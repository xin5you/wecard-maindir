package com.cn.thinkx.wecard.customer.module.merchant.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.domain.trans.InterfaceTrans;
import com.cn.thinkx.common.wecard.domain.trans.TransLog;

/**
 * 交易流水查询
 * @author zqy
 *
 */
public interface TransLogService {
	
	 /**
	  *
	  * @param relatedKey 交易流水表Id
	  * @return
	  */
	 TransLog getTransLogByRelatedKey(String relatedKey);

	/***
	 * 交易记录分页查询
	 * @param searchEntity
	 * @param pagination
	 * @return
	 */
	public Pagination<TransLog> listEntityQuery(TransLog tl, Pagination<TransLog> pagination);
	
	/**查询统计的金额*/
	public long querySumTransAmount(TransLog tl);
	
	/***
	 * 消费 和 充值 按照日期统计  ByCAR is by consume and rechargeable
	 * @param tl
	 * @param pagination
	 * @return
	 */
	public Pagination<TransLog> queryTransByCARList(TransLog tl, Pagination<TransLog> pagination);
	
	public Pagination<TransLog> querymchntTransDetailList(TransLog tl, Pagination<TransLog> pagination);
	
	/**
	 * 按照日期查询交易记录
	 * @param tl
	 * @return
	 */
	public List<TransLog> getTransLogListBySettleDate(TransLog tl);
	public TransLog queryTransCountBySettleDate(TransLog tl);
	
	/**
	 * 查找商户统计记录 历史记录
	 * @param transLog
	 * @return
	 */
	public TransLog queryHisTransCountByMchntId(TransLog transLog);
	
	InterfaceTrans getInterfaceTransByPrimaryKey(String primarykey);
	
	InterfaceTrans getInterfaceTransByInterfaceTrans(InterfaceTrans interfaceTrans);
}
