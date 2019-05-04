package com.cn.thinkx.dubbo.facadeImpl.drools.impl;

import java.util.List;
import java.util.Map;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cn.thinkx.biz.drools.model.TransRule;
import com.cn.thinkx.biz.drools.service.DroolsService;
import com.cn.thinkx.biz.drools.service.TransRuleService;
import com.cn.thinkx.pms.base.utils.DroolsConstants;
import com.cn.thinkx.service.drools.WxDroolsExcutor;

public class WxDroolsExcutorImpl implements WxDroolsExcutor {
	@Autowired
	@Qualifier("droolsService")
	private DroolsService dService;

	@Autowired
	@Qualifier("transRuleService")
	private TransRuleService transRuleService;

	@Override
	public int getConsumeDiscount(String mchntId, String platformId, int oriTxnAmount) throws Exception {
		KieSession kSession = null;
		try {
			Map<String, List<TransRule>> listMap = transRuleService.getTransRuleList(mchntId, platformId, oriTxnAmount,DroolsConstants.RuleTemplateEnum.RuleTemplate_2.getCode());
			List<TransRule> notStackList = listMap.get(DroolsConstants.DROOLS_RULE_NOTSTACK); // 叠加的规则集合
			List<TransRule> stackList = listMap.get(DroolsConstants.DROOLS_RULE_STACK); // 不叠加的规则集合

			if ((notStackList == null || notStackList.size() <= 0) && (stackList == null || stackList.size() <= 0)) {
				return oriTxnAmount;
			}

			kSession = dService.getKieSession("KS_CONSUMERULES");
			if (notStackList != null && notStackList.size() > 0) {
				dService.excuteRule(kSession, notStackList); // 执行不叠加优惠规则
				kSession.fireAllRules();
				oriTxnAmount = transRuleService.maxFavourableConvert(notStackList); // 计算 最大优惠
			}

			if (stackList != null && stackList.size() > 0) {
				if (notStackList != null && notStackList.size() > 0) {
					for (int i = 0; i < stackList.size(); i++) {
						stackList.get(i).setInMoney(oriTxnAmount);
					}
				}
				dService.excuteRule(kSession, stackList); // 执行叠加优惠规则
				kSession.fireAllRules();
				oriTxnAmount = transRuleService.maxFavourableConvert(stackList); // 计算 最大优惠
			}
		} catch (Exception e) {
			if (kSession != null) {
				kSession.fireAllRules();
			}
		} finally {
			if(kSession !=null){
				dService.closeSession(kSession);
			}
		}
		return oriTxnAmount;
	}

	/**
	 * 充值优惠
	 * @param mchntId
	 * @param platformId
	 * @param oriTxnAmount
	 * @return
	 * @throws Exception
	 */
	public int getRechargeDiscount(String mchntId, String platformId, int oriTxnAmount) throws Exception{
		KieSession kSession = null;
		try {
			Map<String, List<TransRule>> listMap = transRuleService.getTransRuleList(mchntId, platformId, oriTxnAmount,DroolsConstants.RuleTemplateEnum.RuleTemplate_1.getCode());
			List<TransRule> notStackList = listMap.get(DroolsConstants.DROOLS_RULE_NOTSTACK); // 叠加的规则集合
			List<TransRule> stackList = listMap.get(DroolsConstants.DROOLS_RULE_STACK); // 不叠加的规则集合

			if ((notStackList == null || notStackList.size() <= 0) && (stackList == null || stackList.size() <= 0)) {
				return oriTxnAmount;
			}

			kSession = dService.getKieSession("KS_RECHAREGRULES");
			if (notStackList != null && notStackList.size() > 0) {
				dService.excuteRule(kSession, notStackList); // 执行不叠加优惠规则
				kSession.fireAllRules();
				oriTxnAmount = transRuleService.maxFavourableConvert(notStackList); // 计算 最大优惠
			}

			if (stackList != null && stackList.size() > 0) {
				if (notStackList != null && notStackList.size() > 0) {
					for (int i = 0; i < stackList.size(); i++) {
						stackList.get(i).setInMoney(oriTxnAmount);
					}
				}
				dService.excuteRule(kSession, stackList); // 执行叠加优惠规则
				kSession.fireAllRules();
				oriTxnAmount = transRuleService.maxFavourableConvert(stackList); // 计算 最大优惠
			}
		} catch (Exception e) {
			if (kSession != null) {
				kSession.fireAllRules();
			}
		} finally {
			dService.closeSession(kSession);
		}
		return oriTxnAmount;
	}
}