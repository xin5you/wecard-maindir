package com.cn.thinkx.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.wxcms.domain.AccountMenu;
import com.cn.thinkx.wxcms.domain.AccountMenuGroup;
import com.cn.thinkx.wxcms.service.AccountMenuGroupService;
import com.cn.thinkx.wxcms.service.AccountMenuService;

@Controller
@RequestMapping("/wxcms/accountMenuGroup")
public class AccountMenuGroupCtrl {

	@Autowired
	@Qualifier("accountMenuGroupService")
	private AccountMenuGroupService accountMenuGroupService;

	@Autowired
	@Qualifier("accountMenuService")
	private AccountMenuService accountMenuService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id) {
		ModelAndView mv = new ModelAndView("wxcms/accountMenuGroup");
		mv.addObject("entity", accountMenuGroupService.getById(id));
		return mv;
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(AccountMenuGroup searchEntity) {
		ModelAndView mv = new ModelAndView("wxcms/accountMenuGroupList");
		List<AccountMenuGroup> list = accountMenuGroupService.list(searchEntity);
		mv.addObject("list", list);
		return mv;
	}

	@RequestMapping(value = "/paginationEntity")
	public ModelAndView paginationEntity(AccountMenuGroup searchEntity, Pagination<AccountMenuGroup> pagination) {
		ModelAndView mv = new ModelAndView("wxcms/accountMenuGroupPagination");
		pagination = accountMenuGroupService.paginationEntity(searchEntity, pagination);
		mv.addObject("pagination", pagination);
		mv.addObject("searchEntity", searchEntity);
		mv.addObject("cur_nav", "menu");
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(AccountMenuGroup entity) {
		ModelAndView modelAndView = new ModelAndView("wxcms/menuList");
		if (entity.getId() != null) {
			entity = accountMenuGroupService.getById(entity.getId().toString());
		}
		modelAndView.addObject("groupEntity", entity);
		List<AccountMenu> list = accountMenuService.listForPage(new AccountMenu());
		modelAndView.addObject("pageList", list);
		modelAndView.addObject("cur_nav", "menu");
		return modelAndView;
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(AccountMenuGroup entity) {
		if (entity.getId() == null) {
			entity.setEnable(0);
			accountMenuGroupService.add(entity);
		} else {
			accountMenuGroupService.update(entity);
		}
		return new ModelAndView("redirect:/accountmenu/list.html?gid=" + entity.getId());
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(AccountMenuGroup entity) {
		accountMenuGroupService.delete(entity);
		return new ModelAndView("redirect:/wxcms/accountMenuGroup/paginationEntity.html");
	}

}
