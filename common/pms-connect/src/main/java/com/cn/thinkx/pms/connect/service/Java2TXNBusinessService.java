package com.cn.thinkx.pms.connect.service;

import java.util.Map;

import com.cn.thinkx.pms.connect.entity.TxnPackageDTO;

public interface Java2TXNBusinessService {

	/**
	 * 心跳交易
	 * @param txnPackageDTO
	 * @return
	 */
	public Boolean heartBeatTransation(TxnPackageDTO txnPackageDTO);

	/**
	 * 交易查询接口（针对单卡消费）
	 * 
	 * @param txnPackageDTO
	 * @return
	 * @throws Exception
	 */
	public TxnPackageDTO queryTransation(TxnPackageDTO txnPackageDTO, Map<String, String> params) throws Exception;

	/**
	 * 支付网关 消费
	 * 
	 * @param txnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO consume(TxnPackageDTO txnPackageDTO, Map<String, String> params)
			throws Exception;

	/**
	 * 支付网关 冲正
	 * 
	 * @param txnPackageDTO
	 * @return
	 * @throws BizServiceException
	 */
	public TxnPackageDTO reversal(TxnPackageDTO txnPackageDTO, Map<String, String> params) throws Exception;
	/**
	 * 支付网关 充值
	 * @param txnPackageDTO
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public TxnPackageDTO reCharge(TxnPackageDTO txnPackageDTO, Map<String, String> params) throws Exception;
}
