package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseReq;

public class MchntInfQueryRequest extends BaseReq {

	private static final long serialVersionUID = -7536211806776038187L;

	/**
	 * 商户号
	 */
	private String innerMerchantNo;

	public String getInnerMerchantNo() {
		return innerMerchantNo;
	}

	public void setInnerMerchantNo(String innerMerchantNo) {
		this.innerMerchantNo = innerMerchantNo;
	}

}
