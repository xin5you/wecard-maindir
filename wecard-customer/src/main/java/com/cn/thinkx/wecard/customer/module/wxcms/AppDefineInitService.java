package com.cn.thinkx.wecard.customer.module.wxcms;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.wecard.customer.core.spring.SpringBeanDefineService;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountService;
import com.cn.thinkx.wechat.base.wxapi.domain.Account;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import redis.clients.jedis.JedisCluster;

/**
 * 系统启动时自动加载，把公众号信息加入到缓存中
 */
public class AppDefineInitService implements SpringBeanDefineService {
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
        System.out.println("加载公众号信息：" + JSONObject.toJSONString(account));
        WxMemoryCacheClient.addMpAccount(account);
    }

}
