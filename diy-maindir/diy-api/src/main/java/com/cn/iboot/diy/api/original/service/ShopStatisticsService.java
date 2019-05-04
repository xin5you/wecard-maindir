package com.cn.iboot.diy.api.original.service;

import java.util.List;

import com.cn.iboot.diy.api.base.service.BaseService;
import com.cn.iboot.diy.api.original.domain.ShopStatistics;
import com.cn.iboot.diy.api.original.domain.ShopStatisticsUpload;
import com.github.pagehelper.PageInfo;

public interface ShopStatisticsService extends BaseService<ShopStatistics> {

	/**
	 * 查询门店信息列表分页
	 * @param startNum
	 * @param pageSize
	 * @param ss
	 * @return
	 */
	PageInfo<ShopStatistics> getShopStatisticsPage(int startNum, int pageSize,ShopStatistics ss);
	
	
	/**
	 * 查询门店数据列表
	 * @param ss
	 * @return
	 */
	List<ShopStatistics> getShopStatisticsList(ShopStatistics ss);
	
	/**
	 * 导出门店数据
	 * @param ss
	 * @return
	 */
	List<ShopStatisticsUpload> getShopStatisticsUploadList(ShopStatistics ss);
}
