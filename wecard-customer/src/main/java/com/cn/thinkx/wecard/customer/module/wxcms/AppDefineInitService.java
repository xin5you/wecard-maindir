package com.cn.thinkx.wecard.customer.module.wxcms;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.wecard.customer.core.spring.SpringBeanDefineService;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountService;
import com.cn.thinkx.wechat.base.wxapi.domain.Account;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import redis.clients.jedis.JedisCluster;

/**
 * 系统启动时自动加载，把公众号信息加入到缓存中
 */
public class AppDefineInitService implements SpringBeanDefineService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    @Autowired
    @Qualifier("jedisCluster")
    private JedisCluster jedisCluster;

    @Override
    public void initApplicationCacheData() {
        Account account = accountService.getByAccount(
//				jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, "WX_CUSTOMER_ACCOUNT"));
                jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, "WX_HUIKABAO_LIFE_ACCOUNT"));
        logger.info("加载公众号信息：{}", JSONObject.toJSONString(account));
        WxMemoryCacheClient.addMpAccount(account);
    }

}
