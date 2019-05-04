package com.cn.iboot.diy.api.dubbo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;

@Configuration
public class DubboConfig {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DubboProperies dubboProperies;

	private Integer port = 20880;
	/**
	 * dubbo 注册的地址
	 * @return
	 */
	@Bean
	public RegistryConfig registry() {
		RegistryConfig registryConfig = new RegistryConfig();
		registryConfig.setAddress(dubboProperies.getRegistryAddress());
		return registryConfig;
	}
	
	@Bean
	public ApplicationConfig application(){
		ApplicationConfig applicationConfig = new ApplicationConfig();
		applicationConfig.setName(dubboProperies.getApplicationName());
		return applicationConfig;
	}
	
//	@Bean
//	public MonitorConfig monitorConfig(){
//		MonitorConfig monitorConfig=new MonitorConfig();
//		monitorConfig.setProtocol("registry");
//		return monitorConfig;
//	}
	
//	@Bean
//	public ReferenceConfig referenceConfig() {
//		ReferenceConfig referenceConfig=new ReferenceConfig();
//		referenceConfig.setMonitor(monitorConfig());
//		return referenceConfig;
//	}
	
	@Bean
	public ProtocolConfig protocol(){
		ProtocolConfig protocolConfig = new ProtocolConfig();
//		protocolConfig.setName(userReferenceProperies.getProtocolName());
//		protocolConfig.setHost(userReferenceProperies.getProtocolHost());
		port = NetUtils.getAvailablePort();
		protocolConfig.setPort(port);
		logger.info("######### ecom cas dubbo used port[{}] #########", port);
//		protocolConfig.setAccepts(userReferenceProperies.getProtocolAccepts());
		return protocolConfig;
	}
	
//	@Bean
//	public ProviderConfig provider(){
//		ProviderConfig providerConfig=new ProviderConfig();
////		providerConfig.setMonitor(monitorConfig());
//		providerConfig.setProtocol(protocol());
//		providerConfig.setId(userReferenceProperies.getProviderId());
////		providerConfig.setFilter(userReferenceProperies.getProviderFilter());
//		providerConfig.setTimeout(userReferenceProperies.getProviderTimeout());
//		providerConfig.setDelay(userReferenceProperies.getProviderDelay());
//		return providerConfig;
//	}
	
//	@Bean
//	public ConsumerConfig customer(){
//		ConsumerConfig consumerConfig=new ConsumerConfig();
//		consumerConfig.setCheck(false);
//		consumerConfig.setTimeout(dubboProperies.getProviderTimeout());
//		return consumerConfig;
//	}
}
