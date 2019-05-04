package com.cn.thinkx.common.activemq.listener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.activemq.util.MQConstants;

/**
 * MQ监听
 * 
 * @author zqy
 *
 */
public class MQServiceListener implements ServletContextListener {

	/**
	 * 在Web应用结束时停止任务
	 */
	@Override
	public void contextDestroyed(ServletContextEvent contextEvent) {

	}

	/**
	 * 在Web应用启动时初始化任务
	 */
	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
		final WechatMQProducerService ws = (WechatMQProducerService) webContext.getBean("wechatMQProducerService");

		Runnable runnable = new Runnable() {
			public void run() {
				try {
					if (MQConstants.MQ_QUEUE_EXEC_FLAG) {
//						ws.doWechatCustomerMessageQueue();
						ws.doTemplateMsgQueue();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		// 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
		service.scheduleAtFixedRate(runnable, 5, 2, TimeUnit.SECONDS);
	}
}
