package com.cn.thinkx.common.redis.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cn.thinkx.pms.base.utils.ObjectUtils;
import com.cn.thinkx.pms.base.utils.StringUtils;

import redis.clients.jedis.JedisCluster;

/**
 * ShardedJedis Cache Redis集群部署时的工具类 
 * 
 * @serial Update by zyl in 2017/10/26
 * @serial Update by pucker in 2017/11/29
 */
public class JedisClusterUtils {

	private static Logger logger = LoggerFactory.getLogger(JedisClusterUtils.class);

	private static JedisCluster jedisCluster;

	private static JedisClusterUtils instance;

	private JedisClusterUtils() {

	}

	/**
	 * 获取工具类实例
	 * 
	 * @return
	 */
	public static JedisClusterUtils getInstance() {
		return getInstance(null);
	}

	/**
	 * 获取工具类实例 param 上下文
	 * 
	 * @return
	 */
	public static JedisClusterUtils getInstance(WebApplicationContext context) {
		if (instance == null) {
			synchronized (JedisClusterUtils.class) {
				if (instance == null) {
					instance = new JedisClusterUtils();
				}
			}
		}

		if (jedisCluster == null) {
			if (context != null)
				jedisCluster = (JedisCluster) context.getBean("jedisCluster");
			else {
				context = ContextLoader.getCurrentWebApplicationContext();
				jedisCluster = (JedisCluster) context.getBean("jedisCluster");
			}
			logger.info("初始加载redis集群配置成功！！！");
		}

		return instance;
	}

	/**
	 * 获取jedisCluster实例
	 * 
	 * @return
	 */
	public JedisCluster getResource() {
		if (jedisCluster == null) {
			WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
			jedisCluster = (JedisCluster) webContext.getBean("jedisCluster");
		}
		return jedisCluster;
	}

	/**
	 * 获取缓存
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public String get(String key) {
		String value = null;
		try {
			if (jedisCluster.exists(key)) {
				value = jedisCluster.get(key);
				value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
			}
		} catch (Exception e) {
			logger.error("## 获取key的值失败---->get key is {},value is {},exception is {}", key, value, e);
		}
		return value;
	}

	/**
	 * 设置缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public String set(String key, String value, int cacheSeconds) {
		String result = null;
		try {
			result = jedisCluster.set(key, value);
			if (cacheSeconds != 0) {
				jedisCluster.expire(key, cacheSeconds);
			}
		} catch (Exception e) {
			logger.error("## 设置key的值失败---->set key is {},value is {},exception is {}", key, value, e);
		}
		return result;
	}

	/**
	 * 设置缓存及超时时间（推荐使用）
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @param cacheSeconds
	 *            超时时间，0为不超时
	 * @return
	 */
	public String setex(String key, String value, int cacheSeconds) {
		String result = null;
		try {
			// SETEX是原子的，也可以通过把上面两个命令放到MULTI/EXEC块中执行的方式重现。相比连续执行上面两个命令，它更快，因为当Redis当做缓存使用时，这个操作更加常用。
			result = jedisCluster.setex(key, cacheSeconds, value);
		} catch (Exception e) {
			logger.error("## 设置key的值失败---->set key is {},value is {},exception is {}", key, value, e);
		}
		return result;
	}

	/**
	 * 删除缓存
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public long del(String key) {
		long flag = 0;
		try {
			if (!instance.exists(key)) {
				logger.error("## key不存在---->delete key is {}", key);
			} else {
				flag = jedisCluster.del(key);
				if (flag <= 0) {
					flag = jedisCluster.del(key);
				}
				if (flag > 0)
					logger.info("删除key的值成功---->delete key is {}", key);
			}
		} catch (Exception e) {
			logger.error("## 删除key的值失败---->delete key is {},exception is {}", key, e);
		}
		return flag;
	}

	/**
	 * 判断key是否存在
	 * 
	 * @param key
	 * @return true OR false
	 */
	public Boolean exists(String key) {
		try {
			return jedisCluster.exists(key);
		} catch (Exception e) {
			logger.error("## 判断key是否存在异常 exists key is {},exception is {}", key, e);
			return false;
		}
	}

	/**
	 * 设置key的过期时间
	 * 
	 * @param key
	 * @param seconds
	 */
	public void expire(String key, int seconds) {
		try {
			jedisCluster.expire(key, seconds);
			logger.info("设置key时间成功---->expire key is {},seconds is {}(s)", key, seconds);
		} catch (Exception e) {
			logger.error("## 设置key时间失败---->expire key is {},e is {}", key, e);
		}
	}

	/**
	 * 设置 list (序列化)
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void setList(String key, List<T> list) {
		try {
			if (list == null || list.isEmpty()) {
				logger.error("## list为空!---->setList key is {}", key);
				return;
			} // SerializeUtils.serialize(list)
			jedisCluster.set(key.getBytes(), SerializeUtils.serialize(list));
		} catch (Exception e) {
			logger.error("## 设置list失败---->setList key is {},e is {}", key, e);
		}
	}

	/**
	 * 设置 list及超时时间(序列化)
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void setexList(String key, List<T> list, int cacheSeconds) {
		try {
			if (list == null || list.isEmpty()) {
				logger.error("## list为空!---->setList key is {}", key);
				return;
			} // SerializeUtils.serialize(list)
			jedisCluster.setex(key.getBytes(), cacheSeconds, SerializeUtils.serialize(list));
		} catch (Exception e) {
			logger.error("## 设置list失败---->setList key is {},e is {}", key, e);
		}
	}

	/**
	 * 获取list (序列化)
	 * 
	 * @param <T>
	 * @param key
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String key) {
		if (jedisCluster == null || !jedisCluster.exists(key.getBytes())) {
			return null;
		}
		List<T> list = null;
		try {
			byte[] in = jedisCluster.get(key.getBytes());
			list = (List<T>) SerializeUtils.deserialize(in);
		} catch (Exception e) {
			logger.error("## 获取list集合失败--->key is {},e is {}", key, e);
		}
		return list;
	}

	/**
	 * 设置 map(序列化)
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void setMap(String key, Map<String, T> map) {
		try {
			jedisCluster.set(key.getBytes(), SerializeUtils.serialize(map));
		} catch (Exception e) {
			logger.error("## 设置map失败--->setMap key is {},e is {}", key, e);
		}
	}

	/**
	 * 设置 map及超时时间(序列化)
	 * 
	 * @param <T>
	 * @param key
	 * @param value
	 */
	public <T> void setexMap(String key, Map<String, T> map, int cacheSeconds) {
		try {
			jedisCluster.setex(key.getBytes(), cacheSeconds, SerializeUtils.serialize(map));
		} catch (Exception e) {
			logger.error("## 设置map失败--->setexMap key is {},cacheSeconds is {}(s),e is {}", key, cacheSeconds, e);
		}
	}

	/**
	 * 获取map (序列化)
	 * 
	 * @param <T>
	 * @param key
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public <T> Map<String, T> getMap(String key) {

		if (jedisCluster == null || !jedisCluster.exists(key.getBytes())) {
			return null;
		}
		Map<String, T> map = null;
		try {
			byte[] in = jedisCluster.get(key.getBytes());
			map = (Map<String, T>) SerializeUtils.deserialize(in);
		} catch (Exception e) {
			logger.error("## 获取map集合失败--->key is {},e is {}", key, e);
		}
		return map;
	}

	/**
	 * 设置key中指定hash集中指定字段的值
	 * 
	 * @param cacheKey
	 * @param key
	 * @param value
	 */
	public void hset(String key, String field, String value) {
		try {
			jedisCluster.hset(key, field, value);
		} catch (Exception e) {
			logger.error("## 设置hash值失败---->hset key is " + key + ",field is " + field + ",value is " + value, e);
		}
	}

	/**
	 * 获取hash的值
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field) {
		String value = null;
		try {
			value = jedisCluster.hget(key, field);
		} catch (Exception e) {
			logger.error("## 获取hash的值失败---->hget key is " + key + ",field is " + field, e);
		}
		return value;
	}

	/**
	 * 删除hash的值
	 * 
	 * @param key
	 *            键
	 * @return 值
	 */
	public void hdel(String key, String field) {

		try {
			Long hdel = jedisCluster.hdel(key, field);
			if (hdel <= 0) {
				logger.error("## 删除hash的值失败---->hdel key is {},field is {}", key, field);
			}
			if (hdel > 0)
				logger.info("## 删除hash的值成功---->hdel key is {},field is {}", key, field);
		} catch (Exception e) {
			logger.error("## 删除hash的值失败---->hdel key is {},field is {}", key, field, e);
		}
	}

	/**
	 * 设置key的sortedset集合
	 * 
	 * @param key
	 * @param member
	 */
	public void zadd(String key, Map<String, Double> scoreMembers) {

		try {
			Long zadd = jedisCluster.zadd(key, scoreMembers);
			if (zadd > 0) {
				logger.info("zadd=" + zadd);
				logger.info("设置sortedset集合成功---->zadd key is {},scoreMembers is {}", key, scoreMembers.entrySet());
			} else {
				logger.error("设置sortedset集合失败---->zadd key is {},scoreMembers is {}", key, scoreMembers.entrySet());
			}
		} catch (Exception e) {
			logger.error("## 设置sortedset集合失败---->zadd key is " + key + ",scoreMembers is " + scoreMembers.entrySet() + "e is ", e);
		}

	}

	/**
	 * 获取sortedset集合
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, Double> zrange(String key) {
		Map<String, Double> zranges = new HashMap<String, Double>();
		try {
			Set<String> zrange = jedisCluster.zrange(key, 0, -1);

			for (String set : zrange) {
				Double zscore = jedisCluster.zscore(key, set);
				zranges.put(set, zscore);
			}
			if (!zranges.isEmpty()) {
				logger.info("获取sortedset集合成功---->zranges key is {},members is {}", key, zranges.entrySet());
			} else {
				logger.error("## 获取sortedset集合失败---->zranges key is {}", key, zranges.entrySet());
			}
		} catch (Exception e) {
			logger.error("## 获取sortedset集合失败---->zranges key is {}", key);
		}
		return zranges;
	}

	/**
	 * 删除set集合中元素
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public boolean zrem(String key, Set<String> members) {
		long zrem = 0;
		boolean flag = false;
		String[] str = new String[members.size()];
		try {
			if (!members.isEmpty()) {
				zrem = jedisCluster.zrem(key, members.toArray(str));
			}
			if (zrem > 0) {
				flag = true;
				logger.info("删除sortedset集合成功---->smembers key is {},members is {}", key, members.toArray(str));
			} else {
				logger.error("删除sortedset集合失败---->smembers key is {}", key, members.toArray(str));
			}
		} catch (Exception e) {
			logger.error("## 删除sortedset集合异常---->smembers key is {},e is {}", key, e);
		}
		return flag;
	}

	/**
	 * 设置list集合
	 * 
	 * @param key
	 * @param values
	 */
	public void lpush(String key, List<String> values) {
		String[] str = new String[values.size()];
		try {
			Long lpush = jedisCluster.lpush(key, values.toArray(str));
			if (lpush > 0) {
				logger.info("lpush=" + lpush);
				logger.info("设置list集合成功---->lpush key is {},member is {}", key, values.toArray(str));
			} else {
				logger.info("lpush=" + lpush);
				logger.error("## 设置list集合失败---->lpush key is {},member is {}", key, values.toArray(str));
			}
		} catch (Exception e) {
			logger.error("## 设置list集合异常---->sadd lpush is " + key + ",member is " + values, values.toArray(str) + "e is ", e);
		}
	}

	/**
	 * 获取list集合
	 * 
	 * @param key
	 * @return
	 */
	public List<String> lrange(String key) {
		List<String> lrange = null;
		try {
			lrange = jedisCluster.lrange(key, 0, -1);
			String[] str = new String[lrange.size()];
			if (!lrange.isEmpty()) {
				logger.info("获取list集合成功---->lrange key is {},values is {}", key, lrange.toArray(str));
			} else {
				logger.error("## 获取list集合失败---->lrange key is {}", key, lrange.toArray(str));
			}
		} catch (Exception e) {
			logger.error("## 获取list集合异常---->lrange key is {}", key);
		}
		return lrange;

	}

	/**
	 * 删除list集合
	 * 
	 * @param key
	 * @return
	 */
	public boolean lremAll(String key) {
		String[] str;
		boolean flag = false;
		List<String> list = new ArrayList<String>();
		try {
			String l = jedisCluster.lpop(key);
			while (l != null) {
				list.add(l);
				l = jedisCluster.lpop(key);
			}
			str = new String[list.size()];
			if (list.size() > 0) {
				flag = true;
				logger.info("删除list集合成功---->lrem key is {},values is {}", key, list.toArray(str));
			} else {
				logger.error("## 删除list集合失败---->lrem key is {} values is {}", key, list.toArray(str));
			}
		} catch (Exception e) {
			logger.error("## 删除list集合异常---->lrem key is {},e is {}", key, e);
		}
		return flag;
	}

	/**
	 * 设置key的set集合
	 * 
	 * @param key
	 * @param member
	 */
	public void sadd(String key, Set<String> member) {
		String[] str = new String[member.size()];
		try {
			Long sadd = jedisCluster.sadd(key, member.toArray(str));
			if (sadd > 0) {
				logger.info("sadd=" + sadd);
				logger.info("设置set集合成功---->sadd key is {},member is {}", key, member.toArray(str));
			} else {
				logger.error("## 设置set集合失败---->sadd key is {},member is {}", key, member.toArray(str));
			}
		} catch (Exception e) {
			logger.error("## 设置set集合异常---->sadd key is " + key + ",member is " + member.toArray(str) + "e is ", e);
		}

	}

	/**
	 * 获取set集合
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> smembers(String key) {
		Set<String> smembers = null;
		try {
			smembers = jedisCluster.smembers(key);
			String[] str = new String[smembers.size()];
			if (!smembers.isEmpty()) {
				logger.info("获取set集合成功---->smembers key is {},members is {}", key, smembers.toArray(str));
			} else {
				logger.error("## 获取set集合失败---->smembers key is {}", key, smembers.toArray(str));
			}
		} catch (Exception e) {
			logger.error("## 获取set集合异常---->smembers key is {}", key);
		}
		return smembers;
	}

	/**
	 * 删除set集合中元素
	 * 
	 * @param key
	 * @param members
	 * @return
	 */
	public boolean srem(String key, Set<String> members) {
		long srem = 0;
		boolean flag = false;
		String[] str = new String[members.size()];
		try {
			for (String s : members.toArray(str)) {
				boolean sismember = instance.sismember(key, s);
				if (!sismember) {
					logger.info("删除set集合---->smembers key is {},members is {},{}不存在", key, members.toArray(str), s);
					members.remove(s);
				}
			}
			if (!members.isEmpty()) {
				srem = jedisCluster.srem(key, members.toArray(str));
			}
			if (srem > 0) {
				flag = true;
				logger.info("删除set集合成功---->smembers key is {},members is {}", key, members.toArray(str));

			} else {
				logger.error("## 删除set集合失败---->smembers key is {}", key, members.toArray(str));
			}
		} catch (Exception e) {
			logger.error("## 删除set集合失败异常---->smembers key is {},e is {}", key, e);

		}
		return flag;
	}

	/**
	 * 判断set集合中是否存在member
	 * 
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sismember(String key, String member) {
		boolean sismember = false;
		try {
			sismember = jedisCluster.sismember(key, member);
		} catch (Exception e) {
			logger.error("## 判断set集合中member是否存在异常---->sismember key {}", key);

		}
		return sismember;
	}

	public void pubChannel(String channel, String msg) {
		try {
			jedisCluster.publish(channel, msg);
			logger.info("pubChannel Channel:{}  MSG:{}", channel, msg);
		} catch (Exception e) {
			logger.error("## pubChannel Channel:{}  MSG:{}", channel, msg, e);
		}
	}

	/**
	 * 获取byte[]类型Key
	 * 
	 * @param key
	 * @return
	 */
	public static byte[] getBytesKey(Object object) {
		if (object instanceof String) {
			return StringUtils.getBytes((String) object);
		} else {
			return ObjectUtils.serialize(object);
		}
	}

	/**
	 * Object转换byte[]类型
	 * 
	 * @param key
	 * @return
	 */
	public byte[] toBytes(Object object) {
		return ObjectUtils.serialize(object);
	}

	/**
	 * byte[]型转换Object
	 * 
	 * @param key
	 * @return
	 */
	public Object toObject(byte[] bytes) {
		return ObjectUtils.unserialize(bytes);
	}

	/**
	 * 关闭资源
	 */
	public static void returnsClusterResource() {
		if (jedisCluster != null) {
			try {
				logger.info("关闭jedisCluster资源---->jedisCluster is " + jedisCluster);
				jedisCluster.close();
			} catch (IOException e) {

			}
		}
	}
}