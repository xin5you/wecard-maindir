package com.cn.thinkx.wecard.customer.module.wxcms.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountMenuGroup;

public interface AccountMenuGroupService {

	public AccountMenuGroup getById(String id);

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity);

	public Pagination<AccountMenuGroup> paginationEntity(AccountMenuGroup searchEntity,
			Pagination<AccountMenuGroup> pagination);

	public void add(AccountMenuGroup entity);

	public void update(AccountMenuGroup entity);

	public void delete(AccountMenuGroup entity);
	
	/***
	 * 获取分组
	 * @return
	 */
	public AccountMenuGroup getMembersGroupsId();

}
