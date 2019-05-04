package com.cn.thinkx.wecard.centre.module.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.thinkx.common.redis.service.RedisCacheService;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.redis.vo.ShopInfVO;
import com.cn.thinkx.wecard.centre.module.biz.service.ShopInfService;

public class ShopInfJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("redisCacheService")
	private RedisCacheService<String, ShopInfVO> redisCacheChannelInfService;

	/*
	 * 查询出一个小时以内更改的门店信息，再保存到redis中
	 */
	public void doRefreshShopInf() {
//		logger.info("定时任务doRefreshShopInf执行开始，时间[{}]", DateUtil.getCurrentDateTimeStr());
		List<ShopInfVO> shopInfList = null;
		ShopInfVO shopInf = null;
		String shopCode = "";
		try {
			shopInfList = shopInfService.getShopInfList();
			if (shopInfList != null && shopInfList.size() > 0) {
				for (int i = 0; i < shopInfList.size(); i++) {
					shopInf = shopInfList.get(i);
					shopCode = shopInf.getShopCode();
					if (shopInf != null) {
						redisCacheChannelInfService.hset(RedisConstants.REDIS_HASH_TABLE_TB_SHOP_INF, shopCode, shopInf);
					}
				}
			}
//			String str = redisCacheChannelInfService.hget(RedisConstants.REDIS_HASH_TABLE_TB_SHOP_INF, shopCode);
		} catch (Exception e) {
			logger.error("定时任务doRefreshShopInf执行异常", e);
		}
	}
}
