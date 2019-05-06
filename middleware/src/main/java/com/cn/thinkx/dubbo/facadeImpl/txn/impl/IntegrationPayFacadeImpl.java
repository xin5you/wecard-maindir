package com.cn.thinkx.dubbo.facadeImpl.txn.impl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.biz.core.service.CtrlSystemService;
import com.cn.thinkx.biz.translog.model.IntfaceTransLog;
import com.cn.thinkx.biz.translog.service.IntfaceTransLogService;
import com.cn.thinkx.dubbo.entity.BaseTransResp;
import com.cn.thinkx.dubbo.facadeImpl.txn.valid.IntegrationPayValidUtil;
import com.cn.thinkx.facade.bean.IntegrationPayRequest;
import com.cn.thinkx.facade.service.IntegrationPayFacade;
import com.cn.thinkx.integrationpay.base.entity.IntegrationPayReq;
import com.cn.thinkx.integrationpay.base.entity.IntegrationPayResp;
import com.cn.thinkx.pms.base.redis.util.IntSignUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.ChnlNoMethod;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service("integrationPayFacade")
public class IntegrationPayFacadeImpl implements IntegrationPayFacade {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    @Qualifier("intfaceTransLogService")
    private IntfaceTransLogService intfaceTransLogService;

    /**
     * 聚合支付消费接口
     *
     * @param req
     * @return
     */
    public String payMentTransactionITF(IntegrationPayRequest req) {
        logger.info("聚合支付消费接口请求参数：[{}]", JSONArray.toJSONString(req));
        BaseTransResp resp = new BaseTransResp();
        resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);

        /****参数校验**/
        if (IntegrationPayValidUtil.payMentTransactionITFValid(req, resp))
            return JSONArray.toJSONString(resp);

        try {
            CtrlSystem cs = ctrlSystemService.getCtrlSystem();//TODO 得到日切信息
            if (cs == null) {
                logger.error("## 聚合支付消费--->日切信息为空");
                return JSONArray.toJSONString(resp);
            }

            // 检查终端流水号swtFlowNo是否有重复记录
            IntfaceTransLog log = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), req.getTermSwtFlowNo());
            if (log != null) {
                logger.error("## 聚合支付消费--->终端流水号[{}]已有消费流水记录", req.getTermSwtFlowNo());
                return JSONArray.toJSONString(resp);
            } else {
                log = new IntfaceTransLog();
            }

            ChnlNoMethod chnlNo = null; //根据渠道号来调用不同的支付类（申鑫，收钱吧）
            // 日切状态为允许时，插入中间层流水
            if (BaseIntegrationPayConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
                String id = intfaceTransLogService.getPrimaryKey();
                log.setInterfacePrimaryKey(id);
                log.setTableNum(cs.getCurLogNum());//得到当前可以进行操作的流水表号
                log.setMchntCode(req.getMchntNo());
                log.setShopCode(req.getShopNo());
                log.setSettleDate(cs.getSettleDate());//交易日期
                log.setDmsRelatedKey(req.getTermSwtFlowNo());//客户端传过来的流水号
                log.setTransId(req.getTransId());//交易类型
                log.setTransSt(0);//插入时为0，报文返回时更新为1
                log.setUserInfUserName(req.getAuthInfo());//条码信息
                log.setTransAmt(req.getTransAmt());//规则引擎处理后的实际交易金额
                log.setUploadAmt(req.getTransAmt());//上送金额
                log.setTransCurrCd(BaseIntegrationPayConstants.TRANS_CURR_CD);
                log.setTransChnl(req.getTransChnlNo());//客户端传过来的渠道号
                log.setInsCode(req.getInsNo());//机构号
                log.setTermCode(req.getTermNo());//终端号（设备号）
                log.setRemarks(req.getTermChnlNo());
                int i = intfaceTransLogService.insertIntfaceTransLog(log);//中间层插入流水
                if (i != 1) {
                    logger.error("## 聚合支付消费--->insertIntfaceTransLog中间层插入流水记录数量不为1");
                    return JSONArray.toJSONString(resp);
                }

                //根据渠道号调用第三方的消费接口
                chnlNo = ChnlNoMethod.findChannelCodeByCode(req.getTermChnlNo());
                if (StringUtil.isNullOrEmpty(chnlNo)) {
                    logger.error("##  聚合支付消费--->渠道号[{}]信息不存在", req.getTermChnlNo());
                    return JSONArray.toJSONString(resp);
                }
                IntegrationPayResp payResp = new IntegrationPayResp();
                Class<?> object = Class.forName(chnlNo.getClassName());
                Method method = null;
                try {
                    method = object.getMethod(BaseIntegrationPayConstants.PAYMENT, IntegrationPayRequest.class);
                    Object obj = object.newInstance();
                    payResp = (IntegrationPayResp) method.invoke(obj, new Object[]{req});
                } catch (Exception e) {
                    logger.error("## 聚合支付消费--->根据通道号[{}]，通过反射找到类方法[{}]出错，终端流水号为[{}]", chnlNo.getChannelCode(), "payment", req.getTermSwtFlowNo(), e);
                    return JSONArray.toJSONString(resp);
                }
                logger.info("聚合支付消费--->第三方[{}]返回的交易应答信息[{}]，终端流水号为[{}]", chnlNo.getChannelCode(), JSONArray.toJSONString(payResp), req.getTermSwtFlowNo());

                //第三方没有应答
                if (payResp == null) {
                    logger.error("## 聚合支付消费--->第三方[{}]没有返回交易应答信息，终端流水号为[{}]", chnlNo.getChannelCode(), req.getTermSwtFlowNo());
                    resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);
                    return JSONArray.toJSONString(resp);
                }
                if (!BaseIntegrationPayConstants.SX_PAYMENT_SUCCESS.equals(payResp.getCode())) {
                    logger.error("## 聚合消费--->交易失败，第三方[{}]返回码[{}]", chnlNo.getChannelCode(), payResp.getCode());
                    resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);
                    return JSONArray.toJSONString(resp);
                }
                log.setRespCode(BaseIntegrationPayConstants.ITFRespCode.CODE00.getCode());
                log.setOrgInterfacePrimaryKey(payResp.getSwtFlowNo());
                log.setAdditionalInfo(payResp.getRemarks());
                log.setTransSt(1);// 插入时为0，报文返回时更新为1

                int u = 0;
                try {
                    u = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
                } catch (Exception e) {
                    logger.error("## 聚合支付消费--->更新消费交易流水异常，终端交易流水号为[{}]", payResp.getTermSwtFlowNo());
                    return paymentException(cs.getCurLogNum(), payResp, req, log);
                }
                if (u != 1) {
                    logger.error("## 聚合支付消费--->updateIntfaceTransLog更新流水失败，itf层流水号为[{}]", log.getInterfacePrimaryKey());
                    return paymentException(cs.getCurLogNum(), payResp, req, log);
                }

                resp.setCode(BaseIntegrationPayConstants.ITFRespCode.CODE00.getCode());
                resp.setInfo(BaseIntegrationPayConstants.ITFRespCode.CODE00.getValue());
                resp.setSwtFlowNo(payResp.getSwtFlowNo());
                resp.setTermSwtFlowNo(payResp.getTermSwtFlowNo());
                resp.setTransDate(DateUtil.getCurrentDateTimeStr());
                resp.setRemarks(payResp.getRemarks());
                resp.setTransAmt(req.getTransAmt());
                resp.setPreferentialAmt(payResp.getPreferentialAmt());
                resp.setTermOrderNo(payResp.getTermOrderNo());
                resp.setPaymentType(payResp.getPaymentType());
                resp.setTimestamp(System.currentTimeMillis());
                resp.setSign(IntSignUtil.genSign(resp, RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")));
            } else {
                logger.error("## 聚合支付消费--->日切信息交易允许状态：日切中");
            }
        } catch (Exception e) {
            logger.error("## 聚合支付消费异常--->", e);
        }
        logger.info("聚合支付消费--->payMentTransactionITF返回参数[{}]", JSONArray.toJSONString(resp));
        return JSONArray.toJSONString(resp);
    }

    /**
     * 聚合支付退款接口
     *
     * @param req
     * @return
     */
    public String refundTransactionITF(IntegrationPayRequest req) {
        logger.info("聚合支付退款接口请求参数：[{}]", JSONArray.toJSONString(req));
        BaseTransResp resp = new BaseTransResp();
        resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);

        /****参数校验**/
        if (IntegrationPayValidUtil.refundTransactionITFValid(req, resp))
            return JSONArray.toJSONString(resp);

        //退款请求参数封装
        IntegrationPayReq refundReq = IntegrationRefundReq(req);
        try {
            CtrlSystem cs = ctrlSystemService.getCtrlSystem();//TODO 得到日切信息
            if (cs == null) {
                logger.error("## 聚合支付退款--->日切信息为空");
                return JSONArray.toJSONString(resp);
            }

            //检查终端流水号swtFlowNo是否有重复记录
            IntfaceTransLog log = null;
            log = intfaceTransLogService.getIntfaceTransLogByRelatedKey(cs.getCurLogNum(), req.getTermSwtFlowNo());
            if (log != null) {
                logger.error("## 聚合支付退款--->终端流水号[{}]在itf层TB_INTFACE_TRANS_LOG{}已有退款流水记录", req.getTermSwtFlowNo(), cs.getCurLogNum());
                return JSONArray.toJSONString(resp);
            } else {
                log = intfaceTransLogService.getIntfaceTransLogHisByDmsRelatedKey(req.getTermSwtFlowNo());
                if (log != null) {
                    logger.error("## 聚合支付退款--->终端流水号[{}]在itf层TB_INTFACE_TRANS_LOG_HIS已有退款流水记录", req.getTermSwtFlowNo());
                    return JSONArray.toJSONString(resp);
                } else {
                    log = new IntfaceTransLog();
                }
            }

            //检查当前流水表原交易流水是否存在
            IntfaceTransLog oriLog = intfaceTransLogService.getIntfaceTransLogByDmsRelatedKey(cs.getCurLogNum(), req.getTermOrderNO());
            boolean isCurrentLog = true;//是否为当前交易记录
            if (oriLog == null) {
                isCurrentLog = false;
                oriLog = intfaceTransLogService.getIntfaceTransLogHisByDmsRelatedKey(req.getTermOrderNO());//检查历史流水表原交易流水是否存在
                if (oriLog == null) {
                    logger.error("## 聚合支付退款--->终端原交易流水号[{}]在itf层无流水记录", req.getTermOrderNO());
                    return JSONArray.toJSONString(resp);
                }
            }

            // 检查原交易流水返回码是否为成功00
            if (!ITFRespCode.CODE00.getCode().equals(oriLog.getRespCode())) {
                logger.error("## 聚合支付退款--->终端原交易流水号[{}]respCode不等于00", req.getTermSwtFlowNo());
                return JSONArray.toJSONString(resp);
            }

            // 日切状态为允许时，插入中间层流水
            if (BaseIntegrationPayConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
                String id = intfaceTransLogService.getPrimaryKey();
                log.setInterfacePrimaryKey(id);
                log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
                log.setMchntCode(req.getMchntNo());
                log.setShopCode(req.getShopNo());
                log.setSettleDate(cs.getSettleDate());// 交易日期
                log.setDmsRelatedKey(req.getTermSwtFlowNo());// 客户端传过来的流水号
                log.setOrgDmsRelatedKey(req.getTermOrderNO());//客户端传过来的原交易流水号
                log.setRemarks(req.getTermChnlNo());
                log.setTransId(req.getTransId());// 交易类型
                log.setTransSt(0);// 插入时为0，报文返回时更新为1
                log.setUserInfUserName(req.getAuthInfo());
                log.setTransAmt(req.getTransAmt());// 规则引擎处理后的实际交易金额
                log.setUploadAmt(req.getTransAmt());// 上送金额
                log.setTransCurrCd(BaseIntegrationPayConstants.TRANS_CURR_CD);
                log.setTransChnl(req.getTransChnlNo());// 客户端传过来的渠道号
                log.setInsCode(req.getInsNo());//机构号
                log.setTermCode(req.getTermNo());//终端号（设备号）
                int i = intfaceTransLogService.insertIntfaceTransLog(log);// 中间层插入流水
                if (i != 1) {
                    logger.error("## 聚合支付退款--->insertIntfaceTransLog中间层插入退款流水记录数量不为1");
                    return JSONArray.toJSONString(resp);
                }

                //查询出新增退款流水的创建时间
                IntfaceTransLog logtime = intfaceTransLogService.getIntfaceTransLogBylogId(cs.getCurLogNum(), log.getInterfacePrimaryKey());
                refundReq.setReqDate(DateUtil.getStringFromDate(logtime.getCreateTime(), DateUtil.FORMAT_YYYYMMDD));
                refundReq.setReqTime(DateUtil.getStringFromDate(logtime.getCreateTime(), DateUtil.FORMAT_HHMMSS));

                refundReq.setSwtFlowNo(oriLog.getOrgInterfacePrimaryKey());
                refundReq.setOrderNo(oriLog.getAdditionalInfo());
                refundReq.setSettleDate(oriLog.getSettleDate());

                //根据渠道号来调用不同的支付类（申鑫，收钱吧）
                ChnlNoMethod chnlNo = ChnlNoMethod.findChannelCodeByCode(req.getTermChnlNo());
                if (StringUtil.isNullOrEmpty(chnlNo)) {
                    logger.error("## 聚合支付退款--->通道号[{}]信息不存在", req.getTermChnlNo());
                    return JSONArray.toJSONString(resp);
                }
                //logger.info("聚合支付退款交易--->通道号[{}]信息为[{}]", req.getTermChnlNo(), JSONArray.toJSONString(chnlNo));
                IntegrationPayResp refundResp = new IntegrationPayResp();
                try {
                    Class<?> object = Class.forName(chnlNo.getClassName());
                    Method method = object.getMethod(BaseIntegrationPayConstants.REFUND, IntegrationPayReq.class);
                    Object obj = object.newInstance();
                    refundResp = (IntegrationPayResp) method.invoke(obj, new Object[]{refundReq});
                } catch (Exception e) {
                    logger.error("## 聚合支付退款--->根据通道号[{}]，通过反射找到类出错，终端流水[{}]", chnlNo.getChannelCode(), req.getTermSwtFlowNo(), e);
                    return JSONArray.toJSONString(resp);
                }

                //第三方没有应答
                if (refundResp == null) {
                    logger.error("## 聚合支付退款--->第三方[{}]没有返回交易应答消息，终端流水号为[{}]", chnlNo.getChannelCode(), req.getTermSwtFlowNo());
                    return JSONArray.toJSONString(resp);
                }
                logger.info("聚合支付退款--->第三方[{}]返回的退款交易应答信息[{}]", chnlNo.getChannelCode(), JSONArray.toJSONString(refundResp));
                if (!BaseIntegrationPayConstants.SX_REFUND_SUCCESS.equals(refundResp.getCode())) {
                    logger.error("## 聚合支付退款--->交易失败，第三方[{}]返回码[{}]", chnlNo.getChannelCode(), refundResp.getCode());
                    return JSONArray.toJSONString(resp);
                }
                log.setRespCode(ITFRespCode.CODE00.getCode());
                log.setOrgInterfacePrimaryKey(refundResp.getTraceId());
                log.setAdditionalInfo(refundResp.getRemarks());
                log.setTransSt(1);//插入时为0，报文返回时更新为1
                int u = 0;
                try {
                    u = intfaceTransLogService.updateIntfaceTransLog(log);//更新流水
                } catch (Exception e) {
                    logger.error("## 聚合支付退款--->更新退款流水异常，itf层流水ID[{}]", log.getInterfacePrimaryKey());
                    return refundException(cs.getCurLogNum(), refundResp, refundReq, log, oriLog, isCurrentLog);
                }
                if (u != 1) {
                    logger.error("## 聚合支付退款--->更新退款流水失败， itf层流水ID[{}]", log.getInterfacePrimaryKey());
                    return refundException(cs.getCurLogNum(), refundResp, refundReq, log, oriLog, isCurrentLog);
                }

                oriLog.setTransSt(3);//更新原交易流水状态为3：交易撤销
                int u2 = 0;
                try {
                    if (isCurrentLog) {
                        oriLog.setTableNum(cs.getCurLogNum());//得到当前可以进行操作的流水表号
                        u2 = intfaceTransLogService.updateIntfaceTransLog(oriLog);
                    } else {
                        u2 = intfaceTransLogService.updateIntfaceHisTransLog(oriLog);
                    }
                } catch (Exception e) {
                    logger.error("## 聚合支付退款--->更新原交易流水异常，itf层流水ID[{}]", oriLog.getInterfacePrimaryKey());
                    return refundException(cs.getCurLogNum(), refundResp, refundReq, log, oriLog, isCurrentLog);
                }
                if (u2 != 1) {
                    logger.info("## 聚合支付退款--->更新原交易流水失败，itf层流水ID[{}]", oriLog.getInterfacePrimaryKey());
                    return refundException(cs.getCurLogNum(), refundResp, refundReq, log, oriLog, isCurrentLog);
                }

                resp.setCode(ITFRespCode.CODE00.getCode());
                resp.setInfo(ITFRespCode.CODE00.getValue());
                resp.setSwtFlowNo(refundResp.getSwtFlowNo());
                resp.setTermSwtFlowNo(refundResp.getTermSwtFlowNo());
                resp.setTransDate(DateUtil.getCurrentDateTimeStr());
                resp.setRemarks(refundResp.getRemarks());
                resp.setTransAmt(req.getTransAmt());
                resp.setPreferentialAmt(refundResp.getPreferentialAmt());
                resp.setTermOrderNo(refundResp.getTermOrderNo());
                resp.setPaymentType(refundResp.getPaymentType());
                resp.setTimestamp(System.currentTimeMillis());
                resp.setSign(IntSignUtil.genSign(resp, RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")));
            } else {
                logger.error("## 聚合支付退款--->日切信息交易允许状态：日切中");
            }
        } catch (Exception e) {
            logger.error("## 聚合支付退款异常--->", e);
        }
        logger.info("聚合支付退款--->refundTransactionITF返回参数[{}]", JSONArray.toJSONString(resp));
        return JSONArray.toJSONString(resp);
    }

    /**
     * 查询接口
     */
    public String queryTransactionITF(IntegrationPayRequest req) {
        BaseTransResp resp = new BaseTransResp();
        resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);
        logger.info("聚合支付查询接口--->请求参数[{}]", JSONArray.toJSONString(req));

        try {
            CtrlSystem cs = ctrlSystemService.getCtrlSystem();//TODO 得到日切信息
            if (cs == null) {
                logger.error("## 聚合支付查询--->日切信息为空");
                return null;
            }

            IntfaceTransLog orgLog = intfaceTransLogService.getIntfaceTransLogByDmsRelatedKey(cs.getCurLogNum(), req.getTermSwtFlowNo());
            if (orgLog == null) {
                orgLog = intfaceTransLogService.getIntfaceTransLogHisByDmsRelatedKey(req.getTermSwtFlowNo());
                if (orgLog == null) {
                    orgLog = intfaceTransLogService.getIntfaceTransLogByOrgDmsRelatedKey(cs.getCurLogNum(), req.getTermSwtFlowNo());
                    if (orgLog == null)
                        orgLog = intfaceTransLogService.getIntfaceTransLogHisByOrgDmsRelatedKey(req.getTermSwtFlowNo());
                }
            }
            if (StringUtil.isNullOrEmpty(orgLog)) {
                logger.error("## 聚合支付查询--->交易流水不存在，交易流水号[{}]", orgLog.getDmsRelatedKey());
                return JSONArray.toJSONString(resp);
            }
            if (ITFRespCode.CODE00.getCode().equals(orgLog.getRespCode())) {
                IntegrationPayReq queryReq = new IntegrationPayReq();
                queryReq.setPaymentType(req.getPaymentType());
                queryReq.setSwtFlowNo(orgLog.getOrgInterfacePrimaryKey());
                queryReq.setTermChnlNo(orgLog.getRemarks());
                queryReq.setTransId(TransCode.CW77.getCode());
                ChnlNoMethod chnlNo = ChnlNoMethod.findChannelCodeByCode(orgLog.getRemarks());
                if (StringUtil.isNullOrEmpty(chnlNo)) {
                    logger.error("## 聚合支付查询--->第三方交易查询通道号[{}]信息不存在", req.getTermChnlNo());
                    return JSONArray.toJSONString(resp);
                }
                //logger.info("第三方交易查询--->通道号[{}]信息为[{}]", req.getTermChnlNo(), JSONArray.toJSONString(queryReq));
                IntegrationPayResp resultResp = new IntegrationPayResp();
                try {
                    Class<?> object = Class.forName(chnlNo.getClassName());
                    Object obj = object.newInstance();
                    Method method = object.getDeclaredMethod(BaseIntegrationPayConstants.SEARCH, IntegrationPayReq.class);
                    resultResp = (IntegrationPayResp) method.invoke(obj, new Object[]{queryReq});
                } catch (Exception e) {
                    logger.error("## 聚合支付查询--->根据通道号[{}]，通过反射找到类出错，终端流水[{}]", chnlNo.getChannelCode(), req.getTermSwtFlowNo(), e);
                    return JSONArray.toJSONString(resp);
                }

                //第三方没有应答
                if (resultResp == null) {
                    logger.error("## 聚合支付查询--->第三方[{}]没有返回交易应答消息，终端流水号为[{}]", chnlNo.getChannelCode(), req.getTermSwtFlowNo());
                    resp.setInfo(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_INFO);
                    return JSONArray.toJSONString(resultResp);
                }
                logger.info("聚合支付查询--->第三方[{}]返回应答信息[{}]", chnlNo.getChannelCode(), JSONArray.toJSONString(resultResp));
                if (BaseIntegrationPayConstants.SX_PAYMENT_TYPE.equals(resultResp.getPayStatus())) {
                    resp.setCode(ITFRespCode.CODE00.getCode());
                    resp.setInfo(ITFRespCode.CODE00.getValue());
                    resp.setTermSwtFlowNo(orgLog.getOrgInterfacePrimaryKey());
                } else {
                    logger.error("## 聚合支付查询--->查询失败，第三方[{}]返回码[{}],返回状态[{}]", chnlNo.getChannelCode(), resultResp.getRespCode(), resultResp.getPayStatus());
                    resp.setInfo(resultResp.getRespMsg());
                }
            }
        } catch (Exception e) {
            logger.error("## 聚合支付查询异常--->", e);
        }
        return JSONArray.toJSONString(resp);
    }

    /**
     * 交易发生异常封装方法
     *
     * @param curLogNum
     * @param payResp
     * @param req
     * @param log
     * @return
     */
    private String paymentException(String curLogNum, IntegrationPayResp payResp, IntegrationPayRequest req, IntfaceTransLog log) {
        BaseTransResp resp = new BaseTransResp();
        IntegrationPayReq refundReq = new IntegrationPayReq();
        IntegrationPayResp refundResp = new IntegrationPayResp();

        resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);

        IntegrationPayReq queryReq = new IntegrationPayReq();
        queryReq.setSwtFlowNo(payResp.getSwtFlowNo());
        queryReq.setPaymentType(req.getPaymentType());
        queryReq.setTermChnlNo(log.getRemarks());
        queryReq.setTransId(TransCode.CW77.getCode());

        ChnlNoMethod chnlNo = ChnlNoMethod.findChannelCodeByCode(queryReq.getTermChnlNo());
        if (StringUtil.isNullOrEmpty(chnlNo)) {
            logger.error("## 聚合支付消费异常封装方法--->通道号[{}]信息不存在", queryReq.getTermChnlNo());
            return JSONArray.toJSONString(resp);
        }
        //		logger.info("第三方交易查询--->通道号[{}]信息为[{}]", queryReq.getTermChnlNo(), JSONArray.toJSONString(chnlNo));
        IntegrationPayResp resultResp = new IntegrationPayResp();
        try {
            Class<?> object = Class.forName(chnlNo.getClassName());
            Method method = object.getMethod(BaseIntegrationPayConstants.SEARCH, IntegrationPayReq.class);
            Object obj = object.newInstance();
            resultResp = (IntegrationPayResp) method.invoke(obj, new Object[]{queryReq});
        } catch (Exception e) {
            logger.error("## 聚合支付消费异常封装方法--->根据通道号[{}]，通过反射找到类出错，终端流水[{}]", chnlNo.getChannelCode(), req.getTermSwtFlowNo(), e);
            return JSONArray.toJSONString(resp);
        }

        //第三方没有应答
        if (resultResp == null) {
            logger.error("## 聚合支付消费异常封装方法--->第三方[{}]没有返回交易应答消息，终端流水号为[{}]", chnlNo.getChannelCode(), req.getTermSwtFlowNo());
            resp.setInfo(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_INFO);
            return JSONArray.toJSONString(resultResp);
        }
        logger.info("聚合支付消费异常封装方法--->第三方[{}]返回应答信息[{}]", chnlNo.getChannelCode(), JSONArray.toJSONString(resultResp));
        if (BaseIntegrationPayConstants.SX_PAYMENT_TYPE.equals(resultResp.getPayStatus())) {
            //根据itf流水ID查询交易流水的创建时间
            IntfaceTransLog logtime = intfaceTransLogService.getIntfaceTransLogBylogId(curLogNum, log.getInterfacePrimaryKey());
            String reqDate = DateUtil.getStringFromDate(logtime.getCreateTime(), DateUtil.FORMAT_YYYYMMDD);
            String reqTime = DateUtil.getStringFromDate(logtime.getCreateTime(), DateUtil.FORMAT_HHMMSS);

            //将信息封装到退款类中
            refundReq.setTermNo(req.getTermNo());
            refundReq.setInsNo(req.getInsNo());
            refundReq.setTermChnlNo(req.getTermChnlNo());
            refundReq.setTermOrderNO(req.getTermOrderNO());
            refundReq.setMchntNo(req.getMchntNo());
            refundReq.setShopNo(req.getShopNo());
            refundReq.setMchntName(req.getMchntName());
            refundReq.setShopName(req.getShopName());
            refundReq.setTransChnlNo(req.getTransChnlNo());
            refundReq.setTermSwtFlowNo(payResp.getTermSwtFlowNo());
            refundReq.setPaymentType(req.getPaymentType());
            refundReq.setPaymentScene(req.getPaymentScene());
            refundReq.setTransId(TransCode.CW74.getCode());
            refundReq.setTransAmt(req.getTransAmt());
            refundReq.setAuthInfo(req.getAuthInfo());
            refundReq.setOrderDesc(req.getOrderDesc());
            refundReq.setOrderNo(payResp.getRemarks());
            refundReq.setSettleDate(payResp.getTransDate().substring(0, 8));
            refundReq.setSwtFlowNo(payResp.getSwtFlowNo());
            if (StringUtil.isNullOrEmpty(reqDate))
                refundReq.setReqDate(DateUtil.getCurrentDateStr());
            else
                refundReq.setReqDate(reqDate);
            if (StringUtil.isNullOrEmpty(reqTime))
                refundReq.setReqTime(DateUtil.getCurrentTimeStr());
            else
                refundReq.setReqTime(reqTime);

            ChnlNoMethod refundchnlNo = ChnlNoMethod.findChannelCodeByCode(req.getTermChnlNo());
            if (StringUtil.isNullOrEmpty(refundchnlNo)) {
                logger.error("##  聚合支付消费异常封装方法--->消费退款异常查询渠道号[{}]信息不存在", req.getTermChnlNo());
                return JSONArray.toJSONString(resp);
            }
            try {
                Class<?> object = Class.forName(refundchnlNo.getClassName());
                Method method = object.getMethod(BaseIntegrationPayConstants.REFUND, IntegrationPayReq.class);
                Object obj = object.newInstance();
                refundResp = (IntegrationPayResp) method.invoke(obj, new Object[]{refundReq});
            } catch (Exception e) {
                logger.error("## 聚合支付消费异常封装方法--->根据通道号[{}]，通过反射找到类[{}]方法[{}]出错，消息交易终端流水号为[{}]", refundchnlNo.getChannelCode(), refundchnlNo.getClassName(), "refund", req.getTermSwtFlowNo(), e);
                return JSONArray.toJSONString(resp);
            }

            if (BaseIntegrationPayConstants.SX_PAYMENT_TYPE.equals(refundResp.getStatus())) {
                logger.error("## 聚合支付消费异常封装方法--->消费退款异常时调用第三方[{}]退款失败，应答返回码[{}]，返回状态[{}]", refundchnlNo.getChannelCode(), payResp.getCode(), refundResp.getStatus());
                return JSONArray.toJSONString(resp);
            }
            log.setRespCode(BaseIntegrationPayConstants.ITFRespCode.CODE00.getCode());
            log.setOrgInterfacePrimaryKey(refundResp.getSwtFlowNo());
            log.setAdditionalInfo(refundResp.getRemarks());
            log.setTransSt(3);
            int u1 = intfaceTransLogService.updateIntfaceTransLog(log);// 更新流水
            if (u1 != 1) {
                logger.error("## 聚合支付消费异常封装方法--->消费退款异常调用第三方退款接口完成，更新交易流水失败，itf层流水ID[{}]", log.getInterfacePrimaryKey());
            }
        } else {
            logger.error("## 聚合支付消费异常封装方法--->第三方[{}]返回码[{}]返回状态[{}]", chnlNo.getChannelCode(), resultResp.getRespCode(), resultResp.getPayStatus());
        }
        return JSONArray.toJSONString(resp);
    }

    /**
     * 退款发生异常封装方法
     *
     * @param curLogNum
     * @param refundResp
     * @param refundReq
     * @param log
     * @param oriLog
     * @param isCurrentLog
     * @return
     */
    private String refundException(String curLogNum, IntegrationPayResp refundResp, IntegrationPayRequest refundReq, IntfaceTransLog log, IntfaceTransLog oriLog, boolean isCurrentLog) {
        IntegrationPayResp resp = new IntegrationPayResp();
        resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);

        String json = queryTransactionITF(refundReq);
        BaseTransResp queryResp = JSONArray.parseObject(json, BaseTransResp.class);
        if (!ITFRespCode.CODE00.getCode().equals(queryResp.getCode())) {
            logger.error("## 聚合支付退款异常封装方法--->异常查询接口返回[{}]，终端流水号为[{}]", queryResp.getCode(), refundReq.getTermOrderNO());
            resp.setCode(BaseIntegrationPayConstants.TXN_TRANS_ERROR);
            return JSONArray.toJSONString(resp);
        }
        IntfaceTransLog logs = intfaceTransLogService.getIntfaceTransLogBylogId(curLogNum, log.getInterfacePrimaryKey());
        if (logs == null) {
            logs = intfaceTransLogService.getIntfaceTransLogHisBylogId(log.getInterfacePrimaryKey());
            if (logs == null) {
                logger.error("## 聚合支付退款异常封装方法--->查询itf层退款流水不存在，终端流水号为[{}]", refundReq.getTermOrderNO());
                return JSONArray.toJSONString(resp);
            }
        }
        if (!"1".equals(logs.getTransSt())) {
            int u = 0;
            try {
                u = intfaceTransLogService.updateIntfaceTransLog(log);//更新流水
            } catch (Exception e) {
                logger.error("## 聚合支付退款异常封装方法--->更新退款流水异常，itf层流水ID[{}]", log.getInterfacePrimaryKey());
                return JSONArray.toJSONString(resp);
            }
            if (u != 1) {
                logger.error("## 聚合支付退款异常封装方法--->更新退款流水失败， itf层流水ID[{}]", log.getInterfacePrimaryKey());
                return JSONArray.toJSONString(resp);
            }
        }
        IntfaceTransLog oriLogs = intfaceTransLogService.getIntfaceTransLogByDmsRelatedKey(curLogNum, refundReq.getTermOrderNO());
        if (oriLogs == null) {
            oriLogs = intfaceTransLogService.getIntfaceTransLogHisByDmsRelatedKey(refundReq.getTermOrderNO());
            if (oriLogs == null) {
                logger.error("## 聚合支付退款异常封装方法--->查询itf层原交易流水不存在，终端流水号为[{}]", refundReq.getTermOrderNO());
                return JSONArray.toJSONString(resp);
            }
        }
        if (!"3".equals(oriLogs.getTransSt())) {
            oriLog.setTransSt(3);//更新原交易流水状态为3：交易撤销
            int u2 = 0;
            try {
                if (isCurrentLog) {
                    oriLog.setTableNum(curLogNum);//得到当前可以进行操作的流水表号
                    u2 = intfaceTransLogService.updateIntfaceTransLog(oriLog);
                } else {
                    u2 = intfaceTransLogService.updateIntfaceHisTransLog(oriLog);
                }
            } catch (Exception e) {
                logger.error("## 聚合支付退款异常封装方法--->更新原交易流水异常，itf层流水ID[{}]", oriLog.getInterfacePrimaryKey());
                return JSONArray.toJSONString(resp);
            }
            if (u2 != 1) {
                logger.info("## 聚合支付退款异常封装方法--->更新原交易流水失败，itf层流水ID[{}]", oriLog.getInterfacePrimaryKey());
                return JSONArray.toJSONString(resp);
            }
        }
        resp.setCode(ITFRespCode.CODE00.getCode());
        resp.setInfo(ITFRespCode.CODE00.getValue());
        resp.setSwtFlowNo(refundResp.getSwtFlowNo());
        resp.setTermSwtFlowNo(refundResp.getTermSwtFlowNo());
        resp.setTransDate(DateUtil.getCurrentDateTimeStr());
        resp.setRemarks(refundResp.getRemarks());
        resp.setTransAmt(refundReq.getTransAmt());
        resp.setPreferentialAmt(refundResp.getPreferentialAmt());
        resp.setTermOrderNo(refundResp.getTermOrderNo());
        resp.setPaymentType(refundResp.getPaymentType());
        resp.setTimestamp(System.currentTimeMillis());
        resp.setSign(IntSignUtil.genSign(resp, RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")));
        return JSONArray.toJSONString(resp);
    }

    /**
     * 退款参数封装
     *
     * @param req
     * @return
     */
    private IntegrationPayReq IntegrationRefundReq(IntegrationPayRequest req) {
        IntegrationPayReq refundReq = new IntegrationPayReq();
        refundReq.setTermNo(req.getTermNo());
        refundReq.setInsNo(req.getInsNo());
        refundReq.setTermChnlNo(req.getTermChnlNo());
        refundReq.setTermOrderNO(req.getTermOrderNO());
        refundReq.setMchntNo(req.getMchntNo());
        refundReq.setMchntName(req.getMchntName());
        refundReq.setShopNo(req.getShopNo());
        refundReq.setShopName(req.getShopName());
        refundReq.setTransChnlNo(req.getTermChnlNo());
        refundReq.setTermSwtFlowNo(req.getTermSwtFlowNo());
        refundReq.setPaymentType(req.getPaymentType());
        refundReq.setPaymentScene(req.getPaymentScene());
        refundReq.setTransId(TransCode.CW74.getCode());
        refundReq.setTransAmt(req.getTransAmt());
        refundReq.setAuthInfo(req.getAuthInfo());
        refundReq.setOrderDesc(req.getOrderDesc());
        return refundReq;
    }

}
