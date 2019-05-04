package com.cn.thinkx.oms.module.phoneRecharge.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.phoneRecharge.service.ChannelProductService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelProductInfFacade;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "channel/product")
public class TelChannelProductController extends BaseController {

	@Autowired
	private TelChannelProductInfFacade telChannelProductInfFacade;

	@Autowired
	private ChannelProductService channelProductService;

	/**
	 * 分销商产品列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listTelChannelProduct")
	public ModelAndView listTelChannelProduct(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelProduct/listTelChannelProduct");
		String operStatus = StringUtils.nullToString(request.getParameter("operStatus"));
		int startNum = parseInt(request.getParameter("pageNum"), 1);
		int pageSize = parseInt(request.getParameter("pageSize"), 10);

		TelChannelProductInf product = this.getTelChannelProductInf(request);
		try {
			PageInfo<TelChannelProductInf> pageList = telChannelProductInfFacade.getTelChannelProductInfPage(startNum,
					pageSize, product);
			mv.addObject("pageInfo", pageList);
		} catch (Exception e) {
			logger.error("## 分销商产品信息列表查询异常", e);
		}
		mv.addObject("telCPInf", product);
		mv.addObject("areaFlagList", BaseConstants.ChannelProductAreaFlag.values());
		mv.addObject("productTypeList", BaseConstants.ChannelProductProType.values());
		mv.addObject("operIdList", BaseConstants.OperatorType.values());
		mv.addObject("operStatus", operStatus);
		return mv;
	}

	/**
	 * 进入添加分销商产品信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddTelChannelProduct")
	public ModelAndView intoAddTelChannelProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelProduct/addTelChannelProduct");
		mv.addObject("productTypeList", BaseConstants.ChannelProductProType.values());
		mv.addObject("areaFlagList", BaseConstants.ChannelProductAreaFlag.values());
		mv.addObject("operIdList", BaseConstants.OperatorType.values());
		return mv;
	}

	/**
	 * 添加分销商产品信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addTelChannelProductCommit")
	@ResponseBody
	public ModelMap addTelChannelProductCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			TelChannelProductInf telChannelProductInf = this.getTelChannelProductInf(req);
			telChannelProductInf.setDataStat("0");
			User user = this.getCurrUser(req);
			if (user != null) {
				telChannelProductInf.setCreateUser(user.getId().toString());
				telChannelProductInf.setUpdateUser(user.getId().toString());
			}
			telChannelProductInfFacade.saveTelChannelProductForId(telChannelProductInf);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "新增失败，请重新添加");
			logger.error("## 添加分销商产品信息异常", e);
		}
		return resultMap;
	}

	/**
	 * 进入编辑分销商产品信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoEditTelChannelProduct")
	public ModelAndView intoTelChannelProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelProduct/editTelChannelProduct");
		String productId = StringUtils.nullToString(req.getParameter("productId"));
		try {
			TelChannelProductInf telChannelProductInf = telChannelProductInfFacade
					.getTelChannelProductInfById(productId);
			mv.addObject("telCPInf", telChannelProductInf);
		} catch (Exception e) {
			logger.error("## 通过id查找分销商产品信息异常", e);
		}
		mv.addObject("areaFlagList", BaseConstants.ChannelProductAreaFlag.values());
		mv.addObject("productTypeList", BaseConstants.ChannelProductProType.values());
		mv.addObject("operIdList", BaseConstants.OperatorType.values());
		return mv;
	}

	/**
	 * 编辑分销商产品信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/editTelChannelProductCommit")
	@ResponseBody
	public ModelMap editTelChannelProductCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String productId = StringUtils.nullToString(req.getParameter("productId"));
		try {
			if (StringUtils.isNullOrEmpty(productId)) {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "编辑失败,分销商产品id为空");
				logger.error("## 编辑分销商产品信息异常,分销商产品productId:[{}]为空", productId);
			}
			TelChannelProductInf telCPInf = telChannelProductInfFacade.getTelChannelProductInfById(productId);
			TelChannelProductInf telChannelProductInf = this.getTelChannelProductInf(req);
			User user = this.getCurrUser(req);
			if (user != null) {
				telCPInf.setUpdateUser(user.getId().toString());
			}
			telCPInf.setOperName(telChannelProductInf.getOperName());
			telCPInf.setOperId(telChannelProductInf.getOperId());
			telCPInf.setAreaFlag(telChannelProductInf.getAreaFlag());
			telCPInf.setProductAmt(telChannelProductInf.getProductAmt());
			telCPInf.setProductPrice(telChannelProductInf.getProductPrice());
			telCPInf.setProductType(telChannelProductInf.getProductType());
			telCPInf.setRemarks(telChannelProductInf.getRemarks());
			telChannelProductInfFacade.updateTelChannelProductInf(telCPInf);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "编辑失败，请联系管理员");
			logger.error("## 编辑分销商产品信息异常", e);
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
	@RequestMapping(value = "intoViewTelChannelProduct")
	public ModelAndView intoViewTelChannelProduct(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelProduct/viewTelChannelProduct");
		String productId = StringUtils.nullToString(req.getParameter("productId"));
		try {
			if (!StringUtils.isNullOrEmpty(productId)) {
				TelChannelProductInf telCPInf = telChannelProductInfFacade.getTelChannelProductInfById(productId);
				mv.addObject("telCPInf", telCPInf);
			}
		} catch (Exception e) {
			logger.error("## 查询分销商产品信息详情异常", e);
		}
		mv.addObject("areaFlagList", BaseConstants.ChannelProductAreaFlag.values());
		mv.addObject("productTypeList", BaseConstants.ChannelProductProType.values());
		mv.addObject("operIdList", BaseConstants.OperatorType.values());
		return mv;
	}

	/**
	 * 删除分销商产品信息
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/deleteTelChannelProductCommit")
	@ResponseBody
	public ModelMap deleteTelChannelProductCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String productId = StringUtils.nullToString(req.getParameter("productId"));
		try {
			if (StringUtils.isNullOrEmpty(productId)) {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "删除失败,分销商产品id为空");
				logger.error("## 删除分销商产品信息异常,供应商productId:[{}]为空", productId);
			}
			channelProductService.deleteTelChannelProductInf(productId);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "删除失败，请联系管理员");
			logger.error("## 删除分销商产品信息异常", e);
		}
		return resultMap;
	}
	
	public TelChannelProductInf getTelChannelProductInf(HttpServletRequest req) {
		String productAmt = StringUtils.nullToString(req.getParameter("productAmt"));
		String productPrice = StringUtils.nullToString(req.getParameter("productPrice"));
		TelChannelProductInf product = new TelChannelProductInf();
		product.setOperName(StringUtils.nullToString(req.getParameter("operName")));
		product.setOperId(StringUtils.nullToString(req.getParameter("operId")));
		product.setAreaFlag(StringUtils.nullToString(req.getParameter("areaFlag")));
		if (!StringUtils.isNullOrEmpty(productAmt))
			product.setProductAmt(new BigDecimal(productAmt));
		if (!StringUtils.isNullOrEmpty(productPrice))
			product.setProductPrice(new BigDecimal(productPrice));
		product.setProductType(StringUtils.nullToString(req.getParameter("productType")));
		product.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
		return product;
	}
}
