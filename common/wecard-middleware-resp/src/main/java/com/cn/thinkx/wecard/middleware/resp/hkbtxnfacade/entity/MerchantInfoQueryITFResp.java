package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.MerchantInfoQueryITFVo;

/**
 * 商户信息查询
 * @author zqy
 *
 */
public class MerchantInfoQueryITFResp  extends BaseResp {

	private MerchantInfoQueryITFVo merchantInfo;

	public MerchantInfoQueryITFVo getMerchantInfo() {
		return merchantInfo;
	}

	public void setMerchantInfo(MerchantInfoQueryITFVo merchantInfo) {
		this.merchantInfo = merchantInfo;
	}
}
