package com.cn.thinkx.wecard.centre.module.biz.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		applicationContext = context;
	}

	/**
	 * 获取applicationContext对象
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据bean的id来查找对象
	 * 
	 * @param id
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public static <T> T getBeanById(String id) {
		return (T) applicationContext.getBean(id);
	}

}