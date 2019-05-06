package com.cn.thinkx.wecard.api.module.withdraw.suning.utils;

import com.cn.thinkx.pms.base.redis.util.RedisPropertiesUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.suning.epps.codec.Digest;
import com.suning.epps.codec.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 〈苏宁易付宝业务工具类〉<br><br>
 * 〈生成批次号与订单详细序列号、生成签名与验签等〉
 *
 * @author pucker
 * @since [卡券集市/1.0]
 */
public class BizUtil {
	private static Logger logger = LoggerFactory.getLogger(BizUtil.class);

	public static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
	public static final LocalDateTime NOW = LocalDateTime.now();

	private static final String PRE_BATCH = "HQ";
	private static final String PRE_DETAIL = "D";
	
	public static final String PUBLIC_KEY = RedisPropertiesUtils.getProperty("WITHDRAW_YFB_PUBLIC_KEY");

	/**
	 * 生成批次号
	 * 
	 * @return StringBuffer
	 */
	public static String generalBatchNo() {
		return new StringBuffer().append(PRE_BATCH).append(getRandomString(10, dtf.format(NOW))).toString();
	}

	/**
	 * 生成随机数字符串
	 * 
	 * @param length 表示生成字符串的长度, dateStr 基本字符串
	 * @return String
	 */
	public static String getRandomString(int length, String dateStr) { // length表示生成字符串的长度
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(dateStr.length());
			sb.append(dateStr.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 生成不重复明细序列号
	 * 
	 * @param
	 * @return String
	 */
	public static String generalDetailNo() {
		return new StringBuffer().append(PRE_DETAIL).append(getRandomString(11, dtf.format(NOW))).toString();
	}
	
	/**
	 * 计算代付业务签名
	 * 
	 * @param body 业务JSON参数值
	 * @return
	 */
	public static String calculateSign(String body) {
		if (StringUtil.isNullOrEmpty(body)) {
			logger.error("## 生成签名body参数为空");
			return null;
		}
		
		Map<String, String> signMap = new HashMap<>();
        signMap.put("merchantNo", RedisPropertiesUtils.getProperty("WITHDRAW_YFB_MERCHANT_CODE"));
        signMap.put("publicKeyIndex", "0001");
        signMap.put("inputCharset", "UTF-8");
        signMap.put("body", body);
        String digest = Digest.digest(Digest.mapToString(Digest.treeMap(signMap)));
        return sign(digest);
	}
	
	/**
	 * 验证代付业务签名
	 * 
	 * @param body 业务JSON参数
	 * @param signature 业务参数签名值
	 * @return
	 */
	public static boolean verifySignature(String data, String signature) {
		if (StringUtil.isNullOrEmpty(data)) {
			logger.error("## 验证签名data参数为空");
			return false;
		}
		// 生成MD5摘要
		Map<String, String> signMap = new HashMap<>();
        signMap.put("content", data);
        String signData = Digest.digest(Digest.mapToString(Digest.treeMap(signMap)));
        
        if (signData == null || signData.length() == 0) {
        	logger.error("## 原数据字符串为空");
			return false;
        }
        if (signature == null || signature.length() == 0) {
        	logger.error("## 签名字符串为空");
			return false;
        }
        try {
        	// 获取根据公钥字符串获取私钥
            PublicKey pubKey = RSAUtil.getPublicKey(PUBLIC_KEY);
            // 返回验证结果
            return RSAUtil.vertiy(signData, signature, pubKey);
        } catch (Exception e) {
        	logger.error("## 验证签名异常");
			return false;
        }
	}
	
	/**
	 * RSA 加签
	 * 
	 * @param data 待加签字符串
	 * @return
	 */
	public static String sign(String data) {
		// 转换为私钥对象
//        PrivateKey priKey = RSAUtil.resolvePrivateKey(BizUtil.class.getResource("/RSA/private.key").getPath());// 开发调试
        PrivateKey priKey = RSAUtil.resolvePrivateKey(RedisPropertiesUtils.getProperty("WITHDRAW_YFB_PRIVATE_KEY_URL"));
        String signData = null;
		try {
			signData = RSAUtil.sign(data, priKey);
		} catch (InvalidKeyException e) {
			logger.error("## Suning-YFB batch withdraw <sign> InvalidKeyException", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("## Suning-YFB batch withdraw <sign> NoSuchAlgorithmException", e);
		} catch (SignatureException e) {
			logger.error("## Suning-YFB batch withdraw <sign> SignatureException", e);
		}
        return signData;
	}
	
	/**
	 * RSA 验签
	 * 
	 * @param data 业务JSON字符串
	 * @param signData 签名值
	 * @return
	 */
	public static boolean vertiy(String data, String signData) {
		// 转换为公钥对象
        PublicKey pubKey = RSAUtil.resolvePublicKey(BizUtil.class.getResource("/RSA/public.key").getPath());
        try {
			return RSAUtil.vertiy(data, signData, pubKey);
		} catch (InvalidKeyException e) {
			logger.error("## Suning-YFB batch withdraw <vertiy> InvalidKeyException", e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("## Suning-YFB batch withdraw <vertiy> NoSuchAlgorithmException", e);
		} catch (SignatureException e) {
			logger.error("## Suning-YFB batch withdraw <vertiy> SignatureException", e);
		}
		return false;
	}

	public static void main(String[] args) {
		String content = "{\"failAmount\":0,\"failNum\":0,\"totalNum\":1,\"dataSource\":\"00\",\"transferOrders\":[{\"bankCity\":\"上海市\",\"bankName\":\"上海浦东发展银行\",\"payTime\":\"20180913185531\",\"bankProvince\":\"上海市\",\"receiverCardNo\":\"6217920203718403\",\"bankCode\":\"SPDB\",\"id\":1809130000003175879,\"amount\":48,\"serialNo\":\"Z52971123\",\"poundage\":50,\"refundTicket\":\"N\",\"receiverName\":\"刘祥龙\",\"receiverType\":\"PERSON\",\"success\":\"true\"}],\"batchNo\":\"HQ1250258819\",\"poundage\":50,\"status\":\"07\",\"successNum\":1,\"totalAmount\":48,\"merchantName\":\"上海昀坤企业服务有限公司\",\"merchantNo\":\"70235957\",\"successAmount\":48}";
		String sign = "FOUvAirM2U1F1Z2lRKcnoVI1b9atO23knFny7xhmXi5guBZXz8k2VoH1UucJTqbPvQz0ezfcLOoRvL22Nn_we7e4VAkuPi_AJMxRVLY0FggUKYs7IyqPx4p7QtGxfKy2MsO7ks75e3UWo1cLhxVsQJG6KKWaypdPGNV7w1BCbbk";
		System.out.println(verifySignature(content, sign));
		String a = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCbFR1mQQxAnXtzEZIp/Lo4RVzU2c/FGCc7QoRHqBQTAxRXtn+n94ldgQBauDNm+nMu5UtsS0r+hXfaeTdJrhJ7pMZUy90kjLdvmzJ5EbjoQGoJdCzmthWBNvRD+m2tAAxYbDb0mcCpvor93RIkbkcphZudCvkG8+/xAfNmJdyZQIDAQAB";
	}

}
