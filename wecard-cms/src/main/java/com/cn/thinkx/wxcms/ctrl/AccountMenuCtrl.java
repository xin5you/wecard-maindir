package com.cn.thinkx.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.wxcms.domain.AccountMenu;
import com.cn.thinkx.wxcms.domain.AccountMenuGroup;
import com.cn.thinkx.wxcms.service.AccountMenuGroupService;
import com.cn.thinkx.wxcms.service.AccountMenuService;

@Controller
@RequestMapping("/accountmenu")
public class AccountMenuCtrl {

	@Autowired
	@Qualifier("accountMenuService")
	private AccountMenuService accountMenuService;

	@Autowired
	@Qualifier("accountMenuGroupService")
	private AccountMenuGroupService accountMenuGroupService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id) {
		accountMenuService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(@ModelAttribute AccountMenu searchEntity) {
		ModelAndView modelAndView = new ModelAndView("wxcms/menuList");
		List<AccountMenu> list = accountMenuService.listForPage(searchEntity);
		AccountMenuGroup groupEntity = accountMenuGroupService.getById(searchEntity.getGid().toString());
		modelAndView.addObject("groupEntity", groupEntity);
		modelAndView.addObject("pageList", list);
		modelAndView.addObject("cur_nav", "menu");
		return modelAndView;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(AccountMenu entity, Long gid) {
		ModelAndView modelAndView = new ModelAndView("wxcms/menuMerge");
		if (gid != null) {
			List<AccountMenu> list = accountMenuService.listParentMenu();
			modelAndView.addObject("parentMenu", list);
			modelAndView.addObject("cur_nav", "menu");
			modelAndView.addObject("gid", gid);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(AccountMenu entity, String gid) {
		if (gid != null) {
			entity.setGid(Long.parseLong(gid));
			accountMenuService.add(entity);
		}
		return new ModelAndView("redirect:/accountmenu/list.html?gid=" + gid);
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(AccountMenu entity, String gid) {
		ModelAndView modelAndView = new ModelAndView("redirect:/accountmenu/list.html?gid=" + gid);
		accountMenuService.delete(entity);
		return modelAndView;
	}

}
