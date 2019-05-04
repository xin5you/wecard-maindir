package com.cn.thinkx.oms.module.trans.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.ui.ModelMap;

import com.cn.thinkx.oms.module.trans.model.PayChannelTransInf;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLog;
import com.cn.thinkx.oms.module.trans.model.PayChannelTransLogUpload;
import com.github.pagehelper.PageInfo;

public interface PayChannelTransLogService {

	/**
	 * 获取交易记录列表
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	PageInfo<PayChannelTransLog> getWxTransLogPage(int startNum, int pageSize, PayChannelTransInf entity);

	/**
	 * 获取交易记录集合
	 * 
	 * @param entity
	 * @return
	 */
	List<PayChannelTransLogUpload> getWxTransLogList(PayChannelTransInf entity);
	
	/**
	 * 退款
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	String getRefundTransactionITF(HttpServletRequest req);
	
}
