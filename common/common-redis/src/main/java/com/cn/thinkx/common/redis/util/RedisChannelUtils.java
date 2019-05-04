package com.cn.thinkx.common.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class RedisChannelUtils {
	private static Logger logger = LoggerFactory.getLogger(RedisChannelUtils.class);
	
	 private static RedisChannelUtils instance;
	 
	 private RedisChannelUtils (){
		 
	 }  
	
	 public static RedisChannelUtils getInstance(){
		 if (instance == null){
			synchronized(RedisChannelUtils.class){
				if (instance == null){
					instance = new RedisChannelUtils();
				}
			}
		}
		return instance;
	}
	
	public String getChannelInfJson(String channel){
	   String channelInfJson=null;
	   try{
		    channelInfJson=JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_CHANNEL_SECURITY_INF,channel);
			if(StringUtil.isNullOrEmpty(channelInfJson)){
				channelInfJson=JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_CHANNEL_SECURITY_INF,channel);
			}
		}catch(Exception ex){
			logger.error("getChannelInfJson error----->"+ex);
		}
		return channelInfJson;
	}
	
}
