package com.cn.thinkx.wxclient.mapper;

import com.cn.thinkx.wxclient.domain.WxFansRole;

public interface WxFansRoleDao {


	public int insertFansRole(WxFansRole domain);
	
	public int deleteWxMchntFansByFansId(String id);

}