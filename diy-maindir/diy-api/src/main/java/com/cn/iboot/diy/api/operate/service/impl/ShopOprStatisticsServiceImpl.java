package com.cn.iboot.diy.api.operate.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.service.impl.BaseServiceImpl;
import com.cn.iboot.diy.api.operate.domain.ShopOprStatistics;
import com.cn.iboot.diy.api.operate.mapper.ShopOprStatisticsMapper;
import com.cn.iboot.diy.api.operate.service.ShopOprStatisticsService;

@Service("shopOprStatisticsService")
public class ShopOprStatisticsServiceImpl extends BaseServiceImpl<ShopOprStatistics> implements ShopOprStatisticsService {
	
	@Autowired
	ShopOprStatisticsMapper shopOprStaticsMapper;
	


	@Override
	public String filedShopProStatistics(Map<String, Object> map) {
		shopOprStaticsMapper.filedShopProStatistics(map);
		String result = (String) map.get("result");
		System.out.println("========="+result);
		
		return result;
	}

}
