
package com.cn.thinkx.wxclient.websocket.ws;

import java.util.Map;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

/**
 * Socket建立连接（握手）和断开
 */
@Component
public class WecardHandShake implements HandshakeInterceptor {
//	private Logger logger = LoggerFactory.getLogger(WecardHandShake.class);

	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			String openId = servletRequest.getServletRequest().getParameter("openId");
			String bizId = servletRequest.getServletRequest().getParameter("bizId");
//			logger.info("<<WebSocket>>用户openID[{}]已经建立连接在[{}]", openId, servletRequest.getServletRequest().getLocalAddr()
//					+ ":" + servletRequest.getServletRequest().getLocalPort());
			if (openId != null) {
				attributes.put("openId", openId);
				attributes.put("bizId", bizId);
			} else {
				return false;
			}
		}
		return true;
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

	}

}
