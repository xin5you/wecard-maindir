package com.cn.thinkx.dubbo.facadeImpl.txn.valid;

import com.cn.thinkx.dubbo.entity.BaseTransResp;
import com.cn.thinkx.facade.bean.IntegrationPayRequest;
import com.cn.thinkx.itf.base.SignUtil;
import com.cn.thinkx.pms.base.redis.util.IntSignUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class IntegrationPayValidUtil {

    public static boolean payMentTransactionITFValid(IntegrationPayRequest req, BaseTransResp resp) {
        if (StringUtil.isNullOrEmpty(req.getInsNo())) {
            resp.setInfo("机构号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTermChnlNo())) {
            resp.setInfo("通道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTransChnlNo())) {
            resp.setInfo("交易渠道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTermSwtFlowNo())) {
            resp.setInfo("终端流水号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getPaymentType())) {
            resp.setInfo("支付方式为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTransId())) {
            resp.setInfo("交易类型为空");
            return true;
        }
        if (!BaseIntegrationPayConstants.TransCode.CW71.getCode().equals(req.getTransId())) {
            resp.setInfo("交易类型不正确");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTransAmt())) {
            resp.setInfo("交易金额为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getAuthInfo())) {
            resp.setInfo("条码信息为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
            resp.setInfo("时间戳为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!IntSignUtil.genSign(req, RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")).equals(req.getSign())) {
            resp.setInfo("签名错误");
            return true;
        }
        if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
            resp.setInfo("签名过期");
            return true;
        }
        return false;
    }

    public static boolean refundTransactionITFValid(IntegrationPayRequest req, BaseTransResp resp) {
        if (StringUtil.isNullOrEmpty(req.getInsNo())) {
            resp.setInfo("机构号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTermChnlNo())) {
            resp.setInfo("通道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTermOrderNO())) {
            resp.setInfo("原交易流水号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTransChnlNo())) {
            resp.setInfo("交易渠道号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTermSwtFlowNo())) {
            resp.setInfo("终端流水号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getPaymentType())) {
            resp.setInfo("支付方式为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTransId())) {
            resp.setInfo("交易类型为空");
            return true;
        }
        if (!BaseIntegrationPayConstants.TransCode.CW74.getCode().equals(req.getTransId())) {
            resp.setInfo("交易类型不正确");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTransAmt())) {
            resp.setInfo("交易金额为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getAuthInfo())) {
            resp.setInfo("条码信息为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
            resp.setInfo("时间戳为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!IntSignUtil.genSign(req, RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")).equals(req.getSign())) {
            resp.setInfo("签名错误");
            return true;
        }
        if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
            resp.setInfo("签名过期");
            return true;
        }
        return false;
    }
}
