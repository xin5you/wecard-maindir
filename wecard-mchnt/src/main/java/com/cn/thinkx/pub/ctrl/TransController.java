package com.cn.thinkx.pub.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.common.redis.util.BaseKeyUtil;
import com.cn.thinkx.core.ctrl.BaseController;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.Constants.ChannelCode;
import com.cn.thinkx.core.util.Constants.TransCode;
import com.cn.thinkx.customer.domain.UserMerchantAcct;
import com.cn.thinkx.customer.service.UserMerchantAcctService;
import com.cn.thinkx.pms.base.utils.DES3Util;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.service.drools.WxDroolsExcutor;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wxclient.domain.WxTransLog;
import com.cn.thinkx.wxclient.service.CtrlSystemService;
import com.cn.thinkx.wxclient.service.WxTransLogService;

@Controller
@RequestMapping(value = "/pay")
public class TransController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;

	@Autowired
	private WxDroolsExcutor wxDroolsExcutor;

	@Autowired
	private WxTransLogService wxTransLogService;

	@Autowired
	private UserMerchantAcctService userMerchantAcctService;

	/** 消费交易-插入微信端流水 Created by Pucker 2016/7/20 **/
	@RequestMapping(value = "/insertWxTransLog")
	@ResponseBody
	public WxTransLog insertWxTransLog(HttpServletRequest request) {
		String oprOpenid = WxMemoryCacheClient.getOpenid(request);
		String sponsor = request.getParameter("sponsor");
		String openid = request.getParameter("openid");// 商户扫描用户二维码时得到的用户openid(已动态加密)
		String merchantCode = request.getParameter("merchantCode");
		String shopCode = request.getParameter("shopCode");
		String insCode = request.getParameter("insCode");
		String money = request.getParameter("money");

		try {
			openid = DES3Util.Decrypt3DES(openid, BaseKeyUtil.getEncodingAesKey());
			openid = StringUtil.trim(openid.substring(8, openid.length()));
		} catch (Exception e) {
			logger.error("## 二维码解密生成失败", e);
			return null;
		}

		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		WxTransLog log = new WxTransLog();
		if (cs != null) {
			if (Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
				String id = wxTransLogService.getPrimaryKey();
				log.setWxPrimaryKey(id);
				log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
				log.setSettleDate(cs.getSettleDate());// 交易日期
				log.setTransId(TransCode.CW10.getCode());// 交易类型
				log.setTransSt(0);// 插入时为0，报文返回时更新为1
				log.setInsCode(insCode);// 客户端传过来的机构code
				log.setMchntCode(merchantCode);
				log.setShopCode(shopCode);
				log.setSponsor(sponsor);
				log.setOperatorOpenId(oprOpenid);
				log.setTransChnl(ChannelCode.CHANNEL1.toString());
				log.setUserInfUserName(openid);
				money = "" + NumberUtils.disRatehundred(Double.parseDouble(money));// 原交易金额单位元转分
				log.setTransAmt(money);// 实际交易金额 插入时候默认与上送金额一致
				log.setUploadAmt(money);// 上送金额
				log.setTransCurrCd(Constants.TRANS_CURR_CD);
				int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
				if (i != 1) {
					logger.error("## 消费交易-插入微信端流水 微信端插入流水记录数量不为1");
					return null;
				}
			} else {
				logger.error("## 消费交易-插入微信端流水 日切信息交易允许状态：日切中");
				return null;
			}
		}
		return log;
	}

	/** 根据主键得到流水 Created by Pucker 2016/8/2 **/
	@RequestMapping(value = "/getWxTransLogById")
	@ResponseBody
	public WxTransLog getWxTransLogById(HttpServletRequest request) {
		String wxPrimaryKey = request.getParameter("wxPrimaryKey");
		WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);
		CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
		if (cs != null) {
			log.setTableNum(cs.getCurLogNum());
			log.setSettleDate(cs.getSettleDate());
		} else {
			logger.error("## getWxTransLogById 日切信息交易允许状态：日切中");
		}
		return log;
	}

	/** 消费交易-根据规则引擎计算后的金额判断客户是否需要输入密码 Created by Pucker 2016/8/1 **/
	@RequestMapping(value = "/doCustomerNeed2EnterPassword")
	@ResponseBody
	public TxnResp doCustomerNeed2EnterPassword(HttpServletRequest request) {
		TxnResp resp = new TxnResp();

		String merchantCode = request.getParameter("merchantCode");
		String openid = request.getParameter("cOpenid");
		String uploadAmt = request.getParameter("uploadAmt");
		String wxPrimaryKey = request.getParameter("wxPrimaryKey");
		String tableNum = request.getParameter("tableNum");

		UserMerchantAcct entity = new UserMerchantAcct();
		try {
			openid = DES3Util.Decrypt3DES(openid, BaseKeyUtil.getEncodingAesKey());
			openid = StringUtil.trim(openid.substring(8, openid.length()));
			entity.setExternalId(openid);
		} catch (Exception e) {
			logger.error("## 二维码解密生成失败，openid[{}]", openid, e);
			return resp;
		}
		entity.setMchntCode(merchantCode);
		List<UserMerchantAcct> list = null;

		try {
			list = userMerchantAcctService.getUserMerchantAcctByUser(entity);// 获取无pin限额视图
		} catch (Exception e) {
			logger.error("## 获取无pin限额视图出错，openid[{}]", openid, e);
			wxTransLogService.updateWxTransLog(tableNum, wxPrimaryKey, Constants.TXN_TRANS_EXCEPTION, uploadAmt);
			return resp;
		}

		if (list != null && list.size() > 0) {
			entity = list.get(0);
			resp.setUserId(entity.getUserId());
			try {
				int noPinTxnAmt = Integer.parseInt(entity.getNopinTxnAmt());
				int oriTxnAmount = Integer.parseInt(uploadAmt);
				int transAmt = wxDroolsExcutor.getConsumeDiscount(entity.getMchntId(), null, oriTxnAmount);// 调用规则引擎
				resp.setTransAmt("" + transAmt);
				if (transAmt > noPinTxnAmt) {// 如果实际消费金额大于无PIN限额 需要验密
					resp.setCode("1");// 需要密码
				} else {
					resp.setCode("0");// 不需要密码
				}
			} catch (Exception e) {
				logger.error("## 远程服务调用规则引擎出错，openid[{}]", openid, e);
				wxTransLogService.updateWxTransLog(tableNum, wxPrimaryKey, Constants.TXN_TRANS_EXCEPTION, uploadAmt);
				return resp;
			}
		} else {
			logger.info("openid[{}]无卡号", openid);
			// 更新微信端流水
			wxTransLogService.updateWxTransLog(tableNum, wxPrimaryKey, Constants.TXN_TRANS_RESP_INVALID_CARD, uploadAmt);
		}
		return resp;
	}

}
