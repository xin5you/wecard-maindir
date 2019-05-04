package com.cn.thinkx.wxclient.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wxclient.domain.WxFansRole;
import com.cn.thinkx.wxclient.mapper.WxFansRoleDao;
import com.cn.thinkx.wxclient.service.WxFansRoleService;

@Service("wxFansRoleService")
public class WxFansRoleServiceImpl implements WxFansRoleService {

	@Autowired
	private WxFansRoleDao wxFansRoleDao;
	
	/**
	 * insert 用户角色关联关系
	 * @return
	 */
	public int insertWxFansRole(WxFansRole wxFansRole) {
		// TODO Auto-generated method stub
		return wxFansRoleDao.insertFansRole(wxFansRole);
	}
	
	/***
	 *  删除粉丝角色
	 * @param 
	 * @return
	 */
	public int deleteWxMchntFansByFansId(String fansId){
		return wxFansRoleDao.deleteWxMchntFansByFansId(fansId);
	}

}
