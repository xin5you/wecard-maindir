package com.cn.thinkx.biz.salesactivity.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.biz.salesactivity.model.SalesActivityDetail;
import com.cn.thinkx.biz.salesactivity.model.SalesActivityList;


/**
 * 营销活动 
 * @author zqy
 *
 */
@Repository("salesActivityListMapper")
public interface SalesActivityListMapper {
	
	/**
	 * 查找商品是否参与活动
	 * 
	 * @param TB_COMMODITY_INF.commodityId 商品主键
	 * @return
	 */
	SalesActivityDetail getSalesActivitByCommodityId(String  commodityId);
	

	/**
	 * 保存用户参与活动明细
	 * @param salesActivityList
	 * @return
	 */
	int insertSalesActivityList(SalesActivityList salesActivityList);
	
	/**
	 * 修改用户参与活动明细
	 * @param salesActivityList
	 * @return
	 */
	int updateSalesActivityList(SalesActivityList salesActivityList);
	
	
	/**
	 * 查找用户已经参与该活动，购买商品数量
	 * @param activityId 营销活动Id
	 * @Param userId 参与主体一般指当前用户ID
	 * @return
	 */
	int getSalesActivityCommodityNumByUserId(@Param("activityId")String activityId,@Param("userId")String userId);
	
	/**
	 * 查找用户是购买商品剩余购买数量
	 * 
	 * @param activityBody 营销活动主体
	 * @Param joinBody 参与主体一般指当前用户ID
	 * @param commodityId 商品主键
	 * @return
	 */
	List<SalesActivityDetail> getSalesActivityListCardStocks(@Param("activityBody") String activityBody,@Param("joinBody") String joinBody,@Param("commodityId") String commodityId);
}
