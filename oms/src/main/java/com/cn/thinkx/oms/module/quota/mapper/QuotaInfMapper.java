package com.cn.thinkx.oms.module.quota.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.quota.model.QuotaInf;

public interface QuotaInfMapper {

	List<QuotaInf> getQuotaInfList(QuotaInf quotaInf);
	
	QuotaInf getQuotaInfByAccountNo(String accountNo);

}
