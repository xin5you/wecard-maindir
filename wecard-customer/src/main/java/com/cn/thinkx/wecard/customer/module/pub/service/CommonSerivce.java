package com.cn.thinkx.wecard.customer.module.pub.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.cn.thinkx.common.wecard.domain.base.ResultHtml;

public interface CommonSerivce {
	
	/**
	 * 获取分秒+6位seqID
	 * 
	 * @param rCodePrefix
	 * @return
	 */
	String findMmSsAddSeqId(String rCodePrefix);
	
	/**
	 * 发送消息目标
	 * 
	 * @param phoneNumber
	 * @param bizName
	 * @param session
	 * @return
	 */
	ResultHtml getResultMap(String phoneNumber,String bizName, HttpSession session);
	
	/**
	 * 发送短信
	 * 
	 * @param request
	 * @return
	 */
	ResultHtml sendPhoneSMS(HttpServletRequest request);
	
	/**
	 * 客户会员发送短信
	 * 
	 * @param request
	 * @return
	 */
	ResultHtml sendUserPhoneSMS(HttpServletRequest request);
	
	/**
	 * 商户发送短信
	 * 
	 * @param request
	 * @return
	 */
	ResultHtml sendMchntPhoneSMS(HttpServletRequest request);
	
	
}
