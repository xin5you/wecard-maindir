package com.cn.thinkx.wecard.centre.module.wxcms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.wecard.centre.core.spring.SpringBeanDefineService;
import com.cn.thinkx.wecard.centre.module.wxcms.service.AccountService;
import com.cn.thinkx.wechat.base.wxapi.domain.Account;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

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

	public void initApplicationCacheData() {
		Account account = accountService.getByAccount(
				jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, "WX_HUIKABAO_LIFE_ACCOUNT"));
//				jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, "WX_CUSTOMER_ACCOUNT"));
		System.out.println("公众号模板消息Account============" + JSONArray.toJSONString(account));
		WxMemoryCacheClient.addMpAccount(account);

		/*account = accountService.getByAccount(jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, "WX_MCHNT_ACCOUNT"));
		WxMemoryCacheClient.addMpAccount(account);*/
	}
}
