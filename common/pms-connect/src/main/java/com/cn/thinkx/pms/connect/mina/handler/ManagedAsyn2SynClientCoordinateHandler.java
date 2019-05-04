package com.cn.thinkx.pms.connect.mina.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.entity.Connection;
import com.cn.thinkx.pms.connect.entity.ConnectionSemaphore;
import com.cn.thinkx.pms.connect.job.ConnectionMaintainJob;
import com.cn.thinkx.pms.connect.pool.ConnectionPool;
import com.cn.thinkx.pms.connect.service.impl.ManagedAsyn2SynClient;
import com.cn.thinkx.pms.connect.utils.HSTConstants;

public class ManagedAsyn2SynClientCoordinateHandler extends IoHandlerAdapter {
	/** The logger. */
	Logger logger = LoggerFactory.getLogger(getClass());

	private ManagedAsyn2SynClient managedAsyn2SynClient;

	private ConnectionPool connectionPool;

	private ConnectionMaintainJob connectionMaintainJob;

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		managedAsyn2SynClient.putReplyMessage((CommMessage) message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		String linkUUID = (String) session.getAttribute(HSTConstants.LINK_UUID);
		String connectionNm = (String) session.getAttribute(HSTConstants.CONNECTION_NM);
		String remoteIp = (String) session.getAttribute(HSTConstants.REMOTE_IP);
		String remotePort = (String) session.getAttribute(HSTConstants.REMOTE_PORT);

		logger.info("session " + connectionNm + " closed, remote ip is: [" + remoteIp + "], remote port is: [" + remotePort + "]");
		if (!StringUtil.isNullOrEmpty(connectionNm)) {
			session.close(true);
			// 如果当前链路被重置,需要判定该链路是否正在进行链路维护,如果正在维护链路则释放链路维护标识（同名链路多次建链,容易导致同名链路被反复重置）
			// 在链路中增加UUID,如果UUID相同者释放维护标识，否则不释放
			Connection connect = connectionPool.getConnection(connectionNm);
			if (linkUUID.equals(ConnectionSemaphore.getLinkUUID()) && ConnectionSemaphore.isBusy()
					&& HSTConstants.CONNECTION_STATUS_CREATING.equals(connect.getStatus())) {
				// 释放链路维护标识
				ConnectionSemaphore.setBusy(false);
				ConnectionSemaphore.setConnectionNm(null);
				connectionPool.setConnectionStatus(connectionNm, HSTConstants.CONNECTION_STATUS_CLOSED);
			}
			connectionMaintainJob.addTask(connectionPool,connectionNm);
		}
	}

	public void setManagedAsyn2SynClient(ManagedAsyn2SynClient managedAsyn2SynClient) {
		this.managedAsyn2SynClient = managedAsyn2SynClient;
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

	public void setConnectionMaintainJob(ConnectionMaintainJob connectionMaintainJob) {
		this.connectionMaintainJob = connectionMaintainJob;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error("getConnection throw exception, session:" + session, cause);
	}

}
