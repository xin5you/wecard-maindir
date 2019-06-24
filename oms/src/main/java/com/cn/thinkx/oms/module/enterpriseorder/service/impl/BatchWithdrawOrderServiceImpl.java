package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.cn.thinkx.cgb.model.CgbRequestDTO;
import com.cn.thinkx.cgb.service.CgbService;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchWithdrawOrderMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderService;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("batchWithdrawOrderService")
public class BatchWithdrawOrderServiceImpl implements BatchWithdrawOrderService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BatchWithdrawOrderMapper batchWithdrawOrderMapper;

    @Autowired
    private BatchWithdrawOrderDetailService batchWithdrawOrderDetailService;

    @Override
    public BatchWithdrawOrder getById(String id) {
        return batchWithdrawOrderMapper.getById(id);
    }

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    @Override
    public int insertBatchWithdrawOrder(BatchWithdrawOrder entity, List<BatchWithdrawOrderDetail> details) {
        //批量订单Id
        Snowflake snowflake = IdUtil.getSnowflake(1, 1);
        String batchOrderId = snowflake.nextIdStr();

        for (BatchWithdrawOrderDetail detail : details) {
            detail.setDetailId(snowflake.nextIdStr());
            detail.setOrderId(batchOrderId);
            //手续费先为0
            detail.setFee(new BigDecimal(0));
            detail.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
            batchWithdrawOrderDetailService.insertBatchWithdrawOrderDetail(detail);
        }
        entity.setOrderId(batchOrderId);
        entity.setTotalNum(details.size());
        // 初始化新建
        entity.setStat(Constants.withdrawStat.S00.getCode());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setLockVersion(0);
        return batchWithdrawOrderMapper.insertBatchWithdrawOrder(entity);
    }

    @Override
    public int updateBatchWithdrawOrder(BatchWithdrawOrder entity) {
        return batchWithdrawOrderMapper.updateBatchWithdrawOrder(entity);
    }

    @Override
    public int deleteBatchWithdrawOrder(String id) {
        batchWithdrawOrderDetailService.deleteBatchWithdrawOrderDetailByOrderId(id);
        return batchWithdrawOrderMapper.deleteBatchWithdrawOrder(id);
    }

    public List<BatchWithdrawOrder> getBatchWithdrawOrderList(BatchWithdrawOrder entity) {
        return batchWithdrawOrderMapper.getBatchWithdrawOrderList(entity);
    }

    @Override
    public PageInfo<BatchWithdrawOrder> getBatchWithdrawOrderPage(int startNum, int pageSize, BatchWithdrawOrder entity) {
        PageHelper.startPage(startNum, pageSize);
        List<BatchWithdrawOrder> list = getBatchWithdrawOrderList(entity);
        list.forEach(e -> {
            if (Constants.withdrawStat.S00.getCode().equals(e.getStat())) {
                e.setStat(Constants.withdrawStat.S00.getValue());
            } else if (Constants.withdrawStat.S03.getCode().equals(e.getStat())) {
                e.setStat(Constants.withdrawStat.S03.getValue());
            } else if (Constants.withdrawStat.S07.getCode().equals(e.getStat())) {
                e.setStat(Constants.withdrawStat.S07.getValue());
            } else {
                e.setStat(Constants.withdrawStat.S04.getValue());
            }
        });
        return new PageInfo<>(list);
    }

    /**
     * 代付提交
     *
     * @param entity
     */
    @Override
    public void doPaymentBatchWithdrawOrder(BatchWithdrawOrder entity) {
        // 代付支付中
        entity.setStat(Constants.withdrawStat.S03.getCode());
        this.updateBatchWithdrawOrder(entity);

        BatchWithdrawOrderDetail detail = new BatchWithdrawOrderDetail();
        detail.setOrderId(entity.getOrderId());
        List<BatchWithdrawOrderDetail> payList = batchWithdrawOrderDetailService.getList(detail);

        JSONObject jsonObject = null;
        CgbRequestDTO cgbRequestDTO;

        //代付service
        CgbService cgbService = new CgbService();
        if (CollectionUtils.isNotEmpty(payList)) {
            boolean payFlag = false;
            //获取代付总额
            try {
                BigDecimal totalMoney = payList.stream().map(BatchWithdrawOrderDetail::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                int paysize = payList.size();
                //支付的笔数和查询出来的笔数  && 支付总额 和 统计的金额要完全一致，避免失败
                if (entity.getTotalNum() == paysize && totalMoney.compareTo(entity.getTotalAmount()) == 0) {
                    payFlag = true;
                } else {
                    logger.info("## 代付失败 代付订单中 总额:{},笔数：{} 。实际处理代付金额:{}，实际处理笔数:{}", entity.getTotalAmount(), entity.getTotalNum(), totalMoney, paysize);
                }
            } catch (Exception ex) {
                logger.info("## 代付检验异常：{}", ex);
            }
            if (payFlag) {
                for (int i = 0; i < payList.size(); i++) {
                    detail = payList.get(i);
                    detail.setPayTime(new Date());
                    detail.setPayChanel("2");

                    try {
                        cgbRequestDTO = new CgbRequestDTO();
                        cgbRequestDTO.setEntSeqNo(detail.getDetailId());
                        // 收款人姓名
                        cgbRequestDTO.setInAccName(detail.getReceiverName());
                        // 收款人银行卡号
                        cgbRequestDTO.setInAcc(detail.getReceiverCardNo());
                        // 银行名称
                        cgbRequestDTO.setInAccBank(detail.getBankName());
                        //金额 单位元
                        cgbRequestDTO.setAmount(detail.getAmount().toString());
                        // 联行号
                        cgbRequestDTO.setPaymentBankid(detail.getPayeeBankLinesNo());
                        // 銀行摘要 用途
                        cgbRequestDTO.setRemark(detail.getBankType());
                        cgbRequestDTO.setComment(detail.getBankType());
                        jsonObject = cgbService.dfPaymentResult(cgbRequestDTO);
                    } catch (Exception ex) {
                        logger.error("## 代付失败 {}", ex);
                    }

                    try {
                        if (jsonObject != null) {
                            detail.setRespCode(StringUtil.nullToString(jsonObject.getJSONObject("BEDC").getJSONObject("Message").getJSONObject("commHead").get("retCode")));

                            jsonObject = jsonObject.getJSONObject("BEDC");
                            jsonObject = jsonObject.getJSONObject("Message");
                            if (jsonObject.get("Body") != null && StringUtil.isNotEmpty(String.valueOf(jsonObject.get("Body")))) {
                                jsonObject = jsonObject.getJSONObject("Body");
                                if (jsonObject != null) {
                                    detail.setDmsSerialNo(StringUtil.nullToString(jsonObject.get("traceNo")));
                                    if (jsonObject.get("handleFee") != null) {
                                        detail.setFee(new BigDecimal(StringUtil.nullToString(jsonObject.get("handleFee"))));
                                    }
                                }
                            }
                        }
                        batchWithdrawOrderDetailService.updateBatchWithdrawOrderDetail(detail);
                    } catch (Exception ex) {
                        logger.error("## 更新代付失败 {}", ex);
                    }
                }
            }
        }
    }
}
