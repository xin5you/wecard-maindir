package com.cn.thinkx.common.wecard.module.account.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountMenuGroup;


public interface AccountMenuGroupDao {

	public AccountMenuGroup getById(String id);

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity);

	public Integer getTotalItemsCount(AccountMenuGroup searchEntity);

	public List<AccountMenuGroup> paginationEntity(AccountMenuGroup searchEntity , @Param("param2")Pagination<AccountMenuGroup> pagination);

	public void add(AccountMenuGroup entity);

	public void update(AccountMenuGroup entity);
	
	public void updateMenuGroupDisable();
	
	public void updateMenuGroupEnable(String gid);
	
	public void deleteAllMenu(AccountMenuGroup entity);
	
	public void delete(AccountMenuGroup entity);

	public AccountMenuGroup getMembersGroupsId();

}

