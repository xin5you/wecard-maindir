package com.cn.thinkx.wecard.api.module.pub.service;

import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.wecard.api.module.pub.model.DetailBizInfo;
import com.cn.thinkx.wecard.api.module.trans.model.WxTransLog;

public interface PublicService {

	/**
	 * 当会员卡余额不足时调用通卡进行消费
	 * 
	 * @param log
	 * @param cs
	 * @param detail
	 * @return
	 */
	public String accMchntTrans(WxTransLog log, CtrlSystem cs, DetailBizInfo detail);

}
