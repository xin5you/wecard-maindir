package com.cn.thinkx.oms.module.trans.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.common.model.WxTransLog;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransInf;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLog;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLogUpload;
@Repository("payChannelTransLogMapper")
public interface PayChannelTransLogMapper {

	/**
	 * 得到当前log表
	 * 
	 * @return
	 */
	String getCurLogNum();

	/**
	 * 根据商户号获取商户历史交易记录
	 * 
	 * @param entity
	 * @return
	 */
	List<PayChannelTransLog> getWxTransLogHisByMchntCode(PayChannelTransInf entity);

	/**
	 * 根据商户号获取商户当天交易记录
	 * @param entity
	 * @return
	 */
	List<PayChannelTransLog> getWxTransLogCurByMchntCode(PayChannelTransInf entity);
	
	/**
	 * 获取通道当天交易记录
	 * 
	 * @param entity
	 * @return
	 */
	List<PayChannelTransLogUpload> getWxTransLogUploadCurByMchntCode(PayChannelTransInf entity);
	
	/**
	 * 获取商户历史交易记录
	 * 
	 * @param entity
	 * @return
	 */
	List<PayChannelTransLogUpload> getWxTransLogUploadHisByMchntCode(PayChannelTransInf entity);
	
	/**
	 * 根据orgDmsRelatedKey 外部原交易流水号查询历史流水
	 * @param orgDmsRelatedKey 外部原交易流水号
	 * @return
	 */
	WxTransLog getIntfaceTransLogHisByDmsRelatedKey(@Param("dmsRelatedKey") String dmsRelatedKey);
	
	/**
	 * 根据orgDmsRelatedKey 外部原交易流水号查询当日流水
	 * 
	 * @param tableNum
	 * @param orgDmsRelatedKey
	 * @return
	 */
	WxTransLog getIntfaceTransLogByOrgDmsRelatedKey(@Param("tableNum") String tableNum, @Param("dmsRelatedKey") String dmsRelatedKey);
}
