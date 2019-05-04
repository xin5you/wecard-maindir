package com.cn.thinkx.wxcms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.wxapi.process.MsgType;
import com.cn.thinkx.wxcms.domain.MsgBase;
import com.cn.thinkx.wxcms.domain.MsgNews;
import com.cn.thinkx.wxcms.domain.MsgNewsVO;
import com.cn.thinkx.wxcms.mapper.MsgBaseDao;
import com.cn.thinkx.wxcms.mapper.MsgNewsDao;
import com.cn.thinkx.wxcms.service.MsgNewsService;

@Service("msgNewsService")
public class MsgNewsServiceImpl implements MsgNewsService {

	@Autowired
	@Qualifier("msgBaseDao")
	private MsgBaseDao msgBaseDao;

	@Autowired
	@Qualifier("msgNewsDao")
	private MsgNewsDao msgNewsDao;

	public MsgNews getById(String id) {
		return msgNewsDao.getById(id);
	}

	public List<MsgNews> listForPage(MsgNews searchEntity) {
		return msgNewsDao.listForPage(searchEntity);
	}

	public List<MsgNewsVO> pageWebNewsList(MsgNews searchEntity, Pagination<MsgNews> page) {
		List<MsgNews> list = msgNewsDao.pageWebNewsList(searchEntity, page);
		List<MsgNewsVO> pageList = new ArrayList<MsgNewsVO>();
		for (MsgNews msg : list) {
			if (pageList.size() == 0) {
				MsgNewsVO vo = new MsgNewsVO();
				vo.setCreateTimeStr(msg.getCreateTimeStr());
				vo.getMsgNewsList().add(msg);
				pageList.add(vo);
			} else {
				MsgNewsVO tmpMsgNewsVO = pageList.get(pageList.size() - 1);
				if (tmpMsgNewsVO.getCreateTimeStr().equals(msg.getCreateTimeStr())) {
					tmpMsgNewsVO.getMsgNewsList().add(msg);
				} else {
					MsgNewsVO vo = new MsgNewsVO();
					vo.setCreateTimeStr(msg.getCreateTimeStr());
					vo.getMsgNewsList().add(msg);
					pageList.add(vo);
				}
			}
		}
		return pageList;
	}

	public void add(MsgNews entity) {

		MsgBase base = new MsgBase();
		base.setInputcode(entity.getInputcode());
		base.setCreatetime(new Date());
		base.setMsgtype(MsgType.News.toString());
		msgBaseDao.add(base);

		entity.setBaseId(base.getId());
		msgNewsDao.add(entity);

		if (StringUtils.isEmpty(entity.getFromurl())) {
			entity.setUrl(entity.getUrl() + "?id=" + entity.getId());
		} else {
			entity.setUrl("");
		}

		msgNewsDao.updateUrl(entity);
	}

	public void update(MsgNews entity) {
		MsgBase base = msgBaseDao.getById(entity.getBaseId().toString());
		base.setInputcode(entity.getInputcode());
		msgBaseDao.updateInputcode(base);

		if (StringUtils.isEmpty(entity.getFromurl())) {
			entity.setUrl(entity.getUrl() + "?id=" + entity.getId());
		} else {
			entity.setUrl("");
		}
		msgNewsDao.update(entity);
	}

	public void delete(MsgNews entity) {
		MsgBase base = new MsgBase();
		base.setId(entity.getBaseId());
		msgNewsDao.delete(entity);
		msgBaseDao.delete(entity);
	}

	public List<MsgNews> getRandomMsg(String inputCode, Integer num) {
		return msgNewsDao.getRandomMsgByContent(inputCode, num);
	}

}
