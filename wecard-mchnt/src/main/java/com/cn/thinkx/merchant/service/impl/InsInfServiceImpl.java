package com.cn.thinkx.merchant.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.merchant.domain.InsInf;
import com.cn.thinkx.merchant.mapper.InsInfDao;
import com.cn.thinkx.merchant.service.InsInfService;

@Service("insInfService")
public class InsInfServiceImpl implements InsInfService {
	
	@Autowired
	@Qualifier("insInfDao")
	private InsInfDao insInfDao;

	@Override
	public InsInf getInsInfByMchntId(String mchntId) {
		return insInfDao.getInsInfByMchntId(mchntId);
	}

}
