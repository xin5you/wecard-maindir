package com.cn.thinkx.wecard.centre.module.wxcms.mapper;

import com.cn.thinkx.wechat.base.wxapi.domain.Account;


public interface AccountDao {

	public Account getById(String id);
	
	public Account getByAccount(String account);
	
	public Account getSingleAccount();

	public void add(Account entity);

	public void update(Account entity);

	public void delete(Account entity);



}