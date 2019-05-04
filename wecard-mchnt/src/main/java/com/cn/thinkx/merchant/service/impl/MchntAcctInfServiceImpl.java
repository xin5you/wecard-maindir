package com.cn.thinkx.merchant.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.merchant.mapper.MchntAcctInfDao;
import com.cn.thinkx.merchant.service.MchntAcctInfService;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;

@Service("mchntAcctInfService")
public class MchntAcctInfServiceImpl implements MchntAcctInfService {

	private Logger logger = LoggerFactory.getLogger(MchntAcctInfServiceImpl.class);

	@Autowired
	@Qualifier("mchntAcctInfDao")
	private MchntAcctInfDao mchntAcctInfDao;

	
	/**
	 * 查找商户沉淀资金
	 * @param acctType 账户类型         100:沉淀资金账户  ,200:微信充值账户,300:支付宝充值账户 ,400:嘉福平台 ,500:网银账户
	 * @param acctStat 账户状态 	00:有效,10:注销,20:冻结
	 * @param insId 机构Id
	 * @param manchtId 主商户Id
	 * @return
	 */
	public long getSumAccBalByMchnt(String acctType, String acctSata, String insId, String manchtId) {
		
		return mchntAcctInfDao.getSumAccBalByMchnt(acctType, acctSata, insId, manchtId);
	}
	
	/**
	 * 卡余额 查询
	 * @param settleDate 清算日期 (yyyyMMdd) 不填写默认为上一个日期
	 * @param insId		机构
	 * @param manchtId 	商户ID
	 * @return
	 */
	public long getSumCardBalByMchnt(String settleDate,String insId,String manchtId){
		if(StringUtil.isNullOrEmpty(settleDate)){
			settleDate=DateUtil.getStringFromDate(DateUtil.getBeforeDate(new Date(),1),DateUtil.FORMAT_YYYYMMDD);
		}
		return mchntAcctInfDao.getSumCardBalByMchnt(settleDate, insId, manchtId);
	}
}
