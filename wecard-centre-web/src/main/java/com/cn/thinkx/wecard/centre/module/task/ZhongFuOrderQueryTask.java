package com.cn.thinkx.wecard.centre.module.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.centre.module.biz.util.CardKeysFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * 卡券转让代付中订单查询Task<br><br>
 * <p>
 * 功能 [定时任务job缓存线程池执行]<br>
 * 描述 [Task完成卡密交易没有实时返回成功状态以及未异步回调，主动发起代付查询并更新订单状态]
 *
 * @author pucker
 */
public class ZhongFuOrderQueryTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 卡密交易订单
     */
    private CardKeysOrderInf cardKeysOrderInf;
    private CardKeysOrderInfService cardKeysOrderInfService = CardKeysFactory.getCardKeysOrderInfService();

    /**
     * 初始化Task<br>
     *
     * @param cko 卡券交易订单
     * @see ZhongFuOrderQueryTask
     */
    public ZhongFuOrderQueryTask(CardKeysOrderInf cko) {
        this.cardKeysOrderInf = cko;
    }

    @Override
    public void run() {
        if (cardKeysOrderInf != null) {
            List<CardKeysOrderInf> cardKeysOrderInfs = cardKeysOrderInfService.getCardKeysOrderInfs(cardKeysOrderInf);
            logger.info("中付已受理卡密订单查询=========>查询条件：{}，查询结果：{}",cardKeysOrderInf, JSONObject.toJSONString(cardKeysOrderInfs));
            JSONObject paramData = new JSONObject();
            cardKeysOrderInfs.forEach(item -> {
                paramData.put("paramData", JSONArray.toJSONString(item.getOrderId()));
                // 调用代付查询接口,根据代付接口返回信息更新卡密订单
                logger.info("中付代付查询传参=========>orderId[{}]", item.getOrderId());
                String rtnStr = HttpClientUtil.sendPost("http://api.xin5you.com/withdraw/zf-pay/queryOrder.html", paramData.toString());
                logger.info("中付代付查询返回=========>{}", rtnStr);
            });
        } else {
            logger.error("## 查询中付代付中订单异常：cardKeysOrderInf对象为空");
        }
    }

}
