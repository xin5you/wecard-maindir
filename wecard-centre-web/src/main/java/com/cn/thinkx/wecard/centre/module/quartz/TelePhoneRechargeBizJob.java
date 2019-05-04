package com.cn.thinkx.wecard.centre.module.quartz;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5SignUtils;
import com.cn.thinkx.wecard.centre.module.vo.FrtPhoneRechargeReq;
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
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 话费充值订单查询订单
 * @author zhuqiuyou
 *
 */
public class TelePhoneRechargeBizJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TelProviderOrderInfFacade telProviderOrderInfFacade;
	
	@Autowired
	private TelChannelOrderInfFacade telChannelOrderInfFacade;
	
	@Autowired
	private TelChannelInfFacade telChannelInfFacade;
	
	public void doRefreshRechargeState() {
			TelProviderOrderInf telProviderOrderInf =new TelProviderOrderInf();
			telProviderOrderInf.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_0.getCode());
			List<TelProviderOrderInf> list=telProviderOrderInfFacade.getListByTimer(telProviderOrderInf);
			
			FrtPhoneRechargeReq frtReq=null;
			if(list !=null && list.size() >0){
				for(TelProviderOrderInf  t:list){
					if(t ==null){
						continue;
					}
					boolean notifyFlag=false;
						try {
							frtReq=new FrtPhoneRechargeReq();
							frtReq.setChannelOrderNo(t.getRegOrderId());
							frtReq.setReqChannel("P1003");
							frtReq.setTimestamp(System.currentTimeMillis());
							String sign=MD5SignUtils.genSign(frtReq, 
									"key",
									 RedisDictProperties.getInstance().getdictValueByCode("P1003_SIGN_KEY"), 
									 new String[]{"sign","serialVersionUID"}, 
									 null);
							frtReq.setSign(sign);
							String rechargeUrl=RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.WELFAREMART_GET_RECHARGE_STATE_URL);
							String reqString=JSONObject.toJSONString(frtReq);
							logger.info("手机充值--->话费充值状态查询接口，提交请求链接[{}] ,参数{}", rechargeUrl, reqString);
							String jsonString= HttpClientUtil.sendPost(rechargeUrl, reqString);
							logger.info("话费充值状态查询返回数据-->{}",jsonString);
							
							ObjectMapper objectMapper = new ObjectMapper();  
					        objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);//设置可用单引号  
					        objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);//设置字段可以不用双引号包括  
					        JsonNode root = objectMapper.readTree(jsonString); 
					        String  code= root.get("code").asText();
					        if("00".equals(code)){
					        	String rechargeStat= root.get("rechargeStat") !=null ?root.get("rechargeStat").asText():"0";
					        	if(rechargeStat !=null && ! "0".equals(rechargeStat)){ //0:充值中,1:充值成功,9:充值失败
					        		notifyFlag=true;
					        		
					        		if("1".equals(rechargeStat)){ //充值成功
					        			t.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_1.getCode());
					        		}else if("9".equals(rechargeStat)){ //充值失败
					        			t.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_3.getCode());
					        		}
								    telProviderOrderInfFacade.updateTelProviderOrderInf(t);
					        	}
						    }else{
						    	notifyFlag=true;
						    }
						    
						} catch (Exception e) {
							logger.error("##定时任务doRefreshRechargeState执行异常", e);
					   }
				
						if(notifyFlag ){
							try{
								
								TelChannelOrderInf	telChannelOrderInf=telChannelOrderInfFacade.getTelChannelOrderInfById(t.getChannelOrderId());
									if("0".equals(telChannelOrderInf.getNotifyFlag())){
										TelChannelInf telChannelInf=telChannelInfFacade.getTelChannelInfById(telChannelOrderInf.getChannelId());
										//异步通知供应商
										TeleRespVO respVo=new TeleRespVO();
										respVo.setSaleAmount(telChannelOrderInf.getPayAmt().toString());
										respVo.setChannelOrderId(telChannelOrderInf.getChannelOrderId());
										respVo.setPayState(telChannelOrderInf.getOrderStat());
										respVo.setRechargeState(t.getRechargeState()); //充值状态
										if(t.getOperateTime() !=null){
											respVo.setOperateTime(DateUtil.COMMON_FULL.getDateText(t.getOperateTime()));
										}
										respVo.setOrderTime(DateUtil.COMMON_FULL.getDateText(telChannelOrderInf.getCreateTime())); //操作时间
										respVo.setFacePrice(telChannelOrderInf.getRechargeValue().toString());
										respVo.setItemNum(telChannelOrderInf.getItemNum());
										respVo.setOuterTid(telChannelOrderInf.getOuterTid());
										respVo.setChannelId(telChannelOrderInf.getChannelId());
										respVo.setChannelToken(telChannelInf.getChannelCode());
										respVo.setV(telChannelOrderInf.getAppVersion());
										respVo.setTimestamp(DateUtil.COMMON_FULL.getDateText(new Date()));
										respVo.setSubErrorCode(t.getResv1());
										if("1".equals(telChannelOrderInf.getRechargeType())){
											respVo.setMethod(ReqMethodCode.R1.getValue());
										}else if("2".equals(telChannelOrderInf.getRechargeType())){
											respVo.setMethod(ReqMethodCode.R2.getValue());
										}
										String psotToken=MD5SignUtils.genSign(respVo, "key", telChannelInf.getChannelKey(), new String[]{"sign","serialVersionUID"}, null);
										respVo.setSign(psotToken);
										
										//修改通知后 分销商的处理状态
										logger.info("发起分销商回调[{}],返回参数:[{}]",telChannelOrderInf.getNotifyUrl(),JSONObject.toJSONString(ResultsUtil.success(respVo)));
										String result=HttpClientUtil.sendPostReturnStr(telChannelOrderInf.getNotifyUrl(),JSONObject.toJSONString(ResultsUtil.success(respVo)));
										if(result !=null && "SUCCESS ".equals(result.toUpperCase() )){
											telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_3.getCode());
										}else{
											telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_2.getCode());
										}
										telChannelOrderInfFacade.updateTelChannelOrderInf(telChannelOrderInf);
									}
								} catch (Exception e) {
									logger.error("##话费充值失败，回调分销商异常-->{}", e);
								}
						}
				}
		 }
	}
}
