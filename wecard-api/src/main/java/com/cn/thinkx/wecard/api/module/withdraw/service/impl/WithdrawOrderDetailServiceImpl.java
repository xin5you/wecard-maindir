package com.cn.thinkx.wecard.api.module.withdraw.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.*;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransFeeType;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderStat;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderType;
import com.cn.thinkx.wecard.api.module.pub.model.ChannelUserInf;
import com.cn.thinkx.wecard.api.module.pub.service.ChannelUserInfService;
import com.cn.thinkx.wecard.api.module.welfaremart.model.*;
import com.cn.thinkx.wecard.api.module.welfaremart.service.*;
import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrder;
import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrderDetail;
import com.cn.thinkx.wecard.api.module.withdraw.mapper.WithdrawOrderDetailMapper;
import com.cn.thinkx.wecard.api.module.withdraw.service.WithdrawOrderDetailService;
import com.cn.thinkx.wecard.api.module.withdraw.service.WithdrawOrderService;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.Constants.RespCode;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.Constants.orderNotifyStat;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.Constants.withdrawStat;
import com.cn.thinkx.wecard.api.module.withdraw.suning.vo.Content;
import com.cn.thinkx.wecard.api.module.withdraw.suning.vo.TransferOrders;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("withdrawOrderDetailService")
public class WithdrawOrderDetailServiceImpl implements WithdrawOrderDetailService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WithdrawOrderDetailMapper withdrawOrderDetailMapper;

    @Autowired
    @Qualifier("wechatMQProducerService")
    private WechatMQProducerService wechatMQProducerService;

    @Autowired
    @Qualifier("channelUserInfService")
    private ChannelUserInfService channelUserInfService;

    @Autowired
    @Qualifier("withdrawOrderService")
    private WithdrawOrderService withdrawOrderService;

    @Autowired
    @Qualifier("cardKeysProductService")
    private CardKeysProductService cardKeysProductService;

    @Autowired
    @Qualifier("cardKeysOrderInfService")
    private CardKeysOrderInfService cardKeysOrderInfService;

    @Autowired
    @Qualifier("cardKeysTransLogService")
    private CardKeysTransLogService cardKeysTransLogService;

    @Autowired
    @Qualifier("cardKeysService")
    private CardKeysService cardKeysService;

@Autowired
    @Qualifier("userBankInfService")
    private UserBankInfService userBankInfService;

    @Override
    public String getPrimaryKey() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("id", "");
        withdrawOrderDetailMapper.getPrimaryKey(paramMap);
        String id = (String) paramMap.get("id");
        return id;
    }

    @Override
    public int getCountBySerialNo(String serialNo) {
        return this.withdrawOrderDetailMapper.getCountBySerialNo(serialNo);
    }

    @Override
    public int insertWithdrawOrderDetail(WithdrawOrderDetail withdrawOrderDetail) {
        return this.withdrawOrderDetailMapper.insertWithdrawOrderDetail(withdrawOrderDetail);
    }

    @Override
    public int updateWithdrawOrderDetail(WithdrawOrderDetail withdrawOrderDetail) {
        return this.withdrawOrderDetailMapper.updateWithdrawOrderDetail(withdrawOrderDetail);
    }

    @Override
    public void YFBBatchWithdrawNotify(Content content) {
        List<TransferOrders> transOrders = content.getTransferOrders();
        if (transOrders == null) {
            logger.error("## 代付回调接口--->批次号[{}]的出款明细为空", content.getBatchNo());
            return;
        }
        if (withdrawOrderService.getCountByBatchNo(content.getBatchNo()) < 1) {
            logger.error("## 代付回调接口--->批次号[{}]在出款订单信息中不存在", content.getBatchNo());
            return;
        }
        WithdrawOrder order = withdrawOrderService.getWithdrawOrderById(content.getBatchNo());
        CardKeysOrderInf cko = cardKeysOrderInfService.getCardKeysOrderByOrderId(order.getPaidId());
        if (cko == null) {
            logger.error("## 代付回调接口--->查询订单号[{}]CardKeysOrderInf的信息为空", order.getPaidId());
            return;
        }

        order.setTotalFee(String.valueOf(content.getPoundage()));
        order.setStat(content.getStatus());
        order.setPaidRespDesc(content.getErrorMsg());
        order.setSuccessAmount(String.valueOf(content.getSuccessAmount()));
        order.setSuccessNum(String.valueOf(content.getSuccessNum()));
        order.setSuccessFee(String.valueOf(content.getPoundage()));
        order.setFailAmount(String.valueOf(content.getFailAmount()));
        order.setFailNum(String.valueOf(content.getFailNum()));
        if (withdrawOrderService.updateWithdrawOrder(order) < 1) {
            logger.error("## 代付回调接口--->更新出款订单信息失败，批次号[{}]", content.getBatchNo());
            return;
        }

        WithdrawOrderDetail orderDetail = new WithdrawOrderDetail();
        transOrders.forEach(t -> {

            if (orderNotifyStat.stat0.getValue().equals(String.valueOf(t.isSuccess()))) {
                logger.info("代付回调接口--->代付失败");
                cko.setStat(BaseConstants.orderStat.OS31.getCode());
            } else if (orderNotifyStat.stat1.getValue().equals(String.valueOf(t.isSuccess()))) {
                logger.info("代付回调接口--->代付成功");
                cko.setStat(BaseConstants.orderStat.OS32.getCode());
                cko.setPaidAmount("0");
            } else if (orderNotifyStat.stat2.getValue().equals(String.valueOf(t.isSuccess()))) {
                logger.info("代付回调接口--->代付处理中");
                cko.setStat(BaseConstants.orderStat.OS33.getCode());
            } else {
                logger.info("代付回调接口--->其他[{}]", t.isSuccess());
                return;
            }

            logger.info("代付回调接口--->更新订单号[{}]的状态为[{}]", cko.getOrderId(), cko.getStat());
            if (cardKeysOrderInfService.updateCardKeysOrderInf(cko) < 1) {
                logger.error("## 代付回调接口--->更新CardKeysOrderInf订单号[{}]的订单信息失败", cko.getOrderId());
                return;
            }

            String id = getPrimaryKey();
            orderDetail.setDetailId(id);
            orderDetail.setOrderId(order.getOrderId());
            orderDetail.setSerialNo(t.getSerialNo());
            orderDetail.setReceiverName(t.getReceiverName());
            orderDetail.setReceiverCardNo(t.getReceiverCardNo());
            orderDetail.setReceiverType(t.getReceiverType());
            String bankDetail = BankUtil.getCardDetail(orderDetail.getReceiverCardNo());
            JSONObject bankJson = JSON.parseObject(bankDetail);
            if (StringUtil.equals(bankJson.getString("validated"), "true")) {
                orderDetail.setBankType(bankJson.getString("cardType"));
            } else {
                logger.error("## 代付回调接口--->根据银行卡号[{}]查询卡类型信息[{}]错误", orderDetail.getReceiverCardNo(), bankJson);
            }
            orderDetail.setBankName(t.getBankName());
            orderDetail.setBankCode(t.getBankCode());
            orderDetail.setPayeeBankLinesNo(t.getPayeeBankLinesNo());
            orderDetail.setBankProvince(t.getBankProvince());
            orderDetail.setBankCity(t.getBankCity());
            orderDetail.setDmsSerialNo(String.valueOf(t.getId()));
            orderDetail.setAmount(new Long(t.getAmount()).intValue());
            orderDetail.setFee(new Long(t.getPoundage()).intValue());
            orderDetail.setRespCode(RespCode.findByValue(String.valueOf(t.isSuccess())).getCode());
            orderDetail.setRespMsg(t.getErrMessage());
            if (StringUtil.isNullOrEmpty(t.getPayTime())) {
                orderDetail.setPayTime(DateUtil.getCurrentDateTimeStr());
            } else {
                orderDetail.setPayTime(t.getPayTime());
            }
            if (insertWithdrawOrderDetail(orderDetail) < 1) {
                logger.error("## 代付回调接口--->新增出款订单明细信息失败，出款流水号[{}]", orderDetail.getSerialNo());
                return;
            }

            if (orderStat.OS31.getCode().equals(cko.getStat())) {
                boolean isUpdateUserWithdraw = YFBBatchWithdrawNotifyUpdateUserCardKey(cko.getUserId(), cko.getOrderId());
                if (!isUpdateUserWithdraw) {
                    logger.error("## 代付回调接口--->代付失败，处理用户[{}]卡密和卡密订单及卡密交易流水等信息失败", cko.getUserId());
                    return;
                }
            }
        });
    }

    @Override
    public void zfPayNotify(String orderNumber, String inTradeOrderNo, String payMoney) {
        if (withdrawOrderService.getCountByBatchNo(orderNumber) < 1) {
            logger.error("## 代付回调接口--->批次号[{}]在出款订单信息中不存在", orderNumber);
            return;
        }
        WithdrawOrder order = withdrawOrderService.getWithdrawOrderById(orderNumber);
        CardKeysOrderInf cko = cardKeysOrderInfService.getCardKeysOrderByOrderId(order.getPaidId());
        if (cko == null) {
            logger.error("## 代付回调接口--->查询订单：{} CardKeysOrderInf的信息为空", JSONObject.toJSONString(order));
            return;
        }

        order.setStat("00");
        order.setPaidRespDesc("代付成功");
        order.setSuccessAmount(payMoney);
        order.setSuccessNum("1");
        order.setSuccessFee("0");
        order.setFailAmount("0");
        order.setFailNum("0");
        if (withdrawOrderService.updateWithdrawOrder(order) < 1) {
            logger.error("## 代付回调接口--->更新出款订单信息失败，批次号[{}]", inTradeOrderNo);
            return;
        }

        WithdrawOrderDetail orderDetail = new WithdrawOrderDetail();
        if (cardKeysOrderInfService.updateCardKeysOrderInf(cko) < 1) {
            logger.error("## 代付回调接口--->更新CardKeysOrderInf：{}失败", JSONObject.toJSONString(cko));
            return;
        }

        // 获取用户银行卡信息
        UserBankInf userBankInf = userBankInfService.getUserBankInfByBankNo(cko.getBankNo());

        String id = getPrimaryKey();
        orderDetail.setDetailId(id);
        orderDetail.setOrderId(order.getOrderId());
        orderDetail.setSerialNo(order.getPaidId());
        orderDetail.setReceiverName(userBankInf.getUserName());
        orderDetail.setReceiverCardNo(cko.getBankNo());
        orderDetail.setReceiverType("PERSON");
        orderDetail.setBankType(userBankInf.getBankType());
        orderDetail.setBankName(userBankInf.getBankName());
        orderDetail.setBankCode(userBankInf.getAccountBank());
        orderDetail.setAmount(new Long(payMoney).intValue());
        orderDetail.setRespCode("0");
        orderDetail.setPayTime(DateUtil.getCurrentDateTimeStr());
        if (insertWithdrawOrderDetail(orderDetail) < 1) {
            logger.error("## 代付回调接口--->新增出款订单明细信息失败，出款流水：{}", JSONObject.toJSONString(orderDetail));
            return;
        }

        if (orderStat.OS31.getCode().equals(cko.getStat())) {
            boolean isUpdateUserWithdraw = YFBBatchWithdrawNotifyUpdateUserCardKey(cko.getUserId(), cko.getOrderId());
            if (!isUpdateUserWithdraw) {
                logger.error("## 代付回调接口--->代付失败，处理用户[{}]卡密和卡密订单及卡密交易流水等信息失败", cko.getUserId());
                return;
            }
        }
    }

    @Override
    public void YFBBatchWithdrawSendMsg(Content content) throws Exception {
        if (withdrawStat.S07.getCode().equals(content.getStatus())) {
            List<TransferOrders> transOrders = content.getTransferOrders();
            if (transOrders == null) {
                logger.error("## 代付回调发送模板消息接口--->批次号[{}]的出款明细为空", content.getBatchNo());
                return;
            }

            WithdrawOrder order = withdrawOrderService.getWithdrawOrderById(content.getBatchNo());
            if (order == null) {
                logger.error("## 代付回调发送模板消息接口--->批次号[{}]在出款订单信息中不存在", content.getBatchNo());
            } else {
                transOrders.forEach(t -> {
                    String isSuccess = String.valueOf(t.isSuccess());
                    if (orderNotifyStat.stat1.getValue().equals(String.valueOf(t.isSuccess()))) {
                        ChannelUserInf chnlUser = new ChannelUserInf();
                        chnlUser.setUserId(order.getUserId());
                        chnlUser.setChannelCode(ChannelCode.CHANNEL1.toString());
                        String openId = channelUserInfService.getExternalId(chnlUser);
                        CardKeysOrderInf cko = cardKeysOrderInfService.getCardKeysOrderByOrderId(order.getPaidId());
                        if (cko != null) {
                            CardKeysProduct product = new CardKeysProduct();
                            product.setProductCode(cko.getProductCode());
                            CardKeysProduct ckp = cardKeysProductService.getCardKeysProductByCode(product);
                            if (ckp != null) {
                                StringBuffer productDesc = new StringBuffer();
                                productDesc.append(new Double(NumberUtils.RMBCentToYuan(ckp.getOrgAmount())).intValue()).append(ckp.getProductUnit()).append(TransFeeType.findByCode(ckp.getProductType()).getValue());
                                wechatMQProducerService.sendTemplateMsg(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"), openId, "WX_TEMPLATE_ID_5", null,
                                        WXTemplateUtil.setResellData(order.getPaidId(), NumberUtils.RMBCentToYuan(String.valueOf(content.getSuccessAmount())), DateUtil.getCurrentDateTimeStr(), NumberUtils.hideCardNo(t.getReceiverCardNo()), cko.getNum(), productDesc.toString()));
                            }
                        }
                    } else if (orderNotifyStat.stat2.getValue().equals(String.valueOf(t.isSuccess()))) {
                        logger.info("代付回调发送模板消息接口--->代付状态为[{}]，代付处理中", isSuccess);
                    } else if (orderNotifyStat.stat0.getValue().equals(String.valueOf(t.isSuccess()))) {
                        logger.info("代付回调发送模板消息接口--->代付状态为[{}]，代付失败", isSuccess);
                    }
                });
            }
        }
    }

    @Override
    public boolean YFBBatchWithdrawNotifyUpdateUserCardKey(String userId, String orderId) {
        if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(orderId)) {
            logger.error("## 代付回调接口---> 更新用户[{}]卡密订单[{}]相关信息失败，请求参数为空", userId, orderId);
            return false;
        }

        CardKeysOrderInf cko = new CardKeysOrderInf();
        cko.setOrderId(orderId);
        cko.setUserId(userId);
        cko.setStat(orderStat.OS31.getCode());
        cko.setType(orderType.O3.getCode());
        cko.setDataStat("0");
        CardKeysOrderInf order = cardKeysOrderInfService.getOrderFailByUserIdAndOrderId(cko);
        if (order == null) {
            logger.error("## 代付回调接口---> 根据用户[{}]和订单[{}]查询卡密订单代付失败信息为空", userId, orderId);
            return false;
        } else {
            order.setStat(orderStat.OS35.getCode());
        }
        CardKeysTransLog ckt = new CardKeysTransLog();
        ckt.setOrderId(orderId);
        ckt.setDataStat("0");
        List<CardKeysTransLog> cktList = cardKeysTransLogService.getCardKeysTransLogByOrderId(ckt);
        if (cktList.size() < 1) {
            logger.error("## 代付回调接口---> 根据用户[{}]和订单[{}]查询未处理的卡密交易流水信息为空", userId, orderId);
            return false;
        }
        List<CardKeys> cardList = new ArrayList<>();
        List<CardKeysTransLog> transLogList = new ArrayList<>();
        for (CardKeysTransLog log : cktList) {
            //卡密流水信息
            CardKeysTransLog transLog = new CardKeysTransLog();
            transLog.setTxnPrimaryKey(log.getTxnPrimaryKey());
            transLog.setCardKey(log.getCardKey());
            transLog.setTransResult(BaseConstants.TXN_TRANS_ERROR);
            transLog.setDataStat("1");
            transLogList.add(transLog);
            //卡密信息
            CardKeys ck = new CardKeys();
            ck.setCardKey(log.getCardKey());
            ck.setDataStat("1");
            CardKeys card = cardKeysService.getCardKeysByCardKey(ck);
            if (card != null) {
                cardList.add(card);
            } else {
                logger.error("## 代付回调接口---> 查询用户[{}]已核销的卡密[{}]信息为空", userId, log.getCardKey());
            }
        }
        if (cardList.size() < 1 || cktList.size() != cardList.size()) {
            logger.error("## 代付回调接口---> 查询用户[{}]已核销的卡密数量和卡密交易流水数量不一致", userId);
            return false;
        }

        boolean isUpdateUserCardKey = false;
        try {
            isUpdateUserCardKey = cardKeysOrderInfService.updateUserNegotiation(order, transLogList);
        } catch (Exception e) {
            logger.error("## 代付回调接口---> 处理用户代付失败订单异常{}", e);
        }

        return isUpdateUserCardKey;
    }

}
