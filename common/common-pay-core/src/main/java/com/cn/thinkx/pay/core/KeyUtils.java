package com.cn.thinkx.pay.core;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.RandomUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 涉及到的HTTP请求方法,JSON工具等可任意替换
 * 
 */
public class KeyUtils {
    
	//测试数据
	public static final String ZHONGFU_PAY_URL="ZHONGFU_PAY_URL"; //中付获取keyURL路径
	public static final String ZHONGFU_KEY_URL="ZHONGFU_KEY_URL"; //中付获取keyURL路径
	public static final String ZHONGFU_PAY_USER_KEY="ZHONGFU_PAY_USER_KEY"; //中付商户号
	public static final String ZHONGFU_ORGAN_NO="ZHONGFU_ORGAN_NO";  //中付结构号
	public static final String ZHONGFU_PAY_KEY="ZHONGFU_PAY_KEY";  //中付代付密码

    private static final String PAY_TYPE="73";
	private static final String IP="127.0.0.1";
    public static final String responseCode="00";  //代付成功标识

	public  String getRSAKey(){
		String batchNo =new SimpleDateFormat("yyyyMMdd").format(new Date());
		String ip = IP;
		String serialNo = RandomUtils.getRandomNumbernStr(10)+new Date().getTime();//0123456789应随机生成
		String userKey = RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_PAY_USER_KEY);
		String organNo=RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_ORGAN_NO);
		String payType=PAY_TYPE;
		JSONObject json=new JSONObject();
		json.put("batchNo", batchNo);
		json.put("ip", ip);
		json.put("serialNo", serialNo);
		json.put("userKey", userKey);
		json.put("organNo", organNo);
		json.put("payType", payType);
		String preSign=batchNo+ip+organNo+payType+serialNo+userKey;
		String res="";
		String signature=RSAUtil.getSHA256Str(preSign);
		json.put("signature", signature);
		res=HttpClientUtil.sendPostReturnStr(RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_KEY_URL)+"getRSAKey", json.toString());
		System.out.println("res="+res);
		String privateKey=JSONObject.parseObject(res).getString("RSAPrivateKey");
		return privateKey;
	}
	public  Map<String, String> getKeys(String privateKey) throws Exception{
		String batchNo =new SimpleDateFormat("yyyyMMdd").format(new Date());
		String ip = IP;
		String serialNo = RandomUtils.getRandomNumbernStr(10)+new Date().getTime();//9876543210应随机生成
		String userKey = RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_PAY_USER_KEY);
		String organNo=RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_ORGAN_NO);
		String payType = PAY_TYPE;
		JSONObject json=new JSONObject();
		json.put("batchNo", batchNo);
		json.put("ip", ip);
		json.put("serialNo", serialNo);
		json.put("userKey", userKey);
		json.put("organNo", organNo);
		json.put("payType", payType);
		String preSign=batchNo+ip+organNo+payType+serialNo+userKey;
		String res="";
		String signature=RSAUtil.sign(preSign.getBytes(), privateKey);
		json.put("signature", signature);
		res=HttpClientUtil.sendPostReturnStr(RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_KEY_URL)+"getKeys", json.toString());
		String aes=JSONObject.parseObject(res).getString("AES");
		String md5=JSONObject.parseObject(res).getString("MD5");
		aes=decode(aes);
		md5=decode(md5);
		Map<String, String> resMap=new HashMap<String, String>(2);
		resMap.put("AES", aes);
		resMap.put("MD5", md5);
        return resMap;
	}
	public  Map<String, String> getZek(String privateKey) throws Exception{
		String batchNo =new SimpleDateFormat("yyyyMMdd").format(new Date());
		String ip = IP;
		String serialNo = RandomUtils.getRandomNumbernStr(10)+new Date().getTime();//9876543210应随机生成
		String userKey = RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_PAY_USER_KEY);
		String organNo=RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_ORGAN_NO);
		String payType = PAY_TYPE;
		JSONObject json=new JSONObject();
		json.put("batchNo", batchNo);
		json.put("ip", ip);
		json.put("serialNo", serialNo);
		json.put("userKey", userKey);
		json.put("organNo", organNo);
		json.put("payType", payType);
		String preSign=batchNo+ip+organNo+payType+serialNo+userKey;
		String res="";
		String signature=RSAUtil.sign(preSign.getBytes(), privateKey);
		json.put("signature", signature);
		res=HttpClientUtil.sendPostReturnStr(RedisDictProperties.getInstance().getdictValueByCode(ZHONGFU_KEY_URL)+"getZek", json.toString());
		String zek=JSONObject.parseObject(res).getString("ZEK");
		zek=decode(zek);
		Map<String, String> resMap=new HashMap<String, String>(2);
		resMap.put("ZEK", zek);
		return resMap;
	}
	public  String decode(String str){
		String res="";
		int xy=Integer.parseInt(str.substring(0,1)+str.substring(str.length()-1));
		str=str.substring(1,str.length()-1);
		int arr[]=new int[str.length()/2];
		for (int i = 0; i < str.length(); i+=2) {
			arr[i/2]=Integer.parseInt(str.substring(i,i+2));
		}
		for (int i = 0; i < arr.length; i++) {
			res+=(char)(arr[i]+xy);
		}
		return res;
	}
}
