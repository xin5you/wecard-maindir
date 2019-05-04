package com.cn.thinkx.wecard.customer.module.welfaremart.service;

import com.cn.thinkx.common.wecard.domain.cardkeys.WithdrawBlacklistInf;

public interface WithdrawBlacklistInfService {
	
	WithdrawBlacklistInf getWithdrawBlacklistInfByUserPhone(String userPhone);
}
