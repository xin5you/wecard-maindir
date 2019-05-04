package com.cn.thinkx.wecard.api.module.pay.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.pay.req.PPScanCodeReq;
import com.cn.thinkx.wecard.api.module.pay.resp.PPScanCodeResp;
import com.cn.thinkx.wecard.api.module.pay.service.PPBoxPayService;
import com.cn.thinkx.wecard.api.module.pay.utils.PPBoxConstants;
import com.cn.thinkx.wecard.api.module.pay.utils.PPRequestJsonUtil;

/**
 * 扫描盒子支付请求
 * 
 * @author zqy
 *
 */
@Controller
@RequestMapping("/pay/ppbox")
public class PPBoxScanPayController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("ppboxPayService")
	private PPBoxPayService ppboxPayService;

	/**
	 * 提交扫码
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/scancode")
	@ResponseBody
	public PPScanCodeResp scancode(HttpServletRequest request) {
		StopWatch sw = new StopWatch();
		sw.start();
		PPScanCodeResp resp = new PPScanCodeResp();
		String reqJsonStr = "";
		PPScanCodeReq scanReq = null;
		try {
			reqJsonStr = PPRequestJsonUtil.getRequestJsonString(request);
			logger.info("PPbox扫码支付请求参数 [{}]", reqJsonStr);
			if (StringUtil.isNullOrEmpty(reqJsonStr)) { // 如果参数没有传入，直接返回失败
				resp.setCode(PPBoxConstants.PPBoxCode.FAIL.getValue());
				resp.setSub_code(PPBoxConstants.PPBoxSubCode.INVALID_PARAMETER.getCode());
				resp.setSub_msg(PPBoxConstants.PPBoxSubCode.INVALID_PARAMETER.getValue());
				return resp;
			}
			scanReq = JSONArray.parseObject(reqJsonStr, PPScanCodeReq.class);
		} catch (IOException ex) {
			logger.error("## 扫码支付请求参数json格式数据解析失败", ex);
		}
		
		String resultStr = null;
		try {
			resultStr = ppboxPayService.doPPScanTrans(scanReq, request);
		} catch (Exception e) {
			logger.error("## 盒子扫码支付失败", e);
		}

		logger.info("PPbox扫码支付返回结果[{}]", resultStr);
		resp = (PPScanCodeResp) JSONArray.parseObject(resultStr, PPScanCodeResp.class);
		sw.stop();
		logger.info("PPbox扫码支付交易总共耗时[{}ms]", sw.getTime());
		return resp;
	}

}
