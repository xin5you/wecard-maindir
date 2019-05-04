package com.cn.iboot.diy.api.original.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cn.iboot.diy.api.base.utils.ExcelUtils;
import com.cn.iboot.diy.api.base.utils.UploadUtil;
import com.cn.iboot.diy.api.original.domain.PositDetail;
import com.cn.iboot.diy.api.original.domain.PositDetailUpload;
import com.cn.iboot.diy.api.original.domain.PositStatistics;
import com.cn.iboot.diy.api.original.domain.PositStatisticsUpload;
import com.cn.iboot.diy.api.original.service.PositStatisticsService;
import com.cn.iboot.diy.api.system.service.UserService;

@RestController
@RequestMapping("/original/posit")
public class PositStatisticsController {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PositStatisticsService positStatisticsService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 档口数据导出
	 * @param req
	 * @param response
	 */
	@RequestMapping("/getPositStatisticsUploadList")
	public void getPositStatisticsUploadList(HttpServletRequest req, HttpServletResponse response){
		try{
			String shCode = req.getParameter("shCode");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			PositStatistics ps = new PositStatistics();
			ps.setShCode(shCode);
			ps.setStartDate(LocalDate.parse(startDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			ps.setEndDate(LocalDate.parse(endDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			String shopName = userService.getShopNameByShopCode(shCode);
			List<PositStatisticsUpload> list = positStatisticsService.getPositStatisticsUploadList(ps);
			String title ="档口数据报表";
			String titlerow = shopName;
			String[] titlehead = new String[]{"档口名","会员卡","会员卡","快捷消费","快捷消费","总消费额"};
			String[] titleheadnum = new String[]{"2,3,0,0","2,2,1,2","2,2,3,4","2,3,5,5"};
			String[] head = new String[]{"消费总额","消费笔数","消费总额","消费笔数"};
			ExcelUtils<PositStatisticsUpload> ex = new ExcelUtils<PositStatisticsUpload>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, startDate, endDate, titlehead, titleheadnum, head, list,PositStatisticsUpload.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			log.error("导出档口数据报表出错",e);
		}
	}
	
	/**
	 * 档口交易流水数据导出
	 * @param req
	 * @param response
	 */
	@RequestMapping("/getPositDetailUploadList")
	public void getPositDetailUploadList(HttpServletRequest req, HttpServletResponse response){
		try{
			String positCode = req.getParameter("positCode");
			String startDate = req.getParameter("startDate");
			String endDate = req.getParameter("endDate");
			PositDetail pd = new PositDetail();
			pd.setPositCode(positCode);
			pd.setStartDate(LocalDate.parse(startDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			pd.setEndDate(LocalDate.parse(endDate).format(DateTimeFormatter.BASIC_ISO_DATE));
			String shopName = userService.getShopNameByShopCode(positCode);
			List<PositDetailUpload> list = positStatisticsService.getPositDetailUploadList(pd);
			String title ="档口明细报表";
			String titlerow = shopName;
			String[] titlehead = new String[]{"时间","会员卡消费总额","会员卡消费笔数","快捷消费总额","快捷消费笔数","总消费额"};
			ExcelUtils<PositDetailUpload> ex = new ExcelUtils<PositDetailUpload>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, startDate, endDate, titlehead, null, null, list,PositDetailUpload.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			log.error("导出档口明细报表出错",e);
		}
	}

}
