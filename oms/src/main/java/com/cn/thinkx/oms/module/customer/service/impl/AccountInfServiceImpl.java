package com.cn.thinkx.oms.module.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.customer.mapper.AccountInfMapper;
import com.cn.thinkx.oms.module.customer.model.AccountInf;
import com.cn.thinkx.oms.module.customer.service.AccountInfService;

@Service("accountInfService")
public class AccountInfServiceImpl implements AccountInfService {
	
	@Autowired
	private AccountInfMapper accountInfMapper;

	public void updateAccountInf(AccountInf account) {
		accountInfMapper.updateAccountInf(account);
	}
}
