package com.cn.thinkx.wecard.customer.module.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.module.msg.mapper.MsgBaseDao;
import com.cn.thinkx.wecard.customer.module.wxcms.service.MsgBaseService;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgBase;

@Service
public class MsgBaseServiceImpl implements MsgBaseService {

	@Autowired
	private MsgBaseDao entityDao;

	public MsgBase getById(String id) {
		return entityDao.getById(id);
	}

	public List<MsgBase> listForPage(MsgBase searchEntity) {
		return entityDao.listForPage(searchEntity);
	}

	public void add(MsgBase entity) {
		entityDao.add(entity);
	}

	public void update(MsgBase entity) {
		entityDao.update(entity);
	}

	public void delete(MsgBase entity) {
		entityDao.delete(entity);
	}

}