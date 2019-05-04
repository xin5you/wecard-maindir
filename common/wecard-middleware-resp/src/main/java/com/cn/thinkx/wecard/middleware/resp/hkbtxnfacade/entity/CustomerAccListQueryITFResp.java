package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity;

import java.util.List;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.CustomerAccListQueryITFVo;

/**
 * 客户会员卡
 * @author zqy
 *
 */
public class CustomerAccListQueryITFResp extends BaseResp{
	
	/**
	 * 客户会员卡列表
	 */
	private List<CustomerAccListQueryITFVo> productList;

	public List<CustomerAccListQueryITFVo> getProductList() {
		return productList;
	}

	public void setProductList(List<CustomerAccListQueryITFVo> productList) {
		this.productList = productList;
	}
	
}
