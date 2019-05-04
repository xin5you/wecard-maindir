package com.cn.thinkx.wecard.centre.module.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.thinkx.common.redis.service.RedisCacheService;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.redis.vo.BoxDeviceInfoVO;
import com.cn.thinkx.wecard.centre.module.biz.service.ScanBoxDeviceInfService;

public class ScanBoxDeviceInfJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("scanBoxDeviceInfService")
	private ScanBoxDeviceInfService scanBoxDeviceInfService;

	@Autowired
	@Qualifier("redisCacheService")
	private RedisCacheService<String, BoxDeviceInfoVO> redisCacheService;

	/*
	 * 定时向redis插入设备信息
	 */
	public void doRefreshScanBoxDeviceInf() {
		List<BoxDeviceInfoVO> boxDeviceInfoList = null;
		BoxDeviceInfoVO boxDeviceInf = null;
		String deviceNo = "";
		try {
			boxDeviceInfoList = scanBoxDeviceInfService.getScanBoxDeviceInfList();
			if (boxDeviceInfoList != null && boxDeviceInfoList.size() > 0) {
				for (int i = 0; i < boxDeviceInfoList.size(); i++) {
					boxDeviceInf = boxDeviceInfoList.get(i);
					deviceNo = boxDeviceInf.getDeviceNo();
					if (boxDeviceInf != null && "0".equals(boxDeviceInf.getDataStat())) {
						redisCacheService.hset(RedisConstants.REDIS_HASH_TABLE_TB_SCAN_BOX_DEVICE_INF, deviceNo, boxDeviceInf);
					} else {
						redisCacheService.hdel(RedisConstants.REDIS_HASH_TABLE_TB_SCAN_BOX_DEVICE_INF, deviceNo);
					}
				}
			}
		} catch (Exception e) {
			logger.error("定时任务doRefreshScanBoxDeviceInf执行异常", e);
		}
	}

}
