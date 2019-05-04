package com.cn.iboot.diy.api.operate.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.operate.domain.ShopOprStatistics;
@Mapper
public interface ShopOprStatisticsMapper extends BaseDao<ShopOprStatistics> {
	
	/**
	 * 归档
	 * @param map
	 * @return
	 */
	String filedShopProStatistics(Map<String, Object> map);
}
