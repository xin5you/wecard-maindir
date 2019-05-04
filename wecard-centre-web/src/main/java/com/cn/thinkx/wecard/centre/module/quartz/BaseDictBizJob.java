package com.cn.thinkx.wecard.centre.module.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.thinkx.common.redis.service.RedisCacheService;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.service.module.dict.domain.BaseDict;
import com.cn.thinkx.common.service.module.dict.service.BaseDictService;
import com.cn.thinkx.pms.base.utils.DateUtil;

public class BaseDictBizJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("baseDictService")
	private BaseDictService baseDictService;

	@Autowired
	@Qualifier("redisCacheService")
	private RedisCacheService<String, BaseDict> redisCacheChannelInfService;

	public void doRefreshBaseDictKList() throws Exception {
//		logger.info("定时任务doRefreshBaseDictKList执行开始，时间[{}]", DateUtil.getCurrentDateTimeStr());
		List<BaseDict> baseDictList = null;
		BaseDict baseDict = null;
		try {
			baseDictList = baseDictService.getBaseDictByKList();
			if (baseDictList != null && baseDictList.size() > 0) {
				for (int i = 0; i < baseDictList.size(); i++) {
					baseDict = baseDictList.get(i);
					if (baseDict != null) {
						redisCacheChannelInfService.hset(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KLIST,
								baseDict.getDictCode(), baseDict);
					}
				}
			}
		} catch (Exception e) {
			logger.error("## 定时任务doRefreshBaseDictKList执行异常", e);
		}
	}

	public void doRefreshBaseDictKV() throws Exception {
//		logger.info("定时任务doRefreshBaseDictKV执行开始，时间[{}]", DateUtil.getCurrentDateTimeStr());
		List<BaseDict> baseDictList = null;
		BaseDict baseDict = null;
		try {
			baseDictList = baseDictService.getAllBaseDictByKey();
			if (baseDictList != null && baseDictList.size() > 0) {
				for (int i = 0; i < baseDictList.size(); i++) {
					baseDict = baseDictList.get(i);
					if (baseDict != null) {
						redisCacheChannelInfService.hsetString(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
								baseDict.getDictCode(), baseDict.getDictValue());
					}
				}
			}
		} catch (Exception e) {
			logger.error("## 定时任务doRefreshBaseDictKV执行异常", e);
		}
	}
}
