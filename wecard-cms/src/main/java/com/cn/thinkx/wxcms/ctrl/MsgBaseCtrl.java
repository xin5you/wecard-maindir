package com.cn.thinkx.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.wxcms.domain.MsgBase;
import com.cn.thinkx.wxcms.domain.MsgNews;
import com.cn.thinkx.wxcms.domain.MsgText;
import com.cn.thinkx.wxcms.service.MsgBaseService;
import com.cn.thinkx.wxcms.service.MsgNewsService;
import com.cn.thinkx.wxcms.service.MsgTextService;

@Controller
@RequestMapping("/msgbase")
public class MsgBaseCtrl {

	@Autowired
	@Qualifier("msgBaseService")
	private MsgBaseService msgBaseService;

	@Autowired
	@Qualifier("msgNewsService")
	private MsgNewsService msgNewsService;

	@Autowired
	@Qualifier("msgTextService")
	private MsgTextService msgTextService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id) {
		msgBaseService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/listForPage")
	public ModelAndView listForPage(@ModelAttribute MsgBase searchEntity) {
		return new ModelAndView();
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(MsgBase entity) {

		return new ModelAndView();
	}

	@RequestMapping(value = "/menuMsgs")
	public ModelAndView menuMsgs() {
		ModelAndView mv = new ModelAndView("wxcms/menuMsgs");
		// 获取所有的图文消息;
		List<MsgNews> newsList = msgNewsService.listForPage(new MsgNews());
		// 获取所有的文本消息;
		List<MsgText> textList = msgTextService.listForPage(new MsgText());

		mv.addObject("newsList", newsList);
		mv.addObject("textList", textList);

		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(MsgBase entity) {
		msgBaseService.add(entity);
		return new ModelAndView();
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(MsgBase entity) {
		msgBaseService.delete(entity);
		return new ModelAndView();
	}

}