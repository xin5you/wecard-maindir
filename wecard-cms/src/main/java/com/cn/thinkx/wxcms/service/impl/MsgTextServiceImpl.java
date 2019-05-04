package com.cn.thinkx.wxcms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wxapi.process.MsgType;
import com.cn.thinkx.wxcms.domain.MsgBase;
import com.cn.thinkx.wxcms.domain.MsgText;
import com.cn.thinkx.wxcms.mapper.MsgBaseDao;
import com.cn.thinkx.wxcms.mapper.MsgTextDao;
import com.cn.thinkx.wxcms.service.MsgTextService;

@Service("msgTextService")
public class MsgTextServiceImpl implements MsgTextService {

	@Autowired
	@Qualifier("msgTextDao")
	private MsgTextDao msgTextDao;

	@Autowired
	@Qualifier("msgBaseDao")
	private MsgBaseDao msgBaseDao;

	public MsgText getById(String id) {
		return msgTextDao.getById(id);
	}

	public List<MsgText> listForPage(MsgText searchEntity) {
		return msgTextDao.listForPage(searchEntity);
	}

	public void add(MsgText entity) {

		MsgBase base = new MsgBase();
		base.setInputcode(entity.getInputcode());
		base.setMsgtype(MsgType.Text.toString());
		msgBaseDao.add(base);

		entity.setBaseId(base.getId());
		msgTextDao.add(entity);
	}

	public void update(MsgText entity) {
		MsgBase base = msgBaseDao.getById(entity.getBaseId().toString());
		base.setInputcode(entity.getInputcode());
		msgBaseDao.updateInputcode(base);
		msgTextDao.update(entity);
	}

	public void delete(MsgText entity) {
		MsgBase base = new MsgBase();
		base.setId(entity.getBaseId());
		msgTextDao.delete(entity);
		msgBaseDao.delete(entity);
	}

	// 根据用户发送的文本消息，随机获取一条文本消息
	public MsgText getRandomMsg(String inputCode) {
		return msgTextDao.getRandomMsg(inputCode);
	}

	public MsgText getRandomMsg2() {
		return msgTextDao.getRandomMsg2();
	}
}
