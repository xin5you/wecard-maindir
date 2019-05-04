package com.cn.thinkx.wxcms.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wxcms.domain.AccountMenu;

@Repository("accountMenuDao")
public interface AccountMenuDao {

	public AccountMenu getById(String id);

	public List<AccountMenu> listForPage(AccountMenu searchEntity);

	public List<AccountMenu> listParentMenu();
	
	public List<AccountMenu> listWxMenus(String gid);
	
	public void add(AccountMenu entity);

	public void update(AccountMenu entity);

	public void delete(AccountMenu entity);



}