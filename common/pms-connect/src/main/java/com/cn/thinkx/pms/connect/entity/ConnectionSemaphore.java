package com.cn.thinkx.pms.connect.entity;


public class ConnectionSemaphore {
	
	//链路名称
	private static String connectionNm;	
	
	//是否可用
	private static boolean isBusy = false;
	
	//最近链路标识开启时间
	private static long lastCreateTms;
	
	//链路UUID
	private static String linkUUID;

	public static String getConnectionNm() {
		return connectionNm;
	}

	public static void setConnectionNm(String connectionNm) {
		ConnectionSemaphore.connectionNm = connectionNm;
	}

	public static boolean isBusy() {
		return isBusy;
	}

	public synchronized static void setBusy(boolean isBusy) {
		ConnectionSemaphore.isBusy = isBusy;		
	}

	public static long getLastCreateTms() {
		return lastCreateTms;
	}

	public static void setLastCreateTms(long lastCreateTms) {
		ConnectionSemaphore.lastCreateTms = lastCreateTms;
	}

	public static String getLinkUUID() {
		return linkUUID;
	}

	public static void setLinkUUID(String linkUUID) {
		ConnectionSemaphore.linkUUID = linkUUID;
	}
}
