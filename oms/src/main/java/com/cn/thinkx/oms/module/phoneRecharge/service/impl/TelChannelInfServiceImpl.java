package com.cn.thinkx.oms.module.phoneRecharge.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.phoneRecharge.service.TelChannelInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5SignUtils;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelItemList;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespVO;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelItemListFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.utils.ResultsUtil;
import com.cn.thinkx.wecard.facade.telrecharge.utils.TeleConstants;
import com.cn.thinkx.wecard.facade.telrecharge.utils.TeleConstants.ReqMethodCode;

@Service("telChannelInfService")
public class TelChannelInfServiceImpl implements TelChannelInfService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TelChannelOrderInfFacade telChannelOrderInfFacade;

	@Autowired
	private TelChannelInfFacade telChannelInfFacade;

	@Autowired
	private TelProviderOrderInfFacade telProviderOrderInfFacade;
	
	@Autowired
	private TelChannelItemListFacade telChannelItemListFacade;

	@Override
	public ModelMap doCallBackNotifyChannel(String channelOrderId) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		// 回调通知分销商
		try {
			if (StringUtils.isNullOrEmpty(channelOrderId)) {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "该订单回调异常，请联系管理员");
				logger.error("## 该分销商订单回调异常，分销商订单号channelOrderId:[{}]是空", channelOrderId);
				return resultMap;
			}
			TelChannelOrderInf telChannelOrderInf = telChannelOrderInfFacade.getTelChannelOrderInfById(channelOrderId);
			if (StringUtils.isNullOrEmpty(telChannelOrderInf.getNotifyUrl())) {
				resultMap.addAttribute("status", Boolean.FALSE);
				resultMap.addAttribute("msg", "该订单不能回调，回调地址是空");
				logger.error("## 该分销商订单不能回调，回调地址是空NotifyUrl:[{}]", telChannelOrderInf.getNotifyUrl());
				return resultMap;
			}
			TelChannelInf telChannelInf = telChannelInfFacade.getTelChannelInfById(telChannelOrderInf.getChannelId());

			TelProviderOrderInf telProviderOrderInf = telProviderOrderInfFacade
					.getTelOrderInfByChannelOrderId(channelOrderId);
			// 异步通知供应商
			TeleRespVO respVo = new TeleRespVO();
			respVo.setSaleAmount(telChannelOrderInf.getPayAmt().toString());
			respVo.setChannelOrderId(telChannelOrderInf.getChannelOrderId());
			respVo.setPayState(telChannelOrderInf.getOrderStat());
			respVo.setRechargeState(telProviderOrderInf.getRechargeState()); // 充值状态
			if (telProviderOrderInf.getOperateTime() != null) {
				respVo.setOperateTime(DateUtil.COMMON_FULL.getDateText(telProviderOrderInf.getOperateTime()));
			}
			respVo.setOrderTime(DateUtil.COMMON_FULL.getDateText(telChannelOrderInf.getCreateTime())); // 操作时间
			respVo.setFacePrice(telChannelOrderInf.getRechargeValue().toString());
			respVo.setItemNum(telChannelOrderInf.getItemNum());
			respVo.setOuterTid(telChannelOrderInf.getOuterTid());
			respVo.setChannelId(telChannelOrderInf.getChannelId());
			respVo.setChannelToken(telChannelInf.getChannelCode());
			respVo.setV(telChannelOrderInf.getAppVersion());
			respVo.setTimestamp(DateUtil.COMMON_FULL.getDateText(new Date()));
			respVo.setSubErrorCode(telProviderOrderInf.getResv1());

			if ("1".equals(telChannelOrderInf.getRechargeType())) {
				respVo.setMethod(ReqMethodCode.R1.getValue());
			} else if ("2".equals(telChannelOrderInf.getRechargeType())) {
				respVo.setMethod(ReqMethodCode.R2.getValue());
			}
			String psotToken = MD5SignUtils.genSign(respVo, "key", telChannelInf.getChannelKey(),
					new String[] { "sign", "serialVersionUID" }, null);
			respVo.setSign(psotToken);

			// 修改通知后 分销商的处理状态
			logger.info("##发起分销商回调[{}],返回参数:[{}]", telChannelOrderInf.getNotifyUrl(),
					JSONObject.toJSONString(ResultsUtil.success(respVo)));
			String result = HttpClientUtil.sendPost(telChannelOrderInf.getNotifyUrl(),
					JSONObject.toJSONString(ResultsUtil.success(respVo)));
			logger.info("## 发起分销商回调,返回结果:[{}]", result);
			if (result != null && "SUCCESS ".equals(result.toUpperCase())) {
				telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_3.getCode());
			} else {
				telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_2.getCode());
			}
			telChannelOrderInfFacade.updateTelChannelOrderInf(telChannelOrderInf);
		} catch (Exception ex) {
			logger.error("## 手机充值 回调通知分销商异常-->{}", ex);
		}
		return resultMap;
	}

	@Override
	public boolean addTelChannelRate(HttpServletRequest req, String channelId,String channelRate, String ids) {
		try {
			String[] productIds = ids.split(",");
			for (int i = 0; i < productIds.length; i++) {
				TelChannelItemList telChannelItemList = new TelChannelItemList();
				HttpSession session = req.getSession();
				User user = (User) session.getAttribute(Constants.SESSION_USER);
				if (user != null) {
					telChannelItemList.setCreateUser(user.getId().toString());
					telChannelItemList.setUpdateUser(user.getId().toString());
				}
				telChannelItemList.setProductId(productIds[i]);
				telChannelItemList.setDataStat("0");
				telChannelItemList.setChannelRate(new BigDecimal(channelRate));
				telChannelItemList.setChannelId(channelId);
				telChannelItemListFacade.saveTelChannelItemList(telChannelItemList);
			}
		} catch (Exception e) {
			logger.error("## 楼层添加商品失败", e);
			return false;
		}
		return true;
	}

}
