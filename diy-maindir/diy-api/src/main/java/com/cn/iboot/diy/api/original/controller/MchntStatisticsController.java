package com.cn.iboot.diy.api.original.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.original.domain.MchntStatistics;
import com.cn.iboot.diy.api.original.domain.PositStatistics;
import com.cn.iboot.diy.api.original.domain.ShopStatistics;
import com.cn.iboot.diy.api.original.service.MchntStatisticsService;
import com.cn.iboot.diy.api.original.service.PositStatisticsService;
import com.cn.iboot.diy.api.original.service.ShopStatisticsService;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.system.service.UserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping(value = "/original/mchnt")
public class MchntStatisticsController {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MchntStatisticsService mchntStatisticsService;

	@Autowired
	private ShopStatisticsService shopStatisticsService;

	@Autowired
	private PositStatisticsService positStatisticsService;

	@Autowired
	private UserService userService;

	/**
	 * 商户数据
	 * @param req
	 * @param response
	 * @param ms
	 * @return
	 */
	@PostMapping(value = "/getMchntDataList")
	public ModelAndView getMchntDataList(HttpServletRequest req, HttpServletResponse response, MchntStatistics ms) {
		ModelAndView mv = new ModelAndView("original/mchnt/hkbMerchantData");
		List<MchntStatistics> mchntList = null;
		PageInfo<ShopStatistics> pageList = new PageInfo<ShopStatistics>(null);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		ms.setMchntCode(user.getMchntCode());
		String mchntName = null;
		try {
			mchntName = userService.getMchntNameByMchntCode(user.getMchntCode());
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询商户名出错",e);
		}
		if (ms.getStartDate() != null && !"".equals(ms.getStartDate()) && ms.getEndDate() != null
				&& !"".equals(ms.getEndDate())) {
			ms.setStartDate(LocalDate.parse(ms.getStartDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			ms.setEndDate(LocalDate.parse(ms.getEndDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			try {
				mchntList = mchntStatisticsService.getMchntStatisticsList(ms);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询商户数据统计出错", e);
			}
			try {
				ShopStatistics ss = new ShopStatistics();
				ss.setMchntCode(user.getMchntCode());
				List<String> shopCodes = mchntStatisticsService.getShopCodeByMchntCode(user.getMchntCode());
				ss.setShopCodes(shopCodes);
				ss.setStartDate(ms.getStartDate());
				ss.setEndDate(ms.getEndDate());
				pageList = shopStatisticsService.getShopStatisticsPage(startNum, pageSize, ss);

			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询门店数据统计出错", e);
			}
		}
		ms.setStartDate(LocalDate.parse(ms.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		ms.setEndDate(LocalDate.parse(ms.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		mv.addObject("mchntName", mchntName);
		mv.addObject("mchntList", mchntList);
		mv.addObject("pageInfo", pageList);
		mv.addObject("ms", ms);
		return mv;
	}

	/**
	 * 商户数据
	 * @param req
	 * @param ms
	 * @return
	 */
	@GetMapping(value = "/getMchntDataList")
	public ModelAndView getMchntDataList(HttpServletRequest req, MchntStatistics ms) {
		ModelAndView mv = new ModelAndView("original/mchnt/hkbMerchantData");
		List<MchntStatistics> mchntList = null;
		PageInfo<ShopStatistics> pageList = new PageInfo<ShopStatistics>(null);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		ms.setMchntCode(user.getMchntCode());
		String mchntName = null;
		try {
			mchntName = userService.getMchntNameByMchntCode(user.getMchntCode());
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询商户名出错",e);
		}
		if (ms.getStartDate() != null && !"".equals(ms.getStartDate()) && ms.getEndDate() != null
				&& !"".equals(ms.getEndDate())) {
			ms.setStartDate(LocalDate.parse(ms.getStartDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			ms.setEndDate(LocalDate.parse(ms.getEndDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			try {
				mchntList = mchntStatisticsService.getMchntStatisticsList(ms);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询商户数据统计出错", e);
			}
			try {
				ShopStatistics ss = new ShopStatistics();
				ss.setMchntCode(user.getMchntCode());
				List<String> shopCodes = mchntStatisticsService.getShopCodeByMchntCode(user.getMchntCode());
				ss.setShopCodes(shopCodes);
				ss.setStartDate(ms.getStartDate());
				ss.setEndDate(ms.getEndDate());
				pageList = shopStatisticsService.getShopStatisticsPage(startNum, pageSize, ss);

			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询门店数据统计出错", e);
			}
			ms.setStartDate(LocalDate.parse(ms.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			ms.setEndDate(LocalDate.parse(ms.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		}
		mv.addObject("mchntName", mchntName);
		mv.addObject("mchntList", mchntList);
		mv.addObject("pageInfo", pageList);
		mv.addObject("ms", ms);
		return mv;
	}

	/**
	 * 门店明细(档口数据列表)
	 * @param req
	 * @param response
	 * @param ps
	 * @return
	 */
	@PostMapping(value = "/getPostiList")
	public ModelAndView getPostiList(HttpServletRequest req, HttpServletResponse response, PositStatistics ps) {
		ModelAndView mv = new ModelAndView("original/mchnt/hkbStoreData");
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PositStatistics> pageList = new PageInfo<PositStatistics>(null);
		String shopName = null;
		try {
			shopName = userService.getShopNameByShopCode(ps.getShCode());
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询门店名出错", e);
		}
		if (ps.getStartDate() != null && !"".equals(ps.getStartDate()) && ps.getEndDate() != null
				&& !"".equals(ps.getEndDate())) {
			try {
				ps.setStartDate(LocalDate.parse(ps.getStartDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
				ps.setEndDate(LocalDate.parse(ps.getEndDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
				pageList = positStatisticsService.getPositStatisticsPage(startNum, pageSize, ps);
				ps.setStartDate(LocalDate.parse(ps.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
				ps.setEndDate(LocalDate.parse(ps.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());

			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询档口数据统计出错", e);
			}
		}
		mv.addObject("shopName", shopName);
		mv.addObject("pageInfo", pageList);
		mv.addObject("ps", ps);
		return mv;
	}

	/**
	 * 门店明细(档口数据列表)
	 * @param req
	 * @param ps
	 * @return
	 */
	@GetMapping(value = "/getPostiList")
	public ModelAndView getPostiList(HttpServletRequest req, PositStatistics ps) {
		ModelAndView mv = new ModelAndView("original/mchnt/hkbStoreData");
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		String shopCode = req.getParameter("shopCode");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		ps = new PositStatistics();
		PageInfo<PositStatistics> pageList = new PageInfo<PositStatistics>(null);
		String shopName = null;
		try {
			shopName = userService.getShopNameByShopCode(shopCode);
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询档口名出错", e);
		}
		if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
			try {
				ps.setShCode(shopCode);
				ps.setStartDate(LocalDate.parse(startDate).format(DateTimeFormatter.BASIC_ISO_DATE));
				ps.setEndDate(LocalDate.parse(endDate).format(DateTimeFormatter.BASIC_ISO_DATE));
				pageList = positStatisticsService.getPositStatisticsPage(startNum, pageSize, ps);
				ps.setStartDate(startDate);
				ps.setEndDate(endDate);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询档口数据统计出错", e);
			}
		}
		mv.addObject("shopName", shopName);
		mv.addObject("pageInfo", pageList);
		mv.addObject("ps", ps);
		return mv;
	}
}
