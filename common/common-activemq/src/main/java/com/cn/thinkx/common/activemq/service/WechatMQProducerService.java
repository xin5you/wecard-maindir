package com.cn.thinkx.common.activemq.service;

import java.util.TreeMap;

import com.cn.thinkx.common.activemq.domain.WechatCustomerParam;
import com.cn.thinkx.common.activemq.domain.WechatTemplateParam;

/**
 * 
 * @author zqy
 * @description 队列消息生产者，发送消息到队列
 * 
 */

public interface WechatMQProducerService {

	/**
	 * 本地队列存储微信客服消息
	 * 
	 * @param acountName
	 * @param notice
	 * @param toOpenId
	 * @throws Exception
	 */
	void addWechatMessageQueue(String acountName, String notice, String toOpenId) throws Exception;

	/**
	 * 执行消费者，处理本地队列存储微信客服消息
	 * 
	 * @throws Exception
	 */
	void doWechatCustomerMessageQueue() throws Exception;

	/**
	 * 发送微信客服消息
	 * 
	 * @param param
	 */
	void sendMessage(final WechatCustomerParam param);

	/**
	 * 发送微信客服消息
	 * 
	 * @param acountName
	 *            公众号
	 * @param notice
	 *            消息内容
	 * @param toOpenId
	 *            用户openId
	 */
	void sendWechatMessage(String acountName, String notice, String toOpenId);
	
	/**
	 * 本地队列存储微信模板消息
	 * 
	 * @param acountName
	 * @param touser
	 * @param template_id
	 * @param url
	 * @param data
	 * @throws Exception
	 */
	void addTemplateMsgQueue(String acountName, String touser, String template_id, String url, TreeMap<String, TreeMap<String, String>> data) throws Exception;

	/**
	 * 执行消费者，处理本地队列存储微信模板消息
	 * 
	 * @throws Exception
	 */
	void doTemplateMsgQueue() throws Exception;

	/**
	 * 微信公众号 模板消息推送
	 * 
	 * @param acountName
	 *            微信公众号
	 * @param touser
	 *            目标用户openId
	 * @param template_id
	 *            模板Id
	 * @param url
	 *            页面跳转url
	 * @param Data
	 *            消息模板数据
	 */
	void sendTemplateMsg(String acountName, String touser, String template_id, String url, TreeMap<String, TreeMap<String, String>> data);

	/**
	 * 微信公众号 模板消息推送
	 * 
	 * @param templateParam
	 */
	void sendTemplateMsg(final WechatTemplateParam templateParam);

}
