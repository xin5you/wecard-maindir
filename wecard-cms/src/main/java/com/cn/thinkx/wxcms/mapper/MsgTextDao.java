package com.cn.thinkx.wxcms.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wxcms.domain.MsgText;

@Repository("msgTextDao")
public interface MsgTextDao {

	public MsgText getById(String id);

	public List<MsgText> listForPage(MsgText searchEntity);

	public void add(MsgText entity);

	public void update(MsgText entity);

	public void delete(MsgText entity);

	public MsgText getRandomMsg(String inputCode);
	
	public MsgText getRandomMsg2();

}