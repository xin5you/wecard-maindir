package com.cn.thinkx.wecard.customer.module.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.module.account.mapper.AccountDao;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountService;
import com.cn.thinkx.wechat.base.wxapi.domain.Account;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao entityDao;

	public Account getById(String id) {
		return entityDao.getById(id);
	}

	public Account getByAccount(String account) {
		return entityDao.getByAccount(account);
	}

	@Override
	public Account getSingleAccount() {
		return entityDao.getSingleAccount();
	}

	public List<Account> listForPage(Account searchEntity) {
		return entityDao.listForPage(searchEntity);
	}

	public void add(Account entity) {
		entityDao.add(entity);
	}

	public void update(Account entity) {
		entityDao.update(entity);
	}

	public void delete(Account entity) {
		entityDao.delete(entity);
	}

}