package com.cn.thinkx.oms.module.margin.service;

import java.util.List;

import com.cn.thinkx.oms.module.margin.model.MerchantMarginList;
import com.github.pagehelper.PageInfo;


public interface MerchantMarginService {


	public MerchantMarginList getMerchantMarginListById(String id);

	/**
	 * 修改商户保证金
	 */
	public int updateMerchantMarginList(MerchantMarginList merchantMarginList);
	
	/**
	 * 追加保证金
	 * @param merchantMarginList
	 * @return
	 */
	public int addMerchantMarginList(MerchantMarginList merchantMarginList);

	/**
	 * 查找商户保证金
	 */
	public List<MerchantMarginList> getMerchantMarginList(MerchantMarginList entity);
	/**
	 * 保证金查询列表
	 */
	public PageInfo<MerchantMarginList> getMerchantMarginListPage(int startNum, int pageSize,
			MerchantMarginList entity);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public int deleteMerchantMarginListById(String id);

	/**
	 * 获取审批任务列表
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	public PageInfo<MerchantMarginList> getMerchantMarginApproveList(int startNum, int pageSize,
			MerchantMarginList entity);
	
	/**
	 * 商户保证金押款确认
	 * @param MerchantMarginList
	 * @return
	 */
	public int saveMchntCashConfirm(MerchantMarginList entity) throws Exception;
	
}
