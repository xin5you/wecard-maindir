package com.cn.thinkx.oms.module.merchant.service;



import java.util.List;
import java.util.Map;

import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ScanBoxDeviceInf;
import com.github.pagehelper.PageInfo;

public interface ScanBoxDeviceInfService {
	
	public int insertScanBoxDeviceInf(ScanBoxDeviceInf scanBD);
	
	public MerchantInf getMerchantInfByMchntCode(String mchntCode);
	
	public List<ScanBoxDeviceInf> getScanBoxDeviceInfList(ScanBoxDeviceInf scanBoxDeviceInf);
	
	public PageInfo<ScanBoxDeviceInf> getScanBoxDeviceInfListPage(int startNum, int pageSize,ScanBoxDeviceInf scanBoxDeviceInf);
	
	public int deleteScanBoxDeviceInfByDeviceId(String deviceId);
	
	public int editScanBoxDeviceInf(ScanBoxDeviceInf scanBoxDeviceInf);
	
	public ScanBoxDeviceInf getScanBoxDeviceInfByDeviceId(String deviceId);
	
	public List<ScanBoxDeviceInf> getScanBoxDeviceInfByDeviceTypeAndDeviceNo(ScanBoxDeviceInf scanBoxDeviceInf);
}
