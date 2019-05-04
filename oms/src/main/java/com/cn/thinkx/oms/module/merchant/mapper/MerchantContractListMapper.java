package com.cn.thinkx.oms.module.merchant.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.MerchantContractList;

@Repository("merchantContractListMapper")
public interface MerchantContractListMapper {

	/**
	 * 查找商户合同明细信息
	 * @param merchantContractId
	 * @return
	 */
	public MerchantContractList getMerchantContractListById(String merchantContractListId);
	/**
	 * 根据商户合同Id查找商户合同明细信息
	 * @param merchantContractId
	 * @return
	 */
	public MerchantContractList getMerchantContractListByMerchantContractId(String merchantContractId);
	/**
	 * 插入商户合同明细信息
	 * @param merchantContractList
	 * @return
	 */
	public int insertMerchantContractList(MerchantContractList merchantContractList);
	/**
	 * 更新商户合同明细信息
	 * @param merchantContractList
	 * @return
	 */
	public int updateMerchantContractList(MerchantContractList merchantContractList);
	/**
	 * 删除商户合同明细信息
	 * @param merchantContractListId
	 * @return
	 */
	public int deleteMerchantContractList(String merchantContractListId);
	
}