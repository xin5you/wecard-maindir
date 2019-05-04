package com.cn.thinkx.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wxcms.domain.MsgBase;
import com.cn.thinkx.wxcms.mapper.MsgBaseDao;
import com.cn.thinkx.wxcms.service.MsgBaseService;

@Service("msgBaseService")
public class MsgBaseServiceImpl implements MsgBaseService {

	@Autowired
	@Qualifier("msgBaseDao")
	private MsgBaseDao msgBaseDao;

	public MsgBase getById(String id) {
		return msgBaseDao.getById(id);
	}

	public List<MsgBase> listForPage(MsgBase searchEntity) {
		return msgBaseDao.listForPage(searchEntity);
	}

	public void add(MsgBase entity) {
		msgBaseDao.add(entity);
	}

	public void update(MsgBase entity) {
		msgBaseDao.update(entity);
	}

	public void delete(MsgBase entity) {
		msgBaseDao.delete(entity);
	}

}