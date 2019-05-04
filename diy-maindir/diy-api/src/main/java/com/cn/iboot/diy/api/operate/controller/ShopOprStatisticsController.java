package com.cn.iboot.diy.api.operate.controller;

import java.text.DecimalFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.operate.domain.PositOprStatistics;
import com.cn.iboot.diy.api.operate.service.PositOprStatisticsService;
import com.cn.iboot.diy.api.operate.service.ShopOprStatisticsService;
import com.cn.iboot.diy.api.system.domain.User;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/operate/shop")
public class ShopOprStatisticsController {
	
	
	private Logger logger = LoggerFactory.getLogger(PositOprStatisticsController.class);

	@Autowired
	PositOprStatisticsService positOprStatisticsService;

	@Autowired
	ShopOprStatisticsService shopOprStatisticsService;

	
	/**
	 * 运营数据设置 门店数据
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping("/listShopStatisticsSet")
	public ModelAndView listShopStatisticsSet(HttpServletRequest req, HttpServletResponse resp, PositOprStatistics pos,
			Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateData");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		// String shopCode = user.getShopCode();
		String startDate = StringUtil.nullToString(req.getParameter("startDate"));
		String endDate = StringUtil.nullToString(req.getParameter("endDate"));
		pos.setStartDate(startDate.replace("-", ""));
		pos.setEndDate(endDate.replace("-", ""));
		try {
			pageList = positOprStatisticsService.getShopStatisticsMonthSetPage(startNum, pageSize, pos);
		} catch (Exception e) {
			logger.error("运营门店数据列表信息出错-->[{}]", e);
		}
		pos.setShopName(getShopName(user));
		mv.addObject("titleName", getMchntName(user) + "(" + pos.getShopName() + ")门店数据");
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);

		return mv;
	}

	/**
	 * 运营数据设置 门店数据月编辑
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping("/monthEdit")
	public ModelAndView monthEdit(HttpServletRequest req, HttpServletResponse resp, PositOprStatistics pos,
			Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateMonthEdit");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		// String shopCode = user.getShopCode();
		pos.setShopCode(user.getShopCode());
		String startDate = StringUtil.nullToString(req.getParameter("settleDate"));
		String endDate = StringUtil.nullToString(req.getParameter("settleDate"));
		pos.setStartDate(startDate.substring(0, 4) + startDate.substring(5, 7));
		pos.setEndDate(endDate.substring(0, 4) + endDate.substring(5, 7));
		try {
			pageList = positOprStatisticsService.getShopStatisticsDaySetPage(startNum, pageSize, pos);
			Double totalPayAmt = pageList.getList().stream().map(mapper ->Double.parseDouble(mapper.getuPayAmt())).reduce(0.00,
					(x, y) -> x + y);
			pos.setPayAmt(new DecimalFormat("0.00").format(totalPayAmt));
		} catch (Exception e) {
			logger.error("运营门店数据月编辑信息出错-->[{}]", e);
		}
		pos.setSettleDate(startDate.substring(0,8));
		pos.setShopStatisticsOprId(req.getParameter("shopId"));
		pos.setShopName(getShopName(user));
		mv.addObject("titleName", getMchntName(user) + "(" + pos.getShopName() + ")门店数据");
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		mv.addObject("statSelect", Constants.DIYStat.values());
		return mv;
	}

	/**
	 * 运营数据设置 门店数据月详情
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping("/monthView")
	public ModelAndView monthView(HttpServletRequest req, HttpServletResponse resp, PositOprStatistics pos,
			Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateMonthDetail");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		pos.setShopCode(user.getShopCode());
		String startDate = StringUtil.nullToString(req.getParameter("settleDate"));
		String endDate = StringUtil.nullToString(req.getParameter("settleDate"));
		pos.setStartDate(startDate.substring(0, 4) + startDate.substring(5, 7));
		pos.setEndDate(endDate.substring(0, 4) + endDate.substring(5, 7));
		try {
			pageList = positOprStatisticsService.getShopStatisticsDaySetPage(startNum, pageSize, pos);
			pos.setSettleDate(StringUtil.nullToString(req.getParameter("settleDate")));
			pos.setuPayAmt(new DecimalFormat("0.00").format(NumberUtils.parseDouble(req.getParameter("uPayAmt"))));
		} catch (Exception e) {
			logger.error("运营门店数据月详情信息出错-->[{}]", e);
		}
		pos.setShopStatisticsOprId(req.getParameter("shopId"));
		pos.setShopName(getShopName(user));
		mv.addObject("titleName", getMchntName(user) + "(" + pos.getShopName() + ")门店数据");
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		return mv;
	}

	/**
	 * 运营数据设置 门店数据日编辑
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping("/dayEdit")
	public ModelAndView dayEdit(HttpServletRequest req, HttpServletResponse resp, PositOprStatistics pos,
			Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateDayEdit");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		String date = StringUtil.nullToString(req.getParameter("settleDate"));
		String dateStr = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		pos.setSettleDate(dateStr);
		pos.setPayAmt(new DecimalFormat("0.00").format(NumberUtils.formatMoney(req.getParameter("payAmt"))));
		// pos.setShopCode(StringUtil.nullToString(shopCode));
		pos.setShopCode(user.getShopCode());
		try {
			pageList = positOprStatisticsService.getPositStatisticsDaySetPage(startNum, pageSize, pos);
		} catch (Exception e) {
			logger.error("运营门店数据日编辑信息出错-->[{}]", e);
		}
		pos.setSettleDate(date);
		pos.setStat(req.getParameter("stat"));
		pos.setShopName(getShopName(user));
		mv.addObject("titleName", getMchntName(user) + "(" + pos.getShopName() + ")门店数据");
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);

		return mv;
	}

	/**
	 * 运营数据设置 门店数据日详情
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping("/dayView")
	public ModelAndView dayView(HttpServletRequest req, HttpServletResponse resp, PositOprStatistics pos,
			Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateDayDetail");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		String shopCode = user.getShopCode();
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		String date = StringUtil.nullToString(req.getParameter("settleDate"));
		String dateStr = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		pos.setSettleDate(dateStr);
		pos.setShopCode(StringUtil.nullToString(shopCode));
		try {
			pageList = positOprStatisticsService.getPositStatisticsDaySetPage(startNum, pageSize, pos);
		} catch (Exception e) {
			logger.error("运营门店数据日详情信息出错-->[{}]", e);
		}

		pos.setSettleDate(date.substring(0, 8));
		pos.setStat(req.getParameter("stat"));
		pos.setShopName(getShopName(user));
		mv.addObject("titleName", getMchntName(user) + "(" + pos.getShopName() + ")门店数据");
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		return mv;
	}

	/**
	 * 查询门店名称
	 * 
	 * @param user
	 * @return
	 */
	private String getShopName(User user) {
		String shopName = null;
		try {
			shopName = positOprStatisticsService.getShopInfByShopCode(user.getShopCode());
		} catch (Exception e) {
			logger.error("查询门店名称出错-->[{}]", e);
		}
		return shopName;
	}

	/**
	 * 查询商户名称
	 * 
	 * @param user
	 * @return
	 */
	private String getMchntName(User user) {
		String mchntName = null;
		try {
			mchntName = positOprStatisticsService.getMchntInfByMchntCode(user.getMchntCode());
		} catch (Exception e) {
			logger.error("查询商户名称出错-->[{}]", e);
		}
		return mchntName;
	}

}
