package com.cn.thinkx.wecard.api.module.telephone.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cn.thinkx.common.activemq.service.RechargeMobileProducerService;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5SignUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.telephone.service.ApiRechargeMobileService;
import com.cn.thinkx.wecard.api.module.telephone.valid.ApiRechangeMobileValid;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleReqVO;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespDomain;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespVO;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.utils.ResultsUtil;

@RestController
@RequestMapping("/api/recharge/mobile")
public class ApiRechangeMobileController {

	private Logger logger = LoggerFactory.getLogger(ApiRechangeMobileController.class);

	@Autowired
	@Qualifier("apiRechargeMobileService")
	private ApiRechargeMobileService apiRechargeMobileService;

	@Autowired
	@Qualifier("telChannelInfFacade")
	private TelChannelInfFacade telChannelInfFacade;

	@Autowired
	@Qualifier("telChannelOrderInfFacade")
	private TelChannelOrderInfFacade telChannelOrderInfFacade;

	@Autowired
	@Qualifier("rechargeMobileProducerService")
	private RechargeMobileProducerService rechargeMobileProducerService;

	@Autowired
	@Qualifier("telProviderOrderInfFacade")
	private TelProviderOrderInfFacade telProviderOrderInfFacade;

	/**
	 * 分销商发起手机充值
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	@ResponseBody
	public TeleRespDomain payment(HttpServletRequest request, TeleReqVO reqVo) {
		try {
			return apiRechargeMobileService.payment(reqVo);
		} catch (Exception e) {
			logger.error("## 手机充值接口调用异常 订单{}", reqVo.toString(), e);
		}
		return ResultsUtil.error("110101", "参数不合法");
	}

	/**
	 * 分销商发起的充值订单状态
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getOrder", method = RequestMethod.POST)
	@ResponseBody
	public TeleRespDomain getOrder(HttpServletRequest request, TeleReqVO reqVo) {

		try {
			TelChannelInf telChannelInf = telChannelInfFacade.getTelChannelInfById(reqVo.getChannelId());
			if (!ApiRechangeMobileValid.rechargeSignValid(reqVo, telChannelInf.getChannelKey())) {
				return ResultsUtil.error("110102", "token验证失败");
			}
			TelChannelOrderInf telChannelOrderInf = null;
			if (StringUtil.isNotEmpty(reqVo.getChannelOrderId())) {
				telChannelOrderInf = telChannelOrderInfFacade.getTelChannelOrderInfById(reqVo.getChannelOrderId());
			} else {
				telChannelOrderInf = telChannelOrderInfFacade.getTelChannelOrderInfByOuterId(reqVo.getOuterTid(),
						reqVo.getChannelId());
			}

			if (telChannelOrderInf != null) {
				TelProviderOrderInf telProviderOrderInf = telProviderOrderInfFacade.getTelOrderInfByChannelOrderId(telChannelOrderInf.getChannelOrderId());
				TeleRespVO respVo = new TeleRespVO();
				respVo.setSaleAmount(telChannelOrderInf.getPayAmt().toString());
				respVo.setChannelOrderId(telChannelOrderInf.getChannelOrderId());
				respVo.setPayState(telChannelOrderInf.getOrderStat());
				respVo.setRechargeState(telProviderOrderInf.getRechargeState());
				respVo.setOrderTime(DateUtil.COMMON_FULL.getDateText(telChannelOrderInf.getCreateTime()));
				if (telProviderOrderInf.getOperateTime() != null) {
					respVo.setOperateTime(DateUtil.COMMON_FULL.getDateText(telProviderOrderInf.getOperateTime()));
				}
				respVo.setFacePrice(telChannelOrderInf.getRechargeValue().toString());
				respVo.setItemNum(telChannelOrderInf.getItemNum());
				respVo.setOuterTid(telChannelOrderInf.getOuterTid());
				respVo.setChannelId(reqVo.getChannelId());
				respVo.setChannelToken(reqVo.getChannelToken());
				respVo.setV(reqVo.getV());
				respVo.setTimestamp(DateUtil.COMMON_FULL.getDateText(new Date()));
				respVo.setMethod(reqVo.getMethod());
				respVo.setSubErrorCode(telProviderOrderInf.getResv1());
				String retSign = MD5SignUtils.genSign(respVo, "key", telChannelInf.getChannelKey(),
						new String[] { "sign", "serialVersionUID" }, null);
				respVo.setSign(retSign);
				return ResultsUtil.success(respVo);
			} else {
				return ResultsUtil.error("110200", "渠道订单不存在");
			}
		} catch (Exception ex) {
			return ResultsUtil.error("110101", "参数不合法");
		}

	}

}
