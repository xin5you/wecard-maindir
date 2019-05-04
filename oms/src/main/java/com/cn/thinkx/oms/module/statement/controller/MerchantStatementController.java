package com.cn.thinkx.oms.module.statement.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.statement.model.MerchantDetail;
import com.cn.thinkx.oms.module.statement.model.MerchantSummarizing;
import com.cn.thinkx.oms.module.statement.model.ShopDetail;
import com.cn.thinkx.oms.module.statement.service.MerchantStatementService;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.cn.thinkx.oms.module.statement.util.ConditionUtil;
import com.cn.thinkx.oms.module.statement.util.UploadUtil;
import com.cn.thinkx.oms.util.ExcelUtil;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "statement/merchantStatement")
public class MerchantStatementController extends BaseController {

	Logger logger = LoggerFactory.getLogger(MerchantStatementController.class);

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Autowired
	@Qualifier("merchantStatementService")
	private MerchantStatementService merchantStatementService;

	@RequestMapping(value = "/listMerchantSummarizing")
	public ModelAndView listMerchantSummarizing(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("statement/merchantStatement/listMerchantSummarizing");
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		MerchantSummarizing ms = null;
		Condition condition = null;
		try {
			condition = ConditionUtil.getCondition(req);
			if (condition.getMchntCode() != null && condition.getStartTime() != null && condition.getEndTime() != null) {
				ms = merchantStatementService.getMerchantSummarizing(condition);
			}
			condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
			condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
		} catch (Exception e) {
			logger.error("## 查询商户汇总列表异常", e);
		}
		mv.addObject("mchntList", mchntList);
		mv.addObject("condition", condition);
		mv.addObject("ms", ms);
		return mv;
	}

	@RequestMapping(value = "/uploadMerchantSummarizing")
	public void uploadMerchantSummarizing(HttpServletRequest req, HttpServletResponse response) {
		try {
			Condition condition = ConditionUtil.getCondition(req);
			MerchantSummarizing ms = merchantStatementService.getMerchantSummarizing(condition);
			List<MerchantSummarizing> list = new ArrayList<>();
			if (ms != null) {
				list.add(ms);
			}
			String title = "商户汇总报表";
			String titlerow = "商户汇总报表";
			String[] titlehead = new String[] { "会员卡\r\n消费总额", "快捷\r\n消费总额", "会员卡充值", "会员卡充值", "会员卡充值", "会员卡充值", "平台补贴",
					"平台补贴", "会员卡余额", "服务费", "结算金额" };
			String[] titleheadnum = new String[] { "2,3,0,0", "2,3,1,1", "2,2,2,5", "2,2,6,7", "2,3,8,8", "2,3,9,9", "2,3,10,10" };
			String[] head = new String[] { "", "线上充\r\n值金额", "线上充\r\n值面额", "平台充\r\n值金额", "平台充\r\n值面额", "笔数", "金额" };
			ExcelUtil<MerchantSummarizing> ex = new ExcelUtil<MerchantSummarizing>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow,
					DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD),
					DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead,
					titleheadnum, head, list, MerchantSummarizing.class, null);
			UploadUtil.upLoad(workBook, title, response);
		} catch (Exception e) {
			logger.error("## 导出商户汇总报表异常", e);
		}
	}

	@RequestMapping(value = "/listMerchantDetail")
	public ModelAndView listMerchantDetail(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("statement/merchantStatement/listMerchantDetail");
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		PageInfo<MerchantDetail> pageList = null;
		List<ShopInf> shopInfList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		Condition condition = null;
		try {
			condition = ConditionUtil.getCondition(req);
			if (condition.getMchntCode() != null && condition.getStartTime() != null && condition.getEndTime() != null) {
				shopInfList = shopInfService.getShopInfListByMchntCode(condition.getMchntCode());
				pageList = merchantStatementService.getMerchantsDetailPage(startNum, pageSize, condition);
				// condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(),
				// DateUtil.FORMAT_YYYY_MM_DD));
				// condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(),
				// DateUtil.FORMAT_YYYY_MM_DD));
				condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
				condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
			}
		} catch (Exception e) {
			logger.error("## 查询商户明细列表异常", e);
		}
		mv.addObject("mchntList", mchntList);
		mv.addObject("shopInfList", shopInfList);
		mv.addObject("condition", condition);
		mv.addObject("pageInfo", pageList);
		return mv;
	}

	@RequestMapping(value = "/uploadMerchantDetail")
	public void uploadMerchantDetail(HttpServletRequest req, HttpServletResponse response) {
		try {
			Condition condition = ConditionUtil.getCondition(req);
			List<MerchantDetail> list = merchantStatementService.getMerchantDetailList(condition);
			MerchantDetail md = merchantStatementService.getMerchantDetailAmount(condition);
			String title = "商户明细报表";
			String titlerow = "商户明细报表";
			String[] titlehead = new String[] { "档口名", "会员卡", "会员卡", "快捷消费", "快捷消费", "总消费额" };
			String[] titleheadnum = new String[] { "2,3,0,0", "2,2,1,2", "2,2,3,4", "2,3,5,5" };
			String[] head = new String[] { "消费总额", "消费笔数", "消费总额", "消费笔数" };
			ExcelUtil<MerchantDetail> ex = new ExcelUtil<MerchantDetail>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow,
					DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD),
					DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead,
					titleheadnum, head, list, MerchantDetail.class, md);
			UploadUtil.upLoad(workBook, title, response);
		} catch (Exception e) {
			logger.error("## 导出商户明细报表异常", e);
		}
	}

	@RequestMapping(value = "/listShopDetail")
	public ModelAndView listShopDetail(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("statement/merchantStatement/listShopDetail");
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		PageInfo<ShopDetail> pageList = null;
		List<ShopInf> shopInfList = null;
		List<ShopInf> positList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		Condition condition = null;
		try {
			condition = ConditionUtil.getCondition(req);
			if (condition.getMchntCode() != null && condition.getShopCode() != null && condition.getStartTime() != null
					&& condition.getEndTime() != null) {
				shopInfList = shopInfService.getShopInfListByMchntCode(condition.getMchntCode());
				positList = shopInfService.getShopInfListByPShopCode(condition.getShopCode());
				pageList = merchantStatementService.getShopDetailPage(startNum, pageSize, condition);
				// condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(),
				// DateUtil.FORMAT_YYYY_MM_DD));
				// condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(),
				// DateUtil.FORMAT_YYYY_MM_DD));
				
				condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
				condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
			}
		} catch (Exception e) {
			logger.error("## 查门店明细列表异常", e);
		}
		mv.addObject("mchntList", mchntList);
		mv.addObject("shopInfList", shopInfList);
		mv.addObject("positList", positList);
		mv.addObject("condition", condition);
		mv.addObject("pageInfo", pageList);
		return mv;
	}

	@RequestMapping(value = "/uploadShopDetail")
	public void uploadShopDetail(HttpServletRequest req, HttpServletResponse response) {
		try {
			Condition condition = ConditionUtil.getCondition(req);
			List<ShopDetail> list = merchantStatementService.getShopDetailList(condition);
			ShopDetail sd = merchantStatementService.getShopDetailAmount(condition);
			String title = "门店明细报表";
			String titlerow = "门店明细报表";
			String[] titlehead = new String[] { "流水号", "外部流水号", "清算日期", "卡号", "账户号", "交易金额", "交易类型", "交易时间" };
			ExcelUtil<ShopDetail> ex = new ExcelUtil<ShopDetail>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow,
					DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD),
					DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead,
					null, null, list, ShopDetail.class, sd);
			UploadUtil.upLoad(workBook, title, response);
		} catch (Exception e) {
			logger.error("## 导出门店明细报表异常", e);
		}
	}

	@RequestMapping(value = "/intoAddMchntEshopInfGetShop")
	@ResponseBody
	public ModelMap intoAddMchntEshopInfGetShop(HttpServletRequest req, HttpServletResponse response) {
		ModelMap map = new ModelMap();
		map.addAttribute("status", Boolean.TRUE);
		String mchntCode = req.getParameter("mchntCode");
		List<ShopInf> shopInfList = shopInfService.getShopInfListByMchntCode(mchntCode);
		map.addAttribute("shopInfList", shopInfList);
		return map;
	}

	@RequestMapping(value = "/listFindPosit")
	@ResponseBody
	public ModelMap listFindPosit(HttpServletRequest req, HttpServletResponse response) {
		ModelMap map = new ModelMap();
		map.addAttribute("status", Boolean.TRUE);
		String shopCode = req.getParameter("shopCode");
		List<ShopInf> positList = shopInfService.getShopInfListByPShopCode(shopCode);
		map.addAttribute("positList", positList);
		return map;
	}
}
