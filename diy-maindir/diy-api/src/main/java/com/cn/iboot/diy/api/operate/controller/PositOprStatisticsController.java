package com.cn.iboot.diy.api.operate.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.ExcelUtils;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.base.utils.UploadUtil;
import com.cn.iboot.diy.api.operate.domain.PositOprStatistics;
import com.cn.iboot.diy.api.operate.domain.PositOprUpdate;
import com.cn.iboot.diy.api.operate.domain.ShopOprStatistics;
import com.cn.iboot.diy.api.operate.service.PositOprStatisticsService;
import com.cn.iboot.diy.api.operate.service.ShopOprStatisticsService;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.system.service.UserService;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/operate/posit")
public class PositOprStatisticsController {

	private Logger logger = LoggerFactory.getLogger(PositOprStatisticsController.class);

	@Autowired
	PositOprStatisticsService positOprStatisticsService;

	@Autowired
	ShopOprStatisticsService shopOprStatisticsService;

	@Autowired
	private UserService userService;
	

	/**
	 * 运营数据查询
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/listOprStatistics", method = RequestMethod.GET)
	public ModelAndView listPositOprStatistics(HttpServletRequest req, PositOprStatistics pos) {
		ModelAndView mv = new ModelAndView("operate/hkbOperateDataQuery");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		String totalPayAmt = "0.00";
		String startDate = StringUtil.nullToString(req.getParameter("startDate"));
		String endDate = StringUtil.nullToString(req.getParameter("endDate"));
		pos.setStartDate(startDate.replace("-", ""));
		pos.setEndDate(endDate.replace("-", ""));
		pos.setShopCode(user.getShopCode());
		try {
			pageList = positOprStatisticsService.getPositOprStatisticsPage(startNum, pageSize, pos);
			if (pageList.getList() != null&&pageList.getList().size()>0)
				totalPayAmt = positOprStatisticsService.getPositOprTotalPay(pos);
		} catch (Exception e) {
			logger.error("查询运营数据列表信息出错-->[{}]", e);

		}
		pos.setStartDate(startDate);
		pos.setEndDate(endDate);
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		mv.addObject("totalPayAmt", totalPayAmt);
		return mv;
	}

	/**
	 * 运营数据查询
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/listOprStatistics", method = RequestMethod.POST)
	public ModelAndView listPositOprStatistics(HttpServletRequest req, HttpServletResponse resp,
			PositOprStatistics pos) {
		ModelAndView mv = new ModelAndView("operate/hkbOperateDataQuery");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		String totalPayAmt = "0.00";
		String startDate = StringUtil.nullToString(req.getParameter("startDate"));
		String endDate = StringUtil.nullToString(req.getParameter("endDate"));
		pos.setStartDate(startDate.replace("-", ""));
		pos.setEndDate(endDate.replace("-", ""));
		pos.setShopCode(user.getShopCode());
		try {
			pageList = positOprStatisticsService.getPositOprStatisticsPage(startNum, pageSize, pos);
			if (pageList.getList() != null)
				totalPayAmt = positOprStatisticsService.getPositOprTotalPay(pos);
		} catch (Exception e) {
			logger.error("查询运营数据列表信息出错-->[{}]", e);

		}
		pos.setShopName(getShopName(user));
		pos.setStartDate(startDate);
		pos.setEndDate(endDate);
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		mv.addObject("totalPayAmt", totalPayAmt);
		return mv;
	}

	/**
	 * 运营数据设置 门店数据
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/listShopStatisticsSet", method = RequestMethod.GET)
	public ModelAndView listShopStatisticsSet(HttpServletRequest req, PositOprStatistics pos, Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateData");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		pos.setShopCode(user.getShopCode());
		String startDate = StringUtil.nullToString(req.getParameter("startDate"));
		String endDate = StringUtil.nullToString(req.getParameter("endDate"));
		pos.setStartDate(startDate.replace("-", ""));
		pos.setEndDate(endDate.replace("-", ""));
		try {
			pageList = positOprStatisticsService.getShopStatisticsMonthSetPage(startNum, pageSize, pos);
			pos.setStartDate(startDate);
			pos.setEndDate(endDate);
		} catch (Exception e) {
			logger.error("运营门店数据列表信息出错-->[{}]", e);
		}
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);

		return mv;
	}

	/**
	 * 运营数据设置 门店数据
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/listShopStatisticsSet", method = RequestMethod.POST)
	public ModelAndView listShopStatisticsSet(HttpServletRequest req, HttpServletResponse resp, PositOprStatistics pos,
			Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateData");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		// String shopCode = user.getShopCode();
		pos.setShopCode(user.getShopCode());
		String startDate = StringUtil.nullToString(req.getParameter("startDate"));
		String endDate = StringUtil.nullToString(req.getParameter("endDate"));
		pos.setStartDate(startDate.replace("-", ""));
		pos.setEndDate(endDate.replace("-", ""));
		try {
			pageList = positOprStatisticsService.getShopStatisticsMonthSetPage(startNum, pageSize, pos);
			pos.setStartDate(startDate);
			pos.setEndDate(endDate);
		} catch (Exception e) {
			logger.error("运营门店数据列表信息出错-->[{}]", e);
		}
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
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
	@RequestMapping(value = "/monthEdit", method = RequestMethod.GET)
	public ModelAndView monthEdit(HttpServletRequest req, PositOprStatistics pos, Map<String, Object> map) {
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
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
			pos.setTotalPay((req.getParameter("totalPay")));
		} catch (Exception e) {
			logger.error("运营门店数据月编辑信息出错-->[{}]", e);
		}
		pos.setSettleDate(startDate.substring(0, 8));
		pos.setShopStatisticsOprId(req.getParameter("shopId"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		mv.addObject("statSelect", Constants.DIYStat.values());
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
	@RequestMapping(value = "/monthEdit", method = RequestMethod.POST)
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
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
			pos.setTotalPay(req.getParameter("totalPay"));
		} catch (Exception e) {
			logger.error("运营门店数据月编辑信息出错-->[{}]", e);
		}
		pos.setSettleDate(startDate.substring(0, 8));
		pos.setShopStatisticsOprId(req.getParameter("shopId"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
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
	@RequestMapping(value = "/monthView", method = RequestMethod.GET)
	public ModelAndView monthView(HttpServletRequest req, PositOprStatistics pos, Map<String, Object> map) {
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
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
			pos.setTotalPay(req.getParameter("totalPay"));
		} catch (Exception e) {
			logger.error("运营门店数据月详情信息出错-->[{}]", e);
		}
		pos.setShopStatisticsOprId(req.getParameter("shopId"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
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
	@RequestMapping(value = "/monthView", method = RequestMethod.POST)
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
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
			pos.setTotalPay(req.getParameter("totalPay"));
		} catch (Exception e) {
			logger.error("运营门店数据月详情信息出错-->[{}]", e);
		}
		pos.setShopStatisticsOprId(req.getParameter("shopId"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
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
	@RequestMapping(value = "/dayEdit", method = RequestMethod.GET)
	public ModelAndView dayEdit(HttpServletRequest req, PositOprStatistics pos, Map<String, Object> map) {
		ModelAndView mv = new ModelAndView("original/opr/hkbOperateDayEdit");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositOprStatistics> pageList = null;
		String date = StringUtil.nullToString(req.getParameter("settleDate"));
		String dateStr = date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10);
		pos.setSettleDate(dateStr);
		// pos.setShopCode(StringUtil.nullToString(shopCode));
		pos.setShopCode(user.getShopCode());
		try {
			pageList = positOprStatisticsService.getPositStatisticsDaySetPage(startNum, pageSize, pos);
			pos.setTotalPay(req.getParameter("totalPay"));
			pos.setPayAmt(req.getParameter("payAmt"));
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
		} catch (Exception e) {
			logger.error("运营门店数据日编辑信息出错-->[{}]", e);
		}
		pos.setSettleDate(date);
		pos.setStat(req.getParameter("stat"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
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
	@RequestMapping(value = "/dayEdit", method = RequestMethod.POST)
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
		// pos.setShopCode(StringUtil.nullToString(shopCode));
		pos.setShopCode(user.getShopCode());
		try {
			pageList = positOprStatisticsService.getPositStatisticsDaySetPage(startNum, pageSize, pos);
			pos.setTotalPay(req.getParameter("totalPay"));
			pos.setPayAmt(req.getParameter("payAmt"));
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
		} catch (Exception e) {
			logger.error("运营门店数据日编辑信息出错-->[{}]", e);
		}
		pos.setSettleDate(date);
		pos.setStat(req.getParameter("stat"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
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
	@RequestMapping(value = "/dayView", method = RequestMethod.GET)
	public ModelAndView dayView(HttpServletRequest req, PositOprStatistics pos, Map<String, Object> map) {
		String s = StringUtil.nullToString(req.getParameter("state"));
		ModelAndView mv = new ModelAndView();
		if ("1".equals(s)) {
			mv = new ModelAndView("original/opr/hkbOperateDayDetail");
		} else {
			mv = new ModelAndView("original/opr/hkbOperate2DayDetail");
		}
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
			pos.setTotalPay(req.getParameter("totalPay"));
			pos.setPayAmt(req.getParameter("payAmt"));
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
		} catch (Exception e) {
			logger.error("运营门店数据日详情信息出错-->[{}]", e);
		}

		pos.setSettleDate(date);
		pos.setStat(req.getParameter("stat"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
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
	@RequestMapping(value = "/dayView", method = RequestMethod.POST)
	public ModelAndView dayView(HttpServletRequest req, HttpServletResponse resp, PositOprStatistics pos,
			Map<String, Object> map) {
		String s = StringUtil.nullToString(req.getParameter("state"));
		ModelAndView mv = new ModelAndView();
		if ("1".equals(s)) {
			mv = new ModelAndView("original/opr/hkbOperateDayDetail");
		} else {
			mv = new ModelAndView("original/opr/hkbOperate2DayDetail");
		}
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
			pos.setTotalPay(req.getParameter("totalPay"));
			pos.setPayAmt(req.getParameter("payAmt"));
			pos.setStartDate(StringUtil.nullToString(req.getParameter("startDate")));
			pos.setEndDate(StringUtil.nullToString(req.getParameter("endDate")));
		} catch (Exception e) {
			logger.error("运营门店数据日详情信息出错-->[{}]", e);
		}

		pos.setSettleDate(date);
		pos.setStat(req.getParameter("stat"));
		pos.setShopName(getShopName(user));
		pos.setMchntName(getMchntName(user));
		mv.addObject("positOprStatistics", pos);
		mv.addObject("pageInfo", pageList);
		return mv;
	}

	/**
	 * 运营数据设置 门店数据 日编辑保存（档口数据保存）
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/savePositOprStatistics", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePositOprStatistics(HttpServletRequest req, HttpServletResponse resp) {

		Map<String, Object> map = new HashMap<String, Object>();
		String data = StringUtil.nullToString(req.getParameter("positData"));
		String sid = StringUtil.nullToString(req.getParameter("sid"));
		String updateUser = StringUtil.nullToString(req.getParameter("user"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse(data);
		try {
			String result = positOprStatisticsService.updatePositProStatistics(sid, updateUser, list);
			map.put("results", result);
		} catch (Exception e) {
			logger.error("保存出错-->[{}]！", e.getMessage());
		}
		return map;
	}

	/**
	 * 运营数据设置 门店数据 归档
	 * 
	 * @param req
	 * @param resp
	 * @param pos
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> file(HttpServletRequest req, HttpServletResponse resp, ShopOprStatistics pos) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sid = StringUtil.nullToString(req.getParameter("sid"));
		String name = StringUtil.nullToString(req.getParameter("name"));
		map.put("sid", sid);
		map.put("name", name);
		try {
			String result = shopOprStatisticsService.filedShopProStatistics(map);
			if (result == null) {
				logger.info("归档失败！");
			}
		} catch (Exception e) {
			logger.error("归档出错-->[{}]！", e.getMessage());
		}
		return map;
	}

	/**
	 * 查询门店名称
	 * 
	 * @param user
	 * @return
	 */
	private String getShopName(User user) {
		String shopName = "";
		try {
			if(user!=null)
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
		String mchntName = "";
		try {
			if(user!=null)
			mchntName = positOprStatisticsService.getMchntInfByMchntCode(user.getMchntCode());
		} catch (Exception e) {
			logger.error("查询商户名称出错-->[{}]", e);
		}
		return mchntName;
	}

	/**
	 * 导出运营数据
	 * 
	 * @param req
	 * @param response
	 */
	@RequestMapping(value = "/getPositOprUploadList", method = RequestMethod.GET)
	public void getPositOprUploadList(HttpServletRequest req, HttpServletResponse response) {
		try {
			// String shopCode = req.getParameter("shopCode");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
			PositOprStatistics pos = new PositOprStatistics();
			pos.setShopCode(user.getShopCode());
			pos.setStartDate(LocalDate.parse(startDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			pos.setEndDate(LocalDate.parse(endDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			String shopName = userService.getShopNameByShopCode(user.getShopCode());
			List<PositOprUpdate> list = positOprStatisticsService.getPositOprUploadList(pos);
			String title = "运营数据报表";
			String titlerow = shopName;
			String[] titlehead = new String[] { "档口名", "总消费额" };
			ExcelUtils<PositOprUpdate> ex = new ExcelUtils<PositOprUpdate>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, startDate, endDate, titlehead, null, null, list,
					PositOprUpdate.class, null);
			UploadUtil.upLoad(workBook, title, response);
		} catch (Exception e) {
			logger.error("导出运营数据报表出错---->[{}]", e);
		}
	}

}
