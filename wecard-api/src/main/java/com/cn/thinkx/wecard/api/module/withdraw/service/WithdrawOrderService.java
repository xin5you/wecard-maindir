package com.cn.thinkx.wecard.api.module.withdraw.service;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pay.domain.UnifyPayForAnotherVO;
import com.cn.thinkx.pay.domain.UnifyQueryVO;
import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrder;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellReq;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellResp;
import org.apache.ibatis.annotations.Param;

public interface WithdrawOrderService {

    String getPrimaryKey();

    int getCountByBatchNo(String batchNo);

    WithdrawOrder getWithdrawOrderById(@Param("batchNo") String batchNo);

    int insertWithdrawOrder(WithdrawOrder withdrawOrder);

    int updateWithdrawOrder(WithdrawOrder withdrawOrder);

    WithdrawOrder getWithdrawOrderByPaidId(@Param("paidId") String paidId);

    /**
     * 苏宁易付宝批量代付
     *
     * @param batchNo  批次号
     * @param jsonData JSON参数
     * @return
     */
    String YFBBatchWithdraw(String batchNo, String jsonData) throws Exception;

    /**
     * 校验签名
     *
     * @param paramData
     * @return
     */
    boolean YFBWithdrawCheckSign(String paramData);

    /**
     * 调用代付查询接口
     *
     * @param batchNo
     * @param payMerchantNo
     * @return
     */
    String YFBBatchWithdrawQuery(String batchNo, String payMerchantNo);

    /**
     * 卡券集市转让提交
     *
     * @param req
     * @return
     */
    WelfaremartResellResp welfaremartResellCommit(WelfaremartResellReq req) throws Exception;

    /**
     * 中付 代付
     *
     * @param un
     * @return
     * @throws Exception
     */
    JSONObject zfPayWithdraw(UnifyPayForAnotherVO un) throws Exception;

    /**
     * 中付 代付查询
     *
     * @param queryVO queryVO
     * @return JSONObject
     */
    JSONObject zfPayQuery(UnifyQueryVO queryVO);

    /**
     * 公司余额提现 转让提交
     *
     * @param req
     * @return
     */
    WelfaremartResellResp welfaremartBalanceDrawCommit(WelfaremartResellReq req,String cardOrderId) throws Exception;
}
