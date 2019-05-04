package com.cn.thinkx.wxclient.websocket.util;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.vo.WsScanCodePayMsg;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.RSAUtil;
import com.cn.thinkx.wxclient.websocket.ws.WecardWebSocketUser;

import net.sf.json.JSONObject;

/**
 * WebSocket信息发送工具类 采用Websokect-Redis分布部署：
 * webSocket项目分布式部署时，采用Redis的订阅和发布机制解决服务器间的通信
 * 
 * @author zqy
 *
 */
public class WebSocketMessageUtil {

	private static Logger log = LoggerFactory.getLogger(WebSocketMessageUtil.class);

	/**
	 * 
	 * @param server
	 *            发送对象
	 * @param message
	 *            发送信息
	 */
	public static synchronized void sendMessageToOne(String toUser, TextMessage message) {
		WecardWebSocketUser toServer = WebSocketServerPool.getWebSocketServer(toUser);
		if (toServer != null && toServer.getSession() != null) {

			WsScanCodePayMsg msg = JSONArray.parseObject(message.getPayload().toString(), WsScanCodePayMsg.class);
			if (BaseConstants.WSSendTypeEnum.SEND_TYPE_20.getCode().equals(msg.getSendType())) {
				// 生成公钥和密钥 begin
				HashMap<String, Object> map;
				RSAPrivateKey privateKey = null;
				try {
					map = RSAUtil.getKeys();
					// 生成公钥和私钥
					RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
					privateKey = (RSAPrivateKey) map.get("private");
					msg.setPublicKeyExponent(publicKey.getPublicExponent().toString(16));
					msg.setPublicKeyModulus(publicKey.getModulus().toString(16));
					toServer.getSession().getAttributes().put(toUser, privateKey);
					message = new TextMessage(JSONArray.toJSONString(msg));
				} catch (NoSuchAlgorithmException e1) {
					log.info("websocket 请求用户输入密码 RSA 公钥 私钥生成失败 toUser[{}]", toUser);
				}
			} else if (BaseConstants.WSSendTypeEnum.SEND_TYPE_80.getCode().equals(msg.getSendType())) {
				msg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_90.getCode());
				message = new TextMessage(JSONArray.toJSONString(msg));
			}
			try {
				if (toServer.getSession().isOpen()) {
					WecardWebSocketUser wcUser = WebSocketServerPool.getWebSocketServer(toUser);
					if (wcUser.getSession() != null && wcUser.getSession().isOpen()) {
						wcUser.getSession().sendMessage(message);
					}
				}
			} catch (Exception e) {
				log.error(toServer.getUserId() + ":" + e.fillInStackTrace());
			}

		}
	}

	/**
	 * 构建统一格式消息
	 * 
	 * 
	 * @return
	 */
	public static String buildMessage(String message) {
		JSONObject result = JSONObject.fromObject(message);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		result.element("sendTime", format.format(new Date()));
		return result.toString();
	}

}
