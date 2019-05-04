package com.cn.iboot.diy.api.dubbo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboProperies {

	@Value("${spring.dubbo.application.name}")
	private String applicationName;
	
	@Value("${spring.dubbo.protocol.name}")
	private String protocolName;
	
	@Value("${spring.dubbo.registry.address}")
	private String registryAddress;
	
	@Value("${spring.dubbo.registry.port}")
	private String port;
	
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getProtocolName() {
		return protocolName;
	}

	public void setProtocolName(String protocolName) {
		this.protocolName = protocolName;
	}
	
	public String getRegistryAddress() {
		return registryAddress;
	}

	public void setRegistryAddress(String registryAddress) {
		this.registryAddress = registryAddress;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
