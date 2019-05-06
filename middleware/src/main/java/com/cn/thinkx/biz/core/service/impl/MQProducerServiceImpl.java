package com.cn.thinkx.biz.core.service.impl;

import com.cn.thinkx.biz.core.service.MQProducerService;
import com.cn.thinkx.biz.mchnt.mapper.BizMchtMapper;
import com.cn.thinkx.biz.mchnt.model.MerchantManager;
import com.cn.thinkx.common.activemq.domain.WechatCustomerParam;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.WechatCustomerMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zqy
 * @description 队列消息生产者，发送消息到队列
 */
@Component("mqProducerService")
public class MQProducerServiceImpl implements MQProducerService {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    @Qualifier("bizMchtMapper")
    private BizMchtMapper bizMchtMapper;


    @Autowired
    @Qualifier("wechatMQProducerService")
    private WechatMQProducerService wechatMQProducerService;

    @Override
    public boolean sendCustomTextMsg(String transId, String txnId, String txnTime, String mchntCode, String shopCode, String payAmt,
                                     String giveAmt, String phoneNumLast4) throws Exception {

        List<MerchantManager> mngList = bizMchtMapper.getMerchantManagerByRoleType(mchntCode, shopCode, "400");


        if (mngList != null && mngList.size() > 0) {

            MerchantManager merchantManager = mngList.get(0);

            String notice = null;
            if (BaseConstants.TransCode.CW71.getCode().equals(transId)) {
                notice = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_W71_SUCCESS, merchantManager.getMchntName(), phoneNumLast4, merchantManager.getShopName(), NumberUtils.RMBCentToYuan(payAmt), txnTime, txnId);
            } else {
                notice = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_CW10_SUCCESS, merchantManager.getMchntName(), phoneNumLast4, merchantManager.getShopName(), NumberUtils.RMBCentToYuan(payAmt), txnTime, txnId);
            }
            for (MerchantManager mng : mngList) {
                try {
                    WechatCustomerParam wechantParam = new WechatCustomerParam();
                    wechantParam.setAcountName(RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT"));
                    wechantParam.setToOpenId(mng.getMangerName());
                    wechantParam.setContent(notice);
                    wechatMQProducerService.sendMessage(wechantParam);
                } catch (Exception e) {
                    logger.error("发送客服消息失败", e);
                }
            }
            return true;
        }
        return false;
    }
}
