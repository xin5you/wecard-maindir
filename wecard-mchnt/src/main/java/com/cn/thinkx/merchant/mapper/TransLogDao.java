package com.cn.thinkx.merchant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.merchant.domain.IntfaceTransLog;
import com.cn.thinkx.merchant.domain.TransLog;

/**
 * 交易明细查询
 * @author zqy
 *
 */
public interface TransLogDao {
	
	/**
	 * 根据主键获取TRANS_LOG表信息
	 * 
	 * @param logId
	 * @return
	 */
	public TransLog getTransLogByLogId(String logId);

	/**
	 * 根据主键获取INTFACE_TRANS_LOG表信息
	 * 
	 * @param logId
	 * @return
	 */
	public IntfaceTransLog getIntfaceTransLogByLogId(String logId);
	
	/**
	 * 分页查询交易统计
	 * @param TransLog
	 * @return
	 */
	public Integer getTotalItemsCount(@Param("entity")TransLog transLog);

	/**
	 * 交易明细分页查询
	 * @param searchEntity
	 * @param pagination
	 * @return
	 */
	public List<TransLog> paginationEntity(@Param("entity")TransLog transLog ,@Param("page")Pagination<TransLog> pagination);
	
	
	/***金额统计**/
	public long querySumTransAmount(@Param("entity")TransLog transLog);
	
	
	/***
	 * 消费 和 充值 按照日期统计  ByCAR is by consume and rechargeable
	 * @param tl
	 * @param pagination
	 * @return
	 */
	public Integer getTotalItemsByCARCount(@Param("entity")TransLog transLog);
	/***
	 * 消费 和 充值 分页查询  ByCAR is by consume and rechargeable
	 * @param tl
	 * @param pagination
	 * @return
	 */
	public List<TransLog> queryTransByCARList(@Param("entity")TransLog transLog ,@Param("page")Pagination<TransLog> pagination);
	
	
	public Integer getTotalTransDetailByCARList(@Param("entity")TransLog transLog);
	/***
	 * 消费 和 充值 分页查询  ByCAR is by consume and rechargeable
	 * @param tl
	 * @param pagination
	 * @return
	 */
	public List<TransLog> queryTransDetailByCARList(@Param("entity")TransLog transLog ,@Param("page")Pagination<TransLog> pagination);

	
	/**
	 * 按照日期查询交易记录
	 * @param tl
	 * @return
	 */
	public List<TransLog> getTransLogListBySettleDate(@Param("entity")TransLog transLog);
	
	/**
	 * 查找商户统计记录 历史记录
	 * @param transLog
	 * @return
	 */
	public TransLog queryHisTransCountByMchntId(@Param("entity")TransLog transLog);
	/**
	 * 按照日期查询统计消费 和 充值金额
	 * @param tl
	 * @return
	 */
	public TransLog queryTransCountBySettleDate(@Param("entity")TransLog transLog);
}