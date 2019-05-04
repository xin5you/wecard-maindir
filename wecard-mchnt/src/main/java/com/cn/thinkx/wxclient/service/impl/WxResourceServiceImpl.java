package com.cn.thinkx.wxclient.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wxclient.domain.WxResource;
import com.cn.thinkx.wxclient.mapper.WxResourceDao;
import com.cn.thinkx.wxclient.service.WxResourceService;

@Service("wxResourceService")
public class WxResourceServiceImpl implements WxResourceService {

	@Autowired
	private WxResourceDao wxResourceDao;

	@Override
	public int insertWxResource(WxResource entity) {
		return wxResourceDao.insertWxResource(entity);
	}

	@Override
	public List<WxResource> getAllWxResourceList(WxResource entity) {
		return wxResourceDao.getAllWxResourceList(entity);
	}

	@Override
	public List<WxResource> findWxResourceListByParam(WxResource entity) {
		return wxResourceDao.findWxResourceListByParam(entity);
	}

}
