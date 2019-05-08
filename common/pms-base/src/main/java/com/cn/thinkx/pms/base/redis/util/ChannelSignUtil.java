package com.cn.thinkx.pms.base.redis.util;

import com.cn.thinkx.pms.base.utils.StringUtil;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 薪无忧 渠道接入 接口签名验证
 * 
 * @author pucker
 *
 */
public class ChannelSignUtil {

	/**
	 * 根据传入对象生成签名
	 * 
	 * @param obj
	 * @return
	 */
	public static String genSign(Object obj) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass());// 将当前类属性及其值放入map
			if (obj.getClass().getSuperclass() !=null && obj.getClass().getGenericSuperclass() != null) {
				getProperty(map, obj, obj.getClass().getSuperclass());// 将当前类父类属性及其值放入map
				if (obj.getClass().getSuperclass().getSuperclass() !=null && obj.getClass().getGenericSuperclass().getClass().getGenericSuperclass() != null)
					getProperty(map, obj, obj.getClass().getSuperclass().getSuperclass());// 将当前类父类属性及其值放入map
			}
		}
		return sign(map,map.get("channel"));
	}
	
	public static void getProperty(SortedMap<String, String> map, Object obj, Class<?> clazz) {
		// 获取f对象对应类中的所有属性域 
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			// 过滤签名字段
			if ("sign".equals(varName) || "serialVersionUID".equals(varName))
				continue;
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null && !StringUtil.isNullOrEmpty(o.toString())){
					if("timestamp".equals(varName) && "0".equals( o.toString())){  //处理时间戳不填页面逻辑处理，后续逻辑若删除该代码，需慎重
						
					}else{
						map.put(varName, o.toString());
					}
				}
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String byteToStr(byte[] byteArray) {
		String rst = "";
		for (int i = 0; i < byteArray.length; i++) {
			rst += byteToHex(byteArray[i]);
		}
		return rst;
	}

	public static String byteToHex(byte b) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(b >>> 4) & 0X0F];
		tempArr[1] = Digit[b & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	/**
	 * MD5编码
	 * 
	 * @param origin
	 *            原始字符串
	 * @return 经过MD5加密之后的结果
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(origin.getBytes("UTF-8"));
			resultString = byteToStr(md.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	/**
	 * 组装报文签名
	 * 
	 * @param items
	 * @param channel
	 * @return
	 */
	public static String sign(SortedMap<String, String> items,String channel) {
		StringBuilder forSign = new StringBuilder();
		for (String key : items.keySet()) {
			forSign.append(key).append("=").append(items.get(key)).append("&");
		}
		String key = "";
		if (StringUtil.isNullOrEmpty(channel)) {
			key = RedisDictProperties.getInstance().getdictValueByCode("COMMON_SIGN_KEY");
		} else {
			key = RedisDictProperties.getInstance().getChannelKeyByCode(channel);
		}
		forSign.append("key=").append(key);
		String result = MD5Encode(forSign.toString());
		return result.toUpperCase();
	}
	
	
	
	
	/**
	 * paipai扫码盒子支付签名
	 * @param obj
	 * @return
	 */
	public static String genPPScanPaySign(Object obj) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass());// 将当前类属性及其值放入map
			if (obj.getClass().getSuperclass() !=null && obj.getClass().getGenericSuperclass() != null) {
				getProperty(map, obj, obj.getClass().getSuperclass());// 将当前类父类属性及其值放入map
				if (obj.getClass().getSuperclass().getSuperclass() !=null && obj.getClass().getGenericSuperclass().getClass().getGenericSuperclass() != null)
					getProperty(map, obj, obj.getClass().getSuperclass().getSuperclass());// 将当前类父类属性及其值放入map
			}
		}
		return scanPaySign(map);
	}
	
	/**
	 * paipai扫码盒子支付签名
	 * @param items
	 * @param channel
	 * @return
	 */
	public static String scanPaySign(SortedMap<String, String> items) {
		StringBuilder forSign = new StringBuilder();
		for (String key : items.keySet()) {
			forSign.append(key).append("=").append(items.get(key)).append("&");
		}
		String key = RedisDictProperties.getInstance().getdictValueByCode("PP_BOX_SCAN_PAY_SIGN_KEY");
		forSign.append("key=").append(key);
		String result = MD5Encode(forSign.toString());
		return result.toUpperCase();
	}
	

	public static void main(String[] args) {
		SortedMap<String, String> itemsMap = new TreeMap<String, String>();
		itemsMap.put("auth_code", "40814944250101347961");
		itemsMap.put("bill_create_ip", "172.18.0.245");
		itemsMap.put("device_no", "4111171100106646");
		itemsMap.put("goods_desc", "goods desc");
		itemsMap.put("nonce_str", "5addb921");
		itemsMap.put("pp_trade_no", "184231844499e463d");
		itemsMap.put("total_fee", "1");
//		
//	    "code": "SUCCESS",
//	    "msg": "支付成功",
//	    "nonce_str": "5996a24d",
//	    "pay_type": "WXPAY",
//	    "pp_trade_no": "17818081613a4146d",
//	    "printType": "0",
//	    "sign": "E6BE1F565FFEF0E851DD6CB42C7F366B",
//	    "time_end": "20170818161637",
//	    "total_fee": "1",
//	    "transaction_id": "2c911e5a-7d8f-434a-813a-2030814e68e4"
		
		StringBuilder forSign = new StringBuilder();
		for (String key : itemsMap.keySet()) {
			forSign.append(key).append("=").append(itemsMap.get(key)).append("&");
		}
		forSign.append("key=").append("68cCSHFD0mAs3H1Dduw0raQMbVlpmVd8");
		System.out.println("forSign.toString--->"+forSign.toString());
		String result = MD5Encode(forSign.toString());
		System.out.println(" result.toUpperCase()--->"+ result.toUpperCase());
		
//	    \"code\": \"SUCCESS\",
//	    \"msg\": \"支付成功\",
//	    \"nonce_str\": \"59969fd2\",
//	    \"pay_type\": \"WXPAY\",
//	    \"pp_trade_no\": \"17818080538a414e0\",
//	    \"print\": \"Print，打印在哪里\",
//	    \"printType\": \"1\",
//	    \"print_qr\": \"http://weixin.qq.com/r/PUTs9GLEwD_-rSaX9xEv\",
//	    \"receipt\": \"suLK1HJlY2VpcHTKssO0tqu2q6GjoaOhow==\",
//	    //"sign\": \"4DB33EDA40CD94A9C097B563FD7891BF\",
//	    \"time_end\": \"20170818160602\",
//	    \"total_fee\": \"1\",
//	    \"transaction_id\": \"b466cd6d-30c7-4282-918b-94d87c276315\"

	}
}
