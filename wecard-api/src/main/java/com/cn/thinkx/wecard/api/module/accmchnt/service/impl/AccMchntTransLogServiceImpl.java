package com.cn.thinkx.wecard.api.module.accmchnt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.accmchnt.mapper.AccMchntTransLogMapper;
import com.cn.thinkx.wecard.api.module.accmchnt.model.AccMchntTransLog;
import com.cn.thinkx.wecard.api.module.accmchnt.service.AccMchntTransLogService;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;

@Service("accMchntTransLogService")
public class AccMchntTransLogServiceImpl implements AccMchntTransLogService {

	@Autowired
	@Qualifier("accMchntTransLogMapper")
	private AccMchntTransLogMapper accMchntTransLogMapper;
	
	@Override
	public int insertAccMchntTransLog(AccMchntTransLog accmchnt) {
		return this.accMchntTransLogMapper.insertAccMchntTransLog(accmchnt);
	}

	@Override
	public int updateAccMchntTransLog(AccMchntTransLog accmchnt, TxnResp txnResp) {
		if (txnResp != null) {
			accmchnt.setRespCode(txnResp.getCode());
			if (!StringUtil.isNullOrEmpty(txnResp.getTransAmt())) {
				accmchnt.setTransAmt(txnResp.getTransAmt());
			}
		}
		accmchnt.setTransSt(1);// 插入时为0，报文返回时更新为1
		return this.accMchntTransLogMapper.updateAccMchntTransLog(accmchnt);
	}

}
