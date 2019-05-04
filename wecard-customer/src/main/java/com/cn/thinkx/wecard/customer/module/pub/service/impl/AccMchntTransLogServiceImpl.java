package com.cn.thinkx.wecard.customer.module.pub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.accmchnt.AccMchntTransLog;
import com.cn.thinkx.common.wecard.module.accmchnt.mapper.AccMchntTransLogMapper;
import com.cn.thinkx.wecard.customer.module.pub.service.AccMchntTransLogService;

@Service("accMchntTransLogService")
public class AccMchntTransLogServiceImpl implements AccMchntTransLogService {
	
	@Autowired
	@Qualifier("accMchntTransLogMapper")
	private AccMchntTransLogMapper accMchntTransLogMapper;

	@Override
	public int insertAccMchntTransLog(AccMchntTransLog accmchnt) {
		return accMchntTransLogMapper.insertAccMchntTransLog(accmchnt);
	}

	@Override
	public int updateAccMchntTransLog(String wxPrimaryKey, String getCode) {
		AccMchntTransLog accmchnt = new AccMchntTransLog();
		accmchnt.setTransSt(1);
		accmchnt.setAccPrimaryKey(wxPrimaryKey);
		accmchnt.setRespCode(getCode);
		return accMchntTransLogMapper.updateAccMchntTransLog(accmchnt);
	}

}
