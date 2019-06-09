package com.cn.thinkx.wecard.centre.module.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.centre.module.biz.util.CardKeysFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

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

    private static final String QUERY_URL = "http://api.xin5you.com/withdraw/zf-pay/queryOrder.html";

    /**
     * 卡密交易订单
     */
    private CardKeysOrderInfService cardKeysOrderInfService = CardKeysFactory.getCardKeysOrderInfService();

    @Override
    public void run() {
        CardKeysOrderInf cko = new CardKeysOrderInf();
        // 转让订单
        cko.setType(BaseConstants.orderType.O3.getCode());
        // 没有放入任务执行
        cko.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
        // 转让受理中
        cko.setStat(BaseConstants.orderStat.OS33.getCode());
        List<CardKeysOrderInf> cardKeysOrderInfs = cardKeysOrderInfService.getCardKeysOrderInfs(cko);
        logger.info("中付已受理订单查询===>RESP：{}", JSONObject.toJSONString(cardKeysOrderInfs));
        if (!CollectionUtils.isEmpty(cardKeysOrderInfs)) {
            JSONObject paramData = new JSONObject();
            cardKeysOrderInfs.forEach(item -> {
                paramData.put("paramData", JSONArray.toJSONString(item.getOrderId()));
                // 调用代付查询接口,根据代付接口返回信息更新卡密订单
                logger.info("中付代付查询传参===>paramData：{}", paramData.toString());
                String rtnStr = HttpClientUtil.sendPost(QUERY_URL, paramData.toString());
                logger.info("中付代付查询返回===>result：{}", rtnStr);
            });
        }
    }

}
