package com.cn.thinkx.wxclient.service;

import java.util.List;

import com.cn.thinkx.wxclient.domain.WxRole;

public interface WxRoleService {

	/**
	 * insert角色
	 * @return
	 */
	public int insertWxRole(WxRole wxRole);
	
	/**
	 * 根据角色类型查询
	 * @param roleType
	 * @return
	 */
	public WxRole findWxRoleByRoleType(String roleType);
	
	/**
	 * 查询商户权限  by fansId
	 * @param fansId
	 * @return
	 */
	public List<WxRole> findWxRoleByFansId(String fansId);
	
	/**
	 * 查询商户权限 by openId
	 * @param openId
	 * @return
	 */
	public List<WxRole> findWxRoleByOpenId(String openId);
}
