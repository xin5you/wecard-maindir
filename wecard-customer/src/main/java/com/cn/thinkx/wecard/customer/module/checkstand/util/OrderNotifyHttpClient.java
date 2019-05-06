package com.cn.thinkx.wecard.customer.module.checkstand.util;

import com.cn.thinkx.common.wecard.domain.trans.WxTransOrder;
import com.cn.thinkx.pms.base.http.HttpClient;
import com.cn.thinkx.pms.base.http.HttpRequest;
import com.cn.thinkx.pms.base.http.HttpResponse;
import com.cn.thinkx.pms.base.redis.util.SignUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransOrderResult;
import com.cn.thinkx.pms.base.utils.ObjectConverMapUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 交易订单 异步通知
 *
 * @author zqy
 */
public class OrderNotifyHttpClient {

    private Logger logger = LoggerFactory.getLogger(OrderNotifyHttpClient.class);

    /**
     * 订单通知
     *
     * @return
     * @throws Exception
     */
    public String doTransOrderNotifyRequest(WxTransOrder wxTransOrder) {
        HttpRequest request = new HttpRequest(wxTransOrder.getOrderDetail().getNotifyUrl());
        request.setConnectionTimeout(5000);
        request.setTimeout(5000);

        TransOrderResp resp = new TransOrderResp();

        // wxTransOrder.getOrderDetail().getNotifyType 1:通知,0,表示不是通知
        if (wxTransOrder != null && wxTransOrder.getOrderDetail() != null && "1".equals(wxTransOrder.getOrderDetail().getNotifyType())) {
            resp.setChannel(wxTransOrder.getTransChnl());
            if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(wxTransOrder.getOrderDetail().getRespCode())) {
                resp.setRespResult(TransOrderResult.SUCCESS.getValue());
            } else {
                resp.setRespResult(TransOrderResult.FAIL.getValue());
            }
            resp.setInnerMerchantNo(wxTransOrder.getMchntCode());
            resp.setInnerShopNo(wxTransOrder.getShopCode());
            resp.setUserId(wxTransOrder.getUserId());
            resp.setOrderId(wxTransOrder.getDmsRelatedKey());
            resp.setSettleDate(wxTransOrder.getOrderDetail().getSettleDate());
            resp.setTxnFlowNo(wxTransOrder.getOrderDetail().getTxnFlowNo());
            resp.setOriTxnAmount(wxTransOrder.getOrderDetail().getUploadAmt()); // 订单请求金额
            resp.setTxnAmount(wxTransOrder.getOrderDetail().getTransAmt());// 订单实际支付金额
            resp.setAttach(wxTransOrder.getOrderDetail().getAdditionalInfo()); // 订单附加数据
            resp.setSign(SignUtil.genSign(resp));// 签名

            Map<String, String> parameters = new ObjectConverMapUtils().getMapValue(resp);// 请求参数 转换成Map对象
            try {
                request.setParameters(parameters);
                HttpResponse response = HttpClient.post(request);
                logger.info("知了企服收银台--->异步回调接口返回值[{}]", response.getStringResult());
                if (response != null && StringUtil.isNotEmpty(response.getStringResult())) {
                    if (response.getStringResult() != null && "SUCCESS".equals(response.getStringResult().toUpperCase())) {
                        return TransOrderResult.SUCCESS.getValue();
                    } else {
                        return TransOrderResult.FAIL.getValue();
                    }
                }
            } catch (Exception e) {
                logger.error("订单异步通知请求发生异常：", e);
            }
        }
        return "";
    }
}
