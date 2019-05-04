package com.cn.thinkx.wecard.customer.module.wxcms.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;

public interface AccountFansService {

	public AccountFans getById(String id);

	public AccountFans getByOpenId(String openId);

	public List<AccountFans> list(AccountFans searchEntity);

	public Pagination<AccountFans> paginationEntity(AccountFans searchEntity, Pagination<AccountFans> pagination);

	public AccountFans getLastOpenId();

	public void add(AccountFans entity);

	public void update(AccountFans entity);

	public void delete(AccountFans entity);

	public void deleteByOpenId(String openId);
	
	/**
	 * 用户关注或者取消关注时候，同步数据
	 */
	public void syncAccountFans(String openId,String subscribeStatus);
	
	/***
	 * 修改商户粉丝表 微信客户端修改状态，不修改日期可用
	 * @param searchEntity
	 * @return
	 */
	public int updateAccountFansByMcht(AccountFans entity);

}