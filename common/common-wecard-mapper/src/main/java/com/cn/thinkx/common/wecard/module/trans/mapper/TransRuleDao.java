package com.cn.thinkx.common.wecard.module.trans.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.domain.trans.TransRule;
import com.cn.thinkx.common.wecard.domain.trans.TransRuleDeclare;

/**
 *  交易规则
 * @author zqy
 *
 */
public interface TransRuleDao {

	
	public TransRule getTransRuleById(String transRuleId);
	
	public int insertTransRule(TransRule transRule);
	
	public int updateTransRule(TransRule transRule);
	
	/**
	 * 获取交易规则列表
	 * @param transRule
	 * @return
	 */
	public List<TransRuleDeclare> getTransRuleDeclareList(Map<String,String> paramMap);
	
	
	/**
	 * 查询商户优惠列表
	 * @param transRule
	 * @return
	 */
	public List<TransRuleDeclare> getTransRuleByMchnt(@Param("entity")TransRuleDeclare tr , @Param("page")Pagination<TransRuleDeclare> pagination);
	
	public int getTransRuleByMchntCount(@Param("entity")TransRuleDeclare tr);
	
	public TransRuleDeclare getTransRuleDeclareByRuleId(String ruleId);
}