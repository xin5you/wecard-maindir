package com.cn.thinkx.oms.module.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchOrderList;
import com.cn.thinkx.oms.module.enterpriseorder.util.OrderConstants;
import com.cn.thinkx.oms.module.withdrawBlacklist.model.WithdrawBlacklist;
import com.cn.thinkx.oms.module.withdrawBlacklist.util.WBConstants;
import com.cn.thinkx.oms.util.XlsReadFile;

@Controller
@RequestMapping(value = "common/excelImport")
public class ExcelImportController {

	Logger logger = LoggerFactory.getLogger(ExcelImportController.class);

	@RequestMapping(value = "/excelImp")
	@ResponseBody
	public ModelMap importEcxel(HttpServletRequest req, HttpServletResponse response)
	// @RequestParam(value = "file", required = false)MultipartFile
	// multipartFile)
	{
		LinkedList<BatchOrderList> orderList = new LinkedList<BatchOrderList>();
		Map<String, BatchOrderList> orderMap = new LinkedHashMap<String, BatchOrderList>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) req;
		MultipartFile multipartFile = multipartRequest.getFile("file");
		ModelMap map = null;
		if (multipartFile == null) {
			map = new ModelMap();
			map.addAttribute("status", Boolean.FALSE);
			map.addAttribute("msg", "请选择上传文件！！！");
			return map;
		}
		String batchType = req.getParameter("batchType");
		try {
			CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
			if (cf != null && cf.getSize() > 0) {
				DiskFileItem fi = (DiskFileItem) cf.getFileItem();
				File file = fi.getStoreLocation();
				XlsReadFile xls = new XlsReadFile();
				InputStream inputStream = new FileInputStream(file);
				map = xls.readOrderExcel(inputStream, multipartFile.getOriginalFilename(), orderMap, batchType);
			}
			if ((boolean) map.get("status") == false) {
				return map;
			}
			for (Iterator<String> it = orderMap.keySet().iterator(); it.hasNext();) {
				Object key = it.next();
				orderList.addLast(orderMap.get(key));
			}
		} catch (Exception e) {

		}
		if (batchType.equals("openAccount")) {
			JedisClusterUtils.getInstance().setex(OrderConstants.bathOpenAccountSession, JSON.toJSONString(orderList),
					1800);
			// req.getSession().setAttribute(OrderConstants.bathOpenAccountSession,
			// orderList);
		}
		if (batchType.equals("openCard")) {
			JedisClusterUtils.getInstance().setex(OrderConstants.bathOpenCardSession, JSON.toJSONString(orderList),
					1800);
			// req.getSession().setAttribute(OrderConstants.bathOpenCardSession,
			// orderList);
		}
		if (batchType.equals("recharge")) {
			JedisClusterUtils.getInstance().setex(OrderConstants.bathRechargeSession, JSON.toJSONString(orderList),
					1800);
			// req.getSession().setAttribute(OrderConstants.bathRechargeSession,
			// orderList);
		}
		if (batchType.equals("openWBAccount")) {
			LinkedList<WithdrawBlacklist> wbList = new LinkedList<WithdrawBlacklist>();
			for (BatchOrderList batchOrderList : orderList) {
				WithdrawBlacklist wb = new WithdrawBlacklist();
				wb.setPuid(batchOrderList.getPuid());
				wb.setUserName(batchOrderList.getUserName());
				wb.setUserPhone(batchOrderList.getPhoneNo());
				wbList.add(wb);
			}
			JedisClusterUtils.getInstance().setex(WBConstants.bathOpenWBSession, JSON.toJSONString(wbList), 1800);
			// req.getSession().setAttribute(OrderConstants.bathRechargeSession,
			// orderList);
		}
		return map;
	}
}
