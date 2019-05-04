package com.cn.thinkx.wecard.customer.module.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.module.account.mapper.AccountMenuGroupDao;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountMenuGroupService;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountMenuGroup;

@Service
public class AccountMenuGroupServiceImpl implements AccountMenuGroupService {

	@Autowired
	private AccountMenuGroupDao entityDao;

	public AccountMenuGroup getById(String id) {
		return entityDao.getById(id);
	}

	public List<AccountMenuGroup> list(AccountMenuGroup searchEntity) {
		return entityDao.list(searchEntity);
	}

	public Pagination<AccountMenuGroup> paginationEntity(AccountMenuGroup searchEntity,
			Pagination<AccountMenuGroup> pagination) {
		Integer totalItemsCount = entityDao.getTotalItemsCount(searchEntity);
		List<AccountMenuGroup> items = entityDao.paginationEntity(searchEntity, pagination);
		if (pagination != null) {
			pagination.setTotalItemsCount(totalItemsCount);
			pagination.setItems(items);
		}
		return pagination;
	}

	public void add(AccountMenuGroup entity) {
		entityDao.add(entity);
	}

	public void update(AccountMenuGroup entity) {
		entityDao.update(entity);
	}

	public void delete(AccountMenuGroup entity) {
		entityDao.deleteAllMenu(entity);
		entityDao.delete(entity);
	}
	
	public AccountMenuGroup getMembersGroupsId() {
		return entityDao.getMembersGroupsId();
	}

}
