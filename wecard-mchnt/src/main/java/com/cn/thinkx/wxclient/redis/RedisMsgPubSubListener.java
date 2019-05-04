package com.cn.thinkx.wxclient.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.wxclient.websocket.util.WebSocketSendUtil;

import redis.clients.jedis.JedisPubSub;

/**
 * Resdis消息发布/订阅
 * 
 * @author zqy
 *
 */

public class RedisMsgPubSubListener extends JedisPubSub {

	private static Logger logger = LoggerFactory.getLogger(RedisMsgPubSubListener.class);

	@Override
	public void unsubscribe() {
		super.unsubscribe();
	}

	@Override
	public void unsubscribe(String... channels) {
		super.unsubscribe(channels);
	}

	@Override
	public void subscribe(String... channels) {
		super.subscribe(channels);
	}

	@Override
	public void psubscribe(String... patterns) {
		super.psubscribe(patterns);
	}

	@Override
	public void punsubscribe() {
		super.punsubscribe();
	}

	@Override
	public void punsubscribe(String... patterns) {
		super.punsubscribe(patterns);
	}

	/**
	 * 监听到订阅频道接受到消息时的回调
	 */
	@Override
	public void onMessage(String channel, String message) {
		logger.info("JedisPubSub onMessage {} = {}", channel, message);
		WebSocketSendUtil.sendMessageTo(message);
		// this.unsubscribe();
	}

	/**
	 * 监听到订阅模式接受到消息时的回调
	 */
	@Override
	public void onPMessage(String pattern, String channel, String message) {

	}

	/**
	 * 订阅频道时的回调
	 */
	@Override
	public void onSubscribe(String channel, int subscribedChannels) {
		logger.debug("onSubscribe {} = {}", channel, subscribedChannels);
	}

	/**
	 * 取消订阅模式时的回调
	 */
	@Override
	public void onPUnsubscribe(String pattern, int subscribedChannels) {

	}

	/**
	 * 订阅频道模式时的回调
	 */
	@Override
	public void onPSubscribe(String pattern, int subscribedChannels) {

	}

	/**
	 * 取消订阅频道时的回调
	 */
	@Override
	public void onUnsubscribe(String channel, int subscribedChannels) {
		logger.debug("onUnsubscribe {} = {}", channel, subscribedChannels);
	}
}
