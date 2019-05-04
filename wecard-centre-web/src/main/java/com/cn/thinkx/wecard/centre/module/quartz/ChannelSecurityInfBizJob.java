package com.cn.thinkx.wecard.centre.module.quartz;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.thinkx.common.redis.service.RedisCacheService;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.service.module.channel.domain.ChannelSecurityInf;
import com.cn.thinkx.common.service.module.channel.service.ChannelSecurityInfService;

/**
 * 微信 accessToken 定时更新 Job
 *
 */
public class ChannelSecurityInfBizJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("channelSecurityInfService")
	private ChannelSecurityInfService channelSecurityInfService;

	@Autowired
	@Qualifier("redisCacheService")
	private RedisCacheService<String, ChannelSecurityInf> redisCacheChannelInfService;

	/**
	 * 刷新渠道安全接入信息
	 * 
	 * @throws Exception
	 */
	public void doRefreshChannelInf() throws Exception {
//		logger.info("定时任务doRefreshChannelInf执行开始，时间[{}]", DateUtil.getCurrentDateTimeStr());
		List<ChannelSecurityInf> channelList = null;
		ChannelSecurityInf channelInf = null;
		try {
			channelList = channelSecurityInfService.getAllChannelSecurityInf();
			if (channelList != null && channelList.size() > 0) {
				Date currDate = new Date();
				for (int i = 0; i < channelList.size(); i++) {
					channelInf = channelList.get(i);
					if (channelInf != null) {
						if ("0".equals(channelInf.getDataStat())
								&& channelInf.getBeginTime().getTime() <= currDate.getTime()
								&& channelInf.getEndTime().getTime() >= currDate.getTime()) {
							redisCacheChannelInfService.hsetString(
									RedisConstants.REDIS_HASH_TABLE_TB_CHANNEL_SECURITY_INF_KV,
									channelInf.getChannelCode(), channelInf.getChannelKey());
						} else {
							redisCacheChannelInfService.hdel(RedisConstants.REDIS_HASH_TABLE_TB_CHANNEL_SECURITY_INF_KV,
									channelInf.getChannelCode());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("## 定时任务doRefreshChannelInf执行异常", e);
		}
	}

}