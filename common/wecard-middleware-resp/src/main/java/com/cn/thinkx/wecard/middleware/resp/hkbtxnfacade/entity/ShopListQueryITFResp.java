package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity;

import java.util.List;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.ShopListQueryITFVo;

public class ShopListQueryITFResp extends BaseResp {

	/**
	 * pageSize 总页数
	 */
	private String pageSize;
	
	/**
	 * currPageSize 当前页
	 */
	private String currPageSize;
	
	/**
	 *shopList 门店列表 
	 */
	private List<ShopListQueryITFVo> shopList;
	

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrPageSize() {
		return currPageSize;
	}

	public void setCurrPageSize(String currPageSize) {
		this.currPageSize = currPageSize;
	}

	public List<ShopListQueryITFVo> getShopList() {
		return shopList;
	}

	public void setShopList(List<ShopListQueryITFVo> shopList) {
		this.shopList = shopList;
	}
}
