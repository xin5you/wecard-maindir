package com.cn.thinkx.wxclient.service;


import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.wxclient.domain.MerchantShareInf;

public interface MerchantShareInfService {

	/**
	 * insert 商户分享操作项
	 * @return
	 */
	public int insertMerchantShareInf(MerchantShareInf entity);
	
	
	/**
	 * insert 商户修改操作项
	 * @return
	 */
	public int updateMerchantShareInf(MerchantShareInf entity);
	
	/**
	 * 修改当前对象
	 * @param shareId
	 * @return
	 */
	public int  updateMerchantShareInfDataStat(String shareId);
	
	/**
	 * 获取当前对象
	 * @param shareId
	 * @return
	 */
	public MerchantShareInf getMerchantShareInfById(String shareId);
	
	/**
	 * 分享操作
	 * @param entity
	 * @param flag  9：新增  ,0:分享成功，1：分享失败 ,8:失败
	 * @return
	 */
	public ResultHtml editMerchantShareInf(MerchantShareInf entity,String flag) throws Exception;
}
