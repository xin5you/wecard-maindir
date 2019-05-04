package com.cn.iboot.diy.api.operate.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.operate.domain.PositOprStatistics;
import com.cn.iboot.diy.api.operate.domain.PositOprUpdate;
@Mapper
public interface PositOprStatisticsMapper extends BaseDao<PositOprStatistics> {

	/**
	 * 根据门店code获取门店名
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
	 * 获取运营门店数据
	 * @param positOprStatistics
	 * @return
	 */
	List<PositOprStatistics> getShopStatisticsSet(PositOprStatistics positOprStatistics);
	/*	
	 *//**
	 * 获取月编辑门店数据
	 * @param positOprStatistics
	 * @return
	 */
	List<PositOprStatistics> getPositStatisticsSet(PositOprStatistics positOprStatistics);

	/**
	 * 获取日编辑档口数据
	 * @param positOprStatistics
	 * @return
	 */
	List<PositOprStatistics> getPositStatisticsDaySet(PositOprStatistics positOprStatistics);


	/**
	 * 日编辑保存
	 * @param map
	 * @return
	 */
	String updatePositProStatistics(Map<String, Object> map);

	/**
	 * 导出运营数据
	 * @param ps
	 * @return
	 */
	List<PositOprUpdate> getPositOprUploadList(PositOprStatistics ps);
	/**
	 * 日编辑保存
	 * @param map
	 * @return
	 */
	String updateShopProStatistics(Map<String, Object> map);

	/**
	 * 获取门店消费总金额
	 * @param sid
	 * @return
	 */
	String getShopTotalPayById(String sid);

	/**
	 * 运营数据查询总金额
	 * @param pos
	 */
	public String getPositOprTotalPay(PositOprStatistics pos);
	/**
	 * 实时查询统计
	 * @param positOprStatistics
	 * @return
	 */
    List<PositOprStatistics> getPositCurStatistics(PositOprStatistics positOprStatistics);

	List<String> getSubShopCode(String shopCode);

	String getCurLogNum();

	List<PositOprStatistics> getPositCurStatistics2(Map<String, Object> params);



}
