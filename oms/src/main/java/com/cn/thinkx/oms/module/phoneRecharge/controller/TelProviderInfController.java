package com.cn.thinkx.oms.module.phoneRecharge.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderInfFacade;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "provider/providerInf")
public class TelProviderInfController extends BaseController {

	@Autowired
	private TelProviderInfFacade telProviderInfFacade;

	/**
	 * 供应商列表 信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/listTelProviderInf")
	public ModelAndView listTelProviderInf(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telProviderInf/listTelProviderInf");
		String operStatus = StringUtils.nullToString(request.getParameter("operStatus"));
		int startNum = parseInt(request.getParameter("pageNum"), 1);
		int pageSize = parseInt(request.getParameter("pageSize"), 10);
		try {
			TelProviderInf telProviderInf = this.getTelProviderInf(request);
			PageInfo<TelProviderInf> pageList = telProviderInfFacade.getTelProviderInfPage(startNum, pageSize,
					telProviderInf);
			mv.addObject("pageInfo", pageList);
			mv.addObject("telProviderInf", telProviderInf);
		} catch (Exception e) {
			logger.error("## 供应商信息列表查询异常", e);
		}
		mv.addObject("operStatus", operStatus);
		return mv;
	}

	/**
	 * 进入添加供应商信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddTelProviderInf")
	public ModelAndView intoAddTelProviderInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telProviderInf/addTelProviderInf");
		TelProviderInf telProviderInf = new TelProviderInf();
		telProviderInf.setDefaultRoute(BaseConstants.providerDefaultRoute.DefaultRoute0.getCode());
		try {
			List<TelProviderInf> list = telProviderInfFacade.getTelProviderInfList(telProviderInf);
			if(list.size() == 0){
				mv.addObject("defaultRouteState", "0");
			}else{
				mv.addObject("defaultRouteState", "1");
			}
		} catch (Exception e) {
			logger.error("## 进入添加供应商信息异常", e);
		}
		mv.addObject("defaultRouteList", BaseConstants.providerDefaultRoute.values());
		return mv;
	}

	/**
	 * 添加供应商信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addTelProviderInfCommit")
	@ResponseBody
	public ModelMap addTelProviderInfCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			TelProviderInf telProviderInf = this.getTelProviderInf(req);
			telProviderInf.setDataStat("0");
			User user = this.getCurrUser(req);
			if (user != null) {
				telProviderInf.setCreateUser(user.getId().toString());
				telProviderInf.setUpdateUser(user.getId().toString());
			}
			telProviderInfFacade.saveTelProviderInf(telProviderInf);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "新增失败，请重新添加");
			logger.error("## 添加供应商信息异常", e);
		}
		return resultMap;
	}

	/**
	 * 进入编辑供应商信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoEditTelProviderInf")
	public ModelAndView intoEditTelProviderInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telProviderInf/editTelProviderInf");
		String providerId = StringUtils.nullToString(req.getParameter("providerId"));
		try {
			TelProviderInf telProviderInf = telProviderInfFacade.getTelProviderInfById(providerId);
			mv.addObject("telProviderInf", telProviderInf);
		} catch (Exception e) {
			logger.error("## 通过id查找供应商信息异常", e);
		}
		mv.addObject("defaultRouteList", BaseConstants.providerDefaultRoute.values());
		return mv;
	}

	/**
	 * 编辑供应商信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editTelProviderInfCommit")
	@ResponseBody
	public ModelMap editTelProviderInfCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String providerId = StringUtils.nullToString(req.getParameter("providerId"));
		try {
			if (StringUtils.isNullOrEmpty(providerId)) {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "编辑失败,供应商id为空");
				logger.error("## 编辑供应商信息异常,供应商providerId:[{}]为空", providerId);
			}
//			TelProviderInf tpInf = telProviderInfFacade.getTelProviderInfById(providerId);
			TelProviderInf telProviderInf = this.getTelProviderInf(req);
			telProviderInf.setProviderId(providerId);
//			tpInf.setProviderId(providerId);
			User user = this.getCurrUser(req);
			if (user != null) {
				telProviderInf.setUpdateUser(user.getId().toString());
			}
//			tpInf.setProviderName(telProviderInf.getProviderName());
//			tpInf.setAppUrl(telProviderInf.getAppUrl());
//			tpInf.setAppSecret(telProviderInf.getAppSecret());
//			tpInf.setAccessToken(telProviderInf.getAccessToken());
//			tpInf.setDefaultRoute(telProviderInf.getDefaultRoute());
//			tpInf.setProviderRate(telProviderInf.getProviderRate());
//			tpInf.setOperSolr(telProviderInf.getOperSolr());
//			tpInf.setRemarks(telProviderInf.getRemarks());
			telProviderInfFacade.updateTelProviderInf(telProviderInf);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "编辑失败，请联系管理员");
			logger.error("## 编辑供应商信息异常", e);
		}
		return resultMap;
	}

	/**
	 * 供应商信息详情
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "intoViewTelProviderInf")
	public ModelAndView intoViewTelProviderInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telProviderInf/viewTelProviderInf");
		String providerId = StringUtils.nullToString(req.getParameter("providerId"));
		try {
			TelProviderInf telProviderInf = telProviderInfFacade.getTelProviderInfById(providerId);
			mv.addObject("telProviderInf", telProviderInf);
		} catch (Exception e) {
			logger.error("## 查询供应商信息详情异常", e);
		}
		mv.addObject("defaultRouteList", BaseConstants.providerDefaultRoute.values());
		return mv;
	}

	/**
	 * 删除供应商信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deleteTelProviderInfCommit")
	@ResponseBody
	public ModelMap deleteTelProviderInfCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String providerId = StringUtils.nullToString(req.getParameter("providerId"));
		try {
			if (StringUtils.isNullOrEmpty(providerId)) {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "删除失败,供应商id为空");
				logger.error("## 删除供应商信息异常,供应商providerId:[{}]为空", providerId);
			}
			telProviderInfFacade.deleteTelProviderInfById(providerId);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "删除失败，请联系管理员");
			logger.error("## 删除供应商信息异常", e);
		}
		return resultMap;
	}

	/**
	 * 封装供应商实体
	 */
	public TelProviderInf getTelProviderInf(HttpServletRequest req) {
		TelProviderInf telProviderInf = new TelProviderInf();
		telProviderInf.setProviderName(StringUtils.nullToString(req.getParameter("providerName")));
		telProviderInf.setAppUrl(StringUtils.nullToString(req.getParameter("appUrl")));
		telProviderInf.setAppSecret(StringUtils.nullToString(req.getParameter("appSecret")));
		telProviderInf.setAccessToken(StringUtils.nullToString(req.getParameter("accessToken")));
		telProviderInf.setDefaultRoute(StringUtils.nullToString(req.getParameter("defaultRoute")));
		telProviderInf.setProviderRate(StringUtils.nullToString(req.getParameter("providerRate")));
		if (!StringUtils.isNullOrEmpty(req.getParameter("operSolr")))
			telProviderInf.setOperSolr(Integer.valueOf(req.getParameter("operSolr")));
		telProviderInf.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
		return telProviderInf;
	}
}
