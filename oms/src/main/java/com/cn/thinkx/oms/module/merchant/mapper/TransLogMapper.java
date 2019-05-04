package com.cn.thinkx.oms.module.merchant.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.TransInf;
import com.cn.thinkx.oms.module.merchant.model.TransLog;
import com.cn.thinkx.oms.module.merchant.model.TransLogUpload;

@Repository("transLogMapper")
public interface TransLogMapper {
	
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
	List<TransLog> getTransLogHisByMchntCode(TransInf entity);

	/**
	 * 根据商户号获取商户当天交易记录
	 * @param entity
	 * @return
	 */
	List<TransLog> getTransLogCurByMchntCode(TransInf entity);
	
	/**
	 * 根据商户号获取商户历史交易记录
	 * 
	 * @param entity
	 * @return
	 */
	List<TransLogUpload> getTransLogUploadCurByMchntCode(TransInf entity);
	
	/**
	 * 根据商户号获取商户当天交易记录
	 * 
	 * @param entity
	 * @return
	 */
	List<TransLogUpload> getTransLogUploadHisByMchntCode(TransInf entity);

}
