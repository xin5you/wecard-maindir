package com.cn.thinkx.wecard.centre.module.biz.mapper;

import java.util.List;

import com.cn.thinkx.common.redis.vo.BoxDeviceInfoVO;


public interface ScanBoxDeviceInfMapper {
	/*
	 * 查询扫码盒子设备信息
	 */
	public List<BoxDeviceInfoVO> getScanBoxDeviceInfList();
}
