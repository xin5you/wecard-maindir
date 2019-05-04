package com.cn.thinkx.oms.module.phoneRecharge.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeOrder;
import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeOrderUpload;
import com.cn.thinkx.oms.module.phoneRecharge.service.PhoneRechargeService;
import com.cn.thinkx.oms.module.statement.util.UploadUtil;
import com.cn.thinkx.oms.util.ExcelUtil;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "phone/phoneRecharge")
public class PhoneRechargeController extends BaseController {

	@Autowired
	@Qualifier("phoneRechargeService")
	private PhoneRechargeService phoneRechargeService;

	/**
	 * 手机充值订单交易明细列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPhoneRechargeList")
	public ModelAndView getPhoneRechargeList(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("trans/phoneRecharge/listPhoneRecharge");
		PageInfo<PhoneRechargeOrder> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		PhoneRechargeOrder pro = this.getPhoneRechargeOrder(req);
		pageList = phoneRechargeService.getPhoneRechargeListPage(startNum, pageSize, pro);
		mv.addObject("phoneRechargeOrder", pro);
		mv.addObject("pageInfo", pageList);
		mv.addObject("supplierList", BaseConstants.phoneRechargeSupplier.values());
		mv.addObject("transStatList", BaseConstants.phoneRechargeTransStat.values());
		mv.addObject("orderTypeList", BaseConstants.phoneRechargeOrderType.values());
		mv.addObject("reqChannelList", BaseConstants.phoneRechargeReqChnl.values());
		return mv;
	}

	/**
	 * 导出excle表格
	 * 
	 * @param req
	 * @param response
	 */
	@RequestMapping(value = "/uploadListPhoneRecharge")
	public void uploadListPhoneRecharge(HttpServletRequest req, HttpServletResponse response) {
		try {
			PhoneRechargeOrder pro = this.getPhoneRechargeOrder(req);
			List<PhoneRechargeOrderUpload> listProLog = phoneRechargeService.getPhoneRechargeList(pro);

			String title = "手机充值交易明细列表";
			String titlerow = "手机充值交易报表";
			String[] titlehead = new String[] { "订单号", "供应商订单号", "交易流水号", "用户名", "会员手机号", "供应商", "手机充值号码", "充值金额（元）",
					"交易金额（元）", "优惠金额（元）", "供应商价格（元）", "流量面额（元）", "交易状态", "订单类型", "支付渠道", "交易时间" };
			ExcelUtil<PhoneRechargeOrderUpload> ex = new ExcelUtil<PhoneRechargeOrderUpload>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow, pro.getStartTime(), pro.getEndTime(), titlehead,
					null, null, listProLog, PhoneRechargeOrderUpload.class, null);
			UploadUtil.upLoad(workBook, title, response);
		} catch (Exception e) {
			logger.error("## 手机充值交易报表出错", e);
		}
	}
	
	/**
	 * 手机充值退款
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/refundPhoneRecharge")
	@ResponseBody
	public ModelMap refundPhoneRecharge(HttpServletRequest req){
		ModelMap resultMap = new ModelMap();
		String rId = StringUtils.nullToString(req.getParameter("rId"));
		String resultStr = phoneRechargeService.doPhoneRechargeRefund(rId);
		resultMap = JSONArray.parseObject(resultStr, ModelMap.class);
		return resultMap;
	}

	/**
	 * 封装手机充值订单表
	 * 
	 * @param req
	 * @return
	 */
	public PhoneRechargeOrder getPhoneRechargeOrder(HttpServletRequest req) {
		PhoneRechargeOrder pro = new PhoneRechargeOrder();
		pro.setrId(StringUtils.nullToString(req.getParameter("rId")));
		pro.setSupplierOrderNo(StringUtils.nullToString(req.getParameter("supplierOrderNo")));
		pro.setChannelOrderNo(StringUtils.nullToString(req.getParameter("channelOrderNo")));
		pro.setMobilePhoneNo(StringUtils.nullToString(req.getParameter("mobilePhoneNo")));
		pro.setPersonalName(StringUtils.nullToString(req.getParameter("personalName")));
		pro.setPhone(StringUtils.nullToString(req.getParameter("phone")));
		pro.setSupplier(StringUtils.nullToString(req.getParameter("supplier")));
		pro.setTransStat(StringUtils.nullToString(req.getParameter("transStat")));
		pro.setOrderType(StringUtils.nullToString(req.getParameter("orderType")));
		pro.setReqChannel(StringUtils.nullToString(req.getParameter("reqChannel")));
		pro.setStartTime(StringUtils.nullToString(req.getParameter("startTime")));
		pro.setEndTime(StringUtils.nullToString(req.getParameter("endTime")));
		return pro;
	}

}
