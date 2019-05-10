package com.cn.thinkx.pms.base.redis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.redis.service.RedisCacheService;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisCacheServiceImpl<V> implements RedisCacheService<String, V> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 设置哈希类型数据到redis 数据库
     *
     * @param cacheKey map.key
     * @param key      表字段
     * @param value
     * @return
     */
    @Override
    public void hset(String cacheKey, String key, V value) {
        String jValue = JSONArray.toJSONString(value);
        logger.info("Redis cachekey{} key{} set hash value{}", cacheKey, key, value);
        JedisClusterUtils.getInstance().hset(cacheKey, key, jValue);
    }

    /**
     * 获取哈希表数据类型的值 <V>
     *
     * @param cacheKey
     * @param key
     * @return
     */
    public V hgetVO(String cacheKey, String key, Class<V> clazz) {
        String jValue = hget(cacheKey, key);

        if (StringUtil.isNullOrEmpty(jValue)) {
            return null;
        }
        return (V) JSONArray.parseObject(jValue, clazz);
    }

    /**
     * 设置哈希类型数据到redis 数据库
     *
     * @param cacheKey map.key
     * @param key
     * @param value
     * @return
     */
    @Override
    public void hsetString(String cacheKey, String key, String value) {
        JedisClusterUtils.getInstance().hset(cacheKey, key, value);
    }

    /**
     * 获取哈希表数据类型的值
     *
     * @param cacheKey
     * @param key
     * @return
     */
    @Override
    public String hget(String cacheKey, String key) {
        return JedisClusterUtils.getInstance().hget(cacheKey, key);
    }

    /**
     * 删除哈希表数据类型的值
     *
     * @param cacheKey
     * @param key
     */
    public void hdel(String cacheKey, String key) {
        JedisClusterUtils.getInstance().hdel(cacheKey, key);
    }
}
