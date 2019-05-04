package com.cn.thinkx.wxclient.websocket.ws;

import java.util.Date;

import org.springframework.web.socket.WebSocketSession;

public class WecardWebSocketUser {

//	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private WebSocketSession session;

	// 用户ID
	private String userId;
	
	private String bizId;
	
	private Date shakeTime; //握手创建时间

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public Date getShakeTime() {
		return shakeTime;
	}

	public void setShakeTime(Date shakeTime) {
		this.shakeTime = shakeTime;
	}
}
