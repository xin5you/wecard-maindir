package com.cn.thinkx.oms.module.enterpriseorder.service;

import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrder;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BatchWithdrawOrderService {


    BatchWithdrawOrder getById(String id);

    /**
     * 保存
     * @param entity
     * @return
     */
    public int insertBatchWithdrawOrder(BatchWithdrawOrder entity, List<BatchWithdrawOrderDetail> details);


    /**
     * 修改产品信息
     * @param entity
     * @return
     */
    public int updateBatchWithdrawOrder(BatchWithdrawOrder entity);


    /**
     * 删除
     * @param id
     * @return
     */
    public int deleteBatchWithdrawOrder(String id);

    /**
     * 产品分页
     * @param startNum
     * @param pageSize
     * @param entity
     * @return
     */
    public PageInfo<BatchWithdrawOrder> getBatchWithdrawOrderPage(int startNum, int pageSize, BatchWithdrawOrder entity);

}
