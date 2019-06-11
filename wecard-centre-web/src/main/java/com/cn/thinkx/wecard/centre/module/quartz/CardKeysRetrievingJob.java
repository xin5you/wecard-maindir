package com.cn.thinkx.wecard.centre.module.quartz;

import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.DataStatEnum;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderStat;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderType;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysTransLogService;
import com.cn.thinkx.wecard.centre.module.task.ZhongFuOrderQueryTask;
import com.cn.thinkx.wecard.centre.module.task.ZhongFuPayTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 黄牛回收卡密 Job
 */
public class CardKeysRetrievingJob {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("cardKeysOrderInfService")
    private CardKeysOrderInfService cardKeysOrderInfService;

    @Autowired
    @Qualifier("cardKeysTransLogService")
    private CardKeysTransLogService cardKeysTransLogService;

    /**
     * 固定线程池
     */
    private ExecutorService es = Executors.newCachedThreadPool();

    private CardKeysOrderInf cko = new CardKeysOrderInf();

    /**
     * 回收卡密<br/>
     * 查找所有“转让中”且没有被放入任务执行的卡密交易订单
     */
    public void doCardKeysRecovery() {
        if (es.isShutdown()) {
            es = Executors.newFixedThreadPool(3);
        }

        Set<CardKeysOrderInf> orderList;
        try {
            // 处理代付处理中的卡密订单
            es.execute(new ZhongFuOrderQueryTask());

            // 转让订单
            cko.setType(orderType.O3.getCode());
            // 没有放入任务执行
            cko.setDataStat(DataStatEnum.TRUE_STATUS.getCode());
            // 转让中
            cko.setStat(orderStat.OS30.getCode());
            // 查询所有可代付订单
            orderList = cardKeysOrderInfService.getCardKeysOrderInfList(cko);
            if (CollectionUtils.isEmpty(orderList)) {
                return;
            }
            // 将“转让中”卡密交易订单加入缓存线程池执行代付处理
            // TODO 二期加入阿里开源消息队列中间件RocketMQ
            CardKeysTransLog cardKeysTransLog = new CardKeysTransLog();
            orderList.forEach(item -> {
                // 根据代付订单号查找所有卡密交易流水
                cardKeysTransLog.setOrderId(item.getOrderId());
                List<CardKeysTransLog> list = cardKeysTransLogService.getCardKeysTransLogList(cardKeysTransLog);
                // 出款卡密订单能否执行代付逻辑标识
                boolean canExecuteTask = false;

                if (!CollectionUtils.isEmpty(list)) {
                    // 根据卡密查找是否有重复卡密流水记录
                    for (CardKeysTransLog log : list) {
                        cardKeysTransLog.setCardKey(log.getCardKey());
                        // 如果流水卡密为空则是余额直接提现
                        cardKeysTransLog.setTransId(StringUtils.isEmpty(log.getCardKey()) ? BaseConstants.TransType.W40.getCode() : BaseConstants.TransType.W30.getCode());
                        int cardKeysNum = cardKeysTransLogService.getCountCardKeys(cardKeysTransLog);
                        if (cardKeysNum == 1) {
                            canExecuteTask = true;
                        } else {
                            canExecuteTask = false;
                            logger.error("## 当前出款卡密订单[{}]中卡密[{}]有[{}]条记录，跳出所有代付", item.getOrderId(),
                                    log.getCardKey(), cardKeysNum);
                            break;
                        }
                    }
                } else {
                    logger.error("## 更新出款卡密订单[{}]为出款失败：没有找到卡密流水", item.getOrderId());
                }

                if (canExecuteTask) {
                    // 执行代付逻辑
                    es.execute(new ZhongFuPayTask(item));
                }
            });
        } catch (Exception e) {
            logger.error("## CardKeysRetrievingJob执行异常", e);
            es.shutdownNow();
        }
    }

}

