package com.cn.thinkx.wecard.customer.module.merchant.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.domain.rule.RuleDeclare;
import com.cn.thinkx.common.wecard.domain.rule.RuleFactor;
import com.cn.thinkx.common.wecard.domain.rule.RuleTemplate;
import com.cn.thinkx.common.wecard.domain.rule.RuleType;
import com.cn.thinkx.common.wecard.domain.trans.TransRule;
import com.cn.thinkx.common.wecard.domain.trans.TransRuleDeclare;
import com.cn.thinkx.common.wecard.module.rule.mapper.RuleDeclareDao;
import com.cn.thinkx.common.wecard.module.rule.mapper.RuleFactorDao;
import com.cn.thinkx.common.wecard.module.rule.mapper.RuleTemplateDao;
import com.cn.thinkx.common.wecard.module.rule.mapper.RuleTypeDao;
import com.cn.thinkx.common.wecard.module.trans.mapper.TransRuleDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.wecard.customer.module.merchant.service.TransRuleService;
import com.cn.thinkx.wecard.customer.module.pub.service.PublicService;

/**
 *  商户优惠
 * @author zqy
 *
 */
@Service("transRuleService")
public class TransRuleServiceImpl implements TransRuleService {
	
	@Autowired
    @Qualifier("ruleTemplateDao")
	private RuleTemplateDao ruleTemplateDao;
	
	@Autowired
    @Qualifier("ruleTypeDao")
	private RuleTypeDao ruleTypeDao;
	
	@Autowired
    @Qualifier("ruleDeclareDao")
	private RuleDeclareDao ruleDeclareDao;
	
	@Autowired
    @Qualifier("transRuleDao")
	private TransRuleDao transRuleDao;
	
	@Autowired
	@Qualifier("ruleFactorDao")
	private RuleFactorDao ruleFactorDao;
	
	@Autowired
	@Qualifier("publicService")
	private PublicService publicService;

	public List<RuleTemplate> getRuleTemplateList() {
		return ruleTemplateDao.getAllRuleTemplate(new RuleTemplate());
	}

	public List<RuleType> getRuleTypeListByTempCode(String tempCode) {
		return ruleTypeDao.getRuleTypeListByTempCode(tempCode);
	}
	
	/**
	 * 交易规则列表
	 * @param paramMap
	 * @return
	 */
	public List<TransRuleDeclare> getTransRuleDeclareList(Map<String,String> paramMap) {
		return transRuleDao.getTransRuleDeclareList(paramMap);
	}

	@Override
	public TransRule getTransRuleById(String transRuleId) {
		// TODO Auto-generated method stub
		return transRuleDao.getTransRuleById(transRuleId);
	}

	
	/**
	 * 优惠规则设置
	 * @param ruleTypeCode
	 * @param transRule
	 * @param ruleFactor
	 * @return
	 */
	public int insertRechargePresentRule(String ruleTypeCode,TransRule transRule,List<RuleFactor> factorList){
		
		int operNum=0;
		//规则类型
		RuleType ruleType=ruleTypeDao.getRuleTypeById(ruleTypeCode);
		
		//交易规则表
		String transRuleId=publicService.getPrimaryKey();
		transRule.setTransRuleId(transRuleId);
		operNum=transRuleDao.insertTransRule(transRule);
		
		// 规则申明表
		String ruleId=publicService.getPrimaryKey();
		RuleDeclare ruleDeclare=new RuleDeclare();
		ruleDeclare.setRuleType(ruleType.getRuleType());
		ruleDeclare.setRuleTypeCode(ruleType.getRuleTypeCode());
		ruleDeclare.setRuleId(ruleId);
		ruleDeclare.setRuleFactorNum(String.valueOf(factorList.size()));
		ruleDeclare.setTransRuleId(transRuleId);
		ruleDeclare.setCreateUser(transRule.getCreateUser()); //创建人
		ruleDeclare.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		operNum=ruleDeclareDao.insertRuleDeclare(ruleDeclare);
		
		//规则因子表
		RuleFactor ruleFactor=null;
		for(int i=0;i<factorList.size();i++){
			ruleFactor=factorList.get(i);
			ruleFactor.setRuleId(ruleId);
			ruleFactorDao.insertRuleFactor(ruleFactor);
		}
		return operNum;
	}
	
	/**
	 * 查询商户优惠列表
	 * @param transRule
	 * @return
	 */
	public Pagination<TransRuleDeclare> getTransRuleByMchnt(@Param("entity")TransRuleDeclare tr , @Param("page")Pagination<TransRuleDeclare> pagination){
		int totalItemsCount = transRuleDao.getTransRuleByMchntCount(tr);
		List<TransRuleDeclare> items = transRuleDao.getTransRuleByMchnt(tr, pagination);
		pagination.setItems(items);
		pagination.setTotalItemsCount(totalItemsCount);
		return pagination;
	}
	
	
	/**
	 * 查询规则
	 * @param ruleId
	 * @return
	 */
	public TransRuleDeclare getTransRuleDeclareByRuleId(String ruleId){
		TransRuleDeclare tr= transRuleDao.getTransRuleDeclareByRuleId(ruleId);
		if(tr !=null){
			tr.setRuleFactor(NumberUtils.formatMoney(tr.getRuleFactor()));
			if(BaseConstants.RuleTypeEnum.RULETYPE_1000.getCode().equals(tr.getRuleType())){
				tr.setRuleParam(NumberUtils.formatMoney(tr.getRuleParam()));
			}
			if(BaseConstants.RuleTypeEnum.RULETYPE_2000.getCode().equals(tr.getRuleType())){
				tr.setRuleParam(NumberUtils.formatRate4(tr.getRuleParam()));
			}
		}
		return tr;
	}
	
	/*
	 * 编辑规则
	 * @param transRule
	 * @return
	 */
	public int updateTransRule(TransRule transRule){
		return	transRuleDao.updateTransRule(transRule);
	}
}