package com.cn.thinkx.oms.core;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class SpringBeanDefineConfigue implements ApplicationListener<ContextRefreshedEvent> {

	private SpringBeanDefineService appService;

	/**
	 * 当一个ApplicationContext被初始化或刷新触发
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
			if (appService != null) {
				appService.initApplicationCacheData();
			}
		}
	}

	public SpringBeanDefineService getAppService() {
		return appService;
	}

	public void setAppService(SpringBeanDefineService appService) {
		this.appService = appService;
	}

}
