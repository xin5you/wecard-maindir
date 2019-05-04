package com.cn.thinkx.itf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.itf.base.MessageUtils;
import com.cn.thinkx.itf.service.BasicTxnITF;
import com.cn.thinkx.itf.service.TxnSendMessageITF;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;

@Service("txnSendMessageITF")
public class TxnSendMessageITFImpl implements TxnSendMessageITF {
	@Autowired
	@Qualifier("basicTxnITF")
	private BasicTxnITF basicTxnITF;

	@Override
	public BizMessageObj sendMessage(TxnPackageBean txn) throws Exception {
		BizMessageObj obj = MessageUtils.initMessageObj();
		MessageUtils.txnPackageBean2BizMessageObj(txn, obj);

		if (!MessageUtils.isRequiredFiled(obj)) {
			CommMessage sendMsg = basicTxnITF.sendMessage(obj);
			if (sendMsg != null)
				obj = (BizMessageObj) sendMsg.getMessageObject();
			else
				return null;
		}
		return obj;
	}

}
