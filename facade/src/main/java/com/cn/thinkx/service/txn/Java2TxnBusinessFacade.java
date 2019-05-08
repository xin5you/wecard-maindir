package com.cn.thinkx.service.txn;

import com.cn.thinkx.beans.PayBackBean;
import com.cn.thinkx.beans.TxnPackageBean;

/**
 * <h3>交易核心接口类</h3> <br>
 * <ul>
 * <li>所有方法均返回json格式字符串，如操作成功返回：{"code":"00","info":"交易成功"}</li>
 * <li>json串的code为交易返回码，如交易成功为："00"</li>
 * <li>json串中info为交易系统返回码（银联标准）备注，可用于log记录，如交易成功为："交易成功"</li>
 * <li>交易出现异常时返回：{"code":"96","info":"系统故障"}</li>
 * </ul>
 * 
 * @version 1.0
 * @since 2016/7/6
 * @author pucker
 *
 */
public interface Java2TxnBusinessFacade {

	/**
	 * 商户开户接口
	 * 
	 * @param txnBean
	 *            接口报文封装bean
	 * @return json格式字符串
	 * 
	 */
	String merchantAccountOpeningITF(TxnPackageBean txnBean) throws Exception;

	/**
	 * 客户开户接口
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String customerAccountOpeningITF(TxnPackageBean txnBean) throws Exception;
	
	/**
	 * 会员密码重置接口
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String customerPasswordResetITF(TxnPackageBean txnBean) throws Exception;
	
	/**
	 * 充值交易接口
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String rechargeTransactionITF(TxnPackageBean txnBean) throws Exception;
	
	/**
	 * 消费交易接口
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String consumeTransactionITF(TxnPackageBean txnBean) throws Exception;
	
	/**
	 * 薪无忧退款至嘉福账户交易接口
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	String HKBPayBackToJF(PayBackBean bean) throws Exception;
	
	/**
	 * 消费交易撤销接口
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String consumeRefundITF(TxnPackageBean txnBean) throws Exception;
	
	/**
	 * 消费交易退款接口（支持当天及隔日退）
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String transRefundITF(TxnPackageBean txnBean) throws Exception;
	
	/**
	 * 商户提现接口
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String merchantWithdrawITF(TxnPackageBean txnBean) throws Exception;
	
	/**
	 * 交易异常查询
	 * @param wxPrimaryKey 客户端流水号
	 * @return
	 * @throws Exception
	 */
	String transExceptionQueryITF(String wxPrimaryKey) throws Exception;
	
	/**
	 * 快捷消费交易接口
	 * 
	 * @param txnBean
	 * @return json格式字符串
	 */
	String quickConsumeTransactionITF(TxnPackageBean txnBean) throws Exception;
}
