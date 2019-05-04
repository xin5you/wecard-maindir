package com.cn.thinkx.wxclient.mapper;

import java.util.List;

import com.cn.thinkx.wxclient.domain.WxRole;

public interface WxRoleDao {


	public int insertWxRole(WxRole domain);
	
	public WxRole findWxRoleByRoleType(String roleType);
	
	public List<WxRole> findWxRoleByFansId(String fansId);
	
	public List<WxRole> findWxRoleByOpenId(String openId);
}