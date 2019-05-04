package com.cn.thinkx.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.wxcms.domain.AccountFans;
import com.cn.thinkx.wxcms.mapper.AccountFansDao;
import com.cn.thinkx.wxcms.service.AccountFansService;

@Service("accountFansService")
public class AccountFansServiceImpl implements AccountFansService {

	@Autowired
	@Qualifier("accountFansDao")
	private AccountFansDao accountFansDao;

	public AccountFans getById(String id) {
		return accountFansDao.getById(id);
	}

	public AccountFans getByOpenId(String openId) {
		return accountFansDao.getByOpenId(openId);
	}

	public List<AccountFans> list(AccountFans searchEntity) {
		return accountFansDao.list(searchEntity);
	}

	public Pagination<AccountFans> paginationEntity(AccountFans searchEntity, Pagination<AccountFans> pagination) {
		Integer totalItemsCount = accountFansDao.getTotalItemsCount(searchEntity);
		List<AccountFans> items = accountFansDao.paginationEntity(searchEntity, pagination);
		pagination.setTotalItemsCount(totalItemsCount);
		pagination.setItems(items);
		return pagination;
	}

	public AccountFans getLastOpenId() {
		return accountFansDao.getLastOpenId();
	}

	public void sync(AccountFans searchEntity) {
		AccountFans lastFans = accountFansDao.getLastOpenId();
		String lastOpenId = "";
		if (lastFans != null) {
			lastOpenId = lastFans.getOpenId();
		}

	}

	public void add(AccountFans entity) {
		accountFansDao.add(entity);
	}

	public void update(AccountFans entity) {
		accountFansDao.update(entity);
	}

	public void delete(AccountFans entity) {
		accountFansDao.delete(entity);
	}

	public void deleteByOpenId(String openId) {
		accountFansDao.deleteByOpenId(openId);
	}
	/***
	 * 修改商户粉丝表 微信客户端修改状态，不修改日期可用
	 * @param searchEntity
	 * @return
	 */
	public int updateAccountFansByMcht(AccountFans entity){
		return accountFansDao.updateAccountFansByMcht(entity);
	}
}