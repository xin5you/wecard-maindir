package com.cn.thinkx.biz.drools.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.biz.drools.model.TransRule;

@Repository("transRuleMapper")
public interface TransRuleMapper {

	/**
	 * 获取交易规则列表
	 * @param transRule
	 * @return
	 */
	public List<TransRule> getTransRuleList(TransRule trFilter);
}
