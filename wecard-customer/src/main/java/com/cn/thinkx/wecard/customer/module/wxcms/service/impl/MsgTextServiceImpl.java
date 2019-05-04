package com.cn.thinkx.wecard.customer.module.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.module.msg.mapper.MsgBaseDao;
import com.cn.thinkx.common.wecard.module.msg.mapper.MsgTextDao;
import com.cn.thinkx.wecard.customer.module.wxcms.service.MsgTextService;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgBase;
import com.cn.thinkx.wechat.base.wxapi.domain.MsgText;
import com.cn.thinkx.wechat.base.wxapi.process.MsgType;

@Service("msgTextService")
public class MsgTextServiceImpl implements MsgTextService {

	@Autowired
	private MsgTextDao entityDao;

	@Autowired
	private MsgBaseDao baseDao;

	public MsgText getById(String id) {
		return entityDao.getById(id);
	}

	public List<MsgText> listForPage(MsgText searchEntity) {
		return entityDao.listForPage(searchEntity);
	}

	public void add(MsgText entity) {

		MsgBase base = new MsgBase();
		base.setInputcode(entity.getInputcode());
		base.setMsgtype(MsgType.Text.toString());
		baseDao.add(base);

		entity.setBaseId(base.getId());
		entityDao.add(entity);
	}

	public void update(MsgText entity) {
		MsgBase base = baseDao.getById(entity.getBaseId().toString());
		base.setInputcode(entity.getInputcode());
		baseDao.updateInputcode(base);
		entityDao.update(entity);
	}

	public void delete(MsgText entity) {
		MsgBase base = new MsgBase();
		base.setId(entity.getBaseId());
		entityDao.delete(entity);
		baseDao.delete(entity);
	}

	// 根据用户发送的文本消息，随机获取一条文本消息
	public MsgText getRandomMsg(String inputCode) {
		return entityDao.getRandomMsg(inputCode);
	}

	public MsgText getRandomMsg2() {
		return entityDao.getRandomMsg2();
	}
}
