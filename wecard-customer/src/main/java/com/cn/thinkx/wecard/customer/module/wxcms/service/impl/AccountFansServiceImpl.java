package com.cn.thinkx.wecard.customer.module.wxcms.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.module.account.mapper.AccountFansDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountFansService;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wechat.base.wxapi.process.MsgType;

@Service
public class AccountFansServiceImpl implements AccountFansService {

	@Autowired
	private AccountFansDao entityDao;

	public AccountFans getById(String id) {
		return entityDao.getById(id);
	}

	public AccountFans getByOpenId(String openId) {
		return entityDao.getByOpenId(openId);
	}

	public List<AccountFans> list(AccountFans searchEntity) {
		return entityDao.list(searchEntity);
	}

	public Pagination<AccountFans> paginationEntity(AccountFans searchEntity, Pagination<AccountFans> pagination) {
		Integer totalItemsCount = entityDao.getTotalItemsCount(searchEntity);
		List<AccountFans> items = entityDao.paginationEntity(searchEntity, pagination);
		if (pagination != null) {
			pagination.setItems(items);
			pagination.setTotalItemsCount(totalItemsCount);
		}
		return pagination;
	}

	public AccountFans getLastOpenId() {
		return entityDao.getLastOpenId();
	}


	public void add(AccountFans entity) {
		entityDao.add(entity);
	}

	public void update(AccountFans entity) {
		entityDao.update(entity);
	}

	public void delete(AccountFans entity) {
		entityDao.delete(entity);
	}

	public void deleteByOpenId(String openId) {
		entityDao.deleteByOpenId(openId);
	}
	
	/**
	 * 用户关注或者取消关注时候，同步数据
	 */
	public void syncAccountFans(String openId,String subscribeStatus){
		AccountFans accountFans=entityDao.getByOpenId(openId);
		if(accountFans==null){
			accountFans=new AccountFans();
			accountFans.setOpenId(openId);
			accountFans.setStatus(BaseConstants.AccountFansStatusEnum.TRUE_STATUS.getCode());
			accountFans.setFansStatus(BaseConstants.FansStatusEnum.Fans_STATUS_00.getCode()); //粉丝菜单权限
			accountFans.setSubscribestatus(BaseConstants.AccountFansStatusEnum.TRUE_STATUS.getCode());
			accountFans.setSubscribeTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			entityDao.add(accountFans);
		}
		else{
			if(MsgType.SUBSCRIBE.getName().equals(subscribeStatus)){//如果是关注状态
				accountFans.setStatus(BaseConstants.AccountFansStatusEnum.TRUE_STATUS.getCode());
				accountFans.setSubscribestatus(BaseConstants.AccountFansStatusEnum.TRUE_STATUS.getCode());
			}else{
				accountFans.setStatus(BaseConstants.AccountFansStatusEnum.FALSE_STATUS.getCode());
				accountFans.setSubscribestatus(BaseConstants.AccountFansStatusEnum.FALSE_STATUS.getCode());
			}
			accountFans.setSubscribeTime(DateUtil.COMMON_FULL.getDateText(new Date()));
			entityDao.updateAccountFansByStatus(accountFans);//更新关注状态
		}
	}
	
	/***
	 * 修改商户粉丝表 微信客户端修改状态，不修改日期可用
	 * @param searchEntity
	 * @return
	 */
	public int updateAccountFansByMcht(AccountFans entity){
		return entityDao.updateAccountFansByMcht(entity);
	}
}