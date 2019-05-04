package com.cn.thinkx.oms.module.quota.service;

import java.util.List;

import com.cn.thinkx.oms.module.customer.model.AccountInf;
import com.cn.thinkx.oms.module.quota.model.QuotaInf;

public interface QuotaInfService {

	List<QuotaInf> getQuotaInfList(QuotaInf quotaInf);
	
	QuotaInf getQuotaInfByAccountNo(String accountNo);
	
	void updateQuotaInf(AccountInf account);
	
	
	
}
