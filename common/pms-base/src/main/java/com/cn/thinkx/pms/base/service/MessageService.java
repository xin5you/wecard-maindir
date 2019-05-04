package com.cn.thinkx.pms.base.service;

public interface MessageService {

	public boolean sendMessage(String target, String content);
	
	/**
	 * 短信消息
	 * 
	 * @param phoneNumber 手机号
	 * @param templateCode 模板Code
	 * @param messageType 短信类型
	 * @param templateParam 短信内容
	 * @return
	 */
	public boolean sendMessage(String phoneNumber, String templateCode, String templateParam);
}
