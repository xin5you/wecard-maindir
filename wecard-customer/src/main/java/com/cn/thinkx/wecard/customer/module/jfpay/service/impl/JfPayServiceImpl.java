package com.cn.thinkx.wecard.customer.module.jfpay.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.common.service.module.jiafupay.constants.Constants.JFRespCode;
import com.cn.thinkx.common.service.module.jiafupay.service.JFPayService;
import com.cn.thinkx.common.service.module.jiafupay.vo.JFChnlReq;
import com.cn.thinkx.common.service.module.jiafupay.vo.JFChnlResp;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.wecard.customer.module.customer.service.CtrlSystemService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.jfpay.service.JfPayService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

@Service("jFPayService")
public class JfPayServiceImpl implements JfPayService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("jfPayService")
	private JFPayService jfPayService;
	
	@Autowired
	@Qualifier("userInfService")
	private UserInfService userInfService;
	
	@Autowired
	private WxTransLogService wxTransLogService;
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;
	
	@Override
	public TxnResp scanCodeJava2JFBusiness(HttpServletRequest request) {
		TxnResp resp = new TxnResp();
		
		String wxPrimaryKey = request.getParameter("wxPrimaryKey");
		String transAmt = request.getParameter("transAmt");
		String tableNum = request.getParameter("tableNum");
		String jfUserID = request.getParameter("JFUserId");
		String userID = request.getParameter("HKBUserId");
		String payType = request.getParameter("payType");
		
		UserInf user = userInfService.getUserInfById(userID);
		if (user == null) {
//			mv = new ModelAndView("redirect:/customer/user/userRegister.html");
//			return mv;
		}
		
		if ("JFBENEFIT_PAY".equals(payType)) {
			payType = "benefit";
		} else if ("JFSALARY_PAY".equalsIgnoreCase(payType)) {
			payType = "salary";
		}
		
		//调用嘉福消费接口
		String json = new String();
		JFChnlResp  jfChnlResp = new JFChnlResp();
		JFChnlReq req = new JFChnlReq();
		req.setTxnAmount(transAmt);
		req.setJfUserId(jfUserID);
		req.setSwtFlowNo(wxPrimaryKey);
		req.setPayType(payType);
		try {
			json = jfPayService.doPayMentTrans(req);
			jfChnlResp = JSONArray.parseObject(json, JFChnlResp.class);
		} catch (Exception e) {
			logger.error("## 远程调用嘉福支付接口异常，返回json--->[{}],流水号---->[{}]", json,wxPrimaryKey, e);
		}
		try {
			String respCode = null;
			if(jfChnlResp == null){
				resp = new TxnResp();
				resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
				resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
				logger.error("## 远程调用嘉福消费接口返回值为空，流水号[{}]", wxPrimaryKey);
			}
			if (JFRespCode.R00.getCode().equals(jfChnlResp.getCode()) || JFRespCode.R09.getCode().equals(jfChnlResp.getCode())) {
				respCode = ITFRespCode.CODE00.getCode();
			} else if (JFRespCode.R01.getCode().equals(jfChnlResp.getCode())) {
				respCode = ITFRespCode.CODE51.getCode();
			} else if (JFRespCode.R07.getCode().equals(jfChnlResp.getCode())) {
				respCode = ITFRespCode.CODE94.getCode();
			} else if (JFRespCode.R08.getCode().equals(jfChnlResp.getCode())) {
				respCode = ITFRespCode.CODE25.getCode();
			} else {
				respCode = BaseConstants.RESPONSE_EXCEPTION_CODE;
			}
			
			resp.setCode(respCode);
			resp.setInfo(ITFRespCode.findByCode(respCode).getValue());
			resp.setTransAmt(transAmt);
			
			WxTransLog updateLog = new WxTransLog();
			updateLog.setTableNum(tableNum);
			updateLog.setTransAmt(transAmt);
			updateLog.setWxPrimaryKey(wxPrimaryKey);
			updateLog.setRespCode(respCode);
			updateLog.setDmsRelatedKey(jfChnlResp.getSwtFlowNo());
			wxTransLogService.updateWxTransLog(updateLog,null);
		} catch (Exception e) {
			logger.error("## 消费交易更新微信流水异常", e);
		}
		
		return resp;
	}
	
	@Override
	public WxTransLog insertWxTransLog(HttpServletRequest request) {
		String sponsor = request.getParameter("sponsor");
		String merchantCode = request.getParameter("merchantCode");
		String shopCode = request.getParameter("shopCode");
		String insCode = request.getParameter("insCode");
		String transMoney = request.getParameter("money");
		String jfUserId = request.getParameter("JFUserId");

		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs == null) {
			logger.error("## insertWxTransLog--->日切信息为空");
			return null;
		}

		WxTransLog log = new WxTransLog();
		if (!BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为不允许时返回空值
			logger.error("## 日切信息交易允许状态：日切中");
			return null;
		}

		String id = wxTransLogService.getPrimaryKey();
		log.setWxPrimaryKey(id);
		log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
		log.setSettleDate(cs.getSettleDate());// 交易日期
		log.setTransId(TransCode.CW71.getCode());// 交易类型 微信快捷支付
		log.setTransChnl(ChannelCode.CHANNEL4.toString());
		log.setTransSt(0);// 插入时为0，报文返回时更新为1
		log.setInsCode(insCode);// 客户端传过来的机构code
		log.setMchntCode(merchantCode);
		log.setShopCode(shopCode);
		log.setSponsor(sponsor);
		log.setUserInfUserName(jfUserId);
		transMoney = NumberUtils.RMBYuanToCent(transMoney);// 原交易金额单位元转分
		log.setTransAmt(transMoney);// 实际交易金额 插入时候默认与上送金额一致
		log.setUploadAmt(transMoney);// 上送金额
		log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
		int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
		if (i != 1) {
			logger.error("## 微信端插入流水记录数量不为1");
			return null;
		}
		return log;
	}

}
