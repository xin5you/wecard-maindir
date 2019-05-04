package com.cn.thinkx.wxclient.mapper;

import java.util.List;

import com.cn.thinkx.wxclient.domain.WxResource;

public interface WxResourceDao {


	public int insertWxResource(WxResource entity);
	
	/**查询所有的资源***/
	public List<WxResource> getAllWxResourceList(WxResource entity);
	
	/**
	 *  查询商户用户的 资源（视图查询）
	 * @param entity
	 * @return 
	 */
	public List<WxResource> findWxResourceListByParam(WxResource entity);
}