package com.cn.thinkx.oms.module.phoneRecharge.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.phoneRecharge.mapper.PhoneRechargeMapper;
import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeOrder;
import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeOrderUpload;
import com.cn.thinkx.oms.module.phoneRecharge.req.PhoneRechargeRefundReq;
import com.cn.thinkx.oms.module.phoneRecharge.resp.PhoneRechargeRefundResp;
import com.cn.thinkx.oms.module.phoneRecharge.service.PhoneRechargeService;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.SignUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;

@Service("phoneRechargeService")
public class PhoneRechargeServiceImpl implements PhoneRechargeService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PhoneRechargeMapper phoneRechargeMapper;

    @Override
    public PageInfo<PhoneRechargeOrder> getPhoneRechargeListPage(int startNum, int pageSize,
                                                                 PhoneRechargeOrder entity) {
        PageHelper.startPage(startNum, pageSize);
        List<PhoneRechargeOrder> list = phoneRechargeMapper.getPhoneRechargeList(entity);
        if (list != null && list.size() > 0) {
            for (PhoneRechargeOrder phoneRechargeOrder : list) {
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrder.getSupplier()))
                    phoneRechargeOrder.setSupplier(BaseConstants.phoneRechargeSupplier.findByCode(phoneRechargeOrder.getSupplier()).getValue());
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrder.getTransStat()))
                    phoneRechargeOrder.setTransStat(BaseConstants.phoneRechargeTransStat.findByCode(phoneRechargeOrder.getTransStat()).getValue());
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrder.getOrderType()))
                    phoneRechargeOrder.setOrderType(BaseConstants.phoneRechargeOrderType.findByCode(phoneRechargeOrder.getOrderType()).getValue());
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrder.getReqChannel()))
                    phoneRechargeOrder.setReqChannel(BaseConstants.phoneRechargeReqChnl.findByCode(phoneRechargeOrder.getReqChannel()).getValue());
                phoneRechargeOrder.setTransAmt(StringUtil.isNullOrEmpty(phoneRechargeOrder.getTransAmt()) ? "0" : NumberUtils.RMBCentToYuan(phoneRechargeOrder.getTransAmt()));
                phoneRechargeOrder.setTelephoneFace(StringUtil.isNullOrEmpty(phoneRechargeOrder.getTelephoneFace()) ? "0" : phoneRechargeOrder.getTelephoneFace());
                phoneRechargeOrder.setDiscountsAmt(StringUtil.isNullOrEmpty(phoneRechargeOrder.getDiscountsAmt()) ? "0" : phoneRechargeOrder.getDiscountsAmt());
                phoneRechargeOrder.setSupplierAmt(StringUtil.isNullOrEmpty(phoneRechargeOrder.getSupplierAmt()) ? "0" : phoneRechargeOrder.getSupplierAmt());
                phoneRechargeOrder.setFluxFace(StringUtil.isNullOrEmpty(phoneRechargeOrder.getFluxFace()) ? "0" : phoneRechargeOrder.getFluxFace());
            }
        }
        PageInfo<PhoneRechargeOrder> page = new PageInfo<PhoneRechargeOrder>(list);
        return page;
    }

    @Override
    public List<PhoneRechargeOrderUpload> getPhoneRechargeList(PhoneRechargeOrder entity) {
        List<PhoneRechargeOrderUpload> proList = phoneRechargeMapper.getPhoneRechargeListUpload(entity);
        if (proList != null && proList.size() > 0) {
            for (PhoneRechargeOrderUpload phoneRechargeOrderUpload : proList) {
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getSupplier()))
                    phoneRechargeOrderUpload.setSupplier(BaseConstants.phoneRechargeSupplier.findByCode(phoneRechargeOrderUpload.getSupplier()).getValue());
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getTransStat()))
                    phoneRechargeOrderUpload.setTransStat(BaseConstants.phoneRechargeTransStat.findByCode(phoneRechargeOrderUpload.getTransStat()).getValue());
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getOrderType()))
                    phoneRechargeOrderUpload.setOrderType(BaseConstants.phoneRechargeOrderType.findByCode(phoneRechargeOrderUpload.getOrderType()).getValue());
                if (!StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getReqChannel()))
                    phoneRechargeOrderUpload.setReqChannel(BaseConstants.phoneRechargeReqChnl.findByCode(phoneRechargeOrderUpload.getReqChannel()).getValue());
                phoneRechargeOrderUpload.setTransAmt(StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getTransAmt()) ? "0" : NumberUtils.RMBCentToYuan(phoneRechargeOrderUpload.getTransAmt()));
                phoneRechargeOrderUpload.setTelephoneFace(StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getTelephoneFace()) ? "0" : phoneRechargeOrderUpload.getTelephoneFace());
                phoneRechargeOrderUpload.setDiscountsAmt(StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getDiscountsAmt()) ? "0" : phoneRechargeOrderUpload.getDiscountsAmt());
                phoneRechargeOrderUpload.setSupplierAmt(StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getSupplierAmt()) ? "0" : phoneRechargeOrderUpload.getSupplierAmt());
                phoneRechargeOrderUpload.setFluxFace(StringUtil.isNullOrEmpty(phoneRechargeOrderUpload.getFluxFace()) ? "0" : phoneRechargeOrderUpload.getFluxFace());
            }
        }
        return proList;
    }

    @Override
    public String doPhoneRechargeRefund(String rId) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.FALSE);
        resultMap.addAttribute("msg", "退款失败");
        try {
            if (StringUtil.isNullOrEmpty(rId)) {
                return JSONObject.toJSONString(resultMap);
            }
            PhoneRechargeOrder phoneRechargeOrder = phoneRechargeMapper.getPhoneRechargeByRid(rId);
            String key = JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
                    BaseConstants.PHONE_RECHARGE_REQ_KEY);
            PhoneRechargeRefundReq req = new PhoneRechargeRefundReq();
            req.setOriOrderId(phoneRechargeOrder.getrId());
            req.setRefundOrderId(phoneRechargeOrder.getrId());
            req.setRefundAmount(phoneRechargeOrder.getTransAmt());
            req.setChannel(ChannelCode.CHANNEL8.toString());// 40007001
            req.setRefundFlag(BaseConstants.refundFalg.refundFalg1.getCode());
            req.setTimestamp(System.currentTimeMillis());
            req.setSign(SignUtil.genSign(req, key));
            String url = JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
                    BaseConstants.PHONE_RECHARGE_REFUND);
            String result = HttpClientUtil.sendPostReturnStr(url, JSONObject.toJSONString(req));// 发起退款请求
            logger.info("## 退款请求参数，[{}]，退款返回参数[{}]", JSONObject.toJSONString(req), result);
            PhoneRechargeRefundResp resp = JSONObject.parseObject(result, PhoneRechargeRefundResp.class);
            if (Constants.SUCCESS_CODE.equals(resp.getCode())) {
                phoneRechargeOrder.setTransStat(BaseConstants.phoneRechargeTransStat.PRTS5.getCode());
                resultMap.addAttribute("status", Boolean.TRUE);
                resultMap.addAttribute("msg", "退款成功");
            } else {
                phoneRechargeOrder.setTransStat(BaseConstants.phoneRechargeTransStat.PRTS6.getCode());
            }
            phoneRechargeMapper.updatePhoneRechargeOrder(phoneRechargeOrder);// 更新订单信息
        } catch (Exception e) {
            logger.error("## 支付渠道是福利余额的手机充值退款异常", e);
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "退款失败");
        }
        return JSONObject.toJSONString(resultMap);
    }

}
