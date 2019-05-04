package com.cn.thinkx.biz.drools.service;

import java.util.List;

import org.kie.api.runtime.KieSession;

import com.cn.thinkx.biz.drools.model.BaseFact;
import com.cn.thinkx.biz.drools.model.TransRule;

/**
 * 规则引擎调用service
 * 
 * @author pucker
 *
 */
public interface DroolsService {

	/**
	 * 根据名称得到KieSession
	 * 
	 * @param kSessionName
	 * @return
	 */
	public KieSession getKieSession(String kSessionName);

	/**
	 * 执行规则方法
	 * 
	 * @param kSession
	 *            KieSession
	 * @param fact
	 *            规则模型类
	 * @param expires
	 *            规则的过期时间
	 * @param effective
	 *            规则的生效时间
	 * @param duration
	 *            规则定时(单位：毫秒)
	 * @param salience
	 *            优先级
	 * @throws Exception
	 */
	public void excute(KieSession kSession, BaseFact fact, String expires, String effective, String duration,
			String salience) throws Exception;

	/***
	 * 执行规则方法
	 * @param kSession
	 * @param ruleList 规则因子定义
	 * @throws Exception
	 */
	public void excuteRule(KieSession kSession,List<TransRule> ruleList) throws Exception;
	
	public void closeSession(KieSession kSession);
}
