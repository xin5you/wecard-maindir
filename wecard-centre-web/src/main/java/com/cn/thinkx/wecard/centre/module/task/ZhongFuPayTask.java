package com.cn.thinkx.wecard.centre.module.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysTransLog;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.scalper.Scalper;
import com.cn.thinkx.pay.domain.UnifyPayForAnotherVO;
import com.cn.thinkx.pms.base.domain.BaseResp;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BankUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.DataStatEnum;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransType;
import com.cn.thinkx.pms.base.utils.BaseConstants.UserTypeEnum;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysTransLogService;
import com.cn.thinkx.wecard.centre.module.biz.service.PersonInfService;
import com.cn.thinkx.wecard.centre.module.biz.util.CardKeysFactory;
import com.cn.thinkx.wecard.centre.module.biz.util.PersonInfFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * 卡券转让Task<br><br>
 * <p>
 * 功能 [定时任务job缓存线程池执行]<br>
 * 描述 [Task完成卡密交易流水更新（更改转入账户）、核销卡密以及调用代付接口]
 *
 * @author pucker
 */
public class ZhongFuPayTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 卡密交易订单
     */
    private CardKeysOrderInf cardKeysOrderInf;

    /**
     * 薪无忧代付接口请求地址
     */
    private static final String REQUEST_URL = RedisDictProperties.getInstance().getdictValueByCode("ZHONGFU_WITHDRAW_REQUEST_URL");

    /**
     * 黄牛机器人1
     */
    private static final Scalper SCALPER_1 = new Scalper("scalper1", "SCALPER-1", UserTypeEnum.HN_TYPE.getCode());
    /**
     * 黄牛机器人2
     */
    private static final Scalper SCALPER_2 = new Scalper("scalper2", "SCALPER-2", UserTypeEnum.HN_TYPE.getCode());
    /**
     * 黄牛机器人3
     */
    private static final Scalper SCALPER_3 = new Scalper("scalper3", "SCALPER-3", UserTypeEnum.HN_TYPE.getCode());

    private static final List<Scalper> SCALPER_LIST = Arrays.asList(SCALPER_1, SCALPER_2, SCALPER_3);
    private CardKeysOrderInfService cardKeysOrderInfService = CardKeysFactory.getCardKeysOrderInfService();
    private PersonInfService personInfService = PersonInfFactory.getPersonInfService();

    /**
     * 初始化卡券转让Task<br>
     *
     * @param cko 卡券交易订单
     * @see ZhongFuPayTask
     */
    public ZhongFuPayTask(CardKeysOrderInf cko) {
        this.cardKeysOrderInf = cko;
    }

    @Override
    public void run() {
        if (cardKeysOrderInf != null) {
            // 用户信息
            PersonInf personInf = personInfService.getPersonInfByUserId(cardKeysOrderInf.getUserId());
            try {
                // 随机选择黄牛号
                int index = (int) (Math.random() * SCALPER_LIST.size());
                logger.info("黄牛[{}]开始回收卡密交易订单{}", SCALPER_LIST.get(index).getUserName(), cardKeysOrderInf.toString());

                CardKeysTransLogService cardKeysTransLogService = CardKeysFactory.getCardKeysTransLogService();
                CardKeysTransLog vo = new CardKeysTransLog();
                vo.setOrderId(cardKeysOrderInf.getOrderId());
                vo.setTransId(TransType.W30.getCode());
                vo.setDataStat(DataStatEnum.TRUE_STATUS.getCode());
                // 根据订单号查找所有卡密交易流水
                List<CardKeysTransLog> list = cardKeysTransLogService.getCardKeysTransLogList(vo);
                // 卡密交易流水不为空
                if (list != null && list.size() > 0) {
                    list.forEach(item -> {
                        // 设置转入账户(随机黄牛号)
                        item.setTfrInAcctNo(SCALPER_LIST.get(index).getUserId());
                        //设置转出账户（转让的用户userID）
                        item.setTfrOutAcctNo(cardKeysOrderInf.getUserId());
                        // 设置卡密流水交易结果
                        item.setTransResult(BaseConstants.RESPONSE_SUCCESS_CODE);
                        // 更新卡密交易流水失败时，出款扣除失败卡密流水出款金额
                        if (cardKeysTransLogService.updateCardKeysTransLog(item) < 1) {
                            logger.error("## 定时任务Task--->回收待转让卡密失败，卡密交易订单号[{}] 卡密交易流水号[{}]", vo.getOrderId(), item.getTxnPrimaryKey());
                            throw new RuntimeException("## 更新卡密交易流水失败，跳出代付");
                        }
                    });

                    //组装调用中付代付接口参数
                    UnifyPayForAnotherVO payVo = new UnifyPayForAnotherVO();
                    // 代付金额
                    payVo.setPayMoney(cardKeysOrderInf.getPaidAmount());
                    // 订单号
                    payVo.setOrderId(cardKeysOrderInf.getOrderId());
                    // 银行卡号
                    payVo.setBankCard(cardKeysOrderInf.getBankNo());
                    // 用户名称
                    payVo.setName(personInf.getPersonalName());
                    // 银行名称
                    payVo.setBankName(BankUtil.getBankNameByCode(cardKeysOrderInf.getAccountBank()));
                    // 账户类型，1:对私; 2:对公
                    payVo.setAcctType("1");
                    // 解析开户行省市
                    if (cardKeysOrderInf.getAccountBankAddr().contains(",")) {
                        String[] bankAddr = cardKeysOrderInf.getAccountBankAddr().split(",");
                        payVo.setProvince(bankAddr[0]);
                        payVo.setCity(bankAddr[1]);
                    }
                    // 联行号
                    payVo.setCnaps(cardKeysOrderInf.getCnaps());
                    // 证件类型，默认为身份证
                    payVo.setCertType("0");
                    // 身份证号
                    payVo.setCertNumber(personInf.getPersonalCardNo());
                    // 调用代付接口,根据代付接口返回信息更新卡密订单
                    JSONObject paramData = new JSONObject();
                    paramData.put("paramData", JSONArray.toJSONString(payVo));
                    logger.info("代付传参=========>{}", paramData.toString());
                    String rtnStr = HttpClientUtil.sendPost(REQUEST_URL, paramData.toString());
                    logger.info("代付返回=========>{}", rtnStr);

                    // 代付成功后更新卡密交易订单：出款金额置0、订单状态为已转让或者转让失败
                    cardKeysOrderInf.setPaidAmount(null);
                    // 代付后将卡密订单设置为不可继续代付
                    cardKeysOrderInf.setDataStat(DataStatEnum.FALSE_STATUS.getCode());
                    // 代付返回为空
                    if (StringUtil.isNullOrEmpty(rtnStr)) {
                        // 转让受理失败
                        cardKeysOrderInf.setStat(BaseConstants.orderStat.OS31.getCode());
                    } else {
                        BaseResp resp = JSONObject.parseObject(rtnStr, BaseResp.class);
                        if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(resp.getRespCode())) {
                            // 转让受理成功
                            cardKeysOrderInf.setStat(BaseConstants.orderStat.OS33.getCode());
                        } else {
                            // 转让受理失败
                            cardKeysOrderInf.setStat(BaseConstants.orderStat.OS31.getCode());
                        }
                    }
                    cardKeysOrderInfService.updateCardKeysOrderInf(cardKeysOrderInf);
                } else {
                    logger.error("## 定时任务执行线程异常：订单号[{}]卡密交易流水为空", cardKeysOrderInf.getOrderId());
                }
            } catch (Exception e) {
                // 转让失败
                cardKeysOrderInf.setStat(BaseConstants.orderStat.OS31.getCode());
                cardKeysOrderInfService.updateCardKeysOrderInf(cardKeysOrderInf);
                logger.error("## 定时任务执行线程异常", e);
            }
        } else {
            logger.error("## 定时任务执行线程异常：cardKeysOrderInf对象为空");
        }
    }

}
