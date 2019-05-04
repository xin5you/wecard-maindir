package com.cn.thinkx.wecard.facade.telrecharge.listener;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5SignUtils;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespVO;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderOrderInfFacade;
import com.cn.thinkx.wecard.facade.telrecharge.utils.ResultsUtil;
import com.cn.thinkx.wecard.facade.telrecharge.utils.TeleConstants;
import com.cn.thinkx.wecard.facade.telrecharge.utils.TeleConstants.ReqMethodCode;
import com.cn.thinkx.wecard.facade.telrecharge.vo.FrtPhoneRechargeReq;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 模板消息队列监听器
 * 
 * @author zhuqiuyou
 * 
 * @since 2018-07-10 11:21:23
 *  
 */
public class RechargeMobileSessionAwareMessageListener implements MessageListener {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("telProviderOrderInfFacade")
	private TelProviderOrderInfFacade telProviderOrderInfFacade;
	
	@Autowired
	@Qualifier("telChannelOrderInfFacade")
	private TelChannelOrderInfFacade telChannelOrderInfFacade;
	
	@Autowired
	@Qualifier("telChannelInfFacade")
	private TelChannelInfFacade telChannelInfFacade;

	public synchronized void onMessage(Message message) {
		ActiveMQTextMessage msg = (ActiveMQTextMessage) message;
			
			//获取分销商订单
			TelChannelOrderInf telChannelOrderInf=null;
			TelProviderOrderInf telProviderOrderInf=null;
			try {
				 String channelOrderId = msg.getText();
				 logger.info("待发起分销商充值的订单号-->{}", channelOrderId);
				 telChannelOrderInf=telChannelOrderInfFacade.getTelChannelOrderInfById(channelOrderId);
				 if(telChannelOrderInf !=null){
					 telProviderOrderInf=telProviderOrderInfFacade.getTelOrderInfByChannelOrderId(channelOrderId);
				  }
			} catch (Exception e) {
				logger.error("## 查询话费充值订单异常-->{}", e);
			}
			
			TelChannelInf telChannelInf=null;
			try{
				 telChannelInf= telChannelInfFacade.getTelChannelInfById(telChannelOrderInf.getChannelId());
			} catch (Exception e) {
				logger.error("## 查询渠道信息异常-->{}", e);
			}
			
			boolean notifyFlag=false; //是否回调分销商
			//30分钟以后的数据订单不出来
			Date currDate=new Date();
			if(currDate.getTime()-telChannelOrderInf.getCreateTime().getTime()<1000*60*30){
				//待充值的订单发起外部充值
				if(telProviderOrderInf !=null && TeleConstants.ProviderRechargeState.RECHARGE_STATE_8.getCode().equals( (telProviderOrderInf.getRechargeState()))){
					FrtPhoneRechargeReq frtReq=new FrtPhoneRechargeReq();
					frtReq.setAccessToken(telChannelInf.getChannelCode());
					frtReq.setChannelOrderNo(telProviderOrderInf.getRegOrderId());
					frtReq.setPhone(telChannelOrderInf.getRechargePhone());
					frtReq.setTelephoneFace(telChannelOrderInf.getRechargeValue().setScale(0, BigDecimal.ROUND_DOWN).toString()); //面额
					frtReq.setOrderType("1");
					frtReq.setReqChannel("P1003");
					frtReq.setAttach(RedisDictProperties.getInstance().getdictValueByCode("P1003_SIGN_KEY"));
					frtReq.setTimestamp(System.currentTimeMillis());
					frtReq.setCallBack(RedisDictProperties.getInstance().getdictValueByCode("WECARD_API_DOMAIN_URL")+"api/recharge/notify/bmHKbCallBack.html");
					String sign=MD5SignUtils.genSign(frtReq, 
							"key",
							 RedisDictProperties.getInstance().getdictValueByCode("P1003_SIGN_KEY"), 
							 new String[]{"sign","serialVersionUID"}, 
							 null);
					frtReq.setSign(sign);
				
					
					//获取所有的数据的fileds & vlaue
					Map<String, String> parameters=MD5SignUtils.getObjectMaps(frtReq, new String[]{"serialVersionUID"}, null);
					String rechargeUrl=RedisDictProperties.getInstance().getdictValueByCode("WELFAREMART_RECHARGE_REQUEST_URL");
					String reqString=JSONObject.toJSONString(frtReq);
					logger.info("手机充值--->流量充值接口，提交请求链接[{}] 参数{}", rechargeUrl, reqString);

					try {
						String jsonString= HttpClientUtil.sendPost(rechargeUrl, reqString);
						logger.info("##话费充值返回数据-->{}",jsonString);
						
						ObjectMapper objectMapper = new ObjectMapper();  
				        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);//设置可用单引号  
				        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//设置字段可以不用双引号包括  
				        JsonNode root = objectMapper.readTree(jsonString); 
				       String  code= root.get("code").asText();
				       if("00".equals(code)){
				    	   String billId=root.get("orderId").asText();
				    	   //已经发起充值
				    	   telProviderOrderInf.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_0.getCode());
				    	   telProviderOrderInf.setBillId(billId);
				       }else{
				    	   notifyFlag=true; //回调分销商
				    	   telProviderOrderInf.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_3.getCode());
				       }
				       telProviderOrderInf.setResv1(code); //记录充值渠道返回的结果信息
				       telProviderOrderInfFacade.updateTelProviderOrderInf(telProviderOrderInf);
					} catch (Exception e) {
						logger.error("##请求话费充值异常-->{}", e);
					}
				}
			}else{
				//取消充值
				try {
				 telProviderOrderInf.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_9.getCode());
				 telProviderOrderInfFacade.updateTelProviderOrderInf(telProviderOrderInf);
				 notifyFlag=true; //回调分销商
				} catch (Exception e) {
					logger.error("##取消话费充值异常-->{}", e);
				}
		 }
			
		if(notifyFlag &&  "0".equals(telChannelOrderInf.getNotifyFlag())){
			try{
				//异步通知供应商
				TeleRespVO respVo=new TeleRespVO();
				respVo.setSaleAmount(telChannelOrderInf.getPayAmt().toString());
				respVo.setChannelOrderId(telChannelOrderInf.getChannelOrderId());
				respVo.setPayState(telChannelOrderInf.getOrderStat());
				respVo.setRechargeState(telProviderOrderInf.getRechargeState()); //充值状态
				if(telProviderOrderInf.getOperateTime() !=null){
					respVo.setOperateTime(DateUtil.COMMON_FULL.getDateText(telProviderOrderInf.getOperateTime()));
				}
				respVo.setOrderTime(DateUtil.COMMON_FULL.getDateText(telChannelOrderInf.getCreateTime())); //操作时间
				respVo.setFacePrice(telChannelOrderInf.getRechargeValue().toString());
				respVo.setItemNum(telChannelOrderInf.getItemNum());
				respVo.setOuterTid(telChannelOrderInf.getOuterTid());
				respVo.setChannelId(telChannelOrderInf.getChannelId());
				respVo.setChannelToken(telChannelInf.getChannelCode());
				respVo.setV(telChannelOrderInf.getAppVersion());
				respVo.setTimestamp(DateUtil.COMMON_FULL.getDateText(new Date()));
				respVo.setSubErrorCode(telProviderOrderInf.getResv1());
				if("1".equals(telChannelOrderInf.getRechargeType())){
					respVo.setMethod(ReqMethodCode.R1.getValue());
				}else if("2".equals(telChannelOrderInf.getRechargeType())){
					respVo.setMethod(ReqMethodCode.R2.getValue());
				}
				String psotToken=MD5SignUtils.genSign(respVo, "key", telChannelInf.getChannelKey(), new String[]{"sign","serialVersionUID"}, null);
				respVo.setSign(psotToken);
				
				//修改通知后 分销商的处理状态
				logger.info("##发起分销商回调[{}],返回参数:[{}]",telChannelOrderInf.getNotifyUrl(),JSONObject.toJSONString(ResultsUtil.success(respVo)));
				String result=HttpClientUtil.sendPostReturnStr(telChannelOrderInf.getNotifyUrl(),JSONObject.toJSONString(ResultsUtil.success(respVo)));
				if(result !=null && "SUCCESS ".equals(result.toUpperCase() )){
					telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_3.getCode());
				}else{
					telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_2.getCode());
				}
				telChannelOrderInfFacade.updateTelChannelOrderInf(telChannelOrderInf);
				} catch (Exception e) {
					logger.error("##话费充值失败，回调分销商异常-->{}", e);
				}
			}
		
			try {
				message.acknowledge();
			} catch (JMSException e) {
				logger.error("##消息ack确认发生异常-->{}", e);
			}
		}
}