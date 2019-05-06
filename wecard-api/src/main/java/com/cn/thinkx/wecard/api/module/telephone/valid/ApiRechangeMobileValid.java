package com.cn.thinkx.wecard.api.module.telephone.valid;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5SignUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleReqVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

public class ApiRechangeMobileValid {

    public static Logger logger = LoggerFactory.getLogger(ApiRechangeMobileValid.class);

    /**
     * 分销商请求充值 数据判断
     *
     * @param resp
     * @return false
     */
    public static boolean rechargeValueValid(TeleReqVO req) {
        logger.info("渠道请求参数{}", JSONObject.toJSON(req));

        if (StringUtil.isNullOrEmpty(req.getChannelId())) {
            logger.info("channelId为空");
            return false;
        }
        if (StringUtil.isNullOrEmpty(req.getChannelToken())) {
            logger.info("channelToken为空");
            return false;
        }
        if (StringUtil.isNullOrEmpty(req.getV())) {
            logger.info("v为空");
            return false;
        }
        if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
            logger.info("timestamp为空");
            return false;
        }
        if (StringUtil.isNullOrEmpty(req.getOuterTid())) {// 充值的外部分销商ID
            logger.info("outerTid为空");
            return false;
        }
        if (StringUtil.isNullOrEmpty(req.getRechargePhone())) {// 充值的手机号
            logger.info("rechargePhone为空");
            return false;
        }
        if (StringUtil.isNullOrEmpty(req.getRechargeAmount())) { // 充值金额
            logger.info("rechargeAmount为空");
            return false;
        } else {
            Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]+)?$");
            if (!pattern.matcher(req.getRechargeAmount()).matches()) {
                logger.info("rechargeAmount不是数字");
                return false;
            }
        }
        if (StringUtil.isNullOrEmpty(req.getSign())) { // 签名
            logger.info("sign为空");
            return false;
        }
        return true;
    }

    /**
     * 分销商请求充值 签名验证
     *
     * @param resp
     * @return false
     * @throws ParseException
     */
    public static boolean rechargeSignValid(TeleReqVO reqVo, String signKey) throws ParseException {
        Date reqTime = DateUtil.COMMON_FULL.getFormat().parse(reqVo.getTimestamp());
        Date currDate = new Date();
        long absTime = Math.abs((currDate.getTime() - reqTime.getTime()) / 1000);
        if (absTime >= 600) {
            return false;
        }
        // 签名验证
        String sginfor = MD5SignUtils.genSign(reqVo, "key", signKey, new String[]{"sign", "serialVersionUID"}, null);
        if (!sginfor.equals(reqVo.getSign())) {
            return false;
        }

        // 是否有5s内的重复
        String tokenV = JedisClusterUtils.getInstance().get("api.recharge.mobile.token:" + sginfor);
        if (StringUtil.isNotEmpty(tokenV)) {
            return false;
        } else {
            JedisClusterUtils.getInstance().set("api.recharge.mobile.token:" + sginfor, sginfor, 5);
        }
        return true;
    }

    public static void main(String[] args) {
        TeleReqVO t = new TeleReqVO();
        t.setChannelId("201807111800008888001");
        t.setChannelToken("8a8d79196b28498eb0358e1dd42ec080");
        t.setMethod("hkb.api.mobile.charge");
        t.setV("1.0");
        t.setTimestamp("2018-07-18 09:52:14");
        t.setRechargePhone("15805178073");
        t.setRechargeAmount("50.000");
        t.setOuterTid("T180718250147113_1");
        t.setCallback("http://19662nx311.iok.la:10066/recharge/hkbnotify&productId=2018070505141610000001");
        System.out.println(MD5SignUtils.genSign(t, "key", "k7yQVsgGf50JvHkgh8ybpqtIOOoTbUXX", new String[]{"sign", "serialVersionUID"}, null));
    }

}
