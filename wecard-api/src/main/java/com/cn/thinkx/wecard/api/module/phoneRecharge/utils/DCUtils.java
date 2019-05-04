package com.cn.thinkx.wecard.api.module.phoneRecharge.utils;

import java.lang.reflect.Field;
import java.util.SortedMap;
import java.util.TreeMap;

import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class DCUtils {

	/**
	 * 根据传入对象生成签名
	 * 
	 * @param obj
	 *            对象
	 * @param key
	 *            秘钥
	 * @return
	 */
	public static String genSign(Object obj, String key) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass());// 将当前类属性及其值放入map
		}
		return sign(map, key);
	}
	
	public static String genUrl(Object obj, String url) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass());// 将当前类属性及其值放入map
		}
		StringBuilder getUrl = new StringBuilder(url + "?");
		for (String key : map.keySet()) {
			getUrl.append(key).append("=").append(map.get(key)).append("&");
		}
		return getUrl.toString().substring(0, getUrl.length() - 1);
	}

	public static void getProperty(SortedMap<String, String> map, Object obj, Class<?> clazz) {
		// 获取f对象对应类中的所有属性域
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			// 过滤签名字段
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null && !StringUtil.isNullOrEmpty(o.toString()))
					map.put(varName, o.toString());
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static String sign(SortedMap<String, String> items, String key) {
		StringBuilder forSign = new StringBuilder();
		for (String value : items.values()) {
			forSign.append(value);
		}
		forSign.append(key);
		return MD5Util.md5(forSign.toString());
	}
	
	/*
    1、移动号段有134,135,136,137, 138,139,147,150,151, 152,157,158,159,178,182,183,184,187,188。
    2、联通号段有130，131，132，155，156，185，186，145，176。
    3、电信号段有133，153，177.173，180，181，189。
    */
    static String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[23478]{1})|([4]{1}[7]{1})|([7]{1}[8]{1}))[0-9]{8}$";
    static String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1})|([4]{1}[5]{1})|([7]{1}[6]{1}))[0-9]{8}$";
    static String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1})|([7]{1}[37]{1}))[0-9]{8}$";

    /**
     * 手机号匹配运营商 
     * 
     * @param mobPhnNum
     * @return 1:YD 2:LT 3:DX
     */
    public static int matchMobPhnNo(String mobPhnNum) {
    	if (StringUtil.isNullOrEmpty(mobPhnNum))
    		return 0;
    	
    	mobPhnNum = StringUtil.trim(mobPhnNum);
        // 判断手机号码是否是11位
        if (mobPhnNum.length() == 11) {
            // 判断手机号码是否符合中国移动的号码规则
            if (mobPhnNum.matches(YD)) {
                return 1;
            }
            // 判断手机号码是否符合中国联通的号码规则
            else if (mobPhnNum.matches(LT)) {
            	return 2;
            }
            // 判断手机号码是否符合中国电信的号码规则
            else if (mobPhnNum.matches(DX)) {
            	return 3;
            }
            // 都不合适 未知
            else {
            	return 0;
            }
        }
        // 不是11位
        else {
        	return 0;
        }
    }
    
}
