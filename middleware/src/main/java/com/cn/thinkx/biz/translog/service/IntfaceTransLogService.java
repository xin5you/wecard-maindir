package com.cn.thinkx.biz.translog.service;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.biz.translog.model.AccountLog;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.biz.translog.model.TransLog;

public interface IntfaceTransLogService {

	/**
	 * 获取主键
	 * 
	 * @return primaryKey
	 */
	String getPrimaryKey();

	int insertIntfaceTransLog(IntfaceTransLog log);

	int updateIntfaceTransLog(IntfaceTransLog log);
	
	int updateIntfaceHisTransLog(IntfaceTransLog log);

	IntfaceTransLog getIntfaceTransLogBylogId(String tableNum, String logId);
	
	IntfaceTransLog getIntfaceTransLogHisBylogId(String logId);
	
	IntfaceTransLog getIntfaceTransLogByRelatedKey(String tableNum, String relatedKey);
	
	IntfaceTransLog getIntfaceTransLog(String tableNum, String relatedKey, String mchntCode);

	/**
	 * 根据wecard平台流水号 更新接口平台日志表的 respCode
	 * 
	 * @param respCode
	 * @param tableNum
	 * @param relatedKey
	 */
	int updateIntfaceTransLogRespCode(String respCode, String tableNum, String relatedKey);
	
	/**
	 * 根据原交易外部流水号 更新接口平台日志表的 respCode
	 * 
	 * @param tableNum
	 * @param oriDmsRelatedKey
	 */
	int updateOriITFLogRespCode(String tableNum, String oriDmsRelatedKey);

	/**
	 * 
	 * @param tableNum
	 *            日切状态
	 * @param relatedKey
	 *            接口平台流水表Id
	 * @return
	 */
	TransLog getTransLogByRelatedKey(String tableNum, String relatedKey);

	/**
	 * 通过接口平台Id 更新交易日志表的 respCode
	 * 
	 * @param respCode
	 * @param tableNum
	 * @param relatedKey
	 */
	int updateTransLogRespCode(String respCode, String tableNum, String relatedKey);
	
	/**
	 * 通过原交易外部流水 更新交易日志表的交易类型
	 * 
	 * @param tableNum
	 * @param relatedKey
	 */
	int updateOriTransLog(String tableNum, String oriDmsRelatedKey);

	/**
	 * @param tableNum
	 *            日切状态
	 * @param relatedKey
	 *            交易流水号
	 * @return
	 */
	AccountLog getAccountLogByRelatedKey(String tableNum, String relatedKey);
	
	/**
	 * 根据orgDmsRelatedKey 外部原交易流水号查询历史流水
	 * @param orgDmsRelatedKey 外部原交易流水号
	 * @return
	 */
	IntfaceTransLog getIntfaceTransLogHisByOrgDmsRelatedKey(@Param("orgDmsRelatedKey") String orgDmsRelatedKey);
	
	/**
	 * 根据orgDmsRelatedKey 外部原交易流水号查询当日流水
	 * 
	 * @param tableNum
	 * @param orgDmsRelatedKey
	 * @return
	 */
	IntfaceTransLog getIntfaceTransLogByOrgDmsRelatedKey(@Param("tableNum") String tableNum, @Param("orgDmsRelatedKey") String orgDmsRelatedKey);
	
	/**
	 * 根据dmsRelatedKey 外部交易流水号查询当日流水
	 * 
	 * @param tableNum
	 * @param dmsRelatedKey
	 * @return
	 */
	IntfaceTransLog getIntfaceTransLogByDmsRelatedKey(@Param("tableNum") String tableNum, @Param("dmsRelatedKey") String dmsRelatedKey);
	
	/**
	 * 根据dmsRelatedKey 外部交易流水号查询历史流水
	 * 
	 * @param dmsRelatedKey
	 * @return
	 */
	IntfaceTransLog getIntfaceTransLogHisByDmsRelatedKey(@Param("dmsRelatedKey") String dmsRelatedKey);
}
