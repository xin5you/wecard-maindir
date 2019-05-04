package com.cn.thinkx.wecard.customer.module.wxcms.service;

import java.util.List;

import com.cn.thinkx.wechat.base.wxapi.domain.MsgBase;

public interface MsgBaseService {

	public MsgBase getById(String id);

	public List<MsgBase> listForPage(MsgBase searchEntity);

	public void add(MsgBase entity);

	public void update(MsgBase entity);

	public void delete(MsgBase entity);

}