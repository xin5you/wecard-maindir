package com.cn.iboot.diy.api.dubbo.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.MethodConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;

/**
 * 调用服务暴露的接口配置
 *
 */
@Configuration
public class DubboServiceConfig extends DubboConfig {

	@Bean
	public ReferenceBean<TelChannelOrderInfFacade> TelChannelOrderInfFacade() {
		ReferenceBean<TelChannelOrderInfFacade> referenceBean = new ReferenceBean<TelChannelOrderInfFacade>();
		
		referenceBean.setInterface(TelChannelOrderInfFacade.class.getName());
		referenceBean.setCluster("failfast");
		
		List<MethodConfig> methods = new ArrayList<MethodConfig>();
		
		MethodConfig methodConfig = new MethodConfig();
		methodConfig.setName("getTelChannelOrderInf");
		methodConfig.setTimeout(10000);
		methodConfig.setRetries(0);
		methods.add(methodConfig);
		
		methodConfig = new MethodConfig();
		methodConfig.setName("getTelChannelOrderInfListToUpload");
		methodConfig.setTimeout(10000);
		methodConfig.setRetries(0);
		methods.add(methodConfig);
		
		methodConfig = new MethodConfig();
		methodConfig.setName("getTelChannelOrderInfCount");
		methodConfig.setTimeout(10000);
		methodConfig.setRetries(0);
		methods.add(methodConfig);
		
		referenceBean.setMethods(methods);
		return referenceBean;
	}
	
	
	@Bean
	public ReferenceBean<TelChannelInfFacade> TelChannelInfFacade() {
		ReferenceBean<TelChannelInfFacade> referenceBean = new ReferenceBean<TelChannelInfFacade>();
		
		referenceBean.setInterface(TelChannelInfFacade.class.getName());
		referenceBean.setCluster("failfast");
		
		List<MethodConfig> methods = new ArrayList<MethodConfig>();
		
		MethodConfig methodConfig = new MethodConfig();
		methodConfig.setName("getTelChannelInfByMchntCode");
		methodConfig.setTimeout(5000);
		methodConfig.setRetries(2);
		methods.add(methodConfig);
		
		referenceBean.setMethods(methods);
		return referenceBean;
	}
	
}
