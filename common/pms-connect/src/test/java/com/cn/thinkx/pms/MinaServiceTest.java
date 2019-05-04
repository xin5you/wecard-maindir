package com.cn.thinkx.pms;

import java.io.IOException;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath*:spring-server-mina.xml")
public class MinaServiceTest {

	

	@Test
	public void testQueryTransation() throws Exception {

		try {
			ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] {"spring-connect-server-mina-test.xml" });
			NioSocketAcceptor acceptorServer = (NioSocketAcceptor) ctx.getBean("acceptorServer");
			acceptorServer.bind();
			Thread.sleep(9000000l);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
