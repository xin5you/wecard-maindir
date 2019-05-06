package com.cn.thinkx.wecard.api.module.mchnt.util;

import com.cn.thinkx.common.service.module.channel.domain.ChannelSecurityInf;
import com.cn.thinkx.facade.bean.ShopInfQueryRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.pms.base.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;

public class MchntReqValid {

    /**
     * 商户门店列表查询参数校验
     *
     * @param req
     * @param resp
     * @param channelInf
     * @return
     */
    public static boolean getShopListQueryITF(ShopInfQueryRequest req, TxnResp resp, ChannelSecurityInf channelInf) {
        if (StringUtil.isNullOrEmpty(req.getChannel())) {
            resp.setInfo("渠道号为空");
            return true;
        }
        if (ChannelCode.findByCode(req.getChannel()) == null) {
            resp.setInfo("无效渠道号");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getLongitude())) {
            resp.setInfo("经度为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getLatitude())) {
            resp.setInfo("纬度为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getDistance())) {
            resp.setInfo("距离范围为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!req.getSign().equals(ChannelSignUtil.genSign(req))) {
            resp.setInfo("签名错误");
            return true;
        }
        return false;
    }

    /**
     * 商户信息查询接口参数校验
     *
     * @param req
     * @param resp
     * @return
     */
    public static boolean getMerchantInfoQueryValid(BaseTxnReq req, TxnResp resp, ChannelSecurityInf channelInf) {
        if (StringUtil.isNullOrEmpty(req.getChannel())) {
            resp.setInfo("渠道号为空");
            return true;
        }
        if (ChannelCode.findByCode(req.getChannel()) == null) {
            resp.setInfo("无效渠道号");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
            resp.setInfo("商户号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!req.getSign().equals(ChannelSignUtil.genSign(req))) {
            resp.setInfo("签名错误");
            return true;
        }
        return false;
    }

    /**
     * 商户门店明细查询接口参数校验
     *
     * @param req
     * @param resp
     * @return
     */
    public static boolean getShopInfoQueryValid(ShopInfQueryRequest req, TxnResp resp, ChannelSecurityInf channelInf) {
        if (StringUtil.isNullOrEmpty(req.getChannel())) {
            resp.setInfo("渠道号为空");
            return true;
        }
        if (ChannelCode.findByCode(req.getChannel()) == null) {
            resp.setInfo("无效渠道号");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
            resp.setInfo("商户号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getInnerShopNo())) {
            resp.setInfo("门店号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!req.getSign().equals(ChannelSignUtil.genSign(req))) {
            resp.setInfo("签名错误");
            return true;
        }
        return false;
    }

    /**
     * 商户在售卡列表查询接口参数校验
     *
     * @param req
     * @param resp
     * @return
     */
    public static boolean getMchtSelListQueryValid(BaseTxnReq req, TxnResp resp, ChannelSecurityInf channelInf) {
        if (StringUtil.isNullOrEmpty(req.getChannel())) {
            resp.setInfo("渠道号为空");
            return true;
        }
        if (ChannelCode.findByCode(req.getChannel()) == null) {
            resp.setInfo("无效渠道号");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
            resp.setInfo("商户号为空");
            return true;
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) {
            resp.setInfo("签名为空");
            return true;
        }
        if (!req.getSign().equals(ChannelSignUtil.genSign(req))) {
            resp.setInfo("签名错误");
            return true;
        }
        return false;
    }

}
