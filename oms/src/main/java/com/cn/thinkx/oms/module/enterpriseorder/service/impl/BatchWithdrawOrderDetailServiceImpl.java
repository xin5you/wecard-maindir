package com.cn.thinkx.oms.module.enterpriseorder.service.impl;

import com.cn.thinkx.oms.module.enterpriseorder.mapper.BatchWithdrawOrderDetailMapper;
import com.cn.thinkx.oms.module.enterpriseorder.model.BatchWithdrawOrderDetail;
import com.cn.thinkx.oms.module.enterpriseorder.service.BatchWithdrawOrderDetailService;
import com.cn.thinkx.pms.base.utils.CnapsUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.Date;
import java.util.List;

@Service("batchWithdrawOrderDetailService")
public class BatchWithdrawOrderDetailServiceImpl implements BatchWithdrawOrderDetailService {

	@Autowired
	private BatchWithdrawOrderDetailMapper batchWithdrawOrderDetailMapper;


    @Override
    public BatchWithdrawOrderDetail getByDetailId(String id) {
        return batchWithdrawOrderDetailMapper.getByDetailId(id);
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

    @Override
    public PageInfo<BatchWithdrawOrderDetail> getBatchWithdrawOrderDetailPage(int startNum, int pageSize, BatchWithdrawOrderDetail entity) {
        PageHelper.startPage(startNum, pageSize);
        List<BatchWithdrawOrderDetail> list = getList(entity);
        PageInfo<BatchWithdrawOrderDetail> page = new PageInfo<BatchWithdrawOrderDetail>(list);
        return page;
    }
    public List<BatchWithdrawOrderDetail> getList(BatchWithdrawOrderDetail entity) {
        return batchWithdrawOrderDetailMapper.getList(entity);
    }

    /**
     *  联行获取
     * @param list
     */
    public ModelMap  getCanps(List<BatchWithdrawOrderDetail> list){
        ModelMap resultMap=new ModelMap();
        BatchWithdrawOrderDetail detail=null;
        for (int i = 0; i < list.size(); i++) {
            detail=list.get(i);
            if(StringUtil.isEmpty(detail.getPayeeBankLinesNo())){
                detail.setPayeeBankLinesNo(CnapsUtil.getCnapsNo(detail.getBankName(),detail.getBankProvince(),detail.getBankCity()));
            }
            if(StringUtil.isEmpty(detail.getPayeeBankLinesNo())){
                resultMap.put("status", Boolean.FALSE);
                resultMap.put("msg", String.format("用户{%s}的银行卡{%s}所属支行联行号获失败，请重新上传",detail.getReceiverName(),detail.getReceiverCardNo()));
                return resultMap;
            }
        };
        resultMap.put("status", Boolean.TRUE);
        return resultMap;
    }
}
