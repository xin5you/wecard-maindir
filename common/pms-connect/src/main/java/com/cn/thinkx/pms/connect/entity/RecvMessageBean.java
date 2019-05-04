package com.cn.thinkx.pms.connect.entity;

import java.util.concurrent.Semaphore;

public class RecvMessageBean {
	private Semaphore recvsemap = new Semaphore(0);
	private CommMessage recvmsg;
	private boolean responsed;

	public Semaphore getRecvsemap() {
		return recvsemap;
	}

	public void setRecvsemap(Semaphore recvsemap) {
		this.recvsemap = recvsemap;
	}

	public CommMessage getRecvmsg() {
		return recvmsg;
	}

	public void setRecvmsg(CommMessage recvmsg) {
		this.recvmsg = recvmsg;
	}

	public boolean isResponsed() {
		return responsed;
	}

	public void setResponsed(boolean responsed) {
		this.responsed = responsed;
	}

}
