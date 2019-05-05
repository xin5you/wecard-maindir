package com.cn.thinkx.pms.base.redis.core;

import com.cn.thinkx.pms.base.redis.util.RedisPropertiesUtils;
import com.cn.thinkx.pms.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Resdis工具类
 * 
 * @author zqy
 *
 */
public class JedisUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisUtils.class);

	public static boolean JEDIS_STATUS;
	static {
		try {
			String redis_unlock = RedisPropertiesUtils.getProperty("redis.unlock");
			JEDIS_STATUS = StringUtils.isEmpty(redis_unlock) ? false : Boolean.parseBoolean(redis_unlock);

		} catch (Exception e) {
			JEDIS_STATUS = false;
			logger.error("## 请检查 public_system.properties 配置", e);
		}
	}

	public static boolean getRedisStatus() {
		String redis_unlock = RedisPropertiesUtils.getProperty("redis.unlock");
		boolean JEDIS_STATUS = StringUtils.isEmpty(redis_unlock) ? false : Boolean.parseBoolean(redis_unlock);
		return JEDIS_STATUS;
	}

	public static String get(String key) {
		return JedisClusterUtils.getInstance().get(key);
	}

	public static String set(String key, String value, int cacheSeconds) {
		return JedisClusterUtils.getInstance().set(key, value, cacheSeconds);
	}

	public static void pubChannel(String channel, String msg) {
		JedisClusterUtils.getInstance().pubChannel(channel, msg);
	}

}
