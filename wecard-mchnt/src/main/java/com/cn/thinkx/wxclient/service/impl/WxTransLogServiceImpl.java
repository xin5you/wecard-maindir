package com.cn.thinkx.wxclient.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.wxclient.domain.WxTransLog;
import com.cn.thinkx.wxclient.mapper.WxTransLogMapper;
import com.cn.thinkx.wxclient.service.WxTransLogService;

@Service("wxTransLogService")
public class WxTransLogServiceImpl implements WxTransLogService {
	Logger logger = LoggerFactory.getLogger(WxTransLogServiceImpl.class);
	
	@Autowired
	@Qualifier("wxTransLogMapper")
	private WxTransLogMapper wxTransLogMapper;

	@Override
	public String getPrimaryKey() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "");
		wxTransLogMapper.getPrimaryKey(paramMap);
		String id = (String) paramMap.get("id");
		return id;
	}

	@Override
	public WxTransLog getWxTransLogById(String id) {
		return wxTransLogMapper.getWxTransLogById(id);
	}

	@Override
	public int insertWxTransLog(WxTransLog log) {
		return wxTransLogMapper.insertWxTransLog(log);
	}

	@Override
	public int updateWxTransLog(String tableNum, String wxPrimaryKey, String respCode, String transAmt) {
		WxTransLog log = new WxTransLog();
		log.setTableNum(tableNum);
		log.setWxPrimaryKey(wxPrimaryKey);
		log.setTransSt(1);// 插入时为0，报文返回时更新为1
		log.setRespCode(respCode);
		log.setTransAmt(transAmt);
		return wxTransLogMapper.updateWxTransLog(log);
	}

	@Override
	public int updateWxTransLog(WxTransLog log, TxnResp txnResp) {
		if (txnResp != null) {
			log.setRespCode(txnResp.getCode());
			log.setTransAmt(txnResp.getTransAmt());
		}
		log.setTransSt(1);// 插入时为0，报文返回时更新为1
		return wxTransLogMapper.updateWxTransLog(log);
	}

}
