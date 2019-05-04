package com.cn.thinkx.wxclient.websocket.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.cn.thinkx.wxclient.websocket.ws.WecardWebSocketUser;

/**
 * WebSokect连接信息池 实现服务连接的存储和获取
 * 
 * @author zqy
 *
 */
public class WebSocketServerPool {

	private static Logger log = LoggerFactory.getLogger(WebSocketServerPool.class);
	
	private static final ConcurrentHashMap<String, WecardWebSocketUser> connections = new ConcurrentHashMap<String, WecardWebSocketUser>();// 保存连接的Map容器

	/**
	 * 向连接池中添加连接
	 *
	 */
	public static void addWebSocketServer(WecardWebSocketUser wServer) {

		if (wServer == null) {
			return;
		}
		if (connections.get(wServer.getUserId()) == null) {
			connections.put(wServer.getUserId(), wServer);
		}
	}

	/**
	 * 移除服务连接
	 *
	 */
	public static void removeWebSocketServer(WecardWebSocketUser wServer) {
		if (wServer == null) {
			return;
		}
		connections.remove(wServer.getUserId());
	}

	/**
	 * 获取当前服务器所有在线用户
	 * 
	 * @param wServer
	 * @return 返回所有用户bussId_userId
	 */
	public static Set<String> getAllOnlineUser() {
		return connections.keySet();
	}

	/**
	 * 获取服务连接 userId 对象
	 * 
	 * @param userId
	 *            全KEY
	 * @return
	 */
	public static WecardWebSocketUser getWebSocketServer(String userId) {
		return connections.get(userId);
	}

	public static void closeSession(WebSocketSession session) {
		String openId = (String) session.getAttributes().get("openId");
		Iterator<Entry<String, WecardWebSocketUser>> it = connections.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, WecardWebSocketUser> entry = it.next();
			if (entry.getKey().equals(openId)) {
				connections.remove(entry.getKey());
				if (session.isOpen()) {
					try {
						session.close();
//						log.info("<<WebSocket>>会话已经移除:用户openID[{}] sessionId[{}]", entry.getKey(), session.getId());
					} catch (IOException e) {
						log.error("<<WebSocket>>会话移除异常:用户openID[{}] sessionId[{}]", entry.getKey(), session.getId(), e);
					}
				}
			}
		}
	}
}
