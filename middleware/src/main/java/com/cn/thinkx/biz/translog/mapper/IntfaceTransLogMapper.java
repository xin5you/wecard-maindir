package com.cn.thinkx.biz.translog.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.biz.translog.model.AccountLog;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.biz.translog.model.TransLog;

@Repository("intfaceTransLogMapper")
public interface IntfaceTransLogMapper {

	/**
	 * 获取主键
	 * 
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);

	int insertIntfaceTransLog(IntfaceTransLog log);

	int updateIntfaceTransLog(IntfaceTransLog log);
	
	int updateIntfaceHisTransLog(IntfaceTransLog log);

	IntfaceTransLog getIntfaceTransLogByLogId(@Param("tableNum") String tableNum, @Param("logId") String logId);
	
	IntfaceTransLog getIntfaceTransLogHisBylogId(@Param("logId") String logId);
	
	IntfaceTransLog getIntfaceTransLogByRelatedKey(@Param("tableNum") String tableNum, 
			@Param("relatedKey") String relatedKey);

	IntfaceTransLog getIntfaceTransLog(@Param("tableNum") String tableNum, @Param("relatedKey") String relatedKey,
			@Param("mchntCode") String mchntCode);

	int updateIntfaceTransLogRespCode(@Param("respCode") String respCode, @Param("tableNum") String tableNum,
			@Param("relatedKey") String relatedKey);

	TransLog getTransLogByRelatedKey(@Param("tableNum") String tableNum, @Param("relatedKey") String relatedKey);

	int updateTransLogRespCode(@Param("respCode") String respCode, @Param("tableNum") String tableNum,
			@Param("relatedKey") String relatedKey);
	
	int updateOriTransLog(@Param("tableNum") String tableNum, @Param("oriDmsRelatedKey") String oriDmsRelatedKey);
	
	int updateOriITFLogRespCode(@Param("tableNum") String tableNum, @Param("oriDmsRelatedKey") String oriDmsRelatedKey);

	AccountLog getAccountLogByRelatedKey(@Param("tableNum") String tableNum, @Param("relatedKey") String relatedKey);
	
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
