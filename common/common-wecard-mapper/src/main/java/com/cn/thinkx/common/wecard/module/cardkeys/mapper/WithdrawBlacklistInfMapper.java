package com.cn.thinkx.common.wecard.module.cardkeys.mapper;

import com.cn.thinkx.common.wecard.domain.cardkeys.WithdrawBlacklistInf;

public interface WithdrawBlacklistInfMapper {
	
	WithdrawBlacklistInf getWithdrawBlacklistInfByUserPhone(String userPhone);
	
}
