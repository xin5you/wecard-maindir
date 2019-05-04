package com.cn.thinkx.common.activemq.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.cn.thinkx.common.activemq.service.RechargeMobileProducerService;

/**
 * 
 * @author zqy
 * @description 队列消息生产者，发送消息到队列
 * 
 */
public class RechargeMobileProducerServiceImpl implements RechargeMobileProducerService {
	private Logger logger = LoggerFactory.getLogger(RechargeMobileProducerServiceImpl.class);
	
	private JmsTemplate rechargeMobileJmsTemplate;

	/**
	 * @param regOrderId 分销商话费充值订单号
	 */
	public void sendRechargeMobileMsg(final String channelOrderId){
		logger.info("channelOrderId={}",channelOrderId);
		rechargeMobileJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(channelOrderId);
			}
		});
	}

	public void setRechargeMobileJmsTemplate(JmsTemplate rechargeMobileJmsTemplate) {
		this.rechargeMobileJmsTemplate = rechargeMobileJmsTemplate;
	}
	
}
