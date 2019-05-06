package com.cn.thinkx.wecard.api.module.pub.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.pms.base.redis.util.TxnChannelSignatureUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.api.module.accmchnt.model.AccMchntTransLog;
import com.cn.thinkx.wecard.api.module.accmchnt.service.AccMchntTransLogService;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.pub.model.DetailBizInfo;
import com.cn.thinkx.wecard.api.module.pub.service.PublicService;
import com.cn.thinkx.wecard.api.module.trans.model.WxTransLog;
import com.cn.thinkx.wecard.api.module.trans.service.WxTransLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("publicService")
public class PublicServiceImpl implements PublicService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("accMchntTransLogService")
    private AccMchntTransLogService accMchntTransLogService;

    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;

    @Autowired
    @Qualifier("java2TxnBusinessFacade")
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Override
    public String accMchntTrans(WxTransLog log, CtrlSystem cs, DetailBizInfo detail) {
        logger.info("通卡支付封装方法 accMchntTrans 会员卡流水参数[{}]，通卡信息参数[{}]", JSONObject.toJSONString(log), JSONObject.toJSONString(detail));
        TxnResp txnResp = new TxnResp();
        txnResp.setCode(BaseConstants.TXN_TRANS_ERROR);

//		logger.info("**********Wxp 插入通卡流水**********");
        /** 插入通卡流水记录*/
        WxTransLog accLog = new WxTransLog();
        String wxPrimaryKey = wxTransLogService.getPrimaryKey();
        accLog.setWxPrimaryKey(wxPrimaryKey);
        accLog.setTableNum(cs.getCurLogNum());
        accLog.setSettleDate(cs.getSettleDate());
        accLog.setTransSt(0);// 插入时为0，报文返回时更新为1
        accLog.setInsCode(detail.getInsCode());// 客户端传过来的机构code TODO
        accLog.setMchntCode(detail.getMchntCode());
        accLog.setShopCode(detail.getShopCode());
        accLog.setUserInfUserName(log.getUserInfUserName());
        accLog.setTransAmt(log.getTransAmt());// 实际交易金额 插入时候默认与上送金额一致
        accLog.setUploadAmt(log.getTransAmt());// 上送金额
        accLog.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
        accLog.setTermCode(log.getTermCode());// 扫描盒子设备ID
        accLog.setSponsor(BaseConstants.SponsorCode.SPONSOR20.toString());
        accLog.setTransId(TransCode.CW10.getCode());// 交易类型 会员卡支付
        accLog.setTransChnl(ChannelCode.CHANNEL7.toString());
        accLog.setAdditionalInfo(log.getAdditionalInfo());
        try {
            int i = wxTransLogService.insertWxTransLog(accLog);
            if (i != 1) {
                logger.error("## 用户[{}]扫码交易，盒子扫码插入 通卡流水 记录数量不为1", log.getUserInfUserName());
                return JSONObject.toJSONString(txnResp);
            }
        } catch (Exception ex) {
            logger.error("## 盒子扫码插入 通卡流水 异常", ex);
            return JSONObject.toJSONString(txnResp);
        }

//		logger.info("**********Wxp 插入通卡会员卡中间表流水**********");
        /**插入通卡会员卡交易关联表流水记录*/
        AccMchntTransLog accmchnt = new AccMchntTransLog();
        accmchnt.setAccPrimaryKey(accLog.getWxPrimaryKey());
        accmchnt.setMchntPrimaryKey(log.getWxPrimaryKey());
        accmchnt.setTransAmt(accLog.getTransAmt());
        accmchnt.setSettleDate(cs.getSettleDate());
        accmchnt.setUserInf(accLog.getUserInfUserName());
        accmchnt.setMchntCode(log.getMchntCode());
        accmchnt.setShopCode(log.getShopCode());
        accmchnt.setDeviceNo(log.getTermCode());
        accmchnt.setTransSt(0);
        accmchnt.setTransId(accLog.getTransId());
        try {
            int i = accMchntTransLogService.insertAccMchntTransLog(accmchnt);
            if (i != 1) {
                logger.error("## 用户[{}]扫码交易，盒子扫码插入 通卡流水 记录数量不为1", log.getUserInfUserName());
                return JSONObject.toJSONString(txnResp);
            }
        } catch (Exception ex) {
            logger.error("## 盒子扫码插入 通卡流水 异常", ex);
            return JSONObject.toJSONString(txnResp);
        }

//		logger.info("***********Wxp 远程调用消费接口**********");
        /** 远程调用消费接口(通卡消费)*/
        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW10.getCode() + "0");// 交易类型，发送报文时补0
        txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
        txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
        txnBean.setSwtSettleDate(accLog.getSettleDate());// 清算日期
        txnBean.setSwtFlowNo(accLog.getWxPrimaryKey());
        txnBean.setIssChannel(accLog.getInsCode());// 机构渠道号
        txnBean.setInnerMerchantNo(accLog.getMchntCode());// 商户号
        txnBean.setInnerShopNo(accLog.getShopCode());// 门店号
        txnBean.setTxnAmount(accLog.getTransAmt());// 交易金额
        txnBean.setOriTxnAmount(accLog.getUploadAmt());// 原交易金额
        txnBean.setCardNo("U" + accLog.getUserInfUserName());// 卡号 U开头为客户端交易，C开头则为刷卡交易
        txnBean.setChannel(accLog.getTransChnl());// 渠道号
        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
        txnBean.setInnerPosNo(accLog.getTermCode()); // 扫描盒子设备ID
        txnBean.setResv6("1"); // 表示不需要输入密码
        String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
        txnBean.setSignature(signature);
        String json = new String();
        try {
            json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
            txnResp = JSONArray.parseObject(json, TxnResp.class);
            if (txnResp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(txnResp.getCode())) {
                json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);
                txnResp = JSONArray.parseObject(json, TxnResp.class);
            }
        } catch (Exception e) {
            logger.error("## 远程调用消费接口异常，返回json[{}]，通卡支付流水号[{}]", json, accLog.getWxPrimaryKey(), e);
        }
//		logger.info("***********远程调用消费接口, 返回值[{}]**********", txnResp);
        if (txnResp == null) {
            txnResp = new TxnResp();
            logger.error("远程调用消费接口（通卡支付）, 返回值为空");
            return JSONObject.toJSONString(txnResp);
        }

//		logger.info("**********Wxp 更新微信端通卡流水**********");
        /** 更新微信端通卡流水*/
        try {
            int i = wxTransLogService.updateWxTransLog(accLog, txnResp);
            if (i != 1) {
                logger.error("## 盒子扫码支付交易更新 通卡流水 失败，流水号[{}]", accLog.getWxPrimaryKey());
                return JSONObject.toJSONString(txnResp);
            }
        } catch (Exception e) {
            logger.error("## 盒子扫码支付交易更新 通卡流水 异常，流水号[{}]", accLog.getWxPrimaryKey(), e);
        }

//		logger.info("**********Wxp 更新通卡会员卡关联表流水**********");
        /** 更新通卡会员卡关联表流水*/
        try {
            int i = accMchntTransLogService.updateAccMchntTransLog(accmchnt, txnResp);
            if (i != 1) {
                logger.error("## 用户[{}]扫码交易，盒子扫码更新 通卡会员卡交易关联表 流水记录数量不为1", log.getUserInfUserName());
                return JSONObject.toJSONString(txnResp);
            }
        } catch (Exception ex) {
            logger.error("## 盒子扫码更新 通卡会员卡交易关联表 流水异常", ex);
            return JSONObject.toJSONString(txnResp);
        }
        txnResp.setAccWxPrimaryKey(wxPrimaryKey);
        return JSONObject.toJSONString(txnResp);
    }

}
