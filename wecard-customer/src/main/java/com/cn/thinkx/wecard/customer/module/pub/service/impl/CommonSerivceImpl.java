package com.cn.thinkx.wecard.customer.module.pub.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantManager;
import com.cn.thinkx.common.wecard.module.common.mapper.CommonDao;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.RandomUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants.SendMsgTypeEnum;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantManagerService;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.pub.service.CommonSerivce;
import com.cn.thinkx.wecard.customer.module.wxcms.WxCmsContents;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

import redis.clients.jedis.JedisCluster;

@Service("commonSerivce")
public class CommonSerivceImpl implements CommonSerivce {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("commonDao")
	private CommonDao commonDao;

	@Autowired
	@Qualifier("messageService")
	private MessageService messageService;

	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;

	@Autowired
	@Qualifier("merchantManagerService")
	private MerchantManagerService merchantManagerService;

	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;

	@Override
	public String findMmSsAddSeqId(String rCodePrefix) {
		String rCode = "";
		String str = commonDao.findMmSsAddSeqId();
		// 第一个4位的随机数
		String num1 = RandomUtils.getRandomNumbernStr(4);
		String num2 = RandomUtils.getRandomNumbernStr(4);
		rCode = rCodePrefix + num1 + str + num2;
		return rCode;
	}

	@Override
	public ResultHtml getResultMap(String phoneNumber, String bizName, HttpSession session) {
		ResultHtml resultMap = new ResultHtml();
		int expireMinutes = NumberUtils
				.parseInt(RedisDictProperties.getInstance().getdictValueByCode("SMS_EXPIRE_TIME"));
		String phoneCode = RandomUtils.getRandomNumbernStr(6);
//		boolean sendStatus = messageService.sendMessage(phoneNumber,
//				"【知了企服】验证码：" + phoneCode + "（有效期" + expireMinutes + "分钟）您正在操作<" + bizName + ">业务，切勿告知他人！");
		String templateCode = "";
		if (SendMsgTypeEnum.msg_01.getName().equals(bizName)) {
			templateCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_TEMPLATE_CODE_REGISTER);
		} else if (SendMsgTypeEnum.msg_02.getName().equals(bizName)) {
			templateCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_TEMPLATE_CODE_PWDRESET);
		} else if (SendMsgTypeEnum.msg_03.getName().equals(bizName)) {
			
		} else if (SendMsgTypeEnum.msg_04.getName().equals(bizName)) {
			templateCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_TEMPLATE_CODE_CARDRESELL);
		} else if (SendMsgTypeEnum.msg_05.getName().equals(bizName)) {
			templateCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_TEMPLATE_CODE_ADDBANKCARD);
		}
		
		String templateParam = "{\"code\":\"" + phoneCode + "\"}";
		boolean sendStatus = messageService.sendMessage(phoneNumber, templateCode, templateParam);
		if (sendStatus) {
			// 手机动态码
			session.setAttribute(WxCmsContents.SESSION_PHONECODE, phoneCode);

			Date expireDate = DateUtil.addDate(DateUtil.getCurrDate(), Calendar.MINUTE, expireMinutes);
			session.setAttribute(WxCmsContents.SESSION_PHONECODE_TIME, expireDate.getTime());
			logger.info("phoneNumber===" + phoneNumber + ",phoneCode= " + phoneCode + " expire time is "
					+ DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", expireDate));
			resultMap.setStatus(true);
		} else {
			resultMap.setStatus(false);
			resultMap.setMsg("短信发送失败");
			logger.error("## 手机号[{}] 验证码[{}]短信发送失败", phoneNumber, phoneCode);
		}
		return resultMap;
	}

	@Override
	public ResultHtml sendPhoneSMS(HttpServletRequest request) {
		ResultHtml resultMap = new ResultHtml();
		String bindingPhone = request.getParameter("phoneNumber");
		String bizCode = request.getParameter("bizCode");
		try {
			HttpSession session = request.getSession();
			if (StringUtil.isNotEmpty(bindingPhone)) {
				return getResultMap(bindingPhone, BaseConstants.SendMsgTypeEnum.findByCode(bizCode).getName(), session);
			} else {
				resultMap.setStatus(false);
				resultMap.setMsg("请重新输入手机号码");
				return resultMap;
			}
		} catch (Exception e) {
			logger.error("## 手机号[{}]发送短信异常", bindingPhone, e);
		}
		return resultMap;
	}

	@Override
	public ResultHtml sendUserPhoneSMS(HttpServletRequest request) {
		ResultHtml resultMap = new ResultHtml();
		String openid = WxMemoryCacheClient.getOpenid(request);
		String bizCode = request.getParameter("bizCode");
		try {
			if (StringUtil.isNullOrEmpty(openid)) {
				resultMap.setStatus(false);
				resultMap.setMsg("请从微信菜单重新进入页面");
				return resultMap;
			}
			String bindingPhone = personInfService.getPhoneNumberByOpenId(openid); // 获取手机号码
			if (bindingPhone == null || StringUtil.isNullOrEmpty(bindingPhone)) {
				resultMap.setStatus(false);
				resultMap.setMsg("您不是管理员，不能访问该页面");
				return resultMap;
			}
			HttpSession session = request.getSession();
			if (StringUtil.isNotEmpty(bindingPhone)) {
				return getResultMap(bindingPhone, BaseConstants.SendMsgTypeEnum.findByCode(bizCode).getName(), session);
			} else {
				resultMap.setStatus(false);
				resultMap.setMsg("请重新输入手机号码");
				return resultMap;
			}
		} catch (Exception e) {
			logger.error("## 用户[{}]发送短信异常", openid, e);
		}
		return resultMap;
	}

	@Override
	public ResultHtml sendMchntPhoneSMS(HttpServletRequest request) {
		ResultHtml resultMap = new ResultHtml();
		// 拦截器已经处理了缓存,这里直接取
		String openid = WxMemoryCacheClient.getOpenid(request);
		String bizCode = request.getParameter("bizCode");
		try {
			if (StringUtil.isNullOrEmpty(openid)) {
				resultMap.setStatus(false);
				resultMap.setMsg("请从微信菜单重新进入页面");
				return resultMap;
			}
			MerchantManager mm = merchantManagerService.getMerchantInsInfByOpenId(openid);// 查询所属商户号 关联查询 查找出来机构ID
			if (mm == null || StringUtil.isNullOrEmpty(mm.getPhoneNumber())) {
				resultMap.setStatus(false);
				resultMap.setMsg("您不是管理员，不能访问该页面");
				return resultMap;
			}
			String bindingPhone = mm.getPhoneNumber();
			HttpSession session = request.getSession();
			if (StringUtil.isNotEmpty(bindingPhone)) {
				return getResultMap(bindingPhone, BaseConstants.SendMsgTypeEnum.findByCode(bizCode).getName(), session);
			} else {
				resultMap.setStatus(false);
				resultMap.setMsg("请重新输入手机号码");
				return resultMap;
			}
		} catch (Exception e) {
			logger.error("## 商户[{}]发送短信异常", openid, e);
		}
		return resultMap;
	}
}
