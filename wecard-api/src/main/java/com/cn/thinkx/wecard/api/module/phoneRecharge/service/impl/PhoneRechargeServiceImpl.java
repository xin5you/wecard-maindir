package com.cn.thinkx.wecard.api.module.phoneRecharge.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeTransStat;
import com.cn.thinkx.wecard.api.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.api.module.phoneRecharge.mapper.PhoneRechargeMapper;
import com.cn.thinkx.wecard.api.module.phoneRecharge.model.PhoneRechargeOrder;
import com.cn.thinkx.wecard.api.module.phoneRecharge.service.PhoneRechargeService;
import com.cn.thinkx.wecard.api.module.phoneRecharge.service.UnicomAyncService;
import com.cn.thinkx.wecard.api.module.phoneRecharge.valid.PhoneRechargeValid;
import com.cn.thinkx.wecard.api.module.welfaremart.vo.NotifyOrder;

import redis.clients.jedis.JedisCluster;

@Service("phoneRechargeService")
public class PhoneRechargeServiceImpl implements PhoneRechargeService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PhoneRechargeMapper phoneRechargeMapper;
	
	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;
	
	@Autowired
	@Qualifier("unicomAyncService")
	private UnicomAyncService unicomAyncService;
	
	@Override
	public PhoneRechargeOrder getPhoneRechargeOrderById(String rId) {
		return this.phoneRechargeMapper.getPhoneRechargeOrderById(rId);
	}

	@Override
	public int insertPhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder) {
		return this.phoneRechargeMapper.insertPhoneRechargeOrder(phoneRechargeOrder);
	}

	@Override
	public int updatePhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder) {
		return this.phoneRechargeMapper.updatePhoneRechargeOrder(phoneRechargeOrder);
	}

	@Override
	public boolean flowRechargeNotify(HttpServletRequest request) {
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
		
		if (PhoneRechargeValid.phoneRechargeNotifyValid(notifyReq))
			return false;
		
		PhoneRechargeOrder flowOrder = phoneRechargeMapper.getPhoneRechargeOrderById(notifyReq.getOrderId());
		if (flowOrder == null) {
			logger.error("## 手机充值--->流量充值接口，查询用户[{}]手机充值订单[{}]信息为空",  notifyReq.getUserId(), notifyReq.getOrderId());
			return false;
		}
		flowOrder.setChannelOrderNo(notifyReq.getTxnFlowNo());
		if (BaseConstants.HKB_SUCCESS.equals(notifyReq.getRespResult())) {
			flowOrder.setTransStat(phoneRechargeTransStat.PRTS1.getCode());
		}
		if (BaseConstants.HKB_FAIL.equals(notifyReq.getRespResult())) {
			flowOrder.setTransStat(phoneRechargeTransStat.PRTS3.getCode());
		}
		try {
			if (phoneRechargeMapper.updatePhoneRechargeOrder(flowOrder) < 1) {
				logger.error("## 手机充值--->流量充值接口，修改用户[{}]订单[{}]信息失败 ", notifyReq.getUserId(), flowOrder.getrId());
				return false;
			}
		} catch (Exception e) {
			logger.error("## 手机充值--->流量充值接口，更新用户[{}]订单[{}]异常{}", notifyReq.getUserId(), flowOrder.getrId(), e);
		}
		if (BaseConstants.HKB_SUCCESS.equals(notifyReq.getRespResult())) {
			phoneRechargeToDingChi(notifyReq.getOrderId());
			return true;
		}
		return false;
	}

	@Override
	public boolean phoneRechargeNotify(HttpServletRequest req) {
		NotifyOrder notifyReq = new NotifyOrder();
		notifyReq.setChannel(req.getParameter("channel"));
		notifyReq.setRespResult(req.getParameter("respResult"));
		notifyReq.setInnerMerchantNo(req.getParameter("innerMerchantNo"));
		notifyReq.setInnerShopNo(req.getParameter("innerShopNo"));
		notifyReq.setUserId(req.getParameter("userId"));
		notifyReq.setOrderId(req.getParameter("orderId"));
		notifyReq.setSettleDate(req.getParameter("settleDate"));
		notifyReq.setTxnFlowNo(req.getParameter("txnFlowNo"));
		notifyReq.setOriTxnAmount(req.getParameter("oriTxnAmount"));
		notifyReq.setTxnAmount(req.getParameter("txnAmount"));
		notifyReq.setAttach(req.getParameter("attach"));
		notifyReq.setSign(req.getParameter("sign"));
		
		if (PhoneRechargeValid.phoneRechargeNotifyValid(notifyReq))
			return false;
		
		PhoneRechargeOrder rechargeOrde = null;
		try {
			rechargeOrde = new PhoneRechargeOrder();
			rechargeOrde.setrId(notifyReq.getOrderId());
			rechargeOrde.setChannelOrderNo(notifyReq.getTxnFlowNo());
			if (BaseConstants.HKB_SUCCESS.equals(notifyReq.getRespResult())) {
				rechargeOrde.setTransStat(phoneRechargeTransStat.PRTS1.getCode());
			}
			if (BaseConstants.HKB_FAIL.equals(notifyReq.getRespResult())) {
				rechargeOrde.setTransStat(phoneRechargeTransStat.PRTS3.getCode());
			}
			int i = phoneRechargeMapper.updatePhoneRechargeOrder(rechargeOrde);
			if (i != 1) {
				logger.error("## 手机充值--->话费充值接口，修改用户[{}]订单[{}]信息失败", notifyReq.getUserId(), rechargeOrde.getrId());
				return false;
			}
			if (BaseConstants.HKB_SUCCESS.equals(notifyReq.getRespResult())) {
				phoneRechargeToLiFang(notifyReq.getOrderId());
				return true;
			}
		} catch (Exception e) {
			logger.error("## 手机充值--->话费充值接口，更新用户[{}]订单[{}]异常{}", notifyReq.getUserId(), rechargeOrde.getrId(), e);
		}
		return false;
	}
	
	@Override
	public void phoneRechargeToLiFang(String orderId) {
		PhoneRechargeOrder rechargeOrder = getPhoneRechargeOrderById(orderId);
		if (rechargeOrder == null) {
			logger.error("## 手机充值--->查询手机充值订单[{}]信息为空", orderId);
			return ;
		}
		
		String accessToken = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
				BaseConstants.BM_ACCESS_TOKEN);
		String PHONE_RECHARGE_FRONT_REQUEST_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
				BaseConstants.PHONE_RECHARGE_FRONT_REQUEST_URL);
		
		rechargeOrder.setAccessToken(accessToken);
		/*JSONObject paramData = new JSONObject();
		paramData.put("paramData", JSONObject.toJSONString(rechargeOrder));*/
		logger.info("手机充值--->话费充值接口，提交请求链接[{}] 参数{}", PHONE_RECHARGE_FRONT_REQUEST_URL, JSONArray.toJSONString(rechargeOrder));
		HttpClientUtil.sendPost(PHONE_RECHARGE_FRONT_REQUEST_URL, JSONArray.toJSONString(rechargeOrder));
	}

	@Override
	public void phoneRechargeToDingChi(String orderId) {
		PhoneRechargeOrder rechargeOrder = getPhoneRechargeOrderById(orderId);
		if (rechargeOrder == null) {
			logger.error("手机充值--->流量充值接口，查询手机充值订单[{}]信息为空", orderId);
			return ;
		}
		String PHONE_RECHARGE_FRONT_REQUEST_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
				BaseConstants.PHONE_RECHARGE_FRONT_REQUEST_URL);
		
		logger.info("手机充值--->流量充值接口，提交请求链接[{}] 参数{}", PHONE_RECHARGE_FRONT_REQUEST_URL, JSONObject.toJSONString(rechargeOrder));
		HttpClientUtil.sendPost(PHONE_RECHARGE_FRONT_REQUEST_URL, JSONObject.toJSONString(rechargeOrder));
	}

}
