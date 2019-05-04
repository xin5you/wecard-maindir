package com.cn.iboot.diy.api.original.service;

import java.util.List;

import com.cn.iboot.diy.api.original.domain.PositDetail;
import com.cn.iboot.diy.api.original.domain.PositDetailUpload;
import com.cn.iboot.diy.api.original.domain.PositStatistics;
import com.cn.iboot.diy.api.original.domain.PositStatisticsUpload;
import com.github.pagehelper.PageInfo;

public interface PositStatisticsService {

	/**
	 * 查询档口数据列表分页
	 * @param startNum
	 * @param pageSize
	 * @param ps
	 * @return
	 */
	PageInfo<PositStatistics> getPositStatisticsPage(int startNum, int pageSize,PositStatistics ps);
	
	/**
	 * 查询档口的明细列表分页
	 * @param startNum
	 * @param pageSize
	 * @param pd
	 * @return
	 */
	PageInfo<PositStatistics> getPositDetailPage(int startNum, int pageSize,PositDetail pd);
	
	/**
	 * 查询档口的交易流水列表
	 * @param pd
	 * @return
	 */
	List<PositStatistics> getPositDetailList(PositDetail pd);
	
	/**
	 * 查询档口的交易流水数据导出列表
	 * @param ps
	 * @return
	 */
	List<PositStatisticsUpload> getPositStatisticsUploadList(PositStatistics ps);
	
	/**
	 * 查询档口数据导出列表
	 * @param pd
	 * @return
	 */
	List<PositDetailUpload> getPositDetailUploadList(PositDetail pd);
	
	/**
	 * 查询档口数据列表
	 * @param ps
	 * @return
	 */
	List<PositStatistics> getPositStatisticsList(PositStatistics ps);
}
