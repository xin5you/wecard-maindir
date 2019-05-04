package com.cn.thinkx.wecard.customer.module.phonerecharge.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeOrder;
import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeShop;
import com.cn.thinkx.common.wecard.module.phoneRecharge.mapper.PhoneRechargeMapper;
import com.cn.thinkx.common.wecard.module.phoneRecharge.mapper.PhoneRechargeShopMapper;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.OperatorType;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeOrderType;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeReqChnl;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeSupplier;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeTransStat;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.RandomUtils;
import com.cn.thinkx.pms.base.utils.SignUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.phonerecharge.service.PhoneRechargeService;
import com.cn.thinkx.wecard.customer.module.phonerecharge.vo.PhoneInfo;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

import redis.clients.jedis.JedisCluster;

@Service("phoneRechargeService")
public class PhoneRechargeServiceImpl implements PhoneRechargeService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;
	
	@Autowired
	private PhoneRechargeMapper phoneRechargeMapper;
	
	@Autowired
	private PhoneRechargeShopMapper phoneRechargeShopMapper;
	
	@Override
	public PhoneRechargeOrder getPhoneRechargeOrderById(String rId) {
		return this.phoneRechargeMapper.getPhoneRechargeOrderById(rId);
	}

	@Override
	public PhoneRechargeOrder insertPhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder) {
		phoneRechargeOrder.setrId(getPrimaryKey());
		phoneRechargeOrder.setSupplier(phoneRechargeSupplier.PRS1.getCode());
		phoneRechargeOrder.setTransStat(phoneRechargeTransStat.PRTS0.getCode());
		phoneRechargeOrder.setSupplierAmt(NumberUtils.RMMultiplyToDecimal(phoneRechargeOrder.getTelephoneFace(), "1.045"));
		phoneRechargeMapper.insertPhoneRechargeOrder(phoneRechargeOrder);
		return phoneRechargeOrder;
	}

	@Override
	public int updatePhoneRechargeOrder(PhoneRechargeOrder phoneRechargeOrder) {
		return this.phoneRechargeMapper.updatePhoneRechargeOrder(phoneRechargeOrder);
	}
	
	@Override
	public List<PhoneRechargeShop> phoneRechargeMobileValid(HttpServletRequest request) throws Exception {
		String openid = WxMemoryCacheClient.getOpenid(request);
		if (StringUtil.isNullOrEmpty(openid)) {
			logger.error("★★★★★Request WelfareMart--->mobileValid get openid is [Null]★★★★★");
			return null;
		}

		PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
		if (StringUtil.isNullOrEmpty(personInf)) {
			logger.error("★★★★★Request WelfareMart--->mobileValid get personInf is [Null]，openID[{}]★★★★★", openid);
			return null;
		}
		
		String mobile = request.getParameter("mobile");
		if (StringUtil.isNullOrEmpty(mobile)) {
			logger.error("## 手机充值--->手机充值接口，获取mobile为空，userID[{}]", personInf.getUserId());
			return null;
		}
		
		JSONObject paramData = new JSONObject();
		paramData.put("phone", mobile);
		String url = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.GET_PHONE_INFO_URL);
		
		String result = HttpClientUtil.sendPost(url, paramData.toString());
		PhoneInfo phoneInfo = JSONObject.parseObject(result, PhoneInfo.class);
		
		List<PhoneRechargeShop> phoneRechargeShopList = new ArrayList<PhoneRechargeShop>();
		List<PhoneRechargeShop> prsList = new ArrayList<PhoneRechargeShop>();
		String phoneRechargeShopArray = null;
		// 移动充值额度列表
		if (OperatorType.OperatorType1.getValue().equals(phoneInfo.getOperator())) {
			phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_YD_GOODS);
		} else if (OperatorType.OperatorType2.getValue().equals(phoneInfo.getOperator())) {
			phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_LT_GOODS);
		} else if (OperatorType.OperatorType3.getValue().equals(phoneInfo.getOperator())) {
			phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_DX_GOODS);
		} else {
			logger.error("## 手机充值--->充值接口，未查询到userID[{}]充值手机号[{}]所属运营商", personInf.getUserId(), mobile);
			return null;
		}
		if (!StringUtil.isNullOrEmpty(phoneRechargeShopArray)) {
			prsList = JSONObject.parseArray(phoneRechargeShopArray, PhoneRechargeShop.class);
		}
		
		if (prsList.size() < 1) {
			logger.error("## 手机充值--->充值接口，查询userID[{}]手机号[{}]可充值信息为空", personInf.getUserId(), mobile);
			return null;
		}
		
		for (PhoneRechargeShop prs : prsList) {
			prs.setShopPrice(NumberUtils.RMBCentToYuan(prs.getShopPrice()));
			prs.setProvince(phoneInfo.getProvince());
			prs.setOper(phoneInfo.getOperator());
			phoneRechargeShopList.add(prs);
		}
		return phoneRechargeShopList;
	}

	@Override
	public TransOrderReq toPhoneRechargeUnifiedOrder(HttpServletRequest request) throws Exception {
		String openid = WxMemoryCacheClient.getOpenid(request); 
		if (StringUtil.isNullOrEmpty(openid)) {
			logger.info("★★★★★Request phoneRecharge--->toPhoneRechargeUnifiedOrder get openid is [Null]★★★★★");
			return null;
		}
		PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
		if (StringUtil.isNullOrEmpty(personInf)) {
			logger.info("★★★★★Request phoneRecharge--->toPhoneRechargeUnifiedOrder get personInf is [Null]★★★★★");
			return null;
		}
		
		String mobile = request.getParameter("mobile");
		String rechargeShopId = request.getParameter("rechargeShopId");
		String type = request.getParameter("type");
		if (StringUtil.isNullOrEmpty(mobile) || StringUtil.isNullOrEmpty(rechargeShopId) || StringUtil.isNullOrEmpty(type)) {
			logger.error("Request phoneRecharge--->toPhoneRechargeUnifiedOrder get mobile or rechargeShopId is [Null] or type is [Null], and userID[{}]", personInf.getUserId());
			return null;
		}
		
		PhoneRechargeShop phoneRechargeShop = phoneRechargeShopMapper.getPhoneRechargeShopById(rechargeShopId);
		PhoneRechargeOrder rechargeOrder = new PhoneRechargeOrder();
		rechargeOrder.setUserId(personInf.getUserId());
		rechargeOrder.setPhone(mobile);
		rechargeOrder.setTelephoneFace(phoneRechargeShop.getShopFace());
		rechargeOrder.setTransAmt(phoneRechargeShop.getShopPrice());
		if (phoneRechargeOrderType.PROT1.getCode().equals(type)) {
			rechargeOrder.setOrderType(phoneRechargeOrderType.PROT1.getCode());
		} else if (phoneRechargeOrderType.PROT2.getCode().equals(type)) {
			rechargeOrder.setOrderType(phoneRechargeOrderType.PROT2.getCode());
		}
		rechargeOrder.setGoodsNo(phoneRechargeShop.getShopNo());
		rechargeOrder.setReqChannel(phoneRechargeReqChnl.PRRC1.getCode());
		try {
			rechargeOrder = insertPhoneRechargeOrder(rechargeOrder);
		} catch (Exception e) {
			logger.error("## 手机充值--->话费充值接口，新增userID[{}]话费充值订单异常{}", personInf.getUserId(), e);
			return null;
		}

		String mchntCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_MCHNT_NO);
		String shopCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_SHOP_NO);
		String phoneKey = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.PHONE_RECHARGE_REQ_KEY);
		
		//知了企服话费充值异步回调接口地址
		String phoneRechargeNotify = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.PHONE_RECHARGE_REQ_NOTIFY_URL);
		String phoneRechargeRedirect = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.PHONE_RECHARGE_REQ_REDIRECT_URL);

		//知了企服流量充值异步回调接口地址
		String flowRechargeNotifyUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.FLOW_RECHARGE_NOTIFY_URL);
		String phoneRechargeRedirectUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.PHONE_RECHARGE_REDIRECT_URL);

		TransOrderReq orderReq = new TransOrderReq();
		if (phoneRechargeOrderType.PROT1.getCode().equals(type)) {
			orderReq.setChannel(ChannelCode.CHANNEL8.toString());
			orderReq.setNotify_url(phoneRechargeNotify);
			orderReq.setRedirect_url(phoneRechargeRedirect);
		} else if (phoneRechargeOrderType.PROT2.getCode().equals(type)) {
			orderReq.setChannel(ChannelCode.CHANNEL10.toString());
			orderReq.setNotify_url(flowRechargeNotifyUrl);
			orderReq.setRedirect_url(phoneRechargeRedirectUrl);
		}
		orderReq.setUserId(rechargeOrder.getUserId());
		orderReq.setOrderId(rechargeOrder.getrId());
		orderReq.setInnerMerchantNo(mchntCode);
		orderReq.setInnerShopNo(shopCode);
		orderReq.setCommodityName(phoneRechargeOrderType.findByCode(rechargeOrder.getOrderType()).getValue());
		orderReq.setCommodityNum("1");
		orderReq.setTxnAmount(rechargeOrder.getTransAmt());
		orderReq.setRedirect_type("1");
		orderReq.setSign(SignUtil.genSign(orderReq, phoneKey));
		return orderReq;
	}
	
	private String getPrimaryKey(){
		String primaryKey = RandomUtils.getOrderIdByUUId("H");
		return primaryKey;
	}

	@Override
	public List<PhoneRechargeOrder> getPhoneRechargeOrderList(PhoneRechargeOrder phoneRechargeOrder) {
		return phoneRechargeMapper.getPhoneRechargeOrderList(phoneRechargeOrder);
	}

}
