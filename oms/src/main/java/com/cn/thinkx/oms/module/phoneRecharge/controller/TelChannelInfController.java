package com.cn.thinkx.oms.module.phoneRecharge.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.phoneRecharge.model.TelChannelProductCheck;
import com.cn.thinkx.oms.module.phoneRecharge.service.TelChannelInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelItemList;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelItemListFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelProductInfFacade;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "channel/channelInf")
public class TelChannelInfController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TelChannelInfFacade telChannelInfFacade;

	@Autowired
	private TelChannelProductInfFacade telChannelProductInfFacade;

	@Autowired
	private TelChannelInfService telChannelInfService;
	
	@Autowired
	private TelChannelItemListFacade telChannelItemListFacade;

	/**
	 * 分销商信息列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/listTelChannelInf")
	public ModelAndView listTelChannelInf(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelInf/listTelChannelInf");
		String operStatus = StringUtils.nullToString(request.getParameter("operStatus"));
		int startNum = parseInt(request.getParameter("pageNum"), 1);
		int pageSize = parseInt(request.getParameter("pageSize"), 10);

		String channelName = StringUtils.nullToString(request.getParameter("channelName"));
		TelChannelInf telChannelInf = new TelChannelInf();
		telChannelInf.setChannelName(channelName);
		PageInfo<TelChannelInf> pageList = null;
		try {
			pageList = telChannelInfFacade.getTelChannelInfPage(startNum, pageSize, telChannelInf);
		} catch (Exception e) {
			logger.error(" ## 查询分销商信息列表出错",e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("telChannelInf", telChannelInf);
		return mv;
	}

	@RequestMapping(value = "/intoAddTelChannelInf")
	public ModelAndView intoAddTelChannelInf(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelInf/addTelChannelInf");

		return mv;
	}

	@RequestMapping(value = "/addTelChannelInfCommit")
	public ModelAndView addTelChannelInfCommit(HttpServletRequest req, HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("redirect:/channel/channelInf/listTelChannelInf.do");

		User user = getCurrUser(req);

		try {
			TelChannelInf telChannelInf = getTelChannelInf(req, user);
			int i = telChannelInfFacade.saveTelChannelInf(telChannelInf);
			if (i == 1) {
				mv.addObject("operStatus", 1);
			}
		} catch (Exception e) {
			logger.error(" ## 添加分销商信息出错",e);
		}
		return mv;
	}

	@RequestMapping(value = "/intoEditTelChannelInf")
	public ModelAndView intoEditTelChannelInf(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelInf/editTelChannelInf");

		String channelId = StringUtils.nullToString(req.getParameter("channelId"));

		TelChannelInf telChannelInf = null;
		try {
			telChannelInf = telChannelInfFacade.getTelChannelInfById(channelId);
		} catch (Exception e) {
			logger.error(" ## 跳转分销商信息编辑页面出错",e);
		}
		mv.addObject("telChannelInf", telChannelInf);
		return mv;
	}

	@RequestMapping(value = "/editTelChannelInfCommit")
	public ModelAndView editTelChannelInfCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("redirect:/channel/channelInf/listTelChannelInf.do");

		User user = getCurrUser(req);

		try {
			TelChannelInf telChannelInf = getTelChannelInf(req, user);
			int i = telChannelInfFacade.updateTelChannelInf(telChannelInf);
			if (i == 1) {
				mv.addObject("operStatus", 2);
			}
		} catch (Exception e) {
			logger.error(" ## 编辑分销商信息列表出错",e);
		}

		return mv;
	}

	@RequestMapping(value = "/intoViewTelChannelInf")
	public ModelAndView intoViewTelChannelInf(HttpServletRequest req, HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelInf/viewTelChannelInf");
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));

		TelChannelInf telChannelInf = null;
		try {
			telChannelInf = telChannelInfFacade.getTelChannelInfById(channelId);
		} catch (Exception e) {
			logger.error(" ## 查看分销商信息详情出错",e);
		}
		mv.addObject("telChannelInf", telChannelInf);
		return mv;
	}

	@RequestMapping(value = "/deleteTelChannelInfCommit")
	@ResponseBody
	public ModelMap deleteTelChannelInfCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));

		try {
			int i = telChannelInfFacade.deleteTelChannelInfById(channelId);
			if (i != 1) {
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "删除商品失败，请重新操作");
			}
		} catch (Exception e) {
			logger.error(" ## 删除手机充值商品出错 ", e);
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除商品失败，请重新操作");
		}
		return resultMap;
	}

	/**
	 * 进入分销商添加折扣率
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping(value = "/intoAddTelChannelRate")
	public ModelAndView intoAddTelChannelRate(HttpServletRequest req, HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelInf/listTelChannelProductRate");

		String channelId = StringUtils.nullToString(req.getParameter("channelId"));

		if (StringUtils.isNullOrEmpty(channelId)) {
			logger.error("## 添加分销商信息出错,分销商主键channelId：[{}]是空", channelId);
			return mv;
		}
		List<TelChannelProductInf> listAll = null;
		TelChannelInf telChannelInf = null;
		List<TelChannelProductInf> channelProductist = null;

		TelChannelProductCheck check = null;
		List<TelChannelProductCheck> channelProductistCheck = new ArrayList<TelChannelProductCheck>();

		TelChannelProductInf telProduct = new TelChannelProductInf();
		try {
			// 查询分销商信息
			telChannelInf = telChannelInfFacade.getTelChannelInfById(channelId);

			// 全部的手机充值产品
			telProduct.setOperId(StringUtils.nullToString(req.getParameter("operId")));
			listAll = telChannelProductInfFacade.getTelChannelProductInfList(telProduct);
			// 当前分销商有手机充值产品
			channelProductist = telChannelProductInfFacade.getChannelProductListByChannelId(channelId);
			if (!StringUtil.isNullOrEmpty(channelProductist) && listAll.size() > 0) {
				for (TelChannelProductInf channelProductAll : listAll) {
					check = new TelChannelProductCheck();
					check.setProductId(channelProductAll.getProductId());
					check.setChannelRate(channelProductAll.getChannelRate());
					check.setOperId(BaseConstants.OperatorType.findByCode(channelProductAll.getOperId()));
					check.setProductType(BaseConstants.ChannelProductProType.findByCode(channelProductAll.getProductType()));
					check.setOperName(channelProductAll.getOperName());
					check.setProductAmt(channelProductAll.getProductAmt());
					check.setProductPrice(channelProductAll.getProductPrice());
					check.setCreateTime(channelProductAll.getCreateTime());
					check.setUpdateTime(channelProductAll.getUpdateTime());
					check.setAreaFlag(BaseConstants.ChannelProductAreaFlag.findByCode(channelProductAll.getAreaFlag()));
					check.setChannelRate(channelProductAll.getChannelRate());
					for (TelChannelProductInf telChannelProduct : channelProductist) {
						if (channelProductAll.getProductId().equals(telChannelProduct.getProductId())) {
							check.setChecked(true);
							check.setChannelRate(telChannelProduct.getChannelRate());
							check.setId(telChannelProduct.getId());
							break;
						}else{
							check.setChannelRate(new BigDecimal("1"));
						}
					}
					channelProductistCheck.add(check);
				}
			}
		} catch (Exception e) {
			logger.error(" ## 添加分销商信息出错", e);
		}
		mv.addObject("operIdList", BaseConstants.OperatorType.values());
		mv.addObject("channelProductistCheck", channelProductistCheck);
		mv.addObject("telChannelInf", telChannelInf);
		mv.addObject("telCPInf", telProduct);
		return mv;
	}

	/**
	 * 添加分销商添加折扣率
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addTelChannelRateCommit")
	@ResponseBody
	public ModelMap addTelChannelRateCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		String channelRate = StringUtils.nullToString(req.getParameter("channelRate"));
		String ids = req.getParameter("ids");
		if (StringUtils.isNullOrEmpty(ids)) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "添加分销商折扣率失败,没有选中的数据");
			return resultMap;
		}
		if (StringUtils.isNullOrEmpty(channelId)) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "添加分销商折扣率失败,分销商是空");
			return resultMap;
		}
		if (StringUtils.isNullOrEmpty(channelRate)) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "添加分销商折扣率失败,分销商折扣率是空");
			return resultMap;
		}
		if (!telChannelInfService.addTelChannelRate(req, channelId, channelRate, ids)) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "添加分销商折扣率失败,请联系管理员");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 进入分销商折扣率编辑页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoEditTelChannelProductRate")
	public ModelAndView intoEditTelChannelProductRate(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelInf/editTelChannelProductRate");
		String id = StringUtils.nullToString(req.getParameter("id"));
		try {
			if(StringUtils.isNullOrEmpty(id)){
				return mv;
			}
			TelChannelProductInf telProductInf = telChannelProductInfFacade.getChannelProductByItemId(id);
			if(!StringUtil.isNullOrEmpty(telProductInf)){
				if(!StringUtils.isNullOrEmpty(telProductInf.getAreaFlag()))
					telProductInf.setAreaFlag(BaseConstants.ChannelProductAreaFlag.findByCode(telProductInf.getAreaFlag()));
				if(!StringUtils.isNullOrEmpty(telProductInf.getOperId()))
					telProductInf.setOperId(BaseConstants.OperatorType.findByCode(telProductInf.getOperId()));
				if(!StringUtils.isNullOrEmpty(telProductInf.getProductType()))
					telProductInf.setProductType(BaseConstants.ChannelProductProType.findByCode(telProductInf.getProductType()));
			}
			mv.addObject("telProductInf", telProductInf);
		} catch (Exception e) {
			logger.error("## 通过id查找分销商产品信息异常", e);
		}
		mv.addObject("areaFlagList", BaseConstants.ChannelProductAreaFlag.values());
		mv.addObject("productTypeList", BaseConstants.ChannelProductProType.values());
		mv.addObject("operIdList", BaseConstants.OperatorType.values());
		return mv;
	}
	
	/**
	 * 分销商折扣率编辑提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editTelChannelProductRateCommit")
	@ResponseBody
	public ModelMap editTelChannelProductRateCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String itemId = StringUtils.nullToString(req.getParameter("itemId"));
		try {
			if (StringUtils.isNullOrEmpty(itemId)) {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "编辑失败,分销商产品折扣率id为空");
				logger.error("## 编辑分销商产品信息折扣率异常,分销商产品折扣率id:[{}]为空", itemId);
			}
			TelChannelItemList telChannelItemList = telChannelItemListFacade.getTelChannelItemListById(itemId);
			User user = this.getCurrUser(req);
			if (user != null) {
				telChannelItemList.setUpdateUser(user.getId().toString());
			}
			if(!StringUtils.isNullOrEmpty(req.getParameter("channelRate")))
				telChannelItemList.setChannelRate(new BigDecimal(req.getParameter("channelRate")));
			telChannelItemListFacade.updateTelChannelItemList(telChannelItemList);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "编辑失败，请联系管理员");
			logger.error("## 编辑分销商产品信息异常", e);
		}
		return resultMap;
	}
	
	private TelChannelInf getTelChannelInf(HttpServletRequest req, User user) throws Exception {
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		String channelName = StringUtils.nullToString(req.getParameter("channelName"));
		String channelCode = StringUtils.nullToString(req.getParameter("channelCode"));
		String channelKey = StringUtils.nullToString(req.getParameter("channelKey"));
		String channelReserveAmt = StringUtils.nullToString(req.getParameter("channelReserveAmt"));
		String channelPrewarningAmt = StringUtils.nullToString(req.getParameter("channelPrewarningAmt"));
		String phoneNo = StringUtils.nullToString(req.getParameter("phoneNo"));
		String email = StringUtils.nullToString(req.getParameter("email"));
		String remarks = StringUtils.nullToString(req.getParameter("remarks"));
		String lockVersion = StringUtils.nullToString(req.getParameter("lockVersion"));

		TelChannelInf telChannelInf = new TelChannelInf();

		telChannelInf.setChannelId(channelId);
		telChannelInf.setChannelName(channelName);
		telChannelInf.setChannelCode(channelCode);
		telChannelInf.setChannelKey(channelKey);
		telChannelInf.setChannelReserveAmt(new BigDecimal(channelReserveAmt));
		telChannelInf.setChannelPrewarningAmt(new BigDecimal(channelPrewarningAmt));
		telChannelInf.setPhoneNo(phoneNo);
		telChannelInf.setEmail(email);
		telChannelInf.setRemarks(remarks);
		if (!StringUtil.isNullOrEmpty(lockVersion)) {
			telChannelInf.setLockVersion(Integer.valueOf(lockVersion));
		}

		if (user != null) {
			telChannelInf.setCreateUser(user.getId().toString());
			telChannelInf.setUpdateUser(user.getId().toString());
		}

		return telChannelInf;
	}

}
