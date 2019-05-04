package com.cn.thinkx.oms.module.margin.service;

import java.util.List;

import com.cn.thinkx.oms.module.margin.model.MerchantCashManage;
import com.github.pagehelper.PageInfo;


public interface MerchantCashManageService {


	public MerchantCashManage getMerchantCashManageById(String marginId);
	
	public int insertMerchantCashManage(MerchantCashManage entity);
	
	public int updateMerchantCashManage(MerchantCashManage entity);

	public List<MerchantCashManage> getMerchantCashManageList(MerchantCashManage entity) ;

	public PageInfo<MerchantCashManage> getMerchantCashManagePage(int startNum, int pageSize,MerchantCashManage entity);

	/**
	 * 查询商户保证金记录
	 * @param marginId
	 * @return
	 */
	public MerchantCashManage getMerchantCashManageByMchntId(String mchntId);
	
}
