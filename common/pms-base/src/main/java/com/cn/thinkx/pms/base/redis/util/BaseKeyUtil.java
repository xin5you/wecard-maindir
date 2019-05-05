package com.cn.thinkx.pms.base.redis.util;


/**
 *  加密key 统一管理
 * @author zqy
 *
 */
public class BaseKeyUtil {

	/**
	 * DES 加密字符串
	 * @return
	 */
	public static String getEncodingAesKey(){
		return RedisDictProperties.getInstance().getdictValueByCode("ENCODING_AES_KEY");
	}
}
