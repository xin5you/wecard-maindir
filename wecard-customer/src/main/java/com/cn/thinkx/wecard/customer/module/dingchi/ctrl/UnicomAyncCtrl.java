package com.cn.thinkx.wecard.customer.module.dingchi.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.dingchi.service.UnicomAyncService;
import com.cn.thinkx.wecard.customer.module.dingchi.vo.UnicomAyncReq;
import com.cn.thinkx.wecard.customer.module.dingchi.vo.UnicomAyncResp;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

@Controller
@RequestMapping("/unicomAync")
public class UnicomAyncCtrl extends BaseController{
	Logger logger = LoggerFactory.getLogger(UnicomAyncCtrl.class);

	@Autowired
	@Qualifier("unicomAyncService")
	private UnicomAyncService unicomAyncService;

	@RequestMapping(value = "/buy")
	public ModelAndView buy(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("welfaremart/cardin/inFail");

		//调用知了企服支付，成功返回交易流水、交易时间传入req
		String json = unicomAyncService.hkbPayment(request);
		if (StringUtil.isNullOrEmpty(json)) {
			logger.error("## 知了企服支付接口返回json为空");
			return mv;
		}
		TxnResp txnResp = JSONArray.parseObject(json, TxnResp.class);
		
		UnicomAyncReq voReq = new UnicomAyncReq();
		voReq.setSerialno(txnResp.getWxPrimaryKey());
		voReq.setDtCreate(DateUtil.getCurrentDateStr(DateUtil.FORMAT_YYYYMMDDHHMMSS));

		if (!ITFRespCode.CODE00.getCode().equals(txnResp.getCode())) {
			logger.error("## 知了企服支付接口hkbPayment()返回[{}]", txnResp.getCode());
			return mv;
		}

		// 通过HTTP GET请求
		UnicomAyncResp voResp = unicomAyncService.buy(voReq, request);
		logger.info("鼎驰返回支付信息[{}]", JSONArray.toJSONString(voResp));
		
		if (StringUtil.isNullOrEmpty(voResp) || !"00".equals(voResp.getCode())) {
			String refundJson = unicomAyncService.hkbRefund(null, txnResp.getInterfacePrimaryKey());
			logger.info("知了企服退款接口返回信息[{}]", refundJson);
			return mv;
		}
		return new ModelAndView("welfaremart/cardin/cardSuccess");
	}

	@RequestMapping("/hkbQuery")
	public UnicomAyncResp hkbQuery(HttpServletRequest request) {
		return unicomAyncService.hkbQuery(request);
	}

	@RequestMapping("/hkbNotify")
	public String hkbNotify(HttpServletRequest request) {
		return unicomAyncService.hkbNotify(request);
	}
	
	/*@RequestMapping("/mobileSign")
	public List<CardKeysProduct> mobileSign(HttpServletRequest request) {
		return unicomAyncService.mobileSign(request);
	}*/

}
