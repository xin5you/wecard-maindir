package com.cn.thinkx.pms.connect.entity;

import java.util.concurrent.ConcurrentHashMap;

public class SemaphoreMap {

	/** 以报文中通讯流水为主键的一个线程安全hashmap */
	private static ConcurrentHashMap<String, RecvMessageBean> excuterthreadsmap = new ConcurrentHashMap<String, RecvMessageBean>();

	public static ConcurrentHashMap<String, RecvMessageBean> getSemaphore() {
		return excuterthreadsmap;
	}

}
