package com.cn.thinkx.wxclient.websocket;

//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//
//import javax.websocket.OnClose;
//import javax.websocket.OnError;
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.alibaba.fastjson.JSONArray;
//import com.cn.thinkx.common.redis.util.BaseKeyUtil;
//import com.cn.thinkx.core.util.Constants;
//import com.cn.thinkx.pms.base.utils.DES3Util;
//import com.cn.thinkx.pms.base.utils.StringUtil;
//import com.cn.thinkx.pub.utils.StringTokenizerUtil;

//@ServerEndpoint(value = "/websocket_cashier/{openid}/{biz}")
public class CashierWebSocket {
	private static Logger logger = LoggerFactory.getLogger(CashierWebSocket.class);

	/**
	 * 商户进入扫一扫收款以及用户进入二维码被扫付款时，缓存双方session; key=openid,value=session
	 */
//	private static ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();
//		
//	@OnMessage
//	public void onMessage(String message, @PathParam("openid") String openid, @PathParam("biz") String biz) throws IOException {
//		if (!StringUtil.isNullOrEmpty(biz)) {
//			message = StringUtil.trim(message);
//			if (Constants.WX_WEBSOCKET_BIZ_SCANCODE.equals(biz)) {// 业务类型为商户——>扫一扫
//				if (Constants.WX_WEBSOCKET_BIZ_SCANNED.equals(message)) {
//					sessionMap.get(openid).getBasicRemote().sendText(Constants.WX_WEBSOCKET_START_TRANS);// 给商户页面发送开始处理交易消息
//					sessionMap.get(openid).getBasicRemote().flushBatch();
//				} else {
//					JSONArray json = StringTokenizerUtil.split(message, Constants.WX_FLAG_SEND_PASS);
//					if (json.size() > 1) {
//						String cOpenid = json.getString(1);
//						try {
//							cOpenid = DES3Util.Decrypt3DES(cOpenid, BaseKeyUtil.getEncodingAesKey());
//							cOpenid = StringUtil.trim(cOpenid.substring(8, cOpenid.length()));
//						} catch (Exception e) {
//							logger.error("websocket二维码解密生成失败：", e);
//						}
//						if (Constants.SUCCESS.equals(json.getString(0))) 
//							sessionMap.get(cOpenid).getBasicRemote().sendText(Constants.SUCCESS);// 提示用户支付成功
//						else if (Constants.FAILED.equals(json.getString(0)))
//							sessionMap.get(cOpenid).getBasicRemote().sendText(Constants.FAILED);// 提示用户支付失败
//						else if (Constants.WX_WEBSOCKET_NEED_PASSWORD.equals(json.getString(0)))
//							sessionMap.get(cOpenid).getBasicRemote().sendText(openid + Constants.WX_FLAG_SEND_PASS + json.getString(2));// 提示用户需要密码并将商户openid及实际交易金额传给用户
//					} else {
//						logger.info("Websocket merchant send message error, openid[" + openid + "]");
//					}
//				}
//			}
//			if (Constants.WX_WEBSOCKET_BIZ_QRCODE.equals(biz)) {// 业务类型为用户——>付款码
//				if (!StringUtil.isNullOrEmpty(message)) {// message为加密后的密码值与商户openid，以特殊符号隔开
//					JSONArray json = StringTokenizerUtil.split(message, Constants.WX_FLAG_SEND_PASS);
//					String password = StringUtil.trim(json.getString(0));
//					String mOpenid = StringUtil.trim(json.getString(1));
//					String transAmt = StringUtil.trim(json.getString(2));
//					
//					Session session = sessionMap.get(mOpenid);
//					if (session != null && session.getBasicRemote() != null) {
//						session.getBasicRemote().sendText(password + Constants.WX_FLAG_SEND_PASS + transAmt);// 向商户发送用户输入的密码
//					} else {
//						logger.info("Websocket merchant session losed, openid[" + mOpenid + "]");
//					}
//				}
//			}
//		}
//	}
//
//	@OnOpen
//	public void onOpen(final Session session, @PathParam("openid") String openid) {
//		sessionMap.put(StringUtil.trim(openid), session);// 缓存session
//	}
//
//	@OnClose
//	public void onClose(@PathParam("openid") String openid) {
//		if (!StringUtil.isNullOrEmpty(openid)) {
//			sessionMap.remove(openid);
//		}
//	}
//	
//	@OnError
//    public void onError(final Session session, @PathParam("openid") String openid, Throwable error) {
//		logger.error("Websocket error, openid[" + openid + "]", error);
//    }
	
}
