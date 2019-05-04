package com.cn.thinkx.itf.listener;

import java.io.IOException;
import java.net.InetSocketAddress;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.connect.pool.ConnectionPool;

/**
 * MINA服务器启停监听器，用于在服务器启停时启停MINA监听
 * 
 */
@Service
public class MinaServerListener implements ServletContextListener {
	@Autowired
	private ConnectionPool connectionPool;

	// 记录日志
	final Logger logger = LoggerFactory.getLogger(MinaServerListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("start destroyed heart beat ");
		WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
		StdScheduler stdScheduler = (StdScheduler)webContext.getBean("heartBeatService");
		stdScheduler.shutdown(false);
		logger.info("end destroyed heart beat ");

		logger.info("start destroyed mina connect pool");
		connectionPool.destroy();
		logger.info("end destroyed mina connect pool");

		logger.info("start destroyed mina listener");
		NioSocketAcceptor acceptor = (NioSocketAcceptor) WebApplicationContextUtils.getWebApplicationContext(
				arg0.getServletContext()).getBean("acceptorServer");
		acceptor.setCloseOnDeactivation(true);
		acceptor.unbind();
		logger.info("end destroyed mina listener");
	}

	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("start init mina listener");
		String ip = ReadPropertiesFile.getInstance().getProperty("LISTEN_IP", null);
		int port = Integer.parseInt( ReadPropertiesFile.getInstance().getProperty("LISTEN_PORT", null).trim());
		InetSocketAddress localAddress = new InetSocketAddress(ip, port);
		NioSocketAcceptor acceptor = (NioSocketAcceptor) WebApplicationContextUtils.getWebApplicationContext(
				arg0.getServletContext()).getBean("acceptorServer");
		try {
			acceptor.setDefaultLocalAddress(localAddress);
			logger.info("LISTEN PORT:" + acceptor.getDefaultLocalAddress().getPort());
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info("end init mina listener");

	}

}
