package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.cn.thinkx.cgb.model.CgbRequestDTO;
import com.cn.thinkx.cgb.service.CgbService;
import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchWithdrawOrderDetailMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderService;
import com.cn.thinkx.pms.base.utils.CnapsUtil;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.List;

@Service("batchWithdrawOrderDetailService")
public class BatchWithdrawOrderDetailServiceImpl implements BatchWithdrawOrderDetailService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BatchWithdrawOrderDetailMapper batchWithdrawOrderDetailMapper;

    @Autowired
    private BatchWithdrawOrderService batchWithdrawOrderService;

    @Override
    public BatchWithdrawOrderDetail getByDetailId(String id) {
        return batchWithdrawOrderDetailMapper.getById(id);
    }

    @Override
    public int insertBatchWithdrawOrderDetail(BatchWithdrawOrderDetail entity) {
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setLockVersion(0);
        return batchWithdrawOrderDetailMapper.insertBatchWithdrawOrderDetail(entity);
    }

    @Override
    public int updateBatchWithdrawOrderDetail(BatchWithdrawOrderDetail entity) {
        return batchWithdrawOrderDetailMapper.updateBatchWithdrawOrderDetail(entity);
    }

    @Override
    public int deleteBatchWithdrawOrderDetail(String id) {
        return batchWithdrawOrderDetailMapper.deleteBatchWithdrawOrderDetail(id);
    }

    /**
     * 删除
     *
     * @param orderId
     * @return
     */
    @Override
    public int deleteBatchWithdrawOrderDetailByOrderId(String orderId) {
        return batchWithdrawOrderDetailMapper.deleteBatchWithdrawOrderDetailByOrderId(orderId);
    }

    @Override
    public PageInfo<BatchWithdrawOrderDetail> getBatchWithdrawOrderDetailPage(int startNum, int pageSize, BatchWithdrawOrderDetail entity) {
        PageHelper.startPage(startNum, pageSize);
        List<BatchWithdrawOrderDetail> list = getList(entity);
        list.forEach(e -> {
            if ("000".equals(e.getRespCode())) {
                e.setRespCode("成功");
            } else {
                e.setRespCode("失败");
            }
            e.setRespMsg(Constants.cgbPayStat.findByCode(e.getRespMsg()).getValue());
        });
        return new PageInfo<>(list);
    }

    public List<BatchWithdrawOrderDetail> getList(BatchWithdrawOrderDetail entity) {
        return batchWithdrawOrderDetailMapper.getList(entity);
    }

    /**
     * 联行获取
     *
     * @param list
     */
    public ModelMap getCanps(List<BatchWithdrawOrderDetail> list) {
        ModelMap resultMap = new ModelMap();
        BatchWithdrawOrderDetail detail;
        for (int i = 0; i < list.size(); i++) {
            detail = list.get(i);
            if (StringUtil.isEmpty(detail.getPayeeBankLinesNo())) {
                detail.setPayeeBankLinesNo(CnapsUtil.getCnapsNo(detail.getBankName(), detail.getBankProvince(), detail.getBankCity()));
            }
            if (StringUtil.isEmpty(detail.getPayeeBankLinesNo())) {
                resultMap.put("status", Boolean.FALSE);
                resultMap.put("msg", String.format("用户{%s}的银行卡{%s}所属支行联行号获失败，请重新上传", detail.getReceiverName(), detail.getReceiverCardNo()));
                return resultMap;
            }
        }
        ;
        resultMap.put("status", Boolean.TRUE);
        return resultMap;
    }

    /**
     * 同步订单数据
     *
     * @param order
     * @return
     */
    public ModelMap doSynchronStat(BatchWithdrawOrder order) {
        ModelMap resultMap = new ModelMap();
        BatchWithdrawOrderDetail detail = new BatchWithdrawOrderDetail();
        detail.setOrderId(order.getOrderId());
        List<BatchWithdrawOrderDetail> list = this.getList(detail);
        if (CollectionUtils.isNotEmpty(list)) {
            JSONObject jsonObject;
            CgbRequestDTO cgbRequestDTO;
            //代付service
            CgbService cgbService = new CgbService();
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);

            for (BatchWithdrawOrderDetail batchWithdrawOrderDetail : list) {
                detail = batchWithdrawOrderDetail;
                if (StringUtil.isNotEmpty(detail.getRespMsg())) {
                    if (Constants.cgbPayStat.S02.getCode().equals(detail.getRespMsg()) ||
                            Constants.cgbPayStat.S03.getCode().equals(detail.getRespMsg())) {
                        continue;
                    }
                }
                cgbRequestDTO = new CgbRequestDTO();
                cgbRequestDTO.setEntSeqNo(snowflake.nextIdStr());
                cgbRequestDTO.setOrigEntseqno(detail.getDetailId());
                if (detail.getPayTime() != null) {
                    cgbRequestDTO.setOrigEntdate(DateUtil.COMPAT.getDateText(detail.getPayTime()));
                } else {
                    cgbRequestDTO.setOrigEntdate(DateUtil.COMPAT.getDateText(new Date()));
                }
                try {
                    jsonObject = cgbService.dfQueryOrderResult(cgbRequestDTO);
                    if (jsonObject != null) {
                        if (StringUtil.isEmpty(detail.getRespCode())) {
                            detail.setRespCode(StringUtil.nullToString(jsonObject.getJSONObject("BEDC").getJSONObject("Message").getJSONObject("commHead").get("retCode")));
                        }
                        jsonObject = jsonObject.getJSONObject("BEDC");
                        jsonObject = jsonObject.getJSONObject("Message");
                        if (jsonObject.get("Body") != null && StringUtil.isNotEmpty(String.valueOf(jsonObject.get("Body")))) {
                            jsonObject = jsonObject.getJSONObject("Body");
                            if (jsonObject != null) {
                                if (jsonObject.get("hostStatus") != null) {
                                    detail.setRespMsg(StringUtil.nullToString(jsonObject.get("hostStatus")));
                                }
                            }
                        }
                    }
                    this.updateBatchWithdrawOrderDetail(detail);
                } catch (Exception ex) {
                    logger.error("## 更新代付失败 {}", ex);
                }
            }
            for (BatchWithdrawOrderDetail orderDetail : list) {
                if (Constants.cgbPayStat.S02.getCode().equals(orderDetail.getRespMsg())) {
                    order.setStat(Constants.withdrawStat.S07.getCode());
                } else if (Constants.cgbPayStat.S01.getCode().equals(orderDetail.getRespMsg())) {
                    order.setStat(Constants.withdrawStat.S03.getCode());
                    break;
                } else {
                    logger.info("同步订单返回广发代付处理失败，订单信息：{}", com.alibaba.fastjson.JSONObject.toJSONString(detail));
                    order.setStat(Constants.withdrawStat.S04.getCode());
                    break;
                }
            }
            batchWithdrawOrderService.updateBatchWithdrawOrder(order);
        }
        resultMap.put("status", Boolean.TRUE);
        return resultMap;
    }
}
