package com.cn.thinkx.pms.base.redis.util;

import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisDictProperties {

    private Logger logger = LoggerFactory.getLogger(RedisDictProperties.class);

    private static RedisDictProperties instance;

    private RedisDictProperties() {

    }

    public static RedisDictProperties getInstance() {
        if (instance == null) {
            synchronized (RedisDictProperties.class) {
                if (instance == null) {
                    instance = new RedisDictProperties();
                }
            }
        }
        return instance;
    }

    public String getdictValueByCode(String dictCode) {
        String value = "";
        try {
            value = JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, dictCode);
            if (StringUtil.isNullOrEmpty(value)) {
                value = JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, dictCode);
            }
//			logger.info("getdictValueByCode--->key[{}] value[{}]", dictCode, value);
        } catch (Exception ex) {
            logger.error("## getdictValueByCode is error" + ex);
        }
        return value;
    }

    /**
     * 获取渠道签名KEY
     *
     * @param channelCode
     * @return
     */
    public String getChannelKeyByCode(String channelCode) {
        String value = "";
        try {
            value = JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_CHANNEL_SECURITY_INF_KV,
                    channelCode);
            if (StringUtil.isNullOrEmpty(value)) {
                value = JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_CHANNEL_SECURITY_INF_KV,
                        channelCode);
            }
        } catch (Exception ex) {
            logger.error("## getChannelKeyByCode is error" + ex);
        }
        return value;
    }

}
