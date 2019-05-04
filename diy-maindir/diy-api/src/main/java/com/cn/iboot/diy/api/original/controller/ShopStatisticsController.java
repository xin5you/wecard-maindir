package com.cn.iboot.diy.api.original.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.ExcelUtils;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.UploadUtil;
import com.cn.iboot.diy.api.original.domain.PositDetail;
import com.cn.iboot.diy.api.original.domain.PositStatistics;
import com.cn.iboot.diy.api.original.domain.ShopStatistics;
import com.cn.iboot.diy.api.original.domain.ShopStatisticsUpload;
import com.cn.iboot.diy.api.original.service.MchntStatisticsService;
import com.cn.iboot.diy.api.original.service.PositStatisticsService;
import com.cn.iboot.diy.api.original.service.ShopStatisticsService;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.system.service.UserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping(value = "/original/shop")
public class ShopStatisticsController {

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
	 * 门店数据
	 * 
	 * @param req
	 * @param response
	 * @param ss
	 * @return
	 */
	@PostMapping(value = "/getShopDataList")
	public ModelAndView getShopDataList(HttpServletRequest req, HttpServletResponse response, ShopStatistics ss) {
		ModelAndView mv = new ModelAndView("original/shop/hkbStoreManagerData");
		List<ShopStatistics> ssList = null;
		PageInfo<PositStatistics> pageList = new PageInfo<PositStatistics>(null);
		PositStatistics ps = new PositStatistics();
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		String mchntName = null;
		String shopName = null;
		try {
			mchntName = userService.getMchntNameByMchntCode(user.getMchntCode());
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询商户名出错",e);
		}
		try {
			shopName = userService.getShopNameByShopCode(user.getShopCode());
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询门店名出错",e);
		}
		if (ss.getStartDate() != null && !"".equals(ss.getStartDate()) && ss.getEndDate() != null
				&& !"".equals(ss.getEndDate())) {
			ss.setMchntCode(user.getMchntCode());
			ss.getShopCodes().add(user.getShopCode());
			ss.setStartDate(LocalDate.parse(ss.getStartDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			ss.setEndDate(LocalDate.parse(ss.getEndDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			try {
				ssList = shopStatisticsService.getShopStatisticsList(ss);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询门店数据统计出错", e);
			}
			try {
				ps.setShCode(user.getShopCode());
				ps.setStartDate(ss.getStartDate());
				ps.setEndDate(ss.getEndDate());
				pageList = positStatisticsService.getPositStatisticsPage(startNum, pageSize, ps);
				mv.addObject("pageInfo", pageList);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询档口数据统计出错", e);
			}
			ss.setStartDate(LocalDate.parse(ss.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			ss.setEndDate(LocalDate.parse(ss.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		}
		mv.addObject("mchntName", mchntName);
		mv.addObject("shopName", shopName);
		mv.addObject("shopCode", user.getShopCode());
		mv.addObject("shopList", ssList);
		mv.addObject("pageInfo", pageList);
		mv.addObject("ss", ss);
		return mv;
	}

	/**
	 * 门店数据
	 * 
	 * @param req
	 * @param ss
	 * @return
	 */
	@GetMapping(value = "/getShopDataList")
	public ModelAndView getShopDataList(HttpServletRequest req, ShopStatistics ss) {
		ModelAndView mv = new ModelAndView("original/shop/hkbStoreManagerData");
		List<ShopStatistics> ssList = null;
		PageInfo<PositStatistics> pageList = new PageInfo<PositStatistics>(null);
		PositStatistics ps = new PositStatistics();
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		String mchntName = null;
		String shopName = null;
		try {
			mchntName = userService.getMchntNameByMchntCode(user.getMchntCode());
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询商户名出错",e);
		}
		try {
			shopName = userService.getShopNameByShopCode(user.getShopCode());
		} catch (Exception e) {
			mv = new ModelAndView("main/error");
			log.error("查询门店名出错",e);
		}
		if (ss.getStartDate() != null && !"".equals(ss.getStartDate()) && ss.getEndDate() != null
				&& !"".equals(ss.getEndDate())) {
			ss.setMchntCode(user.getMchntCode());
			ss.getShopCodes().add(user.getShopCode());
			ss.setStartDate(LocalDate.parse(ss.getStartDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			ss.setEndDate(LocalDate.parse(ss.getEndDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			try {
				ssList = shopStatisticsService.getShopStatisticsList(ss);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询门店数据统计出错", e);
			}
			try {
				ps.setShCode(user.getShopCode());
				ps.setStartDate(ss.getStartDate());
				ps.setEndDate(ss.getEndDate());
				pageList = positStatisticsService.getPositStatisticsPage(startNum, pageSize, ps);
				mv.addObject("pageInfo", pageList);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询档口数据统计出错", e);
			}
			ss.setStartDate(LocalDate.parse(ss.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			ss.setEndDate(LocalDate.parse(ss.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		}
		mv.addObject("mchntName", mchntName);
		mv.addObject("shopName", shopName);
		mv.addObject("shopCode", user.getShopCode());
		mv.addObject("shopList", ssList);
		mv.addObject("pageInfo", pageList);
		mv.addObject("ss", ss);
		return mv;
	}

	/**
	 * 档口交易流水列表查询
	 * 
	 * @param req
	 * @param response
	 * @param pd
	 * @return
	 */
	@PostMapping(value = "/getPositDetailList")
	public ModelAndView getPositDetailList(HttpServletRequest req, HttpServletResponse response, PositDetail pd) {
		ModelAndView mv = new ModelAndView("original/shop/hkbStoreDataDetails");
		PageInfo<PositStatistics> pageList = null;
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		String positName = userService.getShopNameByShopCode(pd.getPositCode());
		if (pd.getStartDate() != null && !"".equals(pd.getStartDate()) && pd.getEndDate() != null
				&& !"".equals(pd.getEndDate())) {
			try {
				pd.setStartDate(LocalDate.parse(pd.getStartDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
				pd.setEndDate(LocalDate.parse(pd.getEndDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
				pageList = positStatisticsService.getPositDetailPage(startNum, pageSize, pd);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询档口明细列表出错", e);
			}
			pd.setStartDate(LocalDate.parse(pd.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			pd.setEndDate(LocalDate.parse(pd.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		}
		mv.addObject("positName", positName);
		mv.addObject("pageInfo", pageList);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 档口交易流水列表查询
	 * 
	 * @param req
	 * @param pd
	 * @return
	 */
	@GetMapping(value = "/getPositDetailList")
	public ModelAndView getPositDetailList(HttpServletRequest req, PositDetail pd) {
		ModelAndView mv = new ModelAndView("original/shop/hkbStoreDataDetails");
		PageInfo<PositStatistics> pageList = new PageInfo<PositStatistics>(null);
		pd = new PositDetail();
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		String positCode = req.getParameter("positCode");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		String positName = userService.getShopNameByShopCode(positCode);
		if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
			pd.setPositCode(positCode);
			pd.setStartDate(LocalDate.parse(startDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			pd.setEndDate(LocalDate.parse(endDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			try {
				pageList = positStatisticsService.getPositDetailPage(startNum, pageSize, pd);
			} catch (Exception e) {
				mv = new ModelAndView("main/error");
				log.error("查询档口明细列表出错", e);
			}
			pd.setStartDate(LocalDate.parse(pd.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			pd.setEndDate(LocalDate.parse(pd.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		}
		mv.addObject("positName", positName);
		mv.addObject("pageInfo", pageList);
		mv.addObject("pd", pd);
		return mv;
	}
	
	@RequestMapping("/getShopStatisticsUploadList")
	public void getShopStatisticsUploadList(HttpServletRequest req, HttpServletResponse response){
		try{
			String mchntCode = req.getParameter("mchntCode");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			List<String> shopCodes = mchntStatisticsService.getShopCodeByMchntCode(mchntCode);
			ShopStatistics ss = new ShopStatistics();
			ss.setMchntCode(mchntCode);
			ss.setShopCodes(shopCodes);
			ss.setStartDate(LocalDate.parse(startDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			ss.setEndDate(LocalDate.parse(endDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			String mchntName = userService.getMchntNameByMchntCode(mchntCode);
			List<ShopStatisticsUpload> list = shopStatisticsService.getShopStatisticsUploadList(ss);
			String title ="商户数据报表";
			String titlerow = mchntName;
			String[] titlehead = new String[]{"门店名","会员卡\r\n消费总额","快捷\r\n消费总额","平台补贴","平台补贴"};
			String[] titleheadnum = new String[]{"2,3,0,0","2,3,1,1","2,3,2,2","2,2,3,4"};
			String[] head = new String[]{"","","笔数","金额"};
			ExcelUtils<ShopStatisticsUpload> ex = new ExcelUtils<ShopStatisticsUpload>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, startDate, endDate, titlehead, titleheadnum, head, list,ShopStatisticsUpload.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			log.error("导出档口数据报表出错",e);
		}
	}
}
