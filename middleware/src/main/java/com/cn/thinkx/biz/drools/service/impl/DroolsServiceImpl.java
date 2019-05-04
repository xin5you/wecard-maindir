package com.cn.thinkx.biz.drools.service.impl;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cn.thinkx.biz.drools.model.BaseFact;
import com.cn.thinkx.biz.drools.model.TransRule;
import com.cn.thinkx.biz.drools.service.DroolsService;

@Service("droolsService")
public class DroolsServiceImpl implements DroolsService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public KieSession getKieSession(String kSessionName) {
		try {
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession session = kContainer.newKieSession(kSessionName);
			return session;
		} catch (Exception e) {
			logger.error("New KieSession throws exception:", e);
		}
		return null;
	}

	@Override
	public void excute(KieSession kSession, BaseFact fact, String expires, String effective, String duration,
			String salience) throws Exception {
		if (kSession == null) {
			throw new Exception("kSession can not be null !");
		} else if (fact == null) {
			throw new Exception("fact can not be null !");
		} else {
			try {
				fact.setExpires(expires);
				fact.setEffective(effective);
				fact.setDuration(duration);
				fact.setSalience(salience);
				
				kSession.insert(fact);
				kSession.fireAllRules();
				kSession.dispose();// Stateful rule session must always be disposed when finished
			} catch (Exception e) {
				logger.error("Excute KieSession throws exception:", e);
			}
		}
	}
	
	/***
	 * 执行规则方法
	 * 
	 * @param kSession
	 * @param ruleList
	 *            规则因子定义
	 * @throws Exception
	 */
	public void excuteRule(KieSession kSession, List<TransRule> ruleList) throws Exception {
		if (kSession == null) {
			throw new Exception("kSession can not be null !");
		} else if (ruleList == null || ruleList.size() < 1) {
			// throw new Exception("ruleList can not be null !");
			logger.info("There are no rules to be executed.");
		}
		
		if (ruleList != null && ruleList.size() > 0) {
			for (TransRule rule : ruleList) {
				kSession.insert(rule);
			}
		}
	}

	/**
	 * 处理session 关闭
	 */
	public void closeSession(KieSession kSession) {
		if (kSession != null) {
			kSession.dispose();// Stateful rule session must always be disposed when finished
		}
	}
}
