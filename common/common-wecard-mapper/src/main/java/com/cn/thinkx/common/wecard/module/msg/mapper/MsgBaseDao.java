package com.cn.thinkx.common.wecard.module.msg.mapper;

import java.util.List;

import com.cn.thinkx.wechat.base.wxapi.domain.MsgBase;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgNews;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgText;


public interface MsgBaseDao {

	public MsgBase getById(String id);

	public List<MsgBase> listForPage(MsgBase searchEntity);

	public List<MsgNews> listMsgNewsByBaseId(String[] ids);
	
	public MsgText getMsgTextByBaseId(String id);
	
	/**
	 * 首次关注欢迎语
	 * @return
	 */
	public MsgText getMsgTextBySubscribe();
	/**
	 * 在次关注欢迎语
	 * @return
	 */
	public MsgText getMsgTextByAgainSubscribe();
	
	public MsgText getMsgTextByInputCode(String inputcode);
	
	public void add(MsgBase entity);

	public void update(MsgBase entity);
	
	public void updateInputcode(MsgBase entity);

	public void delete(MsgBase entity);

}