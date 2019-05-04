package com.cn.iboot.diy.api.operate.service;

import java.util.List;
import java.util.Map;

import com.cn.iboot.diy.api.base.service.BaseService;
import com.cn.iboot.diy.api.operate.domain.PositOprStatistics;
import com.cn.iboot.diy.api.operate.domain.PositOprUpdate;
import com.github.pagehelper.PageInfo;

public interface PositOprStatisticsService extends BaseService<PositOprStatistics>{
	
	/**
	 * 运营数据查询
	 * @param startNum
	 * @param pageSize
	 * @param positOprStatistics
	 * @return
	 */
	public PageInfo<PositOprStatistics> getPositOprStatisticsPage(int startNum, int pageSize, PositOprStatistics positOprStatistics);
	
	/**
	 * 运营数据查询总金额
	 * @param pos
	 */
	public String getPositOprTotalPay(PositOprStatistics pos);
	
	/**
	 * 根据门店code查找门店名
	 * @param shopCode
	 * @return
	 */
	String getShopInfByShopCode(String shopCode);
	
	/**
	 * 根据商户code获取商户名
	 * @param shopCode
	 * @return
	 */
	String getMchntInfByMchntCode(String mchntCode);
	
	
	/**
	 * 运营档口数据设置
	 * @param startNum
	 * @param pageSize
	 * @param positStatistics
	 * @return
	 */
	PageInfo<PositOprStatistics> getPositStatisticsSetPage(int startNum, int pageSize, PositOprStatistics positOprStatistics);

	/**
	 * 运营门店月数据设置
	 * @param startNum
	 * @param pageSize
	 * @param positStatistics
	 * @return
	 */
	PageInfo<PositOprStatistics> getShopStatisticsMonthSetPage(int startNum, int pageSize, PositOprStatistics positOprStatistics);

	/**
	 * 运营门店日数据设置
	 * @param startNum
	 * @param pageSize
	 * @param positStatistics
	 * @return
	 */
	PageInfo<PositOprStatistics> getShopStatisticsDaySetPage(int startNum, int pageSize, PositOprStatistics positOprStatistics);

	/**
	 * 运营档口日数据设置
	 * @param startNum
	 * @param pageSize
	 * @param positStatistics
	 * @return
	 */
	PageInfo<PositOprStatistics> getPositStatisticsDaySetPage(int startNum, int pageSize, PositOprStatistics positOprStatistics);
	
	/**
	 * 保存运营数据
	 * @param map
	 * @return
	 * @throws Exception 
	 */
	public String updatePositProStatistics(String sid, String updateUser, List<Map<String, Object>> list) throws Exception;

	/**
	 * 导出运营数据
	 * @param ps
	 * @return
	 */
	List<PositOprUpdate> getPositOprUploadList(PositOprStatistics ps);
	
	/**
	 * 实时统计查询
	 * @param startNum
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public PageInfo<PositOprStatistics> getPositCurStatisticsPage(int startNum, int pageSize,  PositOprStatistics pos);

	/**
	 * 实时查询 （存储过程）
	 * @param startNum
	 * @param pageSize
	 * @param params
	 * @return
	 */
	public PageInfo<PositOprStatistics> getPositCurStatisticsPage(int startNum, int pageSize,
			Map<String, Object> params);



	String getCurLogNum();
	
}
