package com.cn.iboot.diy.api.base.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.domain.ResultHtml;
import com.cn.iboot.diy.api.base.service.MessageService;
import com.cn.iboot.diy.api.base.utils.DateUtil;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.RandomUtils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.redis.utils.JedisClusterUtils;

@RestController
@RequestMapping(value = "/pub")
public class CommonController {
	
	Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	private WebApplicationContext context; // 获取上下文
	
	@Autowired
	@Qualifier("messageService")
	private  MessageService messageService;
	
	/**
	 * 发送消息目标
	 * @param phoneNumber
	 * @param session
	 * @return
	 */
	private ResultHtml getResultMap(String phoneNumber,String bizName) {
		ResultHtml resultMap = new ResultHtml();
		int expireMinutes = NumberUtils.parseInt(JedisClusterUtils.getInstance(context).hget(Constants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,"SMS_EXPIRE_TIME"));
		String phoneCode = RandomUtils.getRandomNumbernStr(6);
		boolean sendStatus = messageService.sendMessage(phoneNumber, "【知了企服】验证码：" + phoneCode + 
				"（有效期" + expireMinutes + "分钟）您正在操作<" + bizName + ">业务，切勿告知他人！");
		if (sendStatus) {
			// 手机动态码
			JedisClusterUtils.getInstance(context).setex(Constants.SESSION_PHONECODE, phoneCode, expireMinutes*60);

			Date expireDate = DateUtil.addDate(DateUtil.getCurrDate(), Calendar.MINUTE, expireMinutes);
			
			JedisClusterUtils.getInstance(context).setex(Constants.SESSION_PHONECODE_TIME, String.valueOf(expireDate.getTime()), expireMinutes*60);
			
			logger.info("phoneNumber===" + phoneNumber + ",phoneCode= " + phoneCode + " expire time is " + 
					DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss",expireDate));
			resultMap.setStatus(true);
		} else {
			resultMap.setStatus(false);
			resultMap.setMsg("短信发送失败");
		}
		return resultMap;
	}
	
	/***
	 * 发送短信
	 * @param request
	 * @return
	 */
	@RequestMapping("sendPhoneSMS")
	public ResultHtml sendPhoneSMS(HttpServletRequest request) {
		ResultHtml resultMap = new ResultHtml();
		String bindingPhone = request.getParameter("phoneNumber");
		String bizCode=request.getParameter("bizCode");
//		String bindingPhone = "17378125830";
//		String bizCode="03";
		try {
			
			if (StringUtil.isNotEmpty(bindingPhone)) {
				return getResultMap(bindingPhone,Constants.SendMsgTypeEnum.findByCode(bizCode).getName());
			} else {
				resultMap.setStatus(false);
				resultMap.setMsg("请重新输入手机号码");
				return resultMap;
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			
		}
		return resultMap;
	}
	
}
