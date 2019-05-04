package com.cn.thinkx.pms.connect.entity;

import org.apache.mina.core.session.IoSession;


public class Connection {
	//connection name
	private String connectionNm;
	
	//JAVA->C Session
	private IoSession clientSession;
	
	//C->JAVA Session
	private IoSession serverSession;
	
	//link establised timestamp
	private long createTms;
	
	//link status
	private String status;
	
	public String getConnectionNm() {
		return connectionNm;
	}
	public void setConnectionNm(String connectionNm) {
		this.connectionNm = connectionNm;
	}
	
	public IoSession getClientSession() {
		return clientSession;
	}
	public void setClientSession(IoSession clientSession) {
		this.clientSession = clientSession;
	}
	
	public IoSession getServerSession() {
		return serverSession;
	}
	public void setServerSession(IoSession serverSession) {
		this.serverSession = serverSession;
	}
	
	public long getCreateTms() {
		return createTms;
	}
	public void setCreateTms(long createTms) {
		this.createTms = createTms;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
