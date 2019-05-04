package com.cn.thinkx.pms.connect.pool;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.connect.entity.Connection;
import com.cn.thinkx.pms.connect.entity.ConnectionSemaphore;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;
import com.cn.thinkx.pms.connect.utils.ConnectConstant.CONNECTION_STATUS;

public class ConnectionProcessor {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private IoConnector connector;

	private ConnectionPool connectionPool;

	public boolean maintainConnection(String connectionNm) {
		/** 发起测试交易，判定链路（JAVA->C;C->JAVA）的有效性 */
		if (testConnection(connectionNm)) {
			// 如果链路正常，则修改链路状态为"已链接"，链路状态："已关闭"，"建立中"，"已链接"
			connectionPool.setConnectionStatus(connectionNm, ConnectConstant.CONNECTION_STATUS.ESTABLISHED.toString());
			return true;
		}
		/** 关闭当前链路 */
		connectionPool.setConnectionStatus(connectionNm, CONNECTION_STATUS.CLOSED.toString());
		connectionPool.closeConnection(connectionNm);
		/** 修改链路状态为"建立中" */
		connectionPool.setConnectionStatus(connectionNm, CONNECTION_STATUS.CREATING.toString());

		/** 建立链路，更新链路的时间戳 */
		return createConnection(connectionNm);
	}

	/**
	 * create connection by connection name
	 * 
	 * @param connectionNm
	 * @return
	 */
	private boolean createConnection(String connectionNm) {
		// 通过配置文件获取链路中C端的的IP和PORT
		String ip;
		int port;
		if (ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name().equals(connectionNm)) {
			ip = ReadPropertiesFile.getInstance().getProperty("TXN_IP_1", null);
			port = Integer.parseInt(ReadPropertiesFile.getInstance().getProperty("TXN_PORT_1", null));
		} else if (ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_2.name().equals(connectionNm)) {
			ip = ReadPropertiesFile.getInstance().getProperty("TXN_IP_2", null);
			port = Integer.parseInt(ReadPropertiesFile.getInstance().getProperty("TXN_PORT_2", null));
		} else {
			return false;
		}
		InetSocketAddress remoteAddress = new InetSocketAddress(ip, port);

		// 通过注入的IoConnector建立到C端的连接
		ConnectFuture connectfuture = connector.connect(remoteAddress);
		connectfuture.awaitUninterruptibly();
		IoSession session = null;
		try {
			session = connectfuture.getSession();
			// 如果未建立到C端的连接，则返回失败
			if (session == null) {
				throw new Exception("It fail to build connect");
			}

			logger.info("Connected to " + connectionNm + ", ip:{" + ip + "}, port:{" + port + "}");
			// 如果建立了到C端的连接，则在链路中设置到C端的连接
			Connection connect = connectionPool.getConnection(connectionNm);
			// 将链路名称放入session，用于在seesion closed事件时区分链路
			session.setAttribute(ConnectConstant.REMOTE_IP, ip);
			session.setAttribute(ConnectConstant.REMOTE_PORT, String.valueOf(port));
			session.setAttribute(ConnectConstant.CONNECTION_NM, connectionNm);
			session.setAttribute(ConnectConstant.LINK_UUID, ConnectionSemaphore.getLinkUUID());
			connect.setClientSession(session);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			// 释放链路维护标识
			ConnectionSemaphore.setBusy(false);
			ConnectionSemaphore.setConnectionNm(null);
			// 修改链路状态为“已关闭”
			connectionPool.setConnectionStatus(connectionNm, ConnectConstant.CONNECTION_STATUS.CLOSED.toString());
			return false;
		}
	}

	private boolean testConnection(Connection connect) {
		if (connect == null) {
			return false;
		}
		// 通过配置文件获取链路中C端的的IP和PORT
		String ip = ReadPropertiesFile.getInstance().getProperty("TXN_IP_1", null);
		int port = Integer.parseInt(ReadPropertiesFile.getInstance().getProperty("TXN_PORT_1", null));
		if (ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name().equals(connect.getConnectionNm())) {
			ip = ReadPropertiesFile.getInstance().getProperty("TXN_IP_1", null);
			port = Integer.parseInt(ReadPropertiesFile.getInstance().getProperty("TXN_PORT_1", null));
		} else if (ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_2.name().equals(connect.getConnectionNm())) {
			ip = ReadPropertiesFile.getInstance().getProperty("TXN_IP_2", null);
			port = Integer.parseInt(ReadPropertiesFile.getInstance().getProperty("TXN_PORT_2", null));
		} else {
			return false;
		}
		try {
			InetSocketAddress remoteAddress = new InetSocketAddress(ip, port);
			// 通过注入的IoConnector建立到C端的连接
			ConnectFuture connectfuture = connector.connect(remoteAddress);
			connectfuture.awaitUninterruptibly();
			IoSession session = connectfuture.getSession();
			// 如果未建立到C端的连接，则返回失败
			if (session == null) {
				throw new Exception("It fail to build connect");
			} else {
				connect.setClientSession(session);
			}
			return true;
		} catch (Exception e) {
			logger.error("Transaction system is not running", e);
			return false;
		}
	}

	/**
	 * test connection
	 * 
	 * @param connectionNm
	 * @return
	 */
	public boolean testConnection(String connectionNm) {
		Connection connect = connectionPool.getConnection(connectionNm);
		if (testConnection(connect)) {
			return true;
		} else {
			return false;
		}
	}

	public void setConnector(IoConnector connector) throws Exception {
		if (this.connector == null) {
			if (connector == null) {
				throw new Exception("Can not set a null connector");
			}
			connector.getSessionConfig().setUseReadOperation(true);
			this.connector = connector;
		} else
			throw new Exception("Can not reset the connector");
	}

	public void setConnectionPool(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
	}

}
