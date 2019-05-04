package com.cn.thinkx.oms.module.merchant.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.MerchantContract;

@Repository("merchantContractMapper")
public interface MerchantContractMapper {

	/**
	 * 查找商户合同信息
	 * @param merchantContractId
	 * @return
	 */
	public MerchantContract getMerchantContractById(String merchantContractId);
	/**
	 * 根据商户id查找商户合同信息
	 * @param mchntId
	 * @return
	 */
	public MerchantContract getMerchantContractByMerchantId(String mchntId);
	/**
	 * 插入商户合同信息
	 * @param merchantContract
	 * @return
	 */
	public int insertMerchantContract(MerchantContract merchantContract);
	/**
	 * 更新商户合同信息
	 * @param merchantContract
	 * @return
	 */
	public int updateMerchantContract(MerchantContract merchantContract);
	/**
	 * 删除商户合同信息
	 * @param MerchantContractId
	 * @return
	 */
	public int deleteMerchantContract(String merchantContractId);

}