package com.cn.thinkx.wecard.api.module.telephone.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.http.HttpClient;
import com.cn.thinkx.pms.base.http.HttpRequest;
import com.cn.thinkx.pms.base.http.HttpResponse;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TelePhoneCheckUtils {

	private static Logger logger = LoggerFactory.getLogger(TelePhoneCheckUtils.class);
	/**
	 * 中国电信号码格式验证 手机段： 133,153,180,181,189,177,1700,173,199
	 **/
	private static final String CHINA_TELECOM_PATTERN = "(^1(33|53|77|73|99|8[019])\\d{8}$)|(^1700\\d{7}$)";

	/**
	 * 中国联通号码格式验证 手机段：130,131,132,155,156,185,186,145,176,1709
	 **/
	private static final String CHINA_UNICOM_PATTERN = "(^1(3[0-2]|4[5]|5[56]|7[6]|8[56])\\d{8}$)|(^1709\\d{7}$)";

	/**
	 * 中国移动号码格式验证
	 * 手机段：134,135,136,137,138,139,150,151,152,157,158,159,182,183,184,187,188,
	 * 147,178,1705
	 **/
	private static final String CHINA_MOBILE_PATTERN = "(^1(3[4-9]|4[7]|5[0-27-9]|7[8]|8[2-478])\\d{8}$)|(^1705\\d{7}$)";

	/**
	 * 执行正则表达式
	 * 
	 * @param pat 表达式
	 * @param str 待验证字符串
	 * @return 返回true,否则为false
	 */
	private static boolean match(String pat, String str) {
		Pattern pattern = Pattern.compile(pat);
		Matcher match = pattern.matcher(str);
		return match.find();
	}

	/**
	 * 查询电话属于哪个运营商
	 *
	 * @param tel
	 *            手机号码
	 * @return 0：不属于任何一个运营商，1:移动，2：联通，3：电信
	 */
	public static String isChinaMobilePhoneNum(String tel) {
		boolean b1 = tel == null || tel.trim().equals("") ? false : match(CHINA_MOBILE_PATTERN, tel);
		if (b1) {
			return "1";
		}
		b1 = tel == null || tel.trim().equals("") ? false : match(CHINA_UNICOM_PATTERN, tel);
		if (b1) {
			return "2";
		}
		b1 = tel == null || tel.trim().equals("") ? false : match(CHINA_TELECOM_PATTERN, tel);
		if (b1) {
			return "3";
		}
		return "0";
	}
	
	public static String getPhoneMessage(String phoneNo){
		HttpRequest request = new HttpRequest("https://tcc.taobao.com/cc/json/mobile_tel_segment.htm");
		request.setConnectionTimeout(5000);
		request.setTimeout(5000);
		Map<String, String> parameters = new HashMap<>();
		parameters.put("tel", phoneNo);
		request.setParameters(parameters);
		try {
			HttpResponse response = HttpClient.post(request);
			String jsonString = response.getStringResult().split("=")[1].trim();
			ObjectMapper objectMapper = new ObjectMapper();  
	        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);//设置可用单引号  
	        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//设置字段可以不用双引号包括  
	        JsonNode root = objectMapper.readTree(jsonString); 
	        return root.get("province").asText();
		} catch (Exception e) {
			logger.error("获取手机归属异常-->", e);
		}
		return "ERROR";
	}
	
	public static void main(String[] args) {

	}
}