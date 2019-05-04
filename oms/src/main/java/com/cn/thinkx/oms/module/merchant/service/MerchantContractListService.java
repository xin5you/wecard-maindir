package com.cn.thinkx.oms.module.merchant.service;

import com.cn.thinkx.oms.module.merchant.model.MerchantContract;
import com.cn.thinkx.oms.module.merchant.model.MerchantContractList;
import com.cn.thinkx.oms.module.merchant.model.Product;

public interface MerchantContractListService {
	
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
	public MerchantContractList getMerchantContractListByMerchantId(String merchantContractId);
	/**
	 * 插入商户合同明细信息
	 * @param merchantContractList
	 * @return
	 */
	public int insertMerchantContractList(MerchantContractList merchantContractList);
	/**
	 * 插入商户默认合同明细信息
	 * @param merchantInf
	 * @return
	 */
	public int insertDefaultMerchantContractList(MerchantContract merchantContract,Product product);
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
