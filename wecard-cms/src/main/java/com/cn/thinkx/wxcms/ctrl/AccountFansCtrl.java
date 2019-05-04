package com.cn.thinkx.wxcms.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.core.page.Pagination;
import com.cn.thinkx.wxcms.domain.AccountFans;
import com.cn.thinkx.wxcms.service.AccountFansService;

@Controller
@RequestMapping("/accountfans")
public class AccountFansCtrl {

	@Autowired
	@Qualifier("accountFansService")
	private AccountFansService accountFansService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id) {
		accountFansService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list(AccountFans searchEntity) {
		ModelAndView mv = new ModelAndView("wxcms/paginationEntity");
		return mv;
	}

	@RequestMapping(value = "/paginationEntity")
	public ModelAndView paginationEntity(AccountFans searchEntity, Pagination<AccountFans> pagination) {
		ModelAndView mv = new ModelAndView("wxcms/fansPagination");
		pagination = accountFansService.paginationEntity(searchEntity, pagination);
		mv.addObject("pagination", pagination);
		mv.addObject("cur_nav", "fans");
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(AccountFans entity) {

		return new ModelAndView();
	}

	@RequestMapping(value = "/merge")
	public ModelAndView doMerge(AccountFans entity) {
		accountFansService.add(entity);
		return new ModelAndView();
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(AccountFans entity) {
		accountFansService.delete(entity);
		return new ModelAndView();
	}

}