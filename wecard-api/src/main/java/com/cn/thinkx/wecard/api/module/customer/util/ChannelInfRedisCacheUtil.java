package com.cn.thinkx.wecard.api.module.customer.util;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.RedisChannelUtils;
import com.cn.thinkx.common.service.module.channel.domain.ChannelSecurityInf;

public class ChannelInfRedisCacheUtil {

	public static ChannelSecurityInf getChannelSecurityInfByCode(String channelCode){
		String channelInfJson=RedisChannelUtils.getInstance().getChannelInfJson(channelCode);
		ChannelSecurityInf channelInf = JSONArray.parseObject(channelInfJson, ChannelSecurityInf.class);
		if(!"0".equals(channelInf.getDataStat())){
			return null;
		}
		return channelInf;
	}
}
