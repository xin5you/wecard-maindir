package com.cn.thinkx.wecard.api.module.telephone.service;

import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleReqVO;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespDomain;

public interface ApiRechargeMobileService {
	
	@SuppressWarnings("rawtypes")
	TeleRespDomain payment(TeleReqVO reqVo) throws Exception;
}
