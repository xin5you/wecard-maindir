package com.cn.thinkx.wecard.api.module.telephone.controller;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5SignUtils;
import com.cn.thinkx.wecard.api.module.telephone.vo.HKbCallBackResult;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api/recharge/notify")
public class ApiRechargeNotifyController {

    private Logger logger = LoggerFactory.getLogger(ApiRechargeNotifyController.class);

    @Autowired
    @Qualifier("telChannelInfFacade")
    private TelChannelInfFacade telChannelInfFacade;

    @Autowired
    @Qualifier("telChannelOrderInfFacade")
    private TelChannelOrderInfFacade telChannelOrderInfFacade;

    @Autowired
    @Qualifier("telProviderOrderInfFacade")
    private TelProviderOrderInfFacade telProviderOrderInfFacade;

    /**
     * 手机充值 知了企服商城回调
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bmHKbCallBack", method = RequestMethod.POST)
    @ResponseBody
    public String bmHKbCallBack(HttpServletRequest request) throws Exception {

        HKbCallBackResult reqResult = new HKbCallBackResult();
        reqResult.setCode(request.getParameter("code"));
        reqResult.setMsg(request.getParameter("msg"));
        reqResult.setOrderId(request.getParameter("orderId"));
        reqResult.setChannelOrderNo(request.getParameter("channelOrderNo"));
        reqResult.setUserId(request.getParameter("userId"));
        reqResult.setPhone(request.getParameter("phone"));
        reqResult.setTelephoneFace(request.getParameter("telephoneFace"));
        reqResult.setOrderType(request.getParameter("orderType"));
        reqResult.setAttach(request.getParameter("attach"));
        reqResult.setReqChannel(request.getParameter("reqChannel"));
        reqResult.setRespTime(request.getParameter("respTime"));
        reqResult.setSign(request.getParameter("sign"));

        logger.info(JSONObject.toJSONString(reqResult));

        String retSign = MD5SignUtils.genSign(reqResult, "key",
                RedisDictProperties.getInstance().getdictValueByCode("P1003_SIGN_KEY"),
                new String[]{"sign", "serialVersionUID"}, null);
        if (retSign.equals(reqResult.getSign())) {
            //获取供应商订单 && 修改供应商订单
            TelProviderOrderInf telProviderOrderInf = telProviderOrderInfFacade.getTelProviderOrderInfById(reqResult.getChannelOrderNo());
            try {
                if ("1".equals(reqResult.getCode())) {
                    telProviderOrderInf.setResv1("00"); //存储返回code
                    telProviderOrderInf.setOperateTime(new Date());
                    telProviderOrderInf.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_1.getCode());
                } else {
                    telProviderOrderInf.setResv1(reqResult.getCode()); //存储返回错误的code
                    telProviderOrderInf.setRechargeState(TeleConstants.ProviderRechargeState.RECHARGE_STATE_3.getCode());
                }
                telProviderOrderInfFacade.updateTelProviderOrderInf(telProviderOrderInf);
            } catch (Exception ex) {
                logger.error("#手机充值 修改供应商订单异常-->{}", ex);
            }

            //回调通知分销商
            try {
                TelChannelOrderInf telChannelOrderInf = telChannelOrderInfFacade.getTelChannelOrderInfById(telProviderOrderInf.getChannelOrderId());
                if (!"1".equals(reqResult.getCode())) {
                    //修改订单状态 为申请退款，处理状态 为失败
                    telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_2.getCode());  //处理失败
                    telChannelOrderInf.setResv1(reqResult.getCode());
                    telChannelOrderInfFacade.updateTelChannelOrderInf(telChannelOrderInf);
                } else {
                    telChannelOrderInf.setOrderStat(TeleConstants.ChannelOrderPayStat.ORDER_PAY_1.getCode());  //已经付款
                    telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_3.getCode());  //处理成功
                    telChannelOrderInfFacade.updateTelChannelOrderInf(telChannelOrderInf);
                }

                if ("0".equals(telChannelOrderInf.getNotifyFlag())) {
                    TelChannelInf telChannelInf = telChannelInfFacade.getTelChannelInfById(telChannelOrderInf.getChannelId());
                    //异步通知供应商
                    TeleRespVO respVo = new TeleRespVO();
                    respVo.setSaleAmount(telChannelOrderInf.getPayAmt().toString());
                    respVo.setChannelOrderId(telChannelOrderInf.getChannelOrderId());
                    respVo.setPayState(telChannelOrderInf.getOrderStat());
                    respVo.setRechargeState(telProviderOrderInf.getRechargeState()); //充值状态
                    if (telProviderOrderInf.getOperateTime() != null) {
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
                    if (!"1".equals(reqResult.getCode())) {
                        respVo.setSubErrorCode(telProviderOrderInf.getResv1());
                        respVo.setSubErrorMsg(reqResult.getMsg());
                    } else {
                        respVo.setSubErrorCode(telProviderOrderInf.getResv1());
                    }
                    if ("1".equals(telChannelOrderInf.getRechargeType())) {
                        respVo.setMethod(ReqMethodCode.R1.getValue());
                    } else if ("2".equals(telChannelOrderInf.getRechargeType())) {
                        respVo.setMethod(ReqMethodCode.R2.getValue());
                    }
                    String psotToken = MD5SignUtils.genSign(respVo, "key", telChannelInf.getChannelKey(), new String[]{"sign", "serialVersionUID"}, null);
                    respVo.setSign(psotToken);

                    //修改通知后 分销商的处理状态
                    logger.info("##发起分销商回调[{}],返回参数:[{}]", telChannelOrderInf.getNotifyUrl(), JSONObject.toJSONString(ResultsUtil.success(respVo)));
                    String result = HttpClientUtil.sendPostReturnStr(telChannelOrderInf.getNotifyUrl(), JSONObject.toJSONString(ResultsUtil.success(respVo)));
                    if (result != null && "SUCCESS ".equals(result.toUpperCase())) {
                        telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_3.getCode());
                    } else {
                        telChannelOrderInf.setNotifyStat(TeleConstants.ChannelOrderNotifyStat.ORDER_NOTIFY_2.getCode());
                    }
                    telChannelOrderInfFacade.updateTelChannelOrderInf(telChannelOrderInf);
                }
            } catch (Exception ex) {
                logger.error("##手机充值 回调通知分销商异常-->", ex);
            }
        } else {
            logger.info("#手机充值 回调验签失败-->{}", retSign);
            return "FAIL";
        }

        return "SUCCESS";
    }

}
