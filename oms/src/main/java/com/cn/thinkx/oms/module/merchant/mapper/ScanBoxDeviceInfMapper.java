package com.cn.thinkx.oms.module.merchant.mapper;


import java.util.List;
import java.util.Map;

import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ScanBoxDeviceInf;

public interface ScanBoxDeviceInfMapper {

	/*
	 * 添加扫码盒子设备信息
	 */
	public int insertScanBoxDeviceInf(ScanBoxDeviceInf scanBoxDeviceInf);
	
	/*
	 * 通过商户号查找商户信息 获得机构Id
	 */
	public MerchantInf getMerchantInfByMchntCode(String mchntCode);
	
	/*
	 * 查询扫码盒子设备信息
	 */
	public List<ScanBoxDeviceInf> getScanBoxDeviceInfList(ScanBoxDeviceInf scanBoxDeviceInf);
	
	/*
	 * 删除扫码盒子设备信息（通过deviceId）
	 */
	public int deleteScanBoxDeviceInfByDeviceId(String deviceId);
	
	/*
	 *通过 deviceId查询对应扫码盒子设备信息的详细信息
	 */
	public ScanBoxDeviceInf getScanBoxDeviceInfByDeviceId(String deviceId);
	
	/*
	 * 编辑扫码盒子设备信息
	 * 
	 */
	public int editScanBoxDeviceInf(ScanBoxDeviceInf scanBoxDeviceInf);
	
	/**
	 * 通过设备类型和设备号查询验证
	 * 
	 */
	public List<ScanBoxDeviceInf> getScanBoxDeviceInfByDeviceTypeAndDeviceNo(ScanBoxDeviceInf scanBoxDeviceInf);
	
}
