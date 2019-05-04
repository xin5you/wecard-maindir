package com.cn.thinkx.wxapi.service;

import com.cn.thinkx.wxapi.process.MpAccount;
import com.cn.thinkx.wxapi.vo.MsgRequest;
import com.cn.thinkx.wxcms.domain.AccountFans;

import net.sf.json.JSONObject;

/**
 * 我的微信服务接口，主要用于结合自己的业务和微信接口
 */
public interface BizService {
	
	//消息处理
	public String processMsg(MsgRequest msgRequest,MpAccount mpAccount);

	//发布菜单
	public JSONObject publishMenu(String gid,MpAccount mpAccount);
	
	//发布个性化菜单
	public JSONObject publishMenuConditional(String gid,MpAccount mpAccount);
	
	//删除菜单
	public JSONObject deleteMenu(MpAccount mpAccount);
	
	//获取用户列表
	public boolean syncAccountFansList(MpAccount mpAccount);
	
	//获取单个用户信息
	public AccountFans syncAccountFans(String openId, MpAccount mpAccount, boolean merge);
	
	//根据openid 获取粉丝，如果没有，同步粉丝
	public AccountFans getFansByOpenId(String openid,MpAccount mpAccount);
	
	//移动用户分组
	public boolean updateFansGroupId(String openid,String togroupid,MpAccount mpAccount);
}



