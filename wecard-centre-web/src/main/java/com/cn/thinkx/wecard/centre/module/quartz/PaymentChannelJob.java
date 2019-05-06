package com.cn.thinkx.wecard.centre.module.quartz;

import com.cn.thinkx.pms.base.redis.service.RedisCacheService;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.redis.vo.PaymentChannelApiVO;
import com.cn.thinkx.pms.base.redis.vo.PaymentChannelVO;
import com.cn.thinkx.wecard.centre.module.biz.service.PaymentChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Iterator;
import java.util.List;

public class PaymentChannelJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("paymentChannelService")
    private PaymentChannelService paymentChannelService;

    @Autowired
    @Qualifier("redisCacheService")
    private RedisCacheService<String, PaymentChannelVO> redisCacheService;

    /*
     * 定时向redis插入支付通道信息
     */
    public void doRefreshPaymentChannel() {
        List<PaymentChannelVO> pcList = null;
        List<PaymentChannelApiVO> pciList = null;
        PaymentChannelVO paymentChannelVO = null;
        PaymentChannelApiVO pciVO = null;
        String channelNo = "";
        try {
            pcList = paymentChannelService.getPaymentChannelList();
            if (pcList != null && pcList.size() > 0) {
                for (int i = 0; i < pcList.size(); i++) {
                    paymentChannelVO = pcList.get(i);
                    channelNo = paymentChannelVO.getChannelNo();
                    if (paymentChannelVO != null && "0".equals(paymentChannelVO.getDataStat())) {
                        // 状态   非删除
                        if (paymentChannelVO != null && "1".equals(paymentChannelVO.getEnable())) {
                            //判断状态为启用
                            pciList = paymentChannelVO.getApiList();
                            Iterator it = pciList.iterator();
                            while (it.hasNext()) {
                                pciVO = (PaymentChannelApiVO) it.next();
                                if ((pciVO != null && "1".equals(pciVO.getDataStat()))) {
                                    it.remove();
                                } else {
                                    if (pciVO != null && "0".equals(pciVO.getEnable())) {
                                        it.remove();
                                    }
                                }
                            }
                            if (pciList != null) {
                                paymentChannelVO.setApiList(pciList);
                            }
                            redisCacheService.hset(RedisConstants.REDIS_HASH_TABLE_TB_PAYMENT_CHANNELS_INF, channelNo, paymentChannelVO);
                        } else {
                            redisCacheService.hdel(RedisConstants.REDIS_HASH_TABLE_TB_PAYMENT_CHANNELS_INF, channelNo);
                        }
                    } else {
                        redisCacheService.hdel(RedisConstants.REDIS_HASH_TABLE_TB_PAYMENT_CHANNELS_INF, channelNo);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("定时任务doRefreshPaymentChannelJob执行异常", e);
        }
    }

}
