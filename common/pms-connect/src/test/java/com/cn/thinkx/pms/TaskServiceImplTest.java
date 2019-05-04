package com.cn.thinkx.pms;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.service.impl.ManagedAsyn2SynClient;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-connect-serivce.xml", "classpath:spring-connect-server-mina.xml"})
public class TaskServiceImplTest {

	@Autowired
	private ManagedAsyn2SynClient managedAsyn2SynClient;
	
	@Autowired
	private NioSocketAcceptor acceptorServer;

	@Test
	public void testSyncAccountFansList() {
		System.out.println("start init mina listener");
		String ip = ReadPropertiesFile.getInstance().getProperty("LISTEN_IP", null);
		int port = Integer.parseInt( ReadPropertiesFile.getInstance().getProperty("LISTEN_PORT", null).trim());
		InetSocketAddress localAddress = new InetSocketAddress(ip, port);
		
		try {
			acceptorServer.setDefaultLocalAddress(localAddress);
			System.out.println("LISTEN PORT:" + acceptorServer.getDefaultLocalAddress().getPort());
			acceptorServer.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("end init mina listener");
		
		CommMessage sendMsg = new CommMessage();
		Map<String, String> params = new HashMap<String, String>();
		params.put(ConnectConstant.TXN_TYPE, ReadPropertiesFile.getInstance().getProperty("HEART_BEAT_TXN_TYPE", null));
		params.put(ConnectConstant.TXN_CHANNEL, ReadPropertiesFile.getInstance().getProperty("HEART_BEAT_TXN_CHNL_ID", null));
		BizMessageObj obj = initMessageObj(params);
		sendMsg.setMessageObject(obj);
		
		try {
			sendMsg = managedAsyn2SynClient.sendMessage(sendMsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private BizMessageObj initMessageObj(Map<String, String> params) {
		// 组装报文
		BizMessageObj msgObj = new BizMessageObj();
		msgObj.setTxnType(params.get(ConnectConstant.TXN_TYPE));
		msgObj.setChannel(params.get(ConnectConstant.TXN_CHANNEL));
		msgObj.setPackageNo(String.valueOf(UUID.randomUUID().toString()));
		msgObj.setServiceName(ConnectConstant.VTXN);
		return msgObj;
	}
}
