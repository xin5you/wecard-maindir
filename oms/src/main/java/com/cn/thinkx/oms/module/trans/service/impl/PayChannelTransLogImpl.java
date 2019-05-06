package com.cn.thinkx.oms.module.trans.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.facade.bean.IntegrationPayRequest;
import com.cn.thinkx.facade.service.IntegrationPayFacade;
import com.cn.thinkx.oms.module.common.model.WxTransLog;
import com.cn.thinkx.oms.module.common.service.CtrlSystemService;
import com.cn.thinkx.oms.module.common.service.WxTransLogService;
import com.cn.thinkx.oms.module.trans.mapper.PayChannelTransLogMapper;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransInf;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLog;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLogUpload;
import com.cn.thinkx.oms.module.trans.service.PayChannelTransLogService;
import com.cn.thinkx.oms.module.trans.util.BaseTransResp;
import com.cn.thinkx.oms.util.NumberUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.redis.util.IntSignUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service("payChannelTransLogService")
public class PayChannelTransLogImpl implements PayChannelTransLogService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("payChannelTransLogMapper")
    private PayChannelTransLogMapper payChannelTransLogMapper;

    @Autowired
    private IntegrationPayFacade integrationPayFacade;

    @Autowired
    private WxTransLogService wxTransLogService;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Override
    public PageInfo<PayChannelTransLog> getWxTransLogPage(int startNum, int pageSize, PayChannelTransInf entity) {
        List<PayChannelTransLog> list = null;
        try {
            // PageHelper.startPage(startNum, pageSize);
            // list = this.getTransLogList(entity);
            if (StringUtil.isNullOrEmpty(entity.getQueryType()) || "cur".equals(entity.getQueryType())) {
                String curLogNum = payChannelTransLogMapper.getCurLogNum();
                entity.setTableNum(curLogNum);
            }

            PageHelper.startPage(startNum, pageSize);
            if ("his".equals(entity.getQueryType()))
                list = payChannelTransLogMapper.getWxTransLogHisByMchntCode(entity);
            else {
                list = payChannelTransLogMapper.getWxTransLogCurByMchntCode(entity);
            }
            if (list != null && list.size() > 0) {
                for (PayChannelTransLog obj : list) {
                    obj.setSettleDate(DateUtil.getFormatStringFormString(obj.getSettleDate(), "yyyy-MM-dd"));
                    if (obj.getTransAmt() != null && !"".equals(obj.getTransAmt())) {
                        obj.setTransAmt("" + NumberUtils.formatMoney(obj.getTransAmt()));
                    }
                    if (!StringUtil.isEmpty(obj.getRespCode())) {
                        obj.setRespCode(BaseConstants.ITFRespCode.findByCode(obj.getRespCode()).getValue());
                    }
                    if (!StringUtil.isEmpty(obj.getTransId())) {
                        obj.setTransId(BaseIntegrationPayConstants.TransCode.findByCode(obj.getTransId()).getValue());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("## 查询交易明细列表出错：", e);
        }
        PageInfo<PayChannelTransLog> page = new PageInfo<PayChannelTransLog>(list);
        return page;
    }

    @Override
    public List<PayChannelTransLogUpload> getWxTransLogList(PayChannelTransInf entity) {
        List<PayChannelTransLogUpload> list = null;
        try {
            if (StringUtil.isNullOrEmpty(entity.getQueryType()) || "cur".equals(entity.getQueryType())) {
                String curLogNum = payChannelTransLogMapper.getCurLogNum();
                entity.setTableNum(curLogNum);
            }

            if ("his".equals(entity.getQueryType()))
                list = payChannelTransLogMapper.getWxTransLogUploadHisByMchntCode(entity);
            else {
                list = payChannelTransLogMapper.getWxTransLogUploadCurByMchntCode(entity);
            }

            if (list != null && list.size() > 0) {
                for (PayChannelTransLogUpload obj : list) {
                    obj.setSettleDate(DateUtil.getFormatStringFormString(obj.getSettleDate(), "yyyy-MM-dd"));
                    if (obj.getTransAmt() != null && !"".equals(obj.getTransAmt())) {
                        obj.setTransAmt("" + NumberUtils.formatMoney(obj.getTransAmt()));
                    }
                    if (!StringUtil.isEmpty(obj.getRespCode())) {
                        obj.setRespCode(BaseConstants.ITFRespCode.findByCode(obj.getRespCode()).getValue());
                    }
                    if (!StringUtil.isEmpty(obj.getTransId())) {
                        obj.setTransId(BaseIntegrationPayConstants.TransCode.findByCode(obj.getTransId()).getValue());
                    }
                    if ("3".equals(obj.getTransSt())) {
                        obj.setTransSt("已退款");
                    } else {
                        obj.setTransSt("");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("## 查询交易明细列表出错：", e);
        }
        return list;
    }

    @Override
    public String getRefundTransactionITF(HttpServletRequest req) {
        ModelMap resultMap = new ModelMap();
        resultMap.addAttribute("status", Boolean.FALSE);
        resultMap.addAttribute("msg", "退款失败");
        BaseTransResp resp = null;
        IntegrationPayRequest payReq = new IntegrationPayRequest();
        String dmsRelatedKey = ""; // 终端流水号
        try {
            dmsRelatedKey = StringUtils.nullToString(req.getParameter("dmsRelatedKey"));
            logger.info("## 退款传入的终端流水号：[{}]", dmsRelatedKey);
            CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 得到日切信息

            // 检查当前流水表原交易流水是否存在
            WxTransLog pctLog = payChannelTransLogMapper.getIntfaceTransLogByOrgDmsRelatedKey(cs.getCurLogNum(), dmsRelatedKey);
            boolean isCurrentLog = true;// 是否为当前交易记录
            if (pctLog == null) {
                isCurrentLog = false;
                pctLog = payChannelTransLogMapper.getIntfaceTransLogHisByDmsRelatedKey(dmsRelatedKey);// 检查历史流水表原交易流水是否存在
                if (pctLog == null) {
                    logger.error("## 聚合支付退款交易--->终端原交易流水号[{}]在itf层无流水记录", dmsRelatedKey);
                    resultMap.addAttribute("status", Boolean.FALSE);
                    resultMap.addAttribute("msg", "退款失败");
                    return JSONObject.toJSONString(resultMap);
                }
            }
            WxTransLog log = new WxTransLog();
            String wxPrimaryKey = wxTransLogService.getPrimaryKey();
            log.setWxPrimaryKey(wxPrimaryKey);
            log.setOrgWxPrimaryKey(pctLog.getWxPrimaryKey()); // 原交易流水号(WX消费流水号)
            log.setTableNum(cs.getCurLogNum());
            log.setSettleDate(cs.getSettleDate());
            log.setTransSt(0);// 插入时为0，报文返回时更新为1
            log.setInsCode(pctLog.getInsCode());// 客户端传过来的机构code
            log.setMchntCode(pctLog.getMchntCode());
            log.setShopCode(pctLog.getShopCode());
            log.setUserInfUserName(pctLog.getUserInfUserName());
            log.setTransAmt(pctLog.getTransAmt());// 实际交易金额 插入时候默认与上送金额一致
            log.setUploadAmt(pctLog.getTransAmt());// 上送金额
            log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
            log.setTermCode(pctLog.getTermCode());// 设备号
            log.setTransId(BaseIntegrationPayConstants.TransCode.CW74.getCode()); // 退款（快捷）
            log.setTransChnl(ChannelCode.CHANNEL0.toString()); // 渠道类型
            log.setAdditionalInfo(pctLog.getAdditionalInfo());
            log.setSponsor(pctLog.getSponsor());
            try {
                logger.info("## 退款插入wx流水信息[{}]", JSONObject.toJSONString(log));
                int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
                if (i != 1) {
                    logger.info("## 退款插入wx流水错误");
                    return JSONObject.toJSONString(resultMap);
                }
            } catch (Exception ex) {
                logger.error("## 退款插入wx流水错误", ex);
                return JSONObject.toJSONString(resultMap);
            }
            // 退款
            payReq.setTermSwtFlowNo(wxPrimaryKey); // 终端流水号
            payReq.setInsNo(pctLog.getInsCode()); // 机构号
            payReq.setMchntNo(pctLog.getMchntCode()); // 商户号
            payReq.setShopNo(pctLog.getShopCode()); // 门店号
            payReq.setTermOrderNO(pctLog.getWxPrimaryKey()); // 原交易流水号
            payReq.setTermChnlNo(pctLog.getAdditionalInfo()); // 通道号
            payReq.setTransChnlNo(ChannelCode.CHANNEL0.toString()); // 交易渠道号
            payReq.setTransId(BaseIntegrationPayConstants.TransCode.CW74.getCode()); // 交易类型
            payReq.setPaymentType(pctLog.getSponsor()); // 支付方式
            payReq.setAuthInfo(pctLog.getUserInfUserName()); // 条码信息
            payReq.setTransAmt(pctLog.getTransAmt()); // 交易金额
            payReq.setTermNo(pctLog.getTermCode()); // 终端号（设备号）
            payReq.setTimestamp(System.currentTimeMillis());
            payReq.setSign(IntSignUtil.genSign(payReq, RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")));
            logger.info("## 退款传入参数：[{}]", JSONObject.toJSONString(payReq));
            String refundjson = integrationPayFacade.refundTransactionITF(payReq);
            logger.info("## 退款的返回参数：[{}]", refundjson);
            resp = JSONArray.parseObject(refundjson, BaseTransResp.class);
            if (!"00".equals(resp.getCode())) {
                logger.info("## ITF退款失败，返回码[{}]", refundjson);
                return JSONObject.toJSONString(resultMap);
            }
            log.setRespCode(resp.getCode());
            log.setTransSt(1);// 插入时为0，报文返回时更新为1
            int i = 0;
            logger.info("## ITF退款成功后修改退款流水信息：[{}]", JSONObject.toJSONString(log));
            try {
                i = wxTransLogService.updateWxCurTransLog(log); // 更新退款流水信息
            } catch (Exception ex) {
                logger.error("## 更新退款流水[{}],记录出错", log.getWxPrimaryKey(), ex);
                return refundException(payReq);
            }
            if (i != 1) {
                logger.info("## 更新退款流水[{}]记录出错", log.getWxPrimaryKey());
                return refundException(payReq);
            }
            pctLog.setTransSt(3);
            int i2 = 0;
            try {
                if (isCurrentLog) {
                    pctLog.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
                    logger.info("## 更新原交易流水记录信息[{}]", JSONObject.toJSONString(pctLog));
                    i2 = wxTransLogService.updateWxCurTransLog(pctLog); // 更新原交易流水记录
                } else {
                    logger.info("## 更新原交易流水记录信息[{}]", JSONObject.toJSONString(pctLog));
                    i2 = wxTransLogService.updateWxHisTransLog(pctLog);
                }
            } catch (Exception e) {
                logger.error("## 更新原交易流水[{}]出错", pctLog.getWxPrimaryKey(), e);
                return refundException(payReq);
            }
            if (i2 != 1) {
                logger.info("## 更新原交易流水[{}]出错", pctLog.getWxPrimaryKey());
                return refundException(payReq);
            }
            resultMap.addAttribute("status", Boolean.TRUE);
            resultMap.addAttribute("msg", "退款成功");
        } catch (Exception e) {
            logger.error("## 该流水号[{}]退款失败--->[{}]", dmsRelatedKey, e);
            resultMap.addAttribute("status", Boolean.FALSE);
            resultMap.addAttribute("msg", "退款失败");
        }
        return JSONObject.toJSONString(resultMap);
    }

    /**
     * 退款异常处理
     *
     * @param req
     * @return
     */
    public String refundException(IntegrationPayRequest req) {
        BaseTransResp resp = new BaseTransResp();
        resp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);
        logger.info("聚合支付查询接口--->请求参数[{}]", JSONArray.toJSONString(req));
        try {
            String queryTransJson = integrationPayFacade.queryTransactionITF(req);
            resp = JSONArray.parseObject(queryTransJson, BaseTransResp.class);
            if (!"00".equals(resp.getCode())) {
                logger.info("## ITF退款失败，返回码[{}]", resp);
                return JSONObject.toJSONString(resp);
            }
        } catch (Exception e) {
            logger.info("## ITF退款失败，返回码[{}]", resp);
            return JSONObject.toJSONString(resp);
        }

        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 得到日切信息
        // 检查当前流水表原交易流水是否存在
        WxTransLog pctLog = payChannelTransLogMapper.getIntfaceTransLogByOrgDmsRelatedKey(cs.getCurLogNum(), req.getTermSwtFlowNo());
        if (pctLog == null) {
            pctLog = payChannelTransLogMapper.getIntfaceTransLogHisByDmsRelatedKey(req.getTermSwtFlowNo());// 检查历史流水表原交易流水是否存在
            if (pctLog == null) {
                logger.error("## 聚合支付退款交易--->终端原交易流水号[{}]在itf层无流水记录", req.getTermSwtFlowNo());
                return JSONObject.toJSONString(resp);
            }
        }
        if (!"1".equals(pctLog.getTransSt())) {
            pctLog.setRespCode(resp.getCode());
            pctLog.setTransSt(1);// 插入时为0，报文返回时更新为1
            int i = 0;
            logger.info("## 退款成功后修改了流水信息：[{}]", JSONObject.toJSONString(pctLog));
            try {
                i = wxTransLogService.updateWxCurTransLog(pctLog); // 更新退款流水记录
            } catch (Exception ex) {
                logger.error("## 更新退款流水记录", ex);
                return JSONObject.toJSONString(resp);
            }
        }
        // 检查当前流水表原交易流水是否存在
        WxTransLog log = payChannelTransLogMapper.getIntfaceTransLogByOrgDmsRelatedKey(cs.getCurLogNum(), req.getTermOrderNO());
        boolean isCurrentLog = true;// 是否为当前交易记录
        if (log == null) {
            isCurrentLog = false;
            log = payChannelTransLogMapper.getIntfaceTransLogHisByDmsRelatedKey(req.getTermOrderNO());// 检查历史流水表原交易流水是否存在
            if (log == null) {
                logger.error("## 聚合支付退款交易--->终端原交易流水号[{}]在itf层无流水记录", req.getTermOrderNO());
                return JSONObject.toJSONString(resp);
            }
        }
        if (!"3".equals(log.getTransSt())) {
            log.setTransSt(3);
            int i2 = 0;
            try {
                if (isCurrentLog) {
                    pctLog.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
                    logger.info("## 更新原交易流水记录信息[{}]", JSONObject.toJSONString(log));
                    i2 = wxTransLogService.updateWxCurTransLog(log); // 更新原交易流水记录
                } else {
                    logger.info("## 更新原交易流水记录信息[{}]", JSONObject.toJSONString(log));
                    i2 = wxTransLogService.updateWxHisTransLog(log);
                }
            } catch (Exception e) {
                logger.error("## 更新原交易流水出错", e);
                return JSONObject.toJSONString(resp);
            }
            if (i2 != 1) {
                logger.info("## 更新原交易流水出错");
                return JSONObject.toJSONString(resp);
            }
        }
        resp.setCode(ITFRespCode.CODE00.getCode());
        resp.setInfo(ITFRespCode.CODE00.getValue());
        logger.info("聚合支付退款--->返回参数[{}]", JSONArray.toJSONString(resp));
        return "";
    }
}
