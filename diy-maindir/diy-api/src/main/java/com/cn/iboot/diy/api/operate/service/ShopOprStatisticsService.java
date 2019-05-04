package com.cn.iboot.diy.api.operate.service;

import java.util.Map;

import com.cn.iboot.diy.api.base.service.BaseService;
import com.cn.iboot.diy.api.operate.domain.ShopOprStatistics;

public interface ShopOprStatisticsService extends BaseService<ShopOprStatistics>{
	

	String filedShopProStatistics(Map<String, Object> map);

}
