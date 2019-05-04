package com.cn.thinkx.pms.connect.service.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.entity.Connection;
import com.cn.thinkx.pms.connect.entity.RecvMessageBean;
import com.cn.thinkx.pms.connect.entity.SemaphoreMap;
import com.cn.thinkx.pms.connect.job.ConnectionMaintainJob;
import com.cn.thinkx.pms.connect.pool.ConnectionPool;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;

public class ManagedAsyn2SynClient {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	private BlockingQueue<CommMessage> replyList = new LinkedBlockingQueue<CommMessage>();

	private ConnectionPool connectionPool;

	private ConnectionMaintainJob  connectionMaintainJob;
	
	public void putReplyMessage(CommMessage sendmesg) {
		replyList.offer(sendmesg);
	}

	public CommMessage sendMessage(CommMessage sendmesg) throws Exception {
		// 获取业务信息
		BizMessageObj bizMessageObj = (BizMessageObj) sendmesg.getMessageObject();
		
		// 根据目标系统名称获取可用链路
		Connection connect = connectionPool.getAvailableConnection();
		if (connect == null) {
			throw new Exception("no available connection!");
		}
		// 通过链路获取domanID
		String connectionNm = connect.getConnectionNm();
		logger.info(" connection is [" + connectionNm +"]");
		if (connectionNm.equals(ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name()))
			sendmesg.setDomanId(ReadPropertiesFile.getInstance().getProperty("DOMAN1_ID", null));
		else
			sendmesg.setDomanId(ReadPropertiesFile.getInstance().getProperty("DOMAN2_ID", null));

		// 通过链路获取session
		IoSession session = connect.getClientSession();

		// 将空的待接收消息放入容器 KEY：packageNo VALUE: RecvMessageBean
		String sendkey = bizMessageObj.getPackageNo();
		RecvMessageBean temprecv = new RecvMessageBean();
		temprecv.setResponsed(false);
		SemaphoreMap.getSemaphore().putIfAbsent(sendkey, temprecv);
		logger.info(" map size is " + SemaphoreMap.getSemaphore().size());

		// 发送信息
		try {
			session.write(sendmesg);
		} catch (Exception e) {
			SemaphoreMap.getSemaphore().remove(sendkey);
			connectionMaintainJob.addTask(connectionPool,connectionNm);
			throw new Exception("send error!");
		}
		logger.info("Send success");
		try {
			// 挂在该资源上，等待指定秒内有回文
			long waitTime = Long.parseLong(ReadPropertiesFile.getInstance().getProperty("TIME_OUT_SEC", null));
			temprecv.getRecvsemap().tryAcquire(waitTime, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("InterruptedException is {}", e);
			
		}
		// 从容器里取出收到的消息
		RecvMessageBean tempresult = SemaphoreMap.getSemaphore().remove(sendkey);

		// 如果服务端有应答
		if (tempresult != null && tempresult.isResponsed()) {
			logger.info("recv hex msg " + StringUtil.Hex2Str(tempresult.getRecvmsg().getMessagbody()));
			return tempresult.getRecvmsg();
		} else {// 超时处理
			logger.info(" map size is " + SemaphoreMap.getSemaphore().size());
			logger.info("key " + sendkey + " is null");
			// 交给链路维护守护线程处理
			connectionMaintainJob.addTask(connectionPool,connect.getConnectionNm());
			//throw new Exception("time out!");
			logger.error("recv hex msg time out!");
			return null;
		}

	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public void setConnectionMaintainJob(ConnectionMaintainJob connectionMaintainJob) {
		this.connectionMaintainJob = connectionMaintainJob;
	}
	
}
