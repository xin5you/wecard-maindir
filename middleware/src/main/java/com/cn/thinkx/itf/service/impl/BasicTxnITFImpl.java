package com.cn.thinkx.itf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.itf.service.BasicTxnITF;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.service.impl.ManagedAsyn2SynClient;

@Service("basicTxnITF")
public class BasicTxnITFImpl implements BasicTxnITF {

	@Autowired
	private ManagedAsyn2SynClient managedAsyn2SynClient;

	public CommMessage sendMessage(BizMessageObj msgObj) throws Exception {
		CommMessage sendMsg = new CommMessage();
		sendMsg.setMessageObject(msgObj);

		try {
			sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
		} catch (Exception e) {
			throw new Exception("Send message error!");
		}
		return sendMsg;
	}

}
