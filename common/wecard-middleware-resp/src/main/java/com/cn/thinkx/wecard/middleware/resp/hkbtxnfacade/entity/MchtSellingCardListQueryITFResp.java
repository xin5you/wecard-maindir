package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity;

import java.util.List;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.MchtSellingCardListQueryITFVo;

public class MchtSellingCardListQueryITFResp extends BaseResp{
	
	private String productImage;
	
	private 	List<MchtSellingCardListQueryITFVo> cardList;  //商户在售卡列表

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public List<MchtSellingCardListQueryITFVo> getCardList() {
		return cardList;
	}

	public void setCardList(List<MchtSellingCardListQueryITFVo> cardList) {
		this.cardList = cardList;
	}
	
}
