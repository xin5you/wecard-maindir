package com.cn.thinkx.oms.module.statement.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.statement.model.FinancingSummarizing;
import com.cn.thinkx.oms.module.statement.model.ShopDetail;
import com.cn.thinkx.oms.module.statement.service.FinancingStatementService;
import com.cn.thinkx.oms.module.statement.service.MerchantStatementService;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.cn.thinkx.oms.module.statement.util.ConditionUtil;
import com.cn.thinkx.oms.module.statement.util.UploadUtil;
import com.cn.thinkx.oms.util.ExcelUtil;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="statement/financingStatement")
public class FinancingStatementController extends BaseController {

	Logger logger = LoggerFactory.getLogger(FinancingStatementController.class);
	
	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;
	
	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;
	
	@Autowired
	@Qualifier("merchantStatementService")
	private MerchantStatementService merchantStatementService;
	
	@Autowired
	@Qualifier("financingStatementService")
	private FinancingStatementService financingStatementService;
	
	@RequestMapping(value="/listFinancingSummarizing")
	public ModelAndView listFinancingSummarizing(HttpServletRequest req, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("statement/financingStatement/listFinancingSummarizing");
		PageInfo<FinancingSummarizing> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		Condition condition = null;
		try{
			condition = ConditionUtil.getCondition(req);
			if(condition.getStartTime()!=null && condition.getEndTime()!=null){
				pageList = financingStatementService.getFinancingSummarizingPage(startNum, pageSize, condition);
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("查询财务结算汇总列表出错---->[{}]",e);
		}
		try{
			condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
			condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
		} catch(Exception e){
			e.printStackTrace();
			logger.error("时间转化出错---->[{}]",e);
		}
		mv.addObject("condition", condition);
		mv.addObject("pageInfo", pageList);
		return mv;
	}
	
	@RequestMapping(value="/uploadFinancingSummarizing")
	public void uploadFinancingSummarizing(HttpServletRequest req, HttpServletResponse response){
		try{
			Condition condition = ConditionUtil.getCondition(req);
			List<FinancingSummarizing> list = financingStatementService.getFinancingSummarizingList(condition);
			String title ="财务结算汇总报表";
			String titlerow ="财务结算汇总报表";
			String[] titlehead = new String[]{"商户名","快捷消费金额","快捷消费金额","会员卡充值金额","会员卡充值金额","会员卡充值金额","平台补贴金额","服务费","结算金额"};
			String[] titleheadnum = new String[]{"2,3,0,0","2,2,1,2","2,2,3,5","2,3,6,6","2,3,7,7","2,3,8,8"};
			String[] head = new String[]{"微信","嘉福平台","微信","嘉福平台","充值平台"};
			ExcelUtil<FinancingSummarizing> ex = new ExcelUtil<FinancingSummarizing>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead, titleheadnum, head, list,FinancingSummarizing.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出财务结算汇总报表出错---->[{}]",e);
		}
	}
	
	@RequestMapping(value="/listFinancingDetail")
	public ModelAndView listFinancingDetail(HttpServletRequest req, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("statement/financingStatement/listFinancingDetail");
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		PageInfo<ShopDetail> pageList = null;
		List<ShopInf> shopInfList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		Condition condition = null;
		try{
			condition = ConditionUtil.getCondition(req);
			if(condition.getMchntCode()!=null && condition.getStartTime()!=null && condition.getEndTime()!=null){
				shopInfList = shopInfService.getShopInfListByMchntCode(condition.getMchntCode());
				pageList = merchantStatementService.getShopDetailPage(startNum, pageSize, condition);
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("查询财务结算明细列表出错---->[{}]",e);
		}
		try{
			condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
			condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
		} catch(Exception e){
			e.printStackTrace();
			logger.error("时间转化出错---->[{}]",e);
		}
		mv.addObject("mchntList", mchntList);
		mv.addObject("shopInfList", shopInfList);
		mv.addObject("condition", condition);
		mv.addObject("pageInfo", pageList);
		return mv;
	}
	
	@RequestMapping(value="/uploadFinancingDetail")
	public void uploadFinancingDetail(HttpServletRequest req, HttpServletResponse response){
		try{
			Condition condition = ConditionUtil.getCondition(req);
			List<ShopDetail> list = merchantStatementService.getShopDetailList(condition);
			ShopDetail sd = merchantStatementService.getShopDetailAmount(condition);
			String title ="财务结算明细报表";
			String titlerow ="财务结算明细报表";
			String[] titlehead = new String[]{"流水号","外部流水号","清算日期","卡号","账户号","交易金额","交易类型","交易时间"};
			ExcelUtil<ShopDetail> ex = new ExcelUtil<ShopDetail>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead, null, null, list,ShopDetail.class,sd);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出财务结算明细报表出错---->[{}]",e);
		}
	}
}
