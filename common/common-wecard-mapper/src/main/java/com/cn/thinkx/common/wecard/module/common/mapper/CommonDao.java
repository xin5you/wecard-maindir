package com.cn.thinkx.common.wecard.module.common.mapper;

public interface CommonDao {
	
	public String findCommonWebId();
	
	//获取分秒+6位seqID
	public String findMmSsAddSeqId();
	
	public String getUserBankId();
}