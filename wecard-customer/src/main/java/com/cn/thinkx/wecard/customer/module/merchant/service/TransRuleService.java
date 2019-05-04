package com.cn.thinkx.wecard.customer.module.merchant.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.domain.rule.RuleFactor;
import com.cn.thinkx.common.wecard.domain.rule.RuleTemplate;
import com.cn.thinkx.common.wecard.domain.rule.RuleType;
import com.cn.thinkx.common.wecard.domain.trans.TransRule;
import com.cn.thinkx.common.wecard.domain.trans.TransRuleDeclare;

/**
 *  商户优惠
 * @author zqy
 *
 */
public interface TransRuleService {

	
	/**
	 * 获取当前所有的可用模板信息
	 * @return
	 */
	public List<RuleTemplate> getRuleTemplateList();
	
	/**
	 * 获取模板下可以规则类型
	 * @param tempCode
	 * @return
	 */
	public List<RuleType> getRuleTypeListByTempCode(String tempCode);
	
	
	
	/**
	 * 优惠规则列表查询
	 * @param searchEntity
	 * @param pagination
	 * @return
	 */
	public List<TransRuleDeclare> getTransRuleDeclareList(Map<String,String> paramMap);
	
	public TransRule getTransRuleById(String transRuleId);
	
	
	/**
	 * 优惠规则设置
	 * @param ruleTypeCode 规则类型code
	 * @param transRule 规则交易表
	 * @param factorList 规则因子列表
	 * @return
	 */
	public int insertRechargePresentRule(String ruleTypeCode,TransRule transRule,List<RuleFactor> factorList );
	
	/**
	 * 查询商户优惠列表
	 * @param transRule
	 * @return
	 */
	public Pagination<TransRuleDeclare> getTransRuleByMchnt(@Param("entity")TransRuleDeclare tr , @Param("page")Pagination<TransRuleDeclare> pagination);

	/**
	 * 查询规则
	 * @param ruleId
	 * @return
	 */
	public TransRuleDeclare getTransRuleDeclareByRuleId(String ruleId);
	
	/**
	 * 编辑规则
	 * @param transRule
	 * @return
	 */
	public int updateTransRule(TransRule transRule);
}