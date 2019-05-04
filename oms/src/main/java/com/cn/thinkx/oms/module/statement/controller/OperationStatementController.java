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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.mchnteshop.controller.MchntEshopInfController;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.statement.model.CustomerInfo;
import com.cn.thinkx.oms.module.statement.model.CustomerInfoDetail;
import com.cn.thinkx.oms.module.statement.model.MarketingDetail;
import com.cn.thinkx.oms.module.statement.model.OperationDetail;
import com.cn.thinkx.oms.module.statement.model.OperationSummarizing;
import com.cn.thinkx.oms.module.statement.service.OperationStatementService;
import com.cn.thinkx.oms.module.statement.util.Condition;
import com.cn.thinkx.oms.module.statement.util.ConditionUtil;
import com.cn.thinkx.oms.module.statement.util.UploadUtil;
import com.cn.thinkx.oms.util.ExcelUtil;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value="statement/operationStatement")
public class OperationStatementController extends BaseController {
	
	Logger logger = LoggerFactory.getLogger(MchntEshopInfController.class);
	
	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;
	
	@Autowired
	@Qualifier("operationStatementService")
	private OperationStatementService operationStatementService;

	@RequestMapping(value="/listOperationSummarizing")
	public ModelAndView listOperationSummarizing(HttpServletRequest req, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("statement/operationStatement/listOperationSummarizing");
		OperationSummarizing os = null;
		Condition condition = null;
		try{
			condition = ConditionUtil.getCondition(req);
			if(condition.getStartTime()!=null && condition.getEndTime()!=null){
				os = operationStatementService.getOperationSummarizing(condition);
//				condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
//				condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
			}
			
		} catch(Exception e){
			e.printStackTrace();
			logger.error("查询运营汇总列表出错---->[{}]",e);
		}
		try{
			condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
			condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
		} catch(Exception e){
			e.printStackTrace();
			logger.error("时间转化出错---->[{}]",e);
		}
		mv.addObject("condition", condition);
		mv.addObject("os", os);
		return mv;
	}
	@RequestMapping(value="/uploadOperationSummarizing")
	public void uploadOperationSummarizing(HttpServletRequest req, HttpServletResponse response){
		try{
			Condition condition = ConditionUtil.getCondition(req);
			OperationSummarizing os = operationStatementService.getOperationSummarizing(condition);
			List<OperationSummarizing> list = new ArrayList<OperationSummarizing>();
			if(os!=null){
				list.add(os);
			}
			String title ="运营汇总报表";
			String titlerow ="运营汇总报表";
			String[] titlehead = new String[]{"会员卡消费","快捷消费","快捷消费","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡充值","平台补贴金额","会员卡余额"};
			String[] titleheadnum = new String[]{"2,3,0,0","2,2,1,2","2,2,3,8","2,3,9,9","2,3,10,10"};
			String[] head = new String[]{"微信","嘉福平台","微信\r\n充值金额","微信\r\n充值面额","嘉福平台\r\n充值金额","嘉福平台\r\n充值面额","平台\r\n充值金额","平台\r\n充值面额"};
			ExcelUtil<OperationSummarizing> ex = new ExcelUtil<OperationSummarizing>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead, titleheadnum, head, list,OperationSummarizing.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出运营汇总报表出错---->[{}]",e);
		}
	}
	
	@RequestMapping(value="/listOperationDetail")
	public ModelAndView listOperationDetail(HttpServletRequest req, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("statement/operationStatement/listOperationDetail");
		PageInfo<OperationDetail> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		Condition condition = null;
		try{
			condition = ConditionUtil.getCondition(req);
			if(condition.getStartTime()!=null && condition.getEndTime()!=null){
				pageList = operationStatementService.getOperationDetailPage(startNum, pageSize, condition);
//				condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
//				condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("查询运营明细列表出错---->[{}]",e);
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
	
	@RequestMapping(value="/uploadOperationDetail")
	public void uploadOperationDetail(HttpServletRequest req, HttpServletResponse response){
		try{
			Condition condition = ConditionUtil.getCondition(req);
			List<OperationDetail> list = operationStatementService.getOperationDetailList(condition);
			OperationDetail od = operationStatementService.getOperationDetailAmount(condition);
			String title ="运营明细报表";
			String titlerow ="运营明细报表";
			String[] titlehead = new String[]{"商户名称","会员卡消费","会员卡消费","快捷消费","快捷消费","快捷消费","快捷消费","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡充值","会员卡余额"};
			String[] titleheadnum = new String[]{"2,3,0,0","2,2,1,2","2,2,3,6","2,2,7,15","2,3,16,16"};
			String[] head = new String[]{"消费总额","消费笔数","微信\r\n消费金额","微信\r\n消费笔数","嘉福平台\r\n消费金额","嘉福平台\r\n消费笔数","微信\r\n充值金额","微信\r\n充值面额","微信\r\n充值笔数","嘉福平台\r\n充值金额","嘉福平台\r\n充值面额","嘉福平台\r\n充值笔数","平台\r\n充值金额","平台\r\n充值面额","平台\r\n充值笔数"};
			ExcelUtil<OperationDetail> ex = new ExcelUtil<OperationDetail>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead, titleheadnum, head, list,OperationDetail.class,od);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出运营明细报表出错---->[{}]",e);
		}
	}
	
	@RequestMapping(value="/listCustomerInfo")
	public ModelAndView listCustomerInfo(HttpServletRequest req, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("statement/operationStatement/listCustomerInfo");
		CustomerInfo cus = null;
		Condition condition = null;
		try{
			condition = ConditionUtil.getCondition(req);
			if(condition.getStartTime()!=null && condition.getEndTime()!=null){
				cus = operationStatementService.getCustomerInfo(condition);
//				condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
//				condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("查询客户数据列表出错---->[{}]",e);
		}
		try{
			condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
			condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
		} catch(Exception e){
			e.printStackTrace();
			logger.error("时间转化出错---->[{}]",e);
		}
		mv.addObject("condition", condition);
		mv.addObject("cus", cus);
		return mv;
	}
	
	@RequestMapping(value="/uploadCustomerInfo")
	public void uploadCustomerInfo(HttpServletRequest req, HttpServletResponse response){
		try{
			Condition condition = ConditionUtil.getCondition(req);
			CustomerInfo cus = operationStatementService.getCustomerInfo(condition);
			List<CustomerInfo> list = new ArrayList<CustomerInfo>();
			if(cus!=null){
				list.add(cus);
			}
			String title ="客户数据报表";
			String titlerow ="客户数据报表";
			String[] titlehead = new String[]{"关注总量","开户总量","开卡总量"};
			ExcelUtil<CustomerInfo> ex = new ExcelUtil<CustomerInfo>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead, null, null, list,CustomerInfo.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出客户数据报表出错---->[{}]",e);
		}
	}
	
	@RequestMapping(value="/listCustomerInfoDetail")
	public ModelAndView listCustomerInfoDetail(HttpServletRequest req, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("statement/operationStatement/listCustomerInfoDetail");
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		CustomerInfoDetail cusDetail = null;
		Condition condition = null;
		try{
			condition = ConditionUtil.getCondition(req);
			if(condition.getMchntCode()!=null &&condition.getStartTime()!=null && condition.getEndTime()!=null){
				cusDetail = operationStatementService.getCustomerInfoDetail(condition);
				if(cusDetail==null){
					cusDetail = new CustomerInfoDetail();
					MerchantInf merchantInf = merchantInfService.getMerchantInfByMchntCode(condition.getMchntCode());
					cusDetail.setMchntName(merchantInf.getMchntName());
					cusDetail.setOpenCardCount("0");
				}
//				condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
//				condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("查询客户数据明细列表出错---->[{}]",e);
		}
		try{
			condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
			condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
		} catch(Exception e){
			e.printStackTrace();
			logger.error("时间转化出错---->[{}]",e);
		}
		mv.addObject("mchntList", mchntList);
		mv.addObject("condition", condition);
		mv.addObject("cusDetail", cusDetail);
		return mv;
	}
	@RequestMapping(value="/uploadCustomerInfoDetail")
	public void uploadCustomerInfoDetail(HttpServletRequest req, HttpServletResponse response){
		try{
			CustomerInfoDetail cusDetail = null;
			Condition condition = ConditionUtil.getCondition(req);
			cusDetail = operationStatementService.getCustomerInfoDetail(condition);
			if(cusDetail==null){
				cusDetail = new CustomerInfoDetail();
				MerchantInf merchantInf = merchantInfService.getMerchantInfByMchntCode(condition.getMchntCode());
				cusDetail.setMchntName(merchantInf.getMchntName());
				cusDetail.setOpenCardCount("0");
			}
			List<CustomerInfoDetail> list = new ArrayList<CustomerInfoDetail>();
			if(cusDetail!=null){
				list.add(cusDetail);
			}
			String title ="客户数据明细报表";
			String titlerow ="客户数据明细报表";
			String[] titlehead = new String[]{"商户名","开卡量"};
			ExcelUtil<CustomerInfoDetail> ex = new ExcelUtil<CustomerInfoDetail>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead, null, null, list,CustomerInfoDetail.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出客户数据报表出错---->[{}]",e);
		}
	}
	
	@RequestMapping(value="/listMarketingDetail")
	public ModelAndView listMarketingDetail(HttpServletRequest req, HttpServletResponse response){
		ModelAndView mv = new ModelAndView("statement/operationStatement/listMarketingDetail");
		PageInfo<MarketingDetail> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		Condition condition = null;
		try{
			condition = ConditionUtil.getCondition(req);
			if(condition.getStartTime()!=null && condition.getEndTime()!=null){
				pageList = operationStatementService.getMarketingDetailPage(startNum, pageSize, condition);
//				condition.setStartTime(DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD));
//				condition.setEndTime(DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD));
			}
		} catch(Exception e){
			e.printStackTrace();
			logger.error("查询营销数据明细列表出错---->[{}]",e);
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
	
	@RequestMapping(value="/uploadMarketingDetail")
	public void uploadMarketingDetail(HttpServletRequest req, HttpServletResponse response){
		try{
			Condition condition = ConditionUtil.getCondition(req);
			List<MarketingDetail> list = operationStatementService.getMarketingDetailList(condition);
			String title ="营销数据明细报表";
			String titlerow ="营销数据明细报表";
			String[] titlehead = new String[]{"商户名","补贴笔数","补贴总金额"};
			ExcelUtil<MarketingDetail> ex = new ExcelUtil<MarketingDetail>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, DateUtil.getFormatStringFormString(condition.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD), DateUtil.getFormatStringFormString(condition.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead, null, null, list,MarketingDetail.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("导出营销数据明细报表出错---->[{}]",e);
		}
	}
}
