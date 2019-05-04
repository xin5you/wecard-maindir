package com.cn.thinkx.wxcms.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wxcms.domain.MsgBase;
import com.cn.thinkx.wxcms.domain.MsgNews;
import com.cn.thinkx.wxcms.domain.MsgText;

@Repository("msgBaseDao")
public interface MsgBaseDao {

	public MsgBase getById(String id);

	public List<MsgBase> listForPage(MsgBase searchEntity);

	public List<MsgNews> listMsgNewsByBaseId(String[] ids);
	
	public MsgText getMsgTextByBaseId(String id);
	
	public MsgText getMsgTextBySubscribe();
	
	public MsgText getMsgTextByInputCode(String inputcode);
	
	public void add(MsgBase entity);

	public void update(MsgBase entity);
	
	public void updateInputcode(MsgBase entity);

	public void delete(MsgBase entity);

}