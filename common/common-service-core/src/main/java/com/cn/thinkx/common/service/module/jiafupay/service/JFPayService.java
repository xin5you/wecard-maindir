package com.cn.thinkx.common.service.module.jiafupay.service;

import com.cn.thinkx.common.service.module.jiafupay.vo.JFChnlReq;

/**
 * 对接嘉福收银台接口
 * 
 * @author xiaomei
 * @date 2018/4/17
 */
public interface JFPayService {

	/**
	 * 调用嘉福支付方法
	 * 
	 * @param log
	 * @return
	 */
	String doPayMentTrans(JFChnlReq jfChnlReq);
}
