package com.cn.thinkx.facade.service;

import com.cn.thinkx.facade.bean.IntegrationPayRequest;

/**
 * 交易核心接口类
 * 所有方法均返回json格式字符串
 * @author xiaomei
 *
 */
public interface IntegrationPayFacade {
	
	String payMentTransactionITF(IntegrationPayRequest req) throws Exception;
	
	String refundTransactionITF(IntegrationPayRequest req) throws Exception;
	
	String queryTransactionITF(IntegrationPayRequest req) throws Exception;

}
