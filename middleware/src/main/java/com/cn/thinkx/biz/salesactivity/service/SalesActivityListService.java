package com.cn.thinkx.biz.salesactivity.service;

import java.util.List;

import com.cn.thinkx.biz.salesactivity.model.SalesActivityDetail;
import com.cn.thinkx.biz.salesactivity.model.SalesActivityList;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.facade.bean.RechargeTransRequest;

public interface SalesActivityListService {

	/**
	 * 查找用户是否参与活动
	 * 
	 * @param commodityId 商品ID
	 * @return
	 */
	SalesActivityDetail getSalesActivitByCommodityId(String  commodityId);
	

	/**
	 * 查找用户是购买商品剩余购买数量
	 * @param commodityId 商品ID
	 * @param joinBody(tb_user_inf.USER_ID) 参与用户Id 
	 * @return
	 */
	int getCommodityStocksByCommodityId(String commodityId,String joinBody);

	
	/**
	 * 保存用户参与活动明细
	 * @param salesActivityList
	 * @return
	 */
	int saveSalesActivityList(SalesActivityList salesActivityList);
	
	/**
	 * 修改用户参与活动明细
	 * @param salesActivityList
	 * @return
	 */
	int updateSalesActivityList(SalesActivityList salesActivityList);
	
	/**
	 * 查找用户是购买活动商品剩余购买数量
	 * 
	 * @param activityBody 营销活动主体
	 * @Param joinBody 参与主体一般指当前用户ID
	 * @param commodityId 商品主键
	 * @return
	 */
	List<SalesActivityDetail> getSalesActivityListCardStocks(String activityBody,String joinBody,String commodityId);
	
	
	/**
	 * 查找用户已经参与该活动，购买商品数量
	 * @param activityId 营销活动主键
	 * @Param userId 参与主体一般指当前用户ID
	 * @param commodityId 商品主键
	 * @return
	 */
	int getSalesActivityCommodityNumByUserId(String activityId,String userId);

	/**
	 * 充值 优惠活动
	 * @param RechargeTransRequest 重启请求参数
	 * @param userId 参与用户
	 * @param log 接口层日志
	 */
	void doRechargeSpecialActivity(RechargeTransRequest req,String userId,IntfaceTransLog log);
}
