package com.cn.iboot.diy.api.invoice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
import com.cn.iboot.diy.api.trans.domain.TransLog;

@Mapper
public interface InvoiceOrderMapper extends BaseDao<InvoiceOrder>{
	
	/**
	 * 查询个人充值的交易数据
	 * 
	 * @param log
	 * @return
	 */
	List<TransLog> getTransLogCur(TransLog log);
	
	/**
	 * 根据dmsRelatedKey查询交易流水
	 * 
	 * @param dmsRelatedKey
	 * @return
	 */
	TransLog getTransLogByDmsRelatedKey(TransLog transLog);
	
	/**
	 * 查询开票信息详情
	 * 
	 * @param itfPrimaryKey
	 * @return
	 */
	InvoiceOrder getInvoiceByItfPrimaryKey(String itfPrimaryKey);
	
	int getInvoiceCountByItfPrimaryKey(String itfPrimaryKey);
	
}
