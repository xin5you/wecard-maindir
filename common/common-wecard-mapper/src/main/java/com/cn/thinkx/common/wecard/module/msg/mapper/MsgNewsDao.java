package com.cn.thinkx.common.wecard.module.msg.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgNews;


public interface MsgNewsDao {

	public MsgNews getById(String id);

	public List<MsgNews> listForPage(MsgNews searchEntity);
	
	public List<MsgNews> pageWebNewsList(MsgNews searchEntity, @Param("param2")Pagination<MsgNews> page);

	public void add(MsgNews entity);

	public void update(MsgNews entity);
	
	public void updateUrl(MsgNews entity);

	public void delete(MsgNews entity);

	public List<MsgNews> getRandomMsg(Integer num);

	public List<MsgNews> getRandomMsgByContent(String inputcode ,Integer num);
	
	public List<MsgNews> getMsgNewsByIds(String[] array);

	/**
	 * 首次关注欢迎语
	 * @return
	 */
	public List<MsgNews> getMsgNewsBySubscribe();
	
	/**
	 * 首次注册欢迎语
	 * @return
	 */
	public List<MsgNews> getMsgNewsByUserReg();
	
}