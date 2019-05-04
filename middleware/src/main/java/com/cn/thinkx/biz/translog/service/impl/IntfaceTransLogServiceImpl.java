package com.cn.thinkx.biz.translog.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.biz.translog.mapper.IntfaceTransLogMapper;
import com.cn.thinkx.biz.translog.model.AccountLog;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.biz.translog.model.TransLog;
import com.cn.thinkx.biz.translog.service.IntfaceTransLogService;

@Service("intfaceTransLogService")
public class IntfaceTransLogServiceImpl implements IntfaceTransLogService {

	@Autowired
	@Qualifier("intfaceTransLogMapper")
	private IntfaceTransLogMapper intfaceTransLogMapper;

	@Override
	public String getPrimaryKey() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "");
		intfaceTransLogMapper.getPrimaryKey(paramMap);
		String id = (String) paramMap.get("id");
		return id;
	}

	@Override
	public int insertIntfaceTransLog(IntfaceTransLog log) {
		return intfaceTransLogMapper.insertIntfaceTransLog(log);
	}

	@Override
	public int updateIntfaceTransLog(IntfaceTransLog log) {
		return intfaceTransLogMapper.updateIntfaceTransLog(log);
	}
	
	@Override
	public int updateIntfaceHisTransLog(IntfaceTransLog log) {
		return intfaceTransLogMapper.updateIntfaceHisTransLog(log);
	}

	@Override
	public IntfaceTransLog getIntfaceTransLogBylogId(String tableNum, String logId) {
		return intfaceTransLogMapper.getIntfaceTransLogByLogId(tableNum, logId);
	}
	
	@Override
	public IntfaceTransLog getIntfaceTransLogHisBylogId(String logId) {
		return intfaceTransLogMapper.getIntfaceTransLogHisBylogId(logId);
	}
	
	@Override
	public IntfaceTransLog getIntfaceTransLogByRelatedKey(String tableNum, String logId) {
		return intfaceTransLogMapper.getIntfaceTransLogByRelatedKey(tableNum, logId);
	}

	@Override
	public IntfaceTransLog getIntfaceTransLog(String tableNum, String relatedKey, String mchntCode) {
		return intfaceTransLogMapper.getIntfaceTransLog(tableNum, relatedKey, mchntCode);
	}

	/**
	 * 更新接口流水日志表返回状态
	 * 
	 * @param respCode
	 *            账户表 respCode
	 * @param tableNum
	 *            日切状态
	 * @param relatedKey
	 *            接口平台流水表Id
	 * @return
	 */
	public int updateIntfaceTransLogRespCode(String respCode, String tableNum, String relatedKey) {
		return intfaceTransLogMapper.updateIntfaceTransLogRespCode(respCode, tableNum, relatedKey);
	}

	/**
	 * 交易流水
	 * 
	 * @param tableNum
	 *            日切状态
	 * @param relatedKey
	 *            接口平台流水表Id
	 * @return
	 */
	public TransLog getTransLogByRelatedKey(String tableNum, String relatedKey) {
		return intfaceTransLogMapper.getTransLogByRelatedKey(tableNum, relatedKey);
	}

	/**
	 * 更新交易流水表返回状态
	 * 
	 * @param respCode
	 *            账户表 respCode
	 * @param tableNum
	 *            日切状态
	 * @param relatedKey
	 *            接口平台流水表Id
	 */
	public int updateTransLogRespCode(String respCode, String tableNum, String relatedKey) {
		return intfaceTransLogMapper.updateTransLogRespCode(respCode, tableNum, relatedKey);
	}

	@Override
	public int updateOriTransLog(String tableNum, String oriDmsRelatedKey) {
		return intfaceTransLogMapper.updateOriTransLog(tableNum, oriDmsRelatedKey);
	}

	@Override
	public int updateOriITFLogRespCode(String tableNum, String oriDmsRelatedKey) {
		return intfaceTransLogMapper.updateOriITFLogRespCode(tableNum, oriDmsRelatedKey);
	}

	/**
	 * 账户交易流水
	 * 
	 * @param tableNum
	 *            日切状态
	 * @param relatedKey
	 *            交易流水号
	 * @return
	 */
	public AccountLog getAccountLogByRelatedKey(String tableNum, String relatedKey) {
		return intfaceTransLogMapper.getAccountLogByRelatedKey(tableNum, relatedKey);
	}

	@Override
	public IntfaceTransLog getIntfaceTransLogHisByOrgDmsRelatedKey(String orgDmsRelatedKey) {
		return intfaceTransLogMapper.getIntfaceTransLogHisByOrgDmsRelatedKey(orgDmsRelatedKey);
	}

	@Override
	public IntfaceTransLog getIntfaceTransLogByOrgDmsRelatedKey(String tableNum, String orgDmsRelatedKey) {
		return intfaceTransLogMapper.getIntfaceTransLogByOrgDmsRelatedKey(tableNum, orgDmsRelatedKey);
	}

	@Override
	public IntfaceTransLog getIntfaceTransLogByDmsRelatedKey(String tableNum, String dmsRelatedKey) {
		return intfaceTransLogMapper.getIntfaceTransLogByDmsRelatedKey(tableNum, dmsRelatedKey);
	}

	@Override
	public IntfaceTransLog getIntfaceTransLogHisByDmsRelatedKey(String dmsRelatedKey) {
		return intfaceTransLogMapper.getIntfaceTransLogHisByDmsRelatedKey(dmsRelatedKey);
	}

}
