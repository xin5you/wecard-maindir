package com.cn.thinkx.itf.service;

import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;

public interface BasicTxnITF {

	/**
	 * 发送报文(java-->C)
	 * @return
	 * @throws Exception
	 */
	public CommMessage sendMessage(BizMessageObj msgObj) throws Exception;
	
}
