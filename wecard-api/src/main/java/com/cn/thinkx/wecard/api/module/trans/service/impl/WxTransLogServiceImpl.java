package com.cn.thinkx.wecard.api.module.trans.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.trans.mapper.WxTransLogMapper;
import com.cn.thinkx.wecard.api.module.trans.model.WxTransLog;
import com.cn.thinkx.wecard.api.module.trans.service.WxTransLogService;

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
	public int updateWxTransLog(WxTransLog log, TxnResp txnResp) {
		if (txnResp != null) {
			log.setRespCode(txnResp.getCode());
			if (!StringUtil.isNullOrEmpty(txnResp.getTransAmt())) {
				log.setTransAmt(txnResp.getTransAmt());
			}
		}
		log.setTransSt(1);// 插入时为0，报文返回时更新为1
		return wxTransLogMapper.updateWxTransLog(log);
	}

}
