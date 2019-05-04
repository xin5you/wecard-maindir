package com.cn.thinkx.pms.service;

import java.io.IOException;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cn.thinkx.pms.connect.service.HeartBeatService;


public class HeartBeatServiceTest {

	@Test
	public void testProcHeartBeatTask() throws InterruptedException {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "spring-connect-serivce.xml",
				"spring-connect-server-mina.xml" });
		HeartBeatService HeartBeatService = (HeartBeatService) ctx.getBean("heartBeatService");
		NioSocketAcceptor nioSocketAcceptor = (NioSocketAcceptor) ctx.getBean("acceptorServer");
		try {
			nioSocketAcceptor.bind();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HeartBeatService.procHeartBeatTask();
	}

}
