package com.cn.thinkx.wxclient.redis;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cn.thinkx.common.redis.core.JedisUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;

/**
 * Redis监听， 当启动WEB服务是，判断当前Redis是否开启，如果开启则订阅频道准备接收消息
 * 
 * @author zqy
 *
 */
public class RedisListener implements ServletContextListener {

	private Thread thread = null;

	/**
	 * 在Web应用结束时停止任务
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (thread != null) {
			if (thread.isAlive()) {
				thread.interrupt();
			}
		}
	}

	/**
	 * 在Web应用启动时初始化任务
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (JedisUtils.JEDIS_STATUS) {
			// 订阅频道
			thread = new Thread() {
				public void run() {
					try {
						Thread.sleep(300);
						JedisUtilsSubChannel.subChannel(BaseConstants.RedisChannelEnum.B_SCAN_QR_CODE_PAY.getCode());// 订阅商户端扫客户端频道);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			thread.start();
		}
	}

}
