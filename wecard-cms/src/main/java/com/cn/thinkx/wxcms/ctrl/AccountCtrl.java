package com.cn.thinkx.wxcms.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.wxcms.domain.Account;
import com.cn.thinkx.wxcms.service.AccountService;

@Controller
@RequestMapping("/account")
public class AccountCtrl {

	@Autowired
	@Qualifier("accountService")
	private AccountService accountService;

	@RequestMapping(value = "/getById")
	public ModelAndView getById(String id) {
		accountService.getById(id);
		return new ModelAndView();
	}

	@RequestMapping(value = "/listForPage")
	public ModelAndView listForPage(@ModelAttribute Account searchEntity) {
		ModelAndView mv = new ModelAndView();
		List<Account> list = accountService.listForPage(searchEntity);
		mv.addObject("list", list);
		return mv;
	}

	@RequestMapping(value = "/toMerge")
	public ModelAndView toMerge(Account entity) {

		return new ModelAndView();
	}

	@RequestMapping(value = "/doMerge")
	public ModelAndView doMerge(Account entity) {
		accountService.add(entity);
		return new ModelAndView();
	}

	@RequestMapping(value = "/delete")
	public ModelAndView delete(Account entity) {
		accountService.delete(entity);
		return new ModelAndView();
	}

}