package com.cn.thinkx.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wxcms.domain.Account;
import com.cn.thinkx.wxcms.mapper.AccountDao;
import com.cn.thinkx.wxcms.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	@Qualifier("accountDao")
	private AccountDao accountDao;

	public Account getById(String id) {
		return accountDao.getById(id);
	}

	public Account getByAccount(String account) {
		return accountDao.getByAccount(account);
	}

	@Override
	public Account getSingleAccount() {
		return accountDao.getSingleAccount();
	}

	public List<Account> listForPage(Account searchEntity) {
		return accountDao.listForPage(searchEntity);
	}

	public void add(Account entity) {
		accountDao.add(entity);
	}

	public void update(Account entity) {
		accountDao.update(entity);
	}

	public void delete(Account entity) {
		accountDao.delete(entity);
	}

}