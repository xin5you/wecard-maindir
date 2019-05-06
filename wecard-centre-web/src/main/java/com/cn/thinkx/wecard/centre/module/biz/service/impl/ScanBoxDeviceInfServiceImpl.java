package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import com.cn.thinkx.pms.base.redis.vo.BoxDeviceInfoVO;
import com.cn.thinkx.wecard.centre.module.biz.mapper.ScanBoxDeviceInfMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.ScanBoxDeviceInfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("scanBoxDeviceInfService")
public class ScanBoxDeviceInfServiceImpl implements ScanBoxDeviceInfService {

    @Autowired
    private ScanBoxDeviceInfMapper scanBoxDeviceInfMapper;

    @Override
    public List<BoxDeviceInfoVO> getScanBoxDeviceInfList() {
//		System.out.println("*******ssss*******");
        return scanBoxDeviceInfMapper.getScanBoxDeviceInfList();
    }


}
