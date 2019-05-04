package com.cn.thinkx.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.wxcms.domain.AccountMenuGroup;
import com.cn.thinkx.wxcms.mapper.AccountMenuGroupDao;
import com.cn.thinkx.wxcms.service.AccountMenuGroupService;

@Service("accountMenuGroupService")
public class AccountMenuGroupServiceImpl implements AccountMenuGroupService {

	@Autowired
	@Qualifier("accountMenuGroupDao")
	private AccountMenuGroupDao accountMenuGroupDao;

	public AccountMenuGroup getById(String id) {
		return accountMenuGroupDao.getById(id);
	}

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity) {
		return accountMenuGroupDao.list(searchEntity);
	}

	public Pagination<AccountMenuGroup> paginationEntity(AccountMenuGroup searchEntity,
			Pagination<AccountMenuGroup> pagination) {
		Integer totalItemsCount = accountMenuGroupDao.getTotalItemsCount(searchEntity);
		List<AccountMenuGroup> items = accountMenuGroupDao.paginationEntity(searchEntity, pagination);
		pagination.setTotalItemsCount(totalItemsCount);
		pagination.setItems(items);
		return pagination;
	}

	public void add(AccountMenuGroup entity) {
		accountMenuGroupDao.add(entity);
	}

	public void update(AccountMenuGroup entity) {
		accountMenuGroupDao.update(entity);
	}

	public void delete(AccountMenuGroup entity) {
		accountMenuGroupDao.deleteAllMenu(entity);
		accountMenuGroupDao.delete(entity);
	}
	
	public AccountMenuGroup getMembersGroupsId() {
		return accountMenuGroupDao.getMembersGroupsId();
	}

}
