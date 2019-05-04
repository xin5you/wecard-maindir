package com.cn.thinkx.wxclient.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wxclient.domain.WxRole;
import com.cn.thinkx.wxclient.mapper.WxRoleDao;
import com.cn.thinkx.wxclient.service.WxRoleService;

@Service("wxRoleService")
public class WxRoleServiceImpl implements WxRoleService {
	
	@Autowired
	private WxRoleDao wxRoleDao;

	/**
	 * insert 角色
	 * @return int 1：成功标记
	 */
	public int insertWxRole(WxRole wxRole) {
		return wxRoleDao.insertWxRole(wxRole);
	}

	
	/**
	 * 根据角色类型查询启用的角色
	 */
	public WxRole findWxRoleByRoleType(String roleType) {
		if(!StringUtil.isNullOrEmpty(roleType)){
			return wxRoleDao.findWxRoleByRoleType(roleType);
		}else{
			return null;
		}
	}
	

	/**
	 * 查询商户权限  by fansId
	 * @param fansId
	 * @return
	 */
	public List<WxRole> findWxRoleByFansId(String fansId){
		return wxRoleDao.findWxRoleByFansId(fansId);
	}
	
	/**
	 * 查询商户权限 by openId
	 * @param openId
	 * @return
	 */
	public List<WxRole> findWxRoleByOpenId(String openId){
		return wxRoleDao.findWxRoleByOpenId(openId);
	}

}
