package com.cn.thinkx.wecard.customer.module.eshop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.common.wecard.domain.eshop.MchntEshopInf;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;

public interface MchntEshopInfService {
	
	MchntEshopInf getMchntEshopInfById(String eShopId);
	
	List<MchntEshopInf> getMchntEshopInfList(MchntEshopInf mchEshop);
	
	MchntEshopInf getMchntEshopInfByMchntCode(String mchntCode);
	
	/**
	 * 跳转嘉福商城（京东，美团，大众点评）参数封装方法
	 * 
	 * @param request
	 * @param personInf
	 * @param ecmChnl
	 * @param mchntCode
	 * @param shopCode
	 * @return
	 */
	String JFUrl(HttpServletRequest request, PersonInf personInf, String ecmChnl, String mchntCode, String shopCode);
	
	/**
	 * 跳转海豚通通兑商城参数封装方法
	 * 
	 * @param request
	 * @param personInf
	 * @return
	 */
	String HTTTDUrl(HttpServletRequest request, PersonInf personInf);
}
