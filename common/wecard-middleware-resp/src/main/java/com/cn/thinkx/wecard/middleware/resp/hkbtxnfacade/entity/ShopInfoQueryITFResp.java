package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.ShopInfoQueryITFVo;

public class ShopInfoQueryITFResp extends BaseResp {

	private ShopInfoQueryITFVo shopInfo;

	public ShopInfoQueryITFVo getShopInfo() {
		return shopInfo;
	}

	public void setShopInfo(ShopInfoQueryITFVo shopInfo) {
		this.shopInfo = shopInfo;
	}
}
