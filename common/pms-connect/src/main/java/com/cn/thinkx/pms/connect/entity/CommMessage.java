package com.cn.thinkx.pms.connect.entity;

public class CommMessage {
	/*消息包的长度*/
	private int length;
	/*消息包的内容，不含头部的长度*/
	private byte[] messagbody;
	
	private byte[] originMessag;
	
	/*消息包的对端ip*/
	private String remoteip;
	/*消息包的对端port*/
	private int remoteport;
	
	private Object messageObject;
	
	public String getRemoteip() {
		return remoteip;
	}
	public void setRemoteip(String remoteip) {
		this.remoteip = remoteip;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public byte[] getMessagbody() {
		return messagbody;
	}
	public void setMessagbody(byte[] messagbody) {
		this.messagbody = messagbody;
	}
	public byte[] getOriginMessag() {
		return originMessag;
	}
	public void setOriginMessag(byte[] originMessag) {
		this.originMessag = originMessag;
	}
	public int getRemoteport() {
		return remoteport;
	}
	public void setRemoteport(int remoteport) {
		this.remoteport = remoteport;
	}
	public Object getMessageObject() {
		return messageObject;
	}
	public void setMessageObject(Object messageObject) {
		this.messageObject = messageObject;
	}
	
	//一条通信链路对应一个C端doman
	private String domanId;

	public String getDomanId() {
		return domanId;
	}
	public void setDomanId(String domanId) {
		this.domanId = domanId;
	}
	
}
