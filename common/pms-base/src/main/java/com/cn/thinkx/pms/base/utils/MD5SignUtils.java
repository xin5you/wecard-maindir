package com.cn.thinkx.pms.base.utils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MD5SignUtils {

	
	/**
	 * 根据传入对象生成签名
	 * @param obj
	 * @param keyName 加密key的名称
	 * @param keyValue key值
	 * @param fields 过滤的字段名称 例如：new String[]{"sign","serialVersionUID"}
	 * @param v 版本号，自定义扩展
	 * @return
	 */
	public static String genSign(Object obj,String keyName,String keyValue,String[] fields,String v) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass(),fields);// 将当前类属性及其值放入map
			if (obj.getClass().getSuperclass() != null && obj.getClass().getGenericSuperclass() != null) {
				getProperty(map, obj, obj.getClass().getSuperclass(),fields);// 将当前类父类属性及其值放入map
				if (obj.getClass().getSuperclass().getSuperclass() != null && obj.getClass().getGenericSuperclass().getClass().getGenericSuperclass() != null)
					getProperty(map, obj, obj.getClass().getSuperclass().getSuperclass(),fields);// 将当前类父类属性及其值放入map
			}
		}
			return sign(map, keyName, keyValue);
	}

	/**
	 * 获取对象的Map属性值 map集合
	 * @param obj
	 * @param fields 过滤的字段名称 例如：new String[]{"sign","serialVersionUID"}
	 * @param v 版本号，自定义扩展
	 * @return
	 */
	public static Map getObjectMaps(Object obj,String[] fields,String v) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass(),fields);// 将当前类属性及其值放入map
			if (obj.getClass().getSuperclass() != null && obj.getClass().getGenericSuperclass() != null) {
				getProperty(map, obj, obj.getClass().getSuperclass(),fields);// 将当前类父类属性及其值放入map
				if (obj.getClass().getSuperclass().getSuperclass() != null && obj.getClass().getGenericSuperclass().getClass().getGenericSuperclass() != null)
					getProperty(map, obj, obj.getClass().getSuperclass().getSuperclass(),fields);// 将当前类父类属性及其值放入map
			}
		}
		return map;
	}
	
	
	public static void getProperty(SortedMap<String, String> map, Object obj, Class<?> clazz, String[] fieldNames) {
		// 获取f对象对应类中的所有属性域
		boolean isField=false;
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			isField=false;
			String varName = fields[i].getName();
			// 过滤签名字段
			if(fieldNames !=null && fieldNames.length>0){
				for(String fieldName:fieldNames){
						if (fieldName.equals(varName)){
							isField=true;
							break;
						}
				}
				if(isField){
					continue;
				}
			}
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null && !StringUtil.isNullOrEmpty(o.toString())){
					map.put(varName, o.toString());
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
	public static String sign(SortedMap<String, String> items, String keyName ,String keyValue) {
		StringBuilder forSign = new StringBuilder();
		for (String key : items.keySet()) {
			forSign.append(key).append("=").append(items.get(key)).append("&");
		}
		
		if(StringUtil.isNullOrEmpty(keyName)){
			forSign.append(StringUtil.nullToString(keyValue));
		}else{
			forSign.append(keyName).append("=").append(keyValue);
		}
		String result = MD5Encode(forSign.toString());
		return result.toUpperCase();
	}

	public static void main(String[] args) {

		
	}
}
