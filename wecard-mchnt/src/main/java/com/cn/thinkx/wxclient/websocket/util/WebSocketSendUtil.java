package com.cn.thinkx.wxclient.websocket.util;

import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.core.JedisUtils;
import com.cn.thinkx.common.redis.vo.WsScanCodePayMsg;
import com.cn.thinkx.pms.base.utils.BaseConstants;

/**
 * 消息业务发送处理类 调用工具进行发送
 * 
 * @author zqy
 *
 */
public class WebSocketSendUtil {
	/**
	 * 发送信息<br>
	 * 如果开启redis则发布订阅信息<br>
	 * 
	 * @param toTarget
	 *            ONE、BUSS必须
	 * @param message
	 */
	public static void sendMessageToRedis(String toTarget, String message) {
		WsScanCodePayMsg msg = JSONArray.parseObject(message, WsScanCodePayMsg.class);
		if (JedisUtils.JEDIS_STATUS) {
			JedisUtils.pubChannel(BaseConstants.RedisChannelEnum.B_SCAN_QR_CODE_PAY.getCode(), message);
		} else {
			WebSocketMessageUtil.sendMessageToOne(toTarget, new TextMessage(JSONArray.toJSONString(msg)));
		}
	}

	/**
	 * 发送信息<br>
	 * 如果开启redis则发布订阅信息<br>
	 * 
	 * @param toType
	 *            ALL 所有人，ONE 个人，BUSS 场次下所有人
	 * @param toTarget
	 *            ONE、BUSS必须
	 * @param message
	 */
	public static void sendMessageTo(String message) {
		WsScanCodePayMsg msg = JSONArray.parseObject(message, WsScanCodePayMsg.class);
		WebSocketMessageUtil.sendMessageToOne(msg.getToUser(), new TextMessage(JSONArray.toJSONString(msg)));
	}

	/**
	 * 发送信息<br>
	 * 如果开启redis则发布订阅信息<br>
	 * 
	 * @param message
	 */
	public static void sendMessageToUser(WsScanCodePayMsg msg) {
		if (WebSocketServerPool.getWebSocketServer(msg.getToUser()) != null) {
			WebSocketMessageUtil.sendMessageToOne(msg.getToUser(), new TextMessage(JSONArray.toJSONString(msg)));
		} else {
			WebSocketSendUtil.sendMessageToRedis(msg.getToUser(), JSONArray.toJSONString(msg));
		}
	}

}