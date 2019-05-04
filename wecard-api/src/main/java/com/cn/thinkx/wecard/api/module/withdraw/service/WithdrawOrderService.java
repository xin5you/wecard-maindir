package com.cn.thinkx.wecard.api.module.withdraw.service;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrder;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellReq;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellResp;

public interface WithdrawOrderService {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	String getPrimaryKey();
	
	int getCountByBatchNo(String batchNo);
	
	WithdrawOrder getWithdrawOrderById(@Param("batchNo")String batchNo);
	
	int insertWithdrawOrder(WithdrawOrder withdrawOrder);
	
	int updateWithdrawOrder(WithdrawOrder withdrawOrder);
	
	WithdrawOrder getWithdrawOrderByPaidId(@Param("paidId")String paidId);
	
	/**
	 * 苏宁易付宝批量代付
	 * 
	 * @param batchNo 批次号
	 * @param jsonData JSON参数
	 * @return
	 */
	String YFBBatchWithdraw(String batchNo, String jsonData) throws Exception;
	
	/**
	 * 校验签名
	 * 
	 * @param paramData
	 * @return
	 */
	boolean YFBWithdrawCheckSign(String paramData);
	
	/**
	 * 调用代付查询接口
	 * 
	 * @param batchNo
	 * @param payMerchantNo
	 * @return
	 */
	String YFBBatchWithdrawQuery(String batchNo, String payMerchantNo);
	
	/**
	 * 卡券集市转让提交
	 * 
	 * @param req
	 * @return
	 */
	WelfaremartResellResp welfaremartResellCommit(WelfaremartResellReq req)   throws Exception;
	
}
