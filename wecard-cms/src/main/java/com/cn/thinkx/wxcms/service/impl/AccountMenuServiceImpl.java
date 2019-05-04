package com.cn.thinkx.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wxcms.domain.AccountMenu;
import com.cn.thinkx.wxcms.mapper.AccountMenuDao;
import com.cn.thinkx.wxcms.service.AccountMenuService;

@Service("accountMenuService")
public class AccountMenuServiceImpl implements AccountMenuService {

	@Autowired
	@Qualifier("accountMenuDao")
	private AccountMenuDao accountMenuDao;

	public AccountMenu getById(String id) {
		return accountMenuDao.getById(id);
	}

	public List<AccountMenu> listForPage(AccountMenu searchEntity) {
		return accountMenuDao.listForPage(searchEntity);
	}

	public List<AccountMenu> listParentMenu() {
		return accountMenuDao.listParentMenu();
	}

	public void add(AccountMenu entity) {
		accountMenuDao.add(entity);
	}

	public void update(AccountMenu entity) {
		accountMenuDao.update(entity);
	}

	public void delete(AccountMenu entity) {
		accountMenuDao.delete(entity);
	}

}