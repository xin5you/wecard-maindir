package com.cn.thinkx.wxcms.service;

import java.util.List;

import com.cn.thinkx.wxcms.domain.AccountMenu;

public interface AccountMenuService {

	public AccountMenu getById(String id);

	public List<AccountMenu> listForPage(AccountMenu searchEntity);

	public List<AccountMenu> listParentMenu();

	public void add(AccountMenu entity);

	public void update(AccountMenu entity);

	public void delete(AccountMenu entity);

}