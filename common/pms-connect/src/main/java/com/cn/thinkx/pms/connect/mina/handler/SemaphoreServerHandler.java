package com.cn.thinkx.pms.connect.mina.handler;

import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.entity.Connection;
import com.cn.thinkx.pms.connect.entity.ConnectionSemaphore;
import com.cn.thinkx.pms.connect.entity.RecvMessageBean;
import com.cn.thinkx.pms.connect.entity.SemaphoreMap;
import com.cn.thinkx.pms.connect.job.ConnectionMaintainJob;
import com.cn.thinkx.pms.connect.pool.ConnectionPool;
import com.cn.thinkx.pms.connect.utils.HSTConstants;
/**
 * The Class SemaphoreServerHandler.
 */
@Service
public class SemaphoreServerHandler extends IoHandlerAdapter {
	

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The shortconnect. */
	private boolean shortconnect;
	
	private ConnectionPool connectionPool;

	private ConnectionMaintainJob connectionMaintainJob;
	/**
	 * Checks if is shortconnect.
	 * 
	 * @return true, if is shortconnect
	 */
	public boolean isShortconnect() {
		return shortconnect;
	}

	/**
	 * Sets the shortconnect.
	 * 
	 * @param shortconnect
	 *            the new shortconnect
	 */
	public void setShortconnect(boolean shortconnect) {
		this.shortconnect = shortconnect;
	}

	@Override
	public void sessionClosed(IoSession iosession) throws Exception {
		String remoteIp = (String) iosession.getAttribute(HSTConstants.REMOTE_IP);
		String remotePort = (String) iosession.getAttribute(HSTConstants.REMOTE_PORT);
		String connectionNm = (String) iosession.getAttribute(HSTConstants.CONNECTION_NM);
		iosession.close(true);
		logger.info("session " + connectionNm + " closed, remote ip is: [" + remoteIp + "], remote port is: ["
				+ remotePort + "]");
//		connectionMaintainJob.addTask(connectionPool,connectionNm);
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		InetSocketAddress address = (InetSocketAddress) iosession.getRemoteAddress();
		String remoteIp = address.getAddress().getHostAddress();
		String remotePort = String.valueOf(address.getPort());
		iosession.setAttribute(HSTConstants.REMOTE_IP, remoteIp);
		iosession.setAttribute(HSTConstants.REMOTE_PORT, remotePort);
		logger.info("session created, remote ip is: [" + remoteIp + "], remote port is: [" + remotePort + "]");

		// 设置服务端的接收IDLE时间，以秒为单位
		// iosession.getConfig().setReaderIdleTime(120);

		// 判定链接状态
		String connectionNm = ConnectionSemaphore.getConnectionNm();
		if (connectionNm != null && connectionPool.getConnection(connectionNm) != null
				&& ConnectionSemaphore.isBusy()) {
			Connection connect = connectionPool.getConnection(connectionNm);
			if (connect != null && HSTConstants.CONNECTION_STATUS_CREATING.equals(connect.getStatus())) {
				connect.setServerSession(iosession);
				connect.setCreateTms(System.currentTimeMillis());
				connectionPool.setConnectionStatus(connectionNm,
						HSTConstants.CONNECTION_STATUS_ESTABLISHED);
				logger.info("connection created success, connection name is :" + connectionNm);
				// 将链路名称放入session，用于在seesion closed事件时区分链路
				iosession.setAttribute(HSTConstants.CONNECTION_NM, connectionNm);
				// 释放链路维护标识
				ConnectionSemaphore.setBusy(false);
				ConnectionSemaphore.setConnectionNm(null);
			} else {
				iosession.close(true);
			}
		}

	}

	/**
	 * Trap exceptions.
	 * 
	 * @param session
	 *            the session
	 * @param cause
	 *            the cause
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// client 关断时也会触发该异常
		cause.printStackTrace();
		logger.error(cause.getLocalizedMessage(), cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache
	 * .mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.info("messageReceived");
		CommMessage recvmessage = (CommMessage) message;
		if (recvmessage == null) {
			logger.info(" recvmessagefromserver error,sessionid is [" + session.getId() + "]");
			return;
		}
		// 得到通讯报文中包号
		String sendkey = ((BizMessageObj) recvmessage.getMessageObject()).getPackageNo();
		logger.info("recieved message seqno is : " + sendkey);

		// 挂在资源上的消息放入，同时唤醒工作线程
		RecvMessageBean temprecv = SemaphoreMap.getSemaphore().get(sendkey);
		if (temprecv != null) {
			temprecv.setResponsed(true);
			temprecv.setRecvmsg(recvmessage);
			temprecv.getRecvsemap().release();
		} else {
			logger.info(" map size is [" + SemaphoreMap.getSemaphore().size() + "]");
			logger.info("------------------------------------------");
			logger.info(" recv message from server key [" + sendkey + "]: is null");
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("sending message");
		// logger.info("Message len is" + ((CommMessage)message).getLength());
		super.messageSent(session, message);
	}

	/**
	 * On idle, we just write a message on the console.
	 * 
	 * @param session
	 *            the session
	 * @param status
	 *            the status
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public void setConnectionMaintainJob(ConnectionMaintainJob connectionMaintainJob) {
		this.connectionMaintainJob = connectionMaintainJob;
	}
}
