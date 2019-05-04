package com.cn.thinkx.pms.base.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.SMSPropertiesUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;

import redis.clients.jedis.JedisCluster;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
	private static Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;
	
	@Override
	public boolean sendMessage(String target, String content) {
		/*String flag = SMSPropertiesUtils.getProperty("SEND_MSG_FLAG");
		if ("true".equals(flag)) {
			try {
				StringBuffer strBuf = new StringBuffer(SMSPropertiesUtils.getProperty("SEND_URL"));
				strBuf.append("?un=").append(SMSPropertiesUtils.getProperty("ACCOUNT"));
				strBuf.append("&pw=").append(SMSPropertiesUtils.getProperty("PASSWORD"));
				strBuf.append("&da=").append(target);
				strBuf.append("&sm=").append(URLEncoder.encode(content, "UTF-8"));
				strBuf.append("&dc=15&rd=1&rf=2&tf=3");

				LOG.info("手机号[{}]开始发送短信", target);
				HttpResponse response = HttpClient.get(new HttpRequest(strBuf.toString().replace("+", "%20")));
				String result = null;
				if (response != null && StringUtil.isNotEmpty(result = response.getStringResult())) {
					JSONObject obj = JSON.parseObject(result);
					if (obj != null && obj.getBooleanValue("success")) {
						return true;
					}
				}
			} catch (Exception e) {
				LOG.error("## 手机号[{}]短信发送失败", target, e);
			}
		}*/
		return false;
	}
	
	@Override
	public boolean sendMessage(String phoneNumber, String templateCode, String templateParam) {
		LOG.info("手机号[{}]短信发送,模板Code[{}], 模板参数{}", phoneNumber, templateCode, templateParam);
		String flag = SMSPropertiesUtils.getProperty("SEND_MSG_FLAG");
		
		if (StringUtil.isNullOrEmpty(phoneNumber)) {
			LOG.error("## 短信发送失败，手机号phoneNumber为空");
			return false;
		}
		
		if (StringUtil.isNullOrEmpty(templateCode)) {
			LOG.error("## 短信发送失败，短信模板templateCode为空");
			return false;
		}
		
		if (templateParam == null) {
			LOG.error("## 短信发送失败，模板消息内容templateParam为空");
			return false;
		}
		
		if ("true".equals(flag)) {
			try {
				String product = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_PRODUCT);
				String domain = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_DOMAIN);
				String accessKeyId = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_ACCESSKEYID);
				String accessKeySecret = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_ACCESSKEYSECRET);
				String signName = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ALIYUN_MSM_SIGNNAME);
				LOG.info("调用短信模板参数product[{}],domain[{}],accessKeyId[{}],accessKeySecret[{}],signName[{}]", product, domain, accessKeyId, accessKeySecret, signName);
		      
				//初始化acsClient,暂不支持region化
		        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		        IAcsClient acsClient = new DefaultAcsClient(profile);

		        //组装请求对象-具体描述见控制台-文档部分内容
		        SendSmsRequest request = new SendSmsRequest();
		        //必填:待发送手机号
		        request.setPhoneNumbers(phoneNumber);
		        //必填:短信签名-可在短信控制台中找到
		        request.setSignName(signName);
		        //必填:短信模板-可在短信控制台中找到
		        request.setTemplateCode(templateCode);
		        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		        request.setTemplateParam(templateParam);

		        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		        //request.setSmsUpExtendCode("90997");

		        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		        //request.setOutId("yourOutId");
		        LOG.info("手机号[{}]开始发送短信", phoneNumber);
		        //hint 此处可能会抛出异常，注意catch
		        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		        LOG.info("调用短信发送接口返回参数{}", JSONArray.toJSONString(sendSmsResponse));
		        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {//请求成功
		        	return true;
		        }
			} catch (Exception e) {
				LOG.error("## 手机号[{}]短信发送失败", phoneNumber, e);
			}
		}
		return false;
	}
	
	
	 /*public static void main(String[] args) { 
		 MessageService pro = new MessageServiceImpl(); 
		 System.out.println(pro.sendMessage("13162666043",
		 "【知了企服】亲爱的会员，你尾号为0265的会员卡于2016-10-09 13:09:17充值100.00元，余额234.72元[上海宸树]"));
		 
		String str= "{\"name\":\"123456\"}";
		MessageService pro = new MessageServiceImpl();
		System.out.println(pro.sendMessage("15575311169", "1234", "2", str));
	 }*/
	 
}
