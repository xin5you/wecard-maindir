package com.cn.thinkx.wxclient.redis;

import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisCluster;

public class JedisUtilsSubChannel {
    private static Logger logger = LoggerFactory.getLogger(JedisUtilsSubChannel.class);

    public static void subChannel(String channel) {
        JedisCluster jedisCluster;
        try {
            jedisCluster = JedisClusterUtils.getInstance().getResource();
            RedisMsgPubSubListener listener = new RedisMsgPubSubListener();
            jedisCluster.subscribe(listener, channel);
        } catch (Exception e) {
            logger.error("## subChannel {}", channel, e);
        }
    }
}
