package com.cn.iboot.diy.api.original.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.original.domain.PositDetail;
import com.cn.iboot.diy.api.original.domain.PositDetailUpload;
import com.cn.iboot.diy.api.original.domain.PositStatistics;
import com.cn.iboot.diy.api.original.domain.PositStatisticsUpload;
@Mapper
public interface PositStatisticsMapper extends BaseDao<PositStatistics> {


	/**
	 * 查询档口明细列表
	 * @param pd
	 * @return
	 */
	List<PositStatistics> getPositDetailList(PositDetail pd);
	
	/**
	 * 查询档口明细数据导出列表
	 * @param ps
	 * @param pd
	 * @return
	 */
	List<PositDetailUpload> getPositDetailUploadList(PositDetail pd);

	/**
	 * 查询档口数据导出列表
	 * @param ps
	 * @return
	 */
	List<PositStatisticsUpload> getPositStatisticsUploadList(PositStatistics ps);
	
	/**
	 * 查询档口数据列表
	 * @param ps
	 * @return
	 */
	List<PositStatistics> getPositStatisticsList(PositStatistics ps);
}
