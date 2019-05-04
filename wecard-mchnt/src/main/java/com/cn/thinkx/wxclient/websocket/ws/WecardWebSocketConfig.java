package com.cn.thinkx.wxclient.websocket.ws;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebScoket配置处理器
 * @author zqy
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class WecardWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	
	private  Logger logger =  LoggerFactory.getLogger(WecardWebSocketConfig.class);

	@Resource
	WecardWebSocketHandler handler;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		registry.addHandler(handler, "/websocket_cashier.html").addInterceptors(new WecardHandShake()).setAllowedOrigins("http://hkb.tao-lue.com","http://hsh.tao-lue.com","http://wx.i-zhiliao.com","http://wxm.i-zhiliao.com");

		registry.addHandler(handler, "/sockjs/websocket_cashier.html").addInterceptors(new WecardHandShake()).withSockJS();
		
		logger.info("----------------------------spring websocket register 成功----------------------------");
	}

}
