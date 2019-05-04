package com.cn.thinkx.oms.module.trans.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.trans.model.CardTransInf;
import com.cn.thinkx.oms.module.trans.model.CardTransLog;
import com.cn.thinkx.oms.module.trans.model.CardTransLogUpload;

@Repository("cardTransLogMapper")
public interface CardTransLogMapper {
	
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
	List<CardTransLog> getTransLogHisByMchntCode(CardTransInf entity);

	/**
	 * 根据商户号获取商户当天交易记录
	 * @param entity
	 * @return
	 */
	List<CardTransLog> getTransLogCurByMchntCode(CardTransInf entity);
	
	/**
	 * 根据商户号获取商户历史交易记录
	 * 
	 * @param entity
	 * @return
	 */
	List<CardTransLogUpload> getTransLogUploadCurByMchntCode(CardTransInf entity);
	
	/**
	 * 根据商户号获取商户当天交易记录
	 * 
	 * @param entity
	 * @return
	 */
	List<CardTransLogUpload> getTransLogUploadHisByMchntCode(CardTransInf entity);

}
