package com.cn.thinkx.wecard.customer.module.pub.service;

import com.cn.thinkx.common.wecard.domain.detail.DetailBizInfo;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.wechat.base.wxapi.vo.WxPayCallback;

public interface PublicService {

	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public String getPrimaryKey();

	/**
	 * 获取商户业务详细信息（商户号、商户名称、门店号、门店名称、机构号、openid等）
	 * 
	 * @param detail
	 * @return
	 */
	public DetailBizInfo getDetailBizInfo(DetailBizInfo detail);

	/**
	 * 微信支付查询订单
	 * 
	 * @param wxPrimaryKey
	 * @param back
	 * @return
	 */
	public WxPayCallback queryWxPayReusult(WxTransLog log, WxPayCallback back);

}
