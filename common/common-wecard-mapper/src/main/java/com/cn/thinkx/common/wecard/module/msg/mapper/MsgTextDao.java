package com.cn.thinkx.common.wecard.module.msg.mapper;

import java.util.List;

import com.cn.thinkx.wechat.base.wxapi.domain.MsgText;


public interface MsgTextDao {

	public MsgText getById(String id);

	public List<MsgText> listForPage(MsgText searchEntity);

	public void add(MsgText entity);

	public void update(MsgText entity);

	public void delete(MsgText entity);

	public MsgText getRandomMsg(String inputCode);
	
	public MsgText getRandomMsg2();

}