package com.cn.thinkx.wecard.centre.module.wxcms.service;

import com.cn.thinkx.wechat.base.wxapi.domain.Account;


public interface AccountService {

	public Account getById(String id);

	public Account getByAccount(String account);

	public Account getSingleAccount();


	public void add(Account entity);

	public void update(Account entity);

	public void delete(Account entity);

}