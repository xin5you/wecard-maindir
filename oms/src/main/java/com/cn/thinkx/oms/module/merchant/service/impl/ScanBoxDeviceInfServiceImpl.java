package com.cn.thinkx.oms.module.merchant.service.impl;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.merchant.mapper.ScanBoxDeviceInfMapper;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ScanBoxDeviceInf;
import com.cn.thinkx.oms.module.merchant.service.ScanBoxDeviceInfService;
import com.cn.thinkx.oms.module.statement.model.MerchantDetail;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("scanBoxDeviceInfService")
public class ScanBoxDeviceInfServiceImpl implements ScanBoxDeviceInfService{

	@Autowired
	@Qualifier("scanBoxDeviceInfMapper")
	private ScanBoxDeviceInfMapper scanBoxDeviceInfMapper;
	
	
	@Override
	public int insertScanBoxDeviceInf(ScanBoxDeviceInf scanBoxDeviceInf) {
		return scanBoxDeviceInfMapper.insertScanBoxDeviceInf(scanBoxDeviceInf);
	}


	@Override
	public MerchantInf getMerchantInfByMchntCode(String mchntCode) {
		return scanBoxDeviceInfMapper.getMerchantInfByMchntCode(mchntCode);
	}


	@Override
	public List<ScanBoxDeviceInf> getScanBoxDeviceInfList(ScanBoxDeviceInf scanBoxDeviceInf) {
		return scanBoxDeviceInfMapper.getScanBoxDeviceInfList(scanBoxDeviceInf);
	}


	@Override
	public PageInfo<ScanBoxDeviceInf> getScanBoxDeviceInfListPage(int startNum, int pageSize,ScanBoxDeviceInf scanBoxDeviceInf) {
		PageHelper.startPage(startNum, pageSize);
		List<ScanBoxDeviceInf> list = getScanBoxDeviceInfList(scanBoxDeviceInf);
		PageInfo<ScanBoxDeviceInf> page = new PageInfo<ScanBoxDeviceInf>(list);
		return page;
	}


	@Override
	public int deleteScanBoxDeviceInfByDeviceId(String deviceId) {
		return scanBoxDeviceInfMapper.deleteScanBoxDeviceInfByDeviceId(deviceId);
	}


	@Override
	public int editScanBoxDeviceInf(ScanBoxDeviceInf scanBoxDeviceInf) {
		return scanBoxDeviceInfMapper.editScanBoxDeviceInf(scanBoxDeviceInf);
	}


	@Override
	public ScanBoxDeviceInf getScanBoxDeviceInfByDeviceId(String deviceId) {
		ScanBoxDeviceInf scanBoxDeviceInf = scanBoxDeviceInfMapper.getScanBoxDeviceInfByDeviceId(deviceId);
		scanBoxDeviceInf.setFixedPayAmt(NumberUtils.RMBCentToYuan(scanBoxDeviceInf.getFixedPayAmt()));
		return scanBoxDeviceInf;
	}


	@Override
	public List<ScanBoxDeviceInf> getScanBoxDeviceInfByDeviceTypeAndDeviceNo(ScanBoxDeviceInf scanBoxDeviceInf) {
		return scanBoxDeviceInfMapper.getScanBoxDeviceInfByDeviceTypeAndDeviceNo(scanBoxDeviceInf);
	}

}
