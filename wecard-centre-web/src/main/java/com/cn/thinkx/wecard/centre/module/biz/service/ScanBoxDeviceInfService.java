package com.cn.thinkx.wecard.centre.module.biz.service;

import java.util.List;

import com.cn.thinkx.common.redis.vo.BoxDeviceInfoVO;


public interface ScanBoxDeviceInfService {
	
	List<BoxDeviceInfoVO> getScanBoxDeviceInfList();
}
