package com.cn.thinkx.wecard.api.module.welfaremart.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransType;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderStat;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderType;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeReqChnl;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeys;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysOrderInf;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysProduct;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysTransLog;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysProductService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysTransLogService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.WelfareMartService;
import com.cn.thinkx.wecard.api.module.welfaremart.util.CardRechargeSignUtil;
import com.cn.thinkx.wecard.api.module.welfaremart.util.GenCardKeysUtil;
import com.cn.thinkx.wecard.api.module.welfaremart.valid.WelfareMartValid;
import com.cn.thinkx.wecard.api.module.welfaremart.vo.NotifyOrder;
import com.cn.thinkx.wecard.api.module.welfaremart.vo.PhoneRechargeNotifyReq;

import redis.clients.jedis.JedisCluster;

@Service("welfareMartService")
public class WelfareMartServiceImpl implements WelfareMartService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("cardKeysService")
	private CardKeysService cardKeysService;

	@Autowired
	@Qualifier("cardKeysTransLogService")
	private CardKeysTransLogService cardKeysTransLogService;

	@Autowired
	@Qualifier("cardKeysOrderInfService")
	private CardKeysOrderInfService cardKeysOrderInfService;


	@Autowired
	@Qualifier("cardKeysProductService")
	private CardKeysProductService cardKeysProductService;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;

	@Override
	public String welfareBuyCardNotify(HttpServletRequest request) {
		NotifyOrder notifyReq = new NotifyOrder();
		notifyReq.setChannel(request.getParameter("channel"));
		notifyReq.setRespResult(request.getParameter("respResult"));
		notifyReq.setInnerMerchantNo(request.getParameter("innerMerchantNo"));
		notifyReq.setInnerShopNo(request.getParameter("innerShopNo"));
		notifyReq.setUserId(request.getParameter("userId"));
		notifyReq.setOrderId(request.getParameter("orderId"));
		notifyReq.setSettleDate(request.getParameter("settleDate"));
		notifyReq.setTxnFlowNo(request.getParameter("txnFlowNo"));
		notifyReq.setOriTxnAmount(request.getParameter("oriTxnAmount"));
		notifyReq.setTxnAmount(request.getParameter("txnAmount"));
		notifyReq.setAttach(request.getParameter("attach"));
		notifyReq.setSign(request.getParameter("sign"));

		String isUpdateOrder = "FAIL";
		
		if (WelfareMartValid.buyCardNotifyValid(notifyReq))
			return isUpdateOrder;

		/** 查询卡密交易订单信息 */
		CardKeysOrderInf cko = cardKeysOrderInfService.getCardKeysOrderByOrderId(notifyReq.getOrderId());
		if(cko == null){
			logger.error("## 卡券集市--->购卡异步回调接口，查询用户[{}]卡密订单[{}]信息为空", notifyReq.getUserId(), notifyReq.getOrderId());
			return isUpdateOrder;
		} else {
			if (!cko.getType().equals(orderType.O1.getCode())) {
				logger.error("## 卡券集市--->购卡异步回调接口，用户[{}]卡密订单[{}]信息类型[{}]不是购买类型", notifyReq.getUserId(), notifyReq.getOrderId(), cko.getType());
				return isUpdateOrder;
			}
		}
		
		if (!"SUCCESS".equalsIgnoreCase(notifyReq.getRespResult())) {
			cko.setStat(orderStat.OS11.getCode());
			if (cardKeysOrderInfService.updateCardKeysOrderInf(cko) < 1) {
				logger.error("## 卡券集市--->购卡异步回调接口，更新用户[{}]卡券订单[{}]信息失败", notifyReq.getUserId(), cko.getOrderId());
			} else {
				isUpdateOrder = "SUCCESS";
			}
			return isUpdateOrder;
		}
		
		cko.setStat(orderStat.OS12.getCode());
		CardKeysProduct product = new CardKeysProduct();
		product.setProductCode(notifyReq.getAttach());
		product.setDataStat("0");
		CardKeysProduct ckp = cardKeysProductService.getCardKeysProductByCode(product);
		if (ckp == null) {
			logger.error("## 卡券集市--->购卡异步回调接口，查询用户[{}]购买卡产品号[{}]信息为空", notifyReq.getUserId(), notifyReq.getAttach());
			return isUpdateOrder;
		}
		
		/** 获取卡密 及 设置卡密交易流水信息 */
		List<CardKeysTransLog> cktList = new ArrayList<CardKeysTransLog>();
		Set<String> cardKeys = GenCardKeysUtil.genCardKeys("M", Integer.parseInt(cko.getNum()));
		for (String card : cardKeys) {
			/** 设置卡密交易流水信息 */
			CardKeysTransLog ckt = new CardKeysTransLog();
			String id = cardKeysTransLogService.getPrimaryKey();
			ckt.setTxnPrimaryKey(id);
			ckt.setCardKey(card);
			ckt.setOrderId(cko.getOrderId());
			ckt.setTransId(TransType.W10.getCode());
			ckt.setProductCode(cko.getProductCode());
			ckt.setTransAmt(cko.getAmount());
			ckt.setOrgTransAmt(cko.getAmount());
			ckt.setTransResult(BaseConstants.TXN_TRANS_RESP_SUCCESS);
			cktList.add(ckt);
		}
		boolean isBuyCard = false;
		try {
			isBuyCard = cardKeysOrderInfService.updateUserBugCardOrder(cko, cktList);
		} catch (Exception e) {
			logger.error("## 卡券集市--->购卡异步回调接口，更新用户[{}]卡券订单[{}]及流水信息异常{}", notifyReq.getUserId(), cko.getOrderId(), e);
			return isUpdateOrder;
		}
		if (isBuyCard) {
			/** 更新卡密产品已发数量 */
			CardKeysProduct ckpt = new CardKeysProduct();
			ckpt.setProductCode(cko.getProductCode());
			ckpt.setTotalNum(String.valueOf(cardKeys.size()));
			if (cardKeysProductService.updateCardKeysProduct(ckpt) < 1) {
				logger.error("## 卡券集市--->购卡异步回调接口，更新用户[{}]卡产品[{}]信息失败", notifyReq.getUserId(), ckp.getProductCode());
				return isUpdateOrder;
			}
		} else {
			logger.error("## 卡券集市--->购卡异步回调接口，更新用户[{}]卡券订单[{}]及流水信息失败", notifyReq.getUserId(), cko.getOrderId());
			return isUpdateOrder;
		}
		return "SUCCESS";
	}

	@Override
	public String welfareRechargeNotify(HttpServletRequest request) {
		PhoneRechargeNotifyReq notifyReq = new PhoneRechargeNotifyReq();
		notifyReq.setCode(request.getParameter("code"));
		notifyReq.setMsg(request.getParameter("msg"));
		notifyReq.setOrderId(request.getParameter("orderId"));
		notifyReq.setChannelOrderNo(request.getParameter("channelOrderNo"));
		notifyReq.setUserId(request.getParameter("userId"));
		notifyReq.setPhone(request.getParameter("phone"));
		notifyReq.setTelephoneFace(request.getParameter("telephoneFace"));
		notifyReq.setOrderType(request.getParameter("orderType"));
		notifyReq.setAttach(request.getParameter("attach"));
		notifyReq.setReqChannel(request.getParameter("reqChannel"));
		notifyReq.setRespTime(request.getParameter("respTime"));
		notifyReq.setSign(request.getParameter("sign"));
		logger.info("卡券集市--->充值异步回调接口，请求参数[{}]", JSONArray.toJSONString(notifyReq));
		if (StringUtil.isNullOrEmpty(notifyReq.getCode())) {
			logger.error("## 卡密充值回调接口---> 请求参数code为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getOrderId())) {
			logger.error("## 卡密充值回调接口---> 请求参数orderId为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getChannelOrderNo())) {
			logger.error("## 卡密充值回调接口---> 请求参数channelOrderNo为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getUserId())) {
			logger.error("## 卡密充值回调接口---> 请求参数userId为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getPhone())) {
			logger.error("## 卡密充值回调接口---> 请求参数phone为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getTelephoneFace())) {
			logger.error("## 卡密充值回调接口---> 请求参数teltphoneFace为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getOrderType())) {
			logger.error("## 卡密充值回调接口---> 请求参数orderType为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getReqChannel())) {
			logger.error("## 卡密充值回调接口---> 请求参数reqChannel为空");
			return "FAIL";
		}
		if (StringUtil.isNullOrEmpty(notifyReq.getSign())) {
			logger.error("## 卡密充值回调接口---> 请求参数sign为空");
			return "FAIL";
		}
		
		String signKey = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, phoneRechargeReqChnl.PRRC2.getCode()+"_SIGN_KEY");
		String gensign = CardRechargeSignUtil.genSign(notifyReq, signKey);
		if (!notifyReq.getSign().equals(gensign)) {
			logger.error("## 卡密充值回调接口---> 验签失败，请求参数签名[{}]，接口生成签名[{}]", notifyReq.getSign(), gensign);
			return "FAIL";
		} 
		
		CardKeysOrderInf cko = new CardKeysOrderInf();
		cko.setOrderId(notifyReq.getChannelOrderNo());
		cko.setUserId(notifyReq.getUserId());
		//cko.setStat(orderStat.OS20.getCode());
		cko.setType(BaseConstants.orderType.O2.getCode());
		cko.setDataStat("0");
		CardKeysOrderInf order = cardKeysOrderInfService.getCardKeysOrderInfByOrderInf(cko);
		if (order == null) {
			logger.error("## 卡密充值回调接口--->根据用户[{}]和订单[{}]查询卡密订单信息为空", cko.getUserId(), cko.getOrderId());
			return "FAIL";
		}
		
		//根据请求参数更新状态
		if (!notifyReq.getCode().equals("1")) {
			order.setStat(orderStat.OS24.getCode());
		} else {
			order.setStat(orderStat.OS22.getCode());
		}
		CardKeysTransLog ckt = new CardKeysTransLog();
		ckt.setOrderId(cko.getOrderId());
		ckt.setDataStat("0");
		List<CardKeysTransLog> cktList = cardKeysTransLogService.getCardKeysTransLogByOrderId(ckt);
		if (cktList.size() < 1) {
			logger.error("## 卡密充值回调接口---> 根据用户[{}]和订单[{}]查询未处理的卡密交易流水信息为空", cko.getUserId(), cko.getOrderId());
			return "FAIL";
		}
		List<CardKeys> cardList = new ArrayList<CardKeys>();
		List<CardKeysTransLog> transLogList = new ArrayList<CardKeysTransLog>();
		for (CardKeysTransLog log : cktList) {
			//卡密流水信息
			CardKeysTransLog transLog = new CardKeysTransLog();
			transLog.setTxnPrimaryKey(log.getTxnPrimaryKey());
			transLog.setCardKey(log.getCardKey());
			//根据请求参数成功或者失败来更新状态
			if (!notifyReq.getCode().equals("1")) {
				transLog.setTransResult(BaseConstants.TXN_TRANS_ERROR);
				transLog.setDataStat("1");
			} else {
				transLog.setTransResult(BaseConstants.RESPONSE_SUCCESS_CODE);
			}
			transLogList.add(transLog);
			//卡密信息
			CardKeys ck = new CardKeys();
			ck.setCardKey(log.getCardKey());
			ck.setDataStat("1");
			CardKeys card = cardKeysService.getCardKeysByCardKey(ck);
			if (card != null) {
				cardList.add(card);
			} else {
				logger.error("## 卡密充值回调接口---> 查询用户[{}]已核销的卡密[{}]信息为空", cko.getUserId(), log.getCardKey());
			}
		}
		if (cardList.size() < 1 || cktList.size() != cardList.size()) {
			logger.error("## 卡密充值回调接口---> 查询用户[{}]已核销的卡密数量和卡密交易流水数量不一致", cko.getUserId());
			return "FAIL";
		}
		
		boolean isUpdateUserCardKey = false;
		try {
			isUpdateUserCardKey = cardKeysOrderInfService.updateUserRechargeOrder(order, transLogList);
		} catch (Exception e) {
			logger.error("## 卡密充值回调接口---> 处理用户[{}]代付失败订单异常{}", cko.getUserId(), e);
		}
		if (isUpdateUserCardKey)
			return "SUCCESS";
		return "FAIL";
	}

}
