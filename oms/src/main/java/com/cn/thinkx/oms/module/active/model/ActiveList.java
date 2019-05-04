package com.cn.thinkx.oms.module.active.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 活动明细基础类
 * 
 * @author pucker
 *
 */
public class ActiveList extends BaseDomain {
	/**
	 * 活动明细ID
	 */
	private String activeListId;
	/**
	 * 活动ID
	 */
	private String activeId;
	/**
	 * 商品ID
	 */
	private String commodityId;
	/**
	 * 活动售价
	 */
	private String sellingPrice;
	/**
	 * 库存
	 */
	private int stock;
	/**
	 * 数据状态
	 */
	private String dataStat;

	public String getActiveListId() {
		return activeListId;
	}

	public void setActiveListId(String activeListId) {
		this.activeListId = activeListId;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

}
