package com.cn.thinkx.wecard.customer.module.welfaremart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.cardkeys.WithdrawBlacklistInf;
import com.cn.thinkx.common.wecard.module.cardkeys.mapper.WithdrawBlacklistInfMapper;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.WithdrawBlacklistInfService;

@Service("withdrawBlacklistInfService")
public class WithdrawBlacklistInfServiceImpl implements WithdrawBlacklistInfService {

	@Autowired
	private WithdrawBlacklistInfMapper withdrawBlacklistInfMapper;

	@Override
	public WithdrawBlacklistInf getWithdrawBlacklistInfByUserPhone(String userPhone) {
		return withdrawBlacklistInfMapper.getWithdrawBlacklistInfByUserPhone(userPhone);
	}

}
