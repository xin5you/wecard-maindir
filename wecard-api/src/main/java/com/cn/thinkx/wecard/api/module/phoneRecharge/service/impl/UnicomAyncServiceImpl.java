package com.cn.thinkx.wecard.api.module.phoneRecharge.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.redis.util.RedisPropertiesUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeOrderType;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeTransStat;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtils;
import com.cn.thinkx.wecard.api.module.phoneRecharge.model.PhoneRechargeOrder;
import com.cn.thinkx.wecard.api.module.phoneRecharge.service.PhoneRechargeService;
import com.cn.thinkx.wecard.api.module.phoneRecharge.service.UnicomAyncService;
import com.cn.thinkx.wecard.api.module.phoneRecharge.utils.DCUtils;
import com.cn.thinkx.wecard.api.module.phoneRecharge.vo.UnicomAyncNotify;
import com.cn.thinkx.wecard.api.module.phoneRecharge.vo.UnicomAyncReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;

@Service("unicomAyncService")
public class UnicomAyncServiceImpl implements UnicomAyncService {
    Logger logger = LoggerFactory.getLogger(UnicomAyncServiceImpl.class);

    @Autowired
    @Qualifier("jedisCluster")
    private JedisCluster jedisCluster;

    @Autowired
    @Qualifier("phoneRechargeService")
    private PhoneRechargeService phoneRechargeService;

    private static final String DINGCHI_LL_USERID = RedisPropertiesUtils.getProperty("DINGCHI_LL_USERID");
    private static final String DINGCHI_LL_KEY = RedisPropertiesUtils.getProperty("DINGCHI_LL_KEY");
    private static final String DINGCHI_HF_USERID = RedisPropertiesUtils.getProperty("DINGCHI_HF_USERID");
    private static final String DINGCHI_HF_KEY = RedisPropertiesUtils.getProperty("DINGCHI_HF_KEY");

    @Override
    public String buy(PhoneRechargeOrder flowOrder) {
        UnicomAyncReq voReq = new UnicomAyncReq();
        voReq.setSerialno(flowOrder.getrId());
        voReq.setDtCreate(DateUtil.getCurrentDateStr(DateUtil.FORMAT_YYYYMMDDHHMMSS));
        voReq.setUid(flowOrder.getPhone());
        voReq.setItemId(flowOrder.getGoodsNo());
        if (phoneRechargeOrderType.PROT1.getCode().equals(flowOrder.getOrderType())) {// 充值话费
            voReq.setUserId(DINGCHI_HF_USERID);
            voReq.setSign(DCUtils.genSign(voReq, DINGCHI_HF_KEY));
        } else if (phoneRechargeOrderType.PROT2.getCode().equals(flowOrder.getOrderType())) {// 充值流量
            voReq.setUserId(DINGCHI_LL_USERID);
            voReq.setSign(DCUtils.genSign(voReq, DINGCHI_LL_KEY));
        } else {
            logger.error("## 手机充值--->充值类型[{}]错误，用户[{}]", flowOrder.getOrderType(), flowOrder.getUserId());
            return null;
        }

        String DINGCHI_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_HTTP_URL);
        String DINGCHI_BUY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_BUY_URL);

        String url = DCUtils.genUrl(voReq, DINGCHI_URL + DINGCHI_BUY); // 请求GET路径(带参数)
        logger.info("手机充值--->请求鼎驰直充接口URL[{}]", url);
        String dingchiResp = HttpClientUtil.sendGet(url);
        logger.info("手机充值--->鼎驰返回支付信息[{}]", dingchiResp);
        return dingchiResp;
    }

    @Override
    public String query(HttpServletRequest req) {
        String DINGCHI_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_HTTP_URL);
        String DINGCHI_QUERY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_QUERY_URL);

        String userId = req.getParameter("userId");// 合作方用户编号(鼎驰科技方提供)
        String serialno = req.getParameter("serialno");// 合作方商户系统的流水号,全局唯一

        PhoneRechargeOrder flowOrder = phoneRechargeService.getPhoneRechargeOrderById(serialno);
        if (flowOrder == null) {
            logger.error("## 手机充值--->薪无忧查询接口，查询手机充值订单[{}]信息为空", serialno);
            return null;
        }

        UnicomAyncReq vo = new UnicomAyncReq();
        vo.setUserId(userId);
        vo.setSerialno(serialno);
        String key = null;

        if (phoneRechargeOrderType.PROT1.getCode().equals(flowOrder.getOrderType())) {// 充值话费
            key = DINGCHI_HF_KEY;
        } else if (phoneRechargeOrderType.PROT2.getCode().equals(flowOrder.getOrderType())) {// 充值流量
            key = DINGCHI_LL_KEY;
        }

        String hkbSign = DCUtils.genSign(vo, key);
        vo.setSign(hkbSign);
        String url = DCUtils.genUrl(vo, DINGCHI_URL + DINGCHI_QUERY); // 请求GET路径(带参数)
        logger.info("手机充值--->请求鼎驰查询接口URL[{}]", url);
        String result = HttpClientUtil.sendGet(DCUtils.genUrl(vo, url));
        logger.info("手机充值--->鼎驰返回查询信息[{}]", result);
//		UnicomAyncResp resp = JSONArray.parseObject(result, UnicomAyncResp.class);
        return result;
    }

    @Override
    public String notify(HttpServletRequest request) {
        String userId = request.getParameter("userId");// 合作方用户编号(鼎驰科技方提供)
        String bizId = request.getParameter("bizId");// 业务编号
        String id = request.getParameter("id");// 鼎驰科技方订单号
        String downstreamSerialno = request.getParameter("downstreamSerialno");// 合作方商户系统的流水号
        String status = request.getParameter("status");// 状态 2 是成功 3 是失败
        String statusDesc = request.getParameter("statusDesc");// 失败时错误信息、如填写不需要作为加密串
        String sign = request.getParameter("sign");// 校验码   (加密方式和 合作方交易的请求加密方式一样，私钥也一样)

        PhoneRechargeOrder flowOrder = phoneRechargeService.getPhoneRechargeOrderById(downstreamSerialno);
        if (flowOrder == null) {
            logger.error("## 手机充值--->流量充值异步回调接口，查询手机充值订单[{}]信息为空", downstreamSerialno);
            return null;
        }

        UnicomAyncNotify vo = new UnicomAyncNotify();
        vo.setUserId(userId);
        vo.setBizId(bizId);
        vo.setId(id);
        vo.setDownstreamSerialno(downstreamSerialno);
        vo.setStatus(status);

        String key = null;
        if (phoneRechargeOrderType.PROT1.getCode().equals(flowOrder.getOrderType())) {// 充值话费
            key = DINGCHI_HF_KEY;
        } else if (phoneRechargeOrderType.PROT2.getCode().equals(flowOrder.getOrderType())) {// 充值流量
            key = DINGCHI_LL_KEY;
        }

        String hkbSign = DCUtils.genSign(vo, key);
        if (!StringUtils.equals(sign, hkbSign)) {// 签名不正确
            logger.error("## 手机充值--->流量充值异步回调接口，验签失败，鼎驰回调信息[{}]", JSONArray.toJSONString(vo));
            return null;
        }

        if ("3".equals(status)) {// 交易失败
            logger.error("## 手机充值--->流量充值异步回调接口，鼎驰充值失败：鼎驰订单号[{}] 薪无忧订单号[{}] 错误信息[{}]", id, downstreamSerialno, statusDesc);
            flowOrder.setTransStat(phoneRechargeTransStat.PRTS3.getCode());
        } else if ("2".equals(status)) {// 交易成功
            logger.info("## 手机充值--->流量充值异步回调接口，鼎驰充值成功：鼎驰订单号[{}] 薪无忧订单号[{}]", id, downstreamSerialno);
            flowOrder.setTransStat(phoneRechargeTransStat.PRTS2.getCode());
        } else {
            logger.error("## 手机充值--->流量充值异步回调接口，鼎驰回调异常：鼎驰订单号[{}] status[{}] 错误信息[{}]", id, status, statusDesc);
        }

        if (phoneRechargeService.updatePhoneRechargeOrder(flowOrder) < 1) {
            logger.error("## 手机充值--->更新手机流量充值订单[{}]状态[{}]失败，用户[{}]", flowOrder.getrId(), phoneRechargeTransStat.findByCode(flowOrder.getTransStat()).getValue(), flowOrder.getUserId());
            return null;
        }
        return "ok";
    }


}
