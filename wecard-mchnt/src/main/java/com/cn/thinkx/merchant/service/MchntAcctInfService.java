package com.cn.thinkx.merchant.service;

import java.util.Map;

import com.cn.thinkx.pub.domain.TxnResp;

/**
 * 商户账户表
 * @author zqy
 *
 */
public interface MchntAcctInfService {

	/**
	 * 查找商户沉淀资金
	 * @param acctType 账户类型         100:沉淀资金账户  ,200:微信充值账户,300:支付宝充值账户 ,400:嘉福平台 ,500:网银账户
	 * @param acctStat 账户状态 	00:有效,10:注销,20:冻结
	 * @param insId 机构Id
	 * @param manchtId 主商户Id
	 * @return
	 */
	public long getSumAccBalByMchnt(String acctType,String acctSata,String insId,String manchtId);
	
	
	/**
	 * 卡余额 查询
	 * @param settleDate 清算日期 (yyyyMMdd) 不填写默认为上一个日期
	 * @param insId		机构
	 * @param manchtId 	商户ID
	 * @return
	 */
	public long getSumCardBalByMchnt(String settleDate,String insId,String manchtId);
}


