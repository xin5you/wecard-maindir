package com.cn.thinkx.oms.module.enterpriseorder.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.cgb.model.CgbRequestDTO;
import com.cn.thinkx.cgb.service.CgbService;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.oms.util.XlsReadFile;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageInfo;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping(value = "batchWithdrawOrder")
public class BatchWithdrawOrderController extends BaseController {

	Logger logger = LoggerFactory.getLogger(BatchWithdrawOrderController.class);

	@Autowired
	@Qualifier("batchWithdrawOrderService")
	private BatchWithdrawOrderService batchWithdrawOrderService;

	@Autowired
	@Qualifier("batchWithdrawOrderDetailService")
	private BatchWithdrawOrderDetailService batchWithdrawOrderDetailService;

	/**
	 * 后台批量提现列表
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listBatchWithdrawOrder")
	public ModelAndView listBatchWithdrawOrder(HttpServletRequest req, HttpServletResponse response ,BatchWithdrawOrder order) {
		ModelAndView mv = new ModelAndView("enterpriseOrder/batchWithDraw/listBatchWithDrawOrder");
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<BatchWithdrawOrder> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			pageList = batchWithdrawOrderService.getBatchWithdrawOrderPage(startNum,pageSize,order);
		} catch (Exception e) {
			logger.error("## 查询列表信息出错", e);
		}

		//
		Snowflake snowflake = IdUtil.createSnowflake(1, 1);
		long id = snowflake.nextId(); //获取雪花算法，获取分布式id
		CgbService cgbService=new CgbService();
		CgbRequestDTO cgbRequestDTO=new CgbRequestDTO();
		cgbRequestDTO.setEntSeqNo(String.valueOf(id));
		JSONObject jsonObject=cgbService.queryAccountBalResult(cgbRequestDTO);
		JSONObject balObj= jsonObject.getJSONObject("BEDC").getJSONObject("Message").getJSONObject("Body");

		mv.addObject("balObj", balObj);
		mv.addObject("order", order);
		mv.addObject("pageInfo", pageList);
		return mv;
	}

	/**
	 *  批量代付订单
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/excelImp")
	@ResponseBody
	public ModelMap excelImp(HttpServletRequest req, HttpServletResponse response){
		String batchOrderName =req.getParameter("batchOrderName");
		List<BatchWithdrawOrderDetail> orderList = new LinkedList<BatchWithdrawOrderDetail>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		ModelMap map = null;
		if (multipartFile == null) {
			map = new ModelMap();
			map.addAttribute("status", Boolean.FALSE);
			map.addAttribute("msg", "请选择上传文件！！！");
			return map;
		}
		try {
			CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
			if (cf != null && cf.getSize() > 0) {
				DiskFileItem fi = (DiskFileItem) cf.getFileItem();
				File file = fi.getStoreLocation();
				XlsReadFile xls = new XlsReadFile();
				InputStream inputStream = new FileInputStream(file);
				map = xls.readBatchWithDrawOrderDetailsExcel(inputStream, multipartFile.getOriginalFilename(), orderList);
				if(map.get("status").equals(Boolean.FALSE)) {
					//如果文件解析是吧直接返回
					return map;
				}
			}
			// 获取联行号
			map=batchWithdrawOrderDetailService.getCanps(orderList);
			if(map.get("status").equals(Boolean.FALSE)) {
				return map;
			}
            logger.info("# 批量代付的数据 {} " , JSONArray.toJSON(orderList));
			//保存信息
			BigDecimal totalMoney = orderList.stream().map(BatchWithdrawOrderDetail::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
			BatchWithdrawOrder batchWithdrawOrder=new BatchWithdrawOrder();
			batchWithdrawOrder.setOrderName(batchOrderName);
            batchWithdrawOrder.setTotalAmount(totalMoney);
            batchWithdrawOrder.setCreateUser(getCurrUser(req).getName());
            batchWithdrawOrder.setUserId(StringUtil.nullToString(getCurrUser(req).getId()));
			batchWithdrawOrderService.insertBatchWithdrawOrder(batchWithdrawOrder,orderList);
		} catch (Exception e) {
		    e.printStackTrace();
            map.addAttribute("status", Boolean.FALSE);
			logger.error("# 批量代付解析excel文件异常 {}",e);
		}
		return map;
	}
}
