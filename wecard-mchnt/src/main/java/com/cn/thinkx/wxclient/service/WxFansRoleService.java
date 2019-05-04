package com.cn.thinkx.wxclient.service;

import com.cn.thinkx.wxclient.domain.WxFansRole;

public interface WxFansRoleService {

	/**
	 * insert 用户角色关联关系
	 * @return
	 */
	public int insertWxFansRole(WxFansRole wxFansRole);
	
	/***
	 *  删除粉丝角色
	 * @param 
	 * @return
	 */
	public int deleteWxMchntFansByFansId(String fansId);
}
