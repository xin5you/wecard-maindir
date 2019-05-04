package com.cn.thinkx.pms.connect.job;

import java.net.InetSocketAddress;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.annotation.Resource;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cn.thinkx.pms.connect.entity.Connection;
import com.cn.thinkx.pms.connect.entity.ConnectionSemaphore;
import com.cn.thinkx.pms.connect.entity.ConnectionTask;
import com.cn.thinkx.pms.connect.pool.ConnectionPool;
import com.cn.thinkx.pms.connect.pool.ConnectionProcessor;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;

public class ConnectionMaintainJob {
	
	static Logger logger = LoggerFactory.getLogger(ConnectionMaintainJob.class);

	private static ConcurrentLinkedQueue<ConnectionTask> queue = new ConcurrentLinkedQueue<ConnectionTask>();

	private ConnectionProcessor connectionProcessor;
	
	@Resource
	private NioSocketAcceptor acceptorServer;

	@Autowired
	private ConnectionPool connectionPool;

	public void addTask(ConnectionPool connectionPool, String connectionNm) {
		this.connectionPool = connectionPool;
		//logger.info("connectionNm = " + connectionNm);
		Connection connect = connectionPool.getConnection(connectionNm);

		//logger.info("connection [" + connectionNm + "] is going to addTask");

		// 如果链路不是正在创建，则新增任务
		if (connect != null && !ConnectConstant.CONNECTION_STATUS.CREATING.toString().equals(connect.getStatus())) {
			ConnectionTask task = new ConnectionTask();
			task.setConnectionNm(connectionNm);
			task.setRequestTms(System.currentTimeMillis());
			queue.offer(task);
			logger.info("connection [" + connectionNm + "] added task");
		}
	}

	public void procConnectionTask() {
		//logger.info("maintain connect task start");
		while (!queue.isEmpty()) {
			InetSocketAddress address = acceptorServer.getLocalAddress(); 
			if (address == null) {
				logger.info("procConnectionTask()--->mina listener havn't inited");
				break;
			}
			if (!ConnectionSemaphore.isBusy()) {
				ConnectionTask task = queue.poll();
				String connectionNm = task.getConnectionNm();
				Connection connect = connectionPool.getConnection(connectionNm);
				//logger.info("Connection [" + connectionNm + "] begin to create");
				if (ConnectConstant.CONNECTION_STATUS.ESTABLISHED.toString().equals(connect.getStatus())) {
					//logger.info("Connection [" + connectionNm + "] status is CONNECTION_STATUS_ESTABLISHED!");
					// 链路最近建立时间
					long currentConnectionTms = connect.getCreateTms();
					// 链路重建申请时间
					long requestConnectionTms = task.getRequestTms();
					// 链路重建申请时间晚于链路最近建立时间，则进行链路维护
					if (requestConnectionTms > currentConnectionTms) {
						//logger.info("Now connection [" + connectionNm + "] status is CONNECTION_STATUS_ESTABLISHED, need reconnect!");
						establishConnect(connectionNm);
					}
				} else if (ConnectConstant.CONNECTION_STATUS.CLOSED.toString().equals(connect.getStatus())) {
					//logger.info("Now connection [" + connectionNm + "] status is CONNECTION_STATUS_CLOSED!");
					establishConnect(connectionNm);
				} else {
					//logger.info("Now connection is creating");
				}
				continue;
			} else {
				//logger.info("Connection processor is busy,[" + ConnectionSemaphore.getConnectionNm() + "]" + " is creating");
				if ((System.currentTimeMillis() - ConnectionSemaphore.getLastCreateTms()) > ConnectConstant.CONNECTION_CREATING_MAX_MILLIS) {
					connectionPool.getConnection(ConnectionSemaphore.getConnectionNm()).setStatus(ConnectConstant.CONNECTION_STATUS.CLOSED.toString());
					ConnectionSemaphore.setBusy(false);
					//logger.info("Semaphore is opened");
				}
				continue;
			}
		}
		//logger.info("maintain connect task end");
	}

	/**
	 * 创建链接
	 * 
	 * @param connectionNm
	 */
	private void establishConnect(String connectionNm) {
		ConnectionSemaphore.setBusy(true);
		ConnectionSemaphore.setLastCreateTms(System.currentTimeMillis());
		ConnectionSemaphore.setConnectionNm(connectionNm);
		ConnectionSemaphore.setLinkUUID(String.valueOf(UUID.randomUUID().toString()));
		connectionProcessor.maintainConnection(connectionNm);
		ConnectionSemaphore.setBusy(false);
	}

	public void setConnectionProcessor(ConnectionProcessor connectionProcessor) {
		this.connectionProcessor = connectionProcessor;
	}
}
