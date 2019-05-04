package com.cn.thinkx.wxclient.websocket.ws;

import java.security.interfaces.RSAPrivateKey;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.common.redis.vo.CustomerQrCodeVO;
import com.cn.thinkx.common.redis.vo.WsScanCodePayMsg;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.Constants.ChannelCode;
import com.cn.thinkx.core.util.Constants.TransCode;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.merchant.service.MerchantManagerService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.PayReqTypeEnum;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.RSAUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.service.drools.WxDroolsExcutor;
import com.cn.thinkx.wxclient.domain.WxTransLog;
import com.cn.thinkx.wxclient.service.CtrlSystemService;
import com.cn.thinkx.wxclient.service.TransCommonService;
import com.cn.thinkx.wxclient.service.WxTransLogService;
import com.cn.thinkx.wxclient.websocket.util.WebSocketSendUtil;
import com.cn.thinkx.wxclient.websocket.util.WebSocketServerPool;


/**
 * Socket处理器
 */
@Component
public class WecardWebSocketHandler implements WebSocketHandler {
	
	private  Logger logger =  LoggerFactory.getLogger(WecardWebSocketHandler.class);
	

	
	@Autowired
	@Qualifier("transCommonService")
	private TransCommonService transCommonService;
	
	@Autowired
	@Qualifier("merchantManagerService")
	private MerchantManagerService merchantManagerService;
	
	@Autowired
	private WxDroolsExcutor wxDroolsExcutor;
	
	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;
	
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;
	
	/**
	 * 建立连接后,把登录用户的id写入WebSocketSession
	 */
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		String openId = (String) session.getAttributes().get("openId");

		if (!StringUtil.isNullOrEmpty(openId)) {
			WecardWebSocketUser wcUser=new WecardWebSocketUser();
			wcUser.setSession(session);
			wcUser.setUserId(openId);
			wcUser.setBizId((String) session.getAttributes().get("bizId"));
			wcUser.setShakeTime(new Date());
			WebSocketServerPool.addWebSocketServer(wcUser);
		}
	}
	
	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session,CloseStatus closeStatus) throws Exception {
		WebSocketServerPool.closeSession(session);
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	public synchronized void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
			if(message.getPayloadLength()==0){
				return;
			}
			try{
			WsScanCodePayMsg msg=JSONArray.parseObject(message.getPayload().toString(),WsScanCodePayMsg.class);
			
			if(!BaseConstants.WSSendTypeEnum.SEND_TYPE_00.getCode().equals(msg.getSendType())){ //用户保持心跳
				msg.setDate(new Date());
				if(PayReqTypeEnum.REQ_TYPE_B.getCode().equals(msg.getReqType())){  //商户端发起的请求
					if(BaseConstants.WSSendTypeEnum.SEND_TYPE_10.getCode().equals(msg.getSendType())){ //发起的支付请求
						try {
//							String encryptStr = DES3Util.Decrypt3DES(msg.getText(), BaseKeyUtil.getEncodingAesKey());
//							String[] datas=encryptStr.split("@@");
							String authCode=msg.getText();
							String qrCodeJsonStr=JedisClusterUtils.getInstance().get(authCode);
							if(authCode !=null){
								CustomerQrCodeVO qrVo=JSONArray.parseObject(qrCodeJsonStr, CustomerQrCodeVO.class);
								long qrTime=new Long(qrVo.getCurrTime()); //客户端系统生成的时间戳
								String copenId=qrVo.getOpenid(); //客户端二维码中的用户openId;
								String payType=qrVo.getPayType();//客户端二维码中的支付方式
								
								msg.setToUser(copenId);
								msg.setPayType(payType);
								long currTime=DateUtil.getCurrentTimeMillis(); //和客户端获取时间戳保持一致
								if((currTime-qrTime) <=3*60*1000){ //3分钟时间长以内的都可以操作
									try{
										//获取商户端管理员所属的 商户code
										MerchantManager merchantManager=merchantManagerService.getMerchantInsInfByOpenId(msg.getFromUser().trim());
										
										int oritxnAmount=Integer.parseInt(NumberUtils.RMBYuanToCent(msg.getTransAmt()));
										int transAmt = wxDroolsExcutor.getConsumeDiscount(merchantManager.getMchntId(), null,NumberUtils.disRatehundred(Double.parseDouble(msg.getTransAmt())));// 调用交易金额规则引擎
										msg.setOriTxnAmount(msg.getTransAmt()); //原交易金额
										msg.setTransAmt(String.valueOf(transAmt));//实际交易金额
										
										/***判断客户是否需要输入密码  begin***/
										boolean enterPassword=false;
										if(BaseConstants.PayTypeEnum.VIPCARD_PAY.getCode().equals(msg.getPayType())){
											enterPassword=transCommonService.doCustomerNeed2EnterPassword(merchantManager,msg.getToUser().trim(),transAmt);  //判断是否需要输入密码
										}
										
										/***判断客户是否需要输入密码  end***/
										CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
										WxTransLog log = new WxTransLog();
										if (cs != null) {
											if (Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
												String wxPrimaryKey = wxTransLogService.getPrimaryKey();
												log.setWxPrimaryKey(wxPrimaryKey);
												log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
												log.setSettleDate(cs.getSettleDate());// 交易日期
												
												if(BaseConstants.PayTypeEnum.VIPCARD_PAY.getCode().equals(msg.getPayType())){
													log.setTransId(TransCode.CW10.getCode());// 交易类型
													log.setTransChnl(ChannelCode.CHANNEL1.toString());// 渠道号
												}else if(BaseConstants.PayTypeEnum.WECHAT_PAY.getCode().equals(msg.getPayType())){
													log.setTransId(TransCode.CW71.getCode());// 交易类型
													log.setTransChnl(ChannelCode.CHANNEL2.toString());// 渠道号
												}
												log.setTransSt(0);// 插入时为0，报文返回时更新为1
												log.setInsCode(merchantManager.getInsCode());// 客户端传过来的机构code 
												log.setMchntCode(merchantManager.getMchntCode());
												log.setShopCode(merchantManager.getShopCode());
												log.setSponsor("10");
												log.setOperatorOpenId(msg.getFromUser());
												
												log.setUserInfUserName(copenId);
												log.setTransAmt(String.valueOf(transAmt));// 实际交易金额 插入时候默认与上送金额一致
												log.setUploadAmt(String.valueOf(oritxnAmount));// 上送金额
												log.setTransCurrCd(Constants.TRANS_CURR_CD);
												int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
												if (i != 1) {
													logger.info("消费交易--->insertIntfaceTransLog微信端插入流水记录数量不为1");
												}
												msg.setWxTransLogKey(wxPrimaryKey);
												
												if(!enterPassword && BaseConstants.PayTypeEnum.VIPCARD_PAY.getCode().equals(msg.getPayType())){
														//扫描支付请求
														TxnResp resp=transCommonService.scanCodeJava2TxnBusiness(log,merchantManager, msg.getFromUser(), copenId, "10", oritxnAmount,transAmt,"");
														msg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_90.getCode());
														msg.setCode(resp.getCode());
														msg.setText(resp.getInfo());
														msg.setMchntCode(merchantManager.getMchntCode());
														msg.setShopCode(merchantManager.getShopCode());
														session.sendMessage(new TextMessage(JSONArray.toJSONString(msg)));  //主动回复本次调用的结果
														WebSocketSendUtil.sendMessageToUser(msg); //给二维码用户发送消息
													}else{
														msg.setToUser(copenId);
														msg.setCode(Constants.TXN_TRANS_RESP_SUCCESS);
														msg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_20.getCode()); //发送请输入密码的请求
														WebSocketSendUtil.sendMessageToUser(msg); //给生成二维码的openId用户发送消息
														msg.setToUser(msg.getFromUser());
														session.sendMessage(new TextMessage(JSONArray.toJSONString(msg)));  //主动回复本次调用的结果,等待用户输入密码
													}
											}
										}
									}catch(Exception ex){
										ex.printStackTrace();
										logger.error("websocket 扫码支付失败：", ex);
										msg.setCode(Constants.TXN_TRANS_ERROR);
										msg.setText("网络异常，请稍后再试");
										if(session.isOpen()){
											session.sendMessage(new TextMessage(JSONArray.toJSONString(msg)));
										}
									}
								}
							}else{
								//TODO  二维码解析失败。。。。
							}
						} catch (Exception e) {
							logger.error("websocket二维码解密生成失败：", e.getLocalizedMessage());
						}
					}
				}else{
					if(BaseConstants.WSSendTypeEnum.SEND_TYPE_20.getCode().equals(msg.getSendType())){ //C端发起的密码提交
						MerchantManager merchantManager=merchantManagerService.getMerchantInsInfByOpenId(msg.getToUser().trim());
						String copenId=msg.getFromUser();
						int transAmt=Integer.parseInt(NumberUtils.RMBYuanToCent(msg.getTransAmt()));
						int oritxnAmount=Integer.parseInt(NumberUtils.RMBYuanToCent(msg.getOriTxnAmount())); //原交易金额
						//根据模和私钥指数获取私钥
						RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttributes().get(msg.getFromUser());
						String password = RSAUtil.decryptByPrivateKey(msg.getText(), privateKey);
						WxTransLog log =wxTransLogService.getWxTransLogById(msg.getWxTransLogKey());
						
						TxnResp resp=transCommonService.scanCodeJava2TxnBusiness(log,merchantManager, msg.getFromUser(), copenId, "00",oritxnAmount,transAmt,password);
						msg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_90.getCode());
						msg.setCode(resp.getCode());
						msg.setText(resp.getInfo());
						msg.setMchntCode(merchantManager.getMchntCode());
						msg.setShopCode(merchantManager.getShopCode());
						msg.setTransAmt(NumberUtils.RMBYuanToCent(msg.getTransAmt()));
						msg.setOriTxnAmount(NumberUtils.RMBYuanToCent(msg.getOriTxnAmount()));
						session.sendMessage(new TextMessage(JSONArray.toJSONString(msg)));  //主动回复本次调用的结果
						WebSocketSendUtil.sendMessageToUser(msg); //给扫码的openId用户发送消息
					}else if(BaseConstants.WSSendTypeEnum.SEND_TYPE_80.getCode().equals(msg.getSendType())){ //C端发起的微信支付结果消息推送
						
						WxTransLog log = wxTransLogService.getWxTransLogById(msg.getWxTransLogKey());
						if(!Constants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())){ //微信支付失败退款流程
							
						}
						msg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_90.getCode());
						
						//给扫码的商户管理员推送消息
						msg.setToUser(log.getOperatorOpenId());
						WebSocketSendUtil.sendMessageToUser(msg);
						
						//二维码客户推送消息
						msg.setToUser(log.getUserInfUserName());
						WebSocketSendUtil.sendMessageToUser(msg);
					}else if(BaseConstants.WSSendTypeEnum.SEND_TYPE_90.getCode().equals(msg.getSendType())){ //C端发起的微信支付结果消息推送
						
						if(PayReqTypeEnum.REQ_TYPE_A.getCode().equals(msg.getReqType())){ //API系统 支付结果

							msg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_90.getCode());
							//二维码客户推送消息
							WebSocketSendUtil.sendMessageToUser(msg);
						}
					}
				}
			}
			}catch(Exception ex){
				logger.error("websocket 扫码支付失败：", ex);
			}
	}

	/**
	 * 消息传输错误处理
	 */
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
	}

	public boolean supportsPartialMessages() {
		return false;
	}

}
