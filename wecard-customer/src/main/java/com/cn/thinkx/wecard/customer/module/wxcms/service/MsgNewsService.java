package com.cn.thinkx.wecard.customer.module.wxcms.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgNews;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgNewsVO;

public interface MsgNewsService {

	public MsgNews getById(String id);

	public List<MsgNews> listForPage(MsgNews searchEntity);

	public List<MsgNewsVO> pageWebNewsList(MsgNews searchEntity, Pagination<MsgNews> page);

	public void add(MsgNews entity);

	public void update(MsgNews entity);

	public void delete(MsgNews entity);

	// 根据用户发送的文本消息，随机获取 num 条文本消息
	public List<MsgNews> getRandomMsg(String inputcode, Integer num);

}