package com.cn.thinkx.oms.module.trans.controller;

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

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.statement.util.UploadUtil;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransInf;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLog;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLogUpload;
import com.cn.thinkx.oms.module.trans.service.PayChannelTransLogService;
import com.cn.thinkx.oms.util.ChannelCodeUtils;
import com.cn.thinkx.oms.util.ExcelUtil;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.OMSChannel;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.OMSChannelCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "trans/payChannelTransInf")
public class PayChannelTransLogController extends BaseController {

	Logger logger = LoggerFactory.getLogger(PayChannelTransLogController.class);

	@Autowired
	@Qualifier("payChannelTransLogService")
	private PayChannelTransLogService payChannelTransLogService;
	
	/**
	 * 商户交易列表查询
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listTrans")
	public ModelAndView listTrans(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("trans/payChannelTrans/listPayChannelTrans");

		PageInfo<PayChannelTransLog> pageList = null;
		List<ChannelCodeUtils> codeList = new ArrayList<ChannelCodeUtils>();
		for (OMSChannelCode omsChannel : OMSChannelCode.values()) {
			ChannelCodeUtils ccu = new ChannelCodeUtils(omsChannel.getCode(), omsChannel.getValue());
			codeList.add(ccu);
		}
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		PayChannelTransInf entity = getWxTransInf(req);
		try {
			pageList = payChannelTransLogService.getWxTransLogPage(startNum, pageSize, entity);
			for (PayChannelTransLog transLog : pageList.getList()) {
				String chnlName = BaseIntegrationPayConstants.OMSChannelCode.findOMSChannelCodeByCode(transLog.getTransChnl());
				transLog.setChnlName(chnlName);
			}
		} catch (Exception e) {
			logger.error("## 查询商户交易列表出错", e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("transInf", entity);
		mv.addObject("transTypeList", BaseIntegrationPayConstants.TransCode.values());
		mv.addObject("ChannelCodeList", codeList);
		return mv;
	}

	/**
	 * 导出excle表格
	 * 
	 * @param req
	 * @param response
	 */
	@RequestMapping(value = "/uploadListTrans")
	public void uploadListTrans(HttpServletRequest req, HttpServletResponse response) {
		try {
			PayChannelTransInf entity = getWxTransInf(req);
			List<PayChannelTransLogUpload> listTransLog = payChannelTransLogService.getWxTransLogList(entity);
			for (PayChannelTransLogUpload transLogUpload : listTransLog) {
				String chnlName = BaseIntegrationPayConstants.OMSChannelCode.findOMSChannelCodeByCode(transLogUpload.getTransChnl());
				transLogUpload.setChnlName(chnlName);
			}
			String title = "通道交易明细列表";
			String titlerow = "通道交易报表";
			String[] titlehead = new String[] { "流水号", "清算日", "渠道", "商户名称", "通道名称","设备号", "交易类型", "交易金额",
					"交易状态", "交易时间", "交易渠道","操作" };
			
			ExcelUtil<PayChannelTransLogUpload> ex = new ExcelUtil<PayChannelTransLogUpload>();
			HSSFWorkbook workBook = ex.exportExcel(title, titlerow,
					DateUtil.getFormatStringFormString(entity.getStartTime(), DateUtil.FORMAT_YYYY_MM_DD),
					DateUtil.getFormatStringFormString(entity.getEndTime(), DateUtil.FORMAT_YYYY_MM_DD), titlehead,
					null, null, listTransLog, PayChannelTransLogUpload.class, null);
			UploadUtil.upLoad(workBook, title, response);
		} catch (Exception e) {
			logger.error("## 导出通道汇总报表出错", e);
		}
	}

	/**
	 * 退款
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value="refund")
	@ResponseBody
	public ModelMap refund(HttpServletRequest req){
		ModelMap resultMap = new ModelMap();
		String resultStr = payChannelTransLogService.getRefundTransactionITF(req);
		resultMap = JSONArray.parseObject(resultStr, ModelMap.class);
		return resultMap;
	}
	
	
	/**
	 * 封装TransInf实体
	 * 
	 * @param req
	 * @return
	 */
	public PayChannelTransInf getWxTransInf(HttpServletRequest req) {
		PayChannelTransInf entity = new PayChannelTransInf();
		entity.setMchntCode(StringUtils.nullToString(req.getParameter("mchntCode")));
		entity.setShopCode(StringUtils.nullToString(req.getParameter("shopCode")));
		entity.setMchntName(StringUtils.nullToString(req.getParameter("mchntName")));
		entity.setShopName(StringUtils.nullToString(req.getParameter("shopName")));
		String channelCode = req.getParameter("channelCode");
		entity.setOmschannelCode(channelCode);
		entity.setChannelCode(channelCode);
//		if (!"".equals(channelCode) && channelCode != null) {
//			String chnlName = BaseIntegrationPayConstants.OMSChannelCode.findOMSChannelCodeByCode(channelCode);
//			entity.setChannelCode(chnlName.split(","));
//		}
		entity.setQueryType(StringUtils.nullToString(req.getParameter("queryType")));
		entity.setTransType(StringUtils.nullToString(req.getParameter("transType")));
		entity.setStartTime(StringUtils.nullToString(req.getParameter("startTime")));
		entity.setEndTime(StringUtils.nullToString(req.getParameter("endTime")));
		return entity;
	}
}
