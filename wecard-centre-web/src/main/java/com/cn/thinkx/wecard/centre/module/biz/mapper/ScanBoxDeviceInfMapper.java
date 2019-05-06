package com.cn.thinkx.wecard.centre.module.biz.mapper;

import com.cn.thinkx.pms.base.redis.vo.BoxDeviceInfoVO;

import java.util.List;


public interface ScanBoxDeviceInfMapper {
    /*
     * 查询扫码盒子设备信息
     */
    public List<BoxDeviceInfoVO> getScanBoxDeviceInfList();
}
