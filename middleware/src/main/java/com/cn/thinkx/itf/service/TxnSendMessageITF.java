package com.cn.thinkx.itf.service;

import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;

/**
 * 交易核心接口
 * @author pucker
 *
 */
public interface TxnSendMessageITF {

	/**
	 * 报文发送
	 * @param txn
	 * @return
	 */
	public BizMessageObj sendMessage(TxnPackageBean txn) throws Exception;
	
}
