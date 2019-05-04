package com.cn.thinkx.wecard.customer.dubbo.impl;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.config.ProtocolConfig;

public class DynamicDubboPortReaderImpl implements ApplicationContextAware {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplicationContext applicationContext;
	private int port = 20880;

	@PostConstruct
	public void init() {
		Map<String, ProtocolConfig> beansOfType = applicationContext.getBeansOfType(ProtocolConfig.class);
		for (Entry<String, ProtocolConfig> item : beansOfType.entrySet()) {
			port = NetUtils.getAvailablePort();
			logger.info("######wecard-customer used port[{}]######", port);
			item.getValue().setPort(port);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}
}
