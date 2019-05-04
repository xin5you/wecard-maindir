package com.cn.thinkx.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.wxcms.domain.MsgBase;
import com.cn.thinkx.wxcms.domain.MsgText;
import com.cn.thinkx.wxcms.service.MsgBaseService;
import com.cn.thinkx.wxcms.service.MsgTextService;

@Controller
@RequestMapping("/msgtext")
public class MsgTextCtrl {

	@Autowired
	@Qualifier("msgTextService")
	private MsgTextService msgTextService;

	@Autowired
	@Qualifier("msgBaseService")
	private MsgBaseService msgBaseService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id) {
		msgTextService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(@ModelAttribute MsgText searchEntity) {
		ModelAndView modelAndView = new ModelAndView("wxcms/msgtextList");
		List<MsgText> pageList = msgTextService.listForPage(searchEntity);
		modelAndView.addObject("pageList", pageList);
		modelAndView.addObject("cur_nav", "text");
		return modelAndView;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(MsgText entity) {
		ModelAndView mv = new ModelAndView("wxcms/msgtextMerge");
		mv.addObject("cur_nav", "text");
		if (entity.getId() != null) {
			MsgText text = msgTextService.getById(entity.getId().toString());
			mv.addObject("entity", text);
			mv.addObject("baseEntity", msgBaseService.getById(text.getBaseId().toString()));
		} else {
			mv.addObject("entity", new MsgText());
			mv.addObject("baseEntity", new MsgBase());
		}
		return mv;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(MsgText entity) {
		if (entity.getId() != null) {
			msgTextService.update(entity);
		} else {
			msgTextService.add(entity);
		}
		return new ModelAndView("redirect:/msgtext/list.html");
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(MsgText entity) {
		msgTextService.delete(entity);
		return new ModelAndView("redirect:/msgtext/list.html");
	}

}
