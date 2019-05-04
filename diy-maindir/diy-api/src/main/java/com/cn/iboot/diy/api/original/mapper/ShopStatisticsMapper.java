package com.cn.iboot.diy.api.original.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.original.domain.ShopStatistics;
import com.cn.iboot.diy.api.original.domain.ShopStatisticsUpload;
@Mapper
public interface ShopStatisticsMapper extends BaseDao<ShopStatistics> {

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
