package com.cn.thinkx.pms.connect.pool;

import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.session.IoSession;

import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.Connection;
import com.cn.thinkx.pms.connect.job.ConnectionMaintainJob;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;

public class ConnectionPool {

	private ConnectionMaintainJob connectionMaintainJob;

	// 链路池
	private ConcurrentHashMap<String, Connection> pool = new ConcurrentHashMap<String, Connection>();

	public ConcurrentHashMap<String, Connection> getPool() {
		return pool;
	}

	public Connection putConnection(String connectionNm, Connection connect) {
		return pool.put(connectionNm, connect);
	}

	public Connection getConnection(String connectionNm) {
		return pool.get(connectionNm);
	}

	public Connection getAvailableConnection(String connectionNm) {
		Connection connect = pool.get(connectionNm);
		if (connect != null && ConnectConstant.CONNECTION_STATUS.ESTABLISHED.toString().equals(connect.getStatus()))
			return connect;
		else {
			connectionMaintainJob.addTask(this,connectionNm);
			return null;
		}
	}

	public void initialConnection() {
		// 链路池放入初始化链路
		Connection txnConnect = new Connection();
		txnConnect.setConnectionNm(ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name());
		txnConnect.setStatus(ConnectConstant.CONNECTION_STATUS.CLOSED.toString());
		putConnection(txnConnect.getConnectionNm(), txnConnect);
		// 队列插入链路创建任务
		connectionMaintainJob.addTask(this,ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name());

		// 初始化备用链路并往队列插入链路创建任务
		if (!StringUtil.isEmpty(ReadPropertiesFile.getInstance().getProperty("TXN_IP_2", null))) {
			Connection txnConnect2 = new Connection();
			txnConnect2.setConnectionNm(ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_2.name());
			txnConnect2.setStatus(ConnectConstant.CONNECTION_STATUS.CLOSED.toString());
			putConnection(txnConnect2.getConnectionNm(), txnConnect2);
			connectionMaintainJob.addTask(this,ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_2.name());
		}
		connectionMaintainJob.procConnectionTask();
	}

	/**
	 * 获取备用链路名称
	 * 
	 * @param connectionNm
	 * @return
	 */
	private String getBackupConnectionNm(String connectionNm) {
		if (connectionNm.equals(ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name()))
			return ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_2.name();
		else if (connectionNm.equals(ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_2.name()))
			return ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name();
		else
			return connectionNm;
	}

	/**
	 * 获取有效的连接
	 * 
	 * @return
	 */
	public synchronized Connection getAvailableConnection() {
		String connectionNm = ConnectConstant.CONNECTION_NAME.CONNECTION_TXN_1.name();
		Connection connect = getAvailableConnection(connectionNm);
		if (connect != null)
			return connect;
		else {
			return getAvailableConnection(getBackupConnectionNm(connectionNm));
		}
	}

	/**
	 * close connection by connection name
	 * 
	 * @param connectionNm
	 * @return
	 */
	public boolean closeConnection(String connectionNm) {
		Connection connect = getConnection(connectionNm);
		if (connect != null) {
			IoSession clientSession = connect.getClientSession();
			IoSession serverSession = connect.getServerSession();
			if (clientSession != null && clientSession.isConnected()) {
				// to交易系统的连接直接关闭
				connect.getClientSession().close(true);
			}
			if (serverSession != null && serverSession.isConnected()) {
				// 等待交易系统的服务，需要得到flash才能关闭，防止丢包
				connect.getServerSession().close(false);
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * set connection status
	 * 
	 * @param connectionNm
	 * @param status
	 */
	public void setConnectionStatus(String connectionNm, String status) {
		Connection connect = getConnection(connectionNm);
		if (connect != null)
			connect.setStatus(status);
	}

	/**
	 * 关闭所有链路，一般用于停止应用的时候
	 */
	public void destroy() {
		Enumeration<Connection> connections = pool.elements();
		while (connections.hasMoreElements()) {
			closeConnection(connections.nextElement().getConnectionNm());
		}
	}

	public void setConnectionMaintainJob(ConnectionMaintainJob connectionMaintainJob) {
		this.connectionMaintainJob = connectionMaintainJob;
	}

}
