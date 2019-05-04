package com.cn.thinkx.common.activemq.service.impl;

import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.activemq.domain.WechatCustomerParam;
import com.cn.thinkx.common.activemq.domain.WechatTemplateParam;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.activemq.util.MQConstants;

/**
 * 
 * @author zqy
 * @description 队列消息生产者，发送消息到队列
 * 
 */
public class WechatMQProducerServiceImpl implements WechatMQProducerService {
	Logger logger = LoggerFactory.getLogger(WechatMQProducerServiceImpl.class);

	final BlockingQueue<WechatCustomerParam> msgQueue = new LinkedBlockingQueue<WechatCustomerParam>();

	final BlockingQueue<WechatTemplateParam> tempQueue = new LinkedBlockingQueue<WechatTemplateParam>();

	private final ExecutorService execService = Executors.newFixedThreadPool(3);

	private JmsTemplate consumerMsgJmsTemplate;

	private JmsTemplate templateMsgJmsTemplate;

	public void addWechatMessageQueue(String acountName, String notice, String toOpenId) throws Exception {
		WechatCustomerParam wechantParam = new WechatCustomerParam();
		wechantParam.setAcountName(acountName);
		wechantParam.setToOpenId(toOpenId);
		wechantParam.setContent(notice);
		msgQueue.put(wechantParam);
	}

	public void doWechatCustomerMessageQueue() throws Exception {
		if (MQConstants.MQ_QUEUE_EXEC_FLAG && !msgQueue.isEmpty()) {
			MQConstants.MQ_QUEUE_EXEC_FLAG = false;
			while (!msgQueue.isEmpty()) {
				// for(int i=0;i<10;i++){
				Runnable msgThread = new Runnable() {
					public void run() {
						// 如果当前queue 为空，则直接返回
						if (msgQueue.isEmpty()) {
							MQConstants.MQ_QUEUE_EXEC_FLAG = true;
							return;
						}
						// 发送客服消息
						try {
							final WechatCustomerParam wechantParam = msgQueue.take();
							logger.info("wechantParam acountName={},openId={}", wechantParam.getAcountName(),
									wechantParam.getToOpenId());
							sendMessage(wechantParam);// 发送微信客服消息

						} catch (Exception ex) {
							logger.error("doCustomerWechatMessageQueue 线程池执行异常：", ex);
						}
					}
				};
				execService.execute(msgThread);
			}

			try {
				execService.awaitTermination(1, TimeUnit.SECONDS);
			} catch (Exception ex) {
				logger.error("execService awaitTermination exception", ex);
			}

			MQConstants.MQ_QUEUE_EXEC_FLAG = true;
		}
	}

	public void sendMessage(final WechatCustomerParam param) {
		consumerMsgJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(JSONObject.toJSONString(param));
			}
		});
	}

	public void sendWechatMessage(String acountName, String notice, String toOpenId) {
		WechatCustomerParam wechantParam = new WechatCustomerParam();
		wechantParam.setAcountName(acountName);
		wechantParam.setToOpenId(toOpenId);
		wechantParam.setContent(notice);
		this.sendMessage(wechantParam);
	}

	public void setConsumerMsgJmsTemplate(JmsTemplate consumerMsgJmsTemplate) {
		this.consumerMsgJmsTemplate = consumerMsgJmsTemplate;
	}

	public JmsTemplate getConsumerMsgJmsTemplate() {
		return consumerMsgJmsTemplate;
	}

	public void setTemplateMsgJmsTemplate(JmsTemplate templateMsgJmsTemplate) {
		this.templateMsgJmsTemplate = templateMsgJmsTemplate;
	}

	public void addTemplateMsgQueue(String acountName, String touser, String template_id, String url,
			TreeMap<String, TreeMap<String, String>> data) throws Exception {
		WechatTemplateParam templateParam = new WechatTemplateParam();
		templateParam.setAcountName(acountName);
		templateParam.setTouser(touser);
		templateParam.setTemplate_id(template_id);
		templateParam.setData(data);
		templateParam.setUrl(url);
		tempQueue.put(templateParam);
	}

	public void doTemplateMsgQueue() throws Exception {
		if (MQConstants.MQ_QUEUE_EXEC_FLAG && !tempQueue.isEmpty()) {
			MQConstants.MQ_QUEUE_EXEC_FLAG = false;
			while (!tempQueue.isEmpty()) {
				Runnable tempThread = new Runnable() {
					public void run() {
						if (tempQueue.isEmpty()) {// 如果当前queue 为空，则直接返回
							MQConstants.MQ_QUEUE_EXEC_FLAG = true;
							return;
						}
						try {
							final WechatTemplateParam templateParam = tempQueue.take();
							sendTemplateMsg(templateParam);// 发送微信模板消息
						} catch (Exception ex) {
							logger.error("doTemplateMsgQueue 线程池执行异常：", ex);
						}
					}
				};
				execService.execute(tempThread);
			}
			
			try {
				execService.awaitTermination(1, TimeUnit.SECONDS);
			} catch (Exception ex) {
				logger.error("execService awaitTermination exception", ex);
			}
			MQConstants.MQ_QUEUE_EXEC_FLAG = true;
		}
	}

	public void sendTemplateMsg(String acountName, String touser, String template_id, String url,
			TreeMap<String, TreeMap<String, String>> data) {
		WechatTemplateParam templateParam = new WechatTemplateParam();
		templateParam.setAcountName(acountName);
		templateParam.setTouser(touser);
		templateParam.setTemplate_id(template_id);
		templateParam.setData(data);
		templateParam.setUrl(url);
		this.sendTemplateMsg(templateParam);
	}

	public void sendTemplateMsg(final WechatTemplateParam templateParam) {
		templateMsgJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(JSONObject.toJSONString(templateParam));
			}
		});
	}

}
