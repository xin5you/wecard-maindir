package com.cn.thinkx.biz.drools.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.biz.drools.mapper.TransRuleMapper;
import com.cn.thinkx.biz.drools.model.TransRule;
import com.cn.thinkx.biz.drools.service.TransRuleService;
import com.cn.thinkx.pms.base.utils.DroolsConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;

@Service("transRuleService")
public class TransRuleServiceImpl implements TransRuleService {

	
	@Autowired
	@Qualifier("transRuleMapper")
	private TransRuleMapper transRuleMapper;
	
	public List<TransRule> getTransRuleDeclareList(TransRule trFilter) throws Exception {
		if(trFilter ==null){
			throw new Exception("param is null");
		}
		return transRuleMapper.getTransRuleList(trFilter);
	}
	
	/**
	 * 查询规则List 区分叠加和 不叠加的 。key=NOTSTACK 表示不叠加 ; Key=STACK 标记叠加,
	 * @param mchntId
	 * @param platformId
	 * @param oriTxnAmount
	 * @param templateCode
	 * @return
	 * @throws Exception 
	 */
	public Map<String, List<TransRule>> getTransRuleList(String mchntId,String platformId,int oriTxnAmount,String templateCode) throws Exception{
		Map<String, List<TransRule>> mapList=new HashMap<String, List<TransRule>>();
		
		List<TransRule> notStackList=new ArrayList<TransRule>(); //不叠加的集合列表
		List<TransRule> stackList=new ArrayList<TransRule>();   //叠加的集合列表
		
		TransRule trFilter=null;
		List<TransRule> tranRuleList=null;
		
		if(StringUtil.isNotEmpty(mchntId)){
			 trFilter=new TransRule();
			 trFilter.setMchntId(mchntId);
			 trFilter.setInMoney(oriTxnAmount);
			 trFilter.setTemplateCode(templateCode);
			tranRuleList=this.getTransRuleDeclareList(trFilter);
			ruleDataConvert(tranRuleList,notStackList,stackList,oriTxnAmount);
		}
		if(StringUtil.isNotEmpty(platformId)){
			trFilter=new TransRule();
			trFilter.setPlatformId(platformId);
			trFilter.setInMoney(oriTxnAmount);
			trFilter.setTemplateCode(templateCode);
			tranRuleList=this.getTransRuleDeclareList(trFilter);
			ruleDataConvert(tranRuleList,notStackList,stackList,oriTxnAmount);
		}
		mapList.put(DroolsConstants.DROOLS_RULE_NOTSTACK, notStackList); //不叠加的集合
		mapList.put(DroolsConstants.DROOLS_RULE_STACK, stackList); //叠加的集合
		return mapList;
	}
	
	/**
	 * 数据转换
	 * @param tranRuleList
	 * @param notStackList
	 * @param stackList
	 * @param oriTxnAmount
	 */
	private void ruleDataConvert(List<TransRule> tranRuleList,List<TransRule> notStackList,List<TransRule> stackList,int oriTxnAmount){
		if(tranRuleList !=null && tranRuleList.size()>0){
			TransRule tr=null;
			 for(int i=0;i<tranRuleList.size();i++){
				 tr=tranRuleList.get(i);
				 tr.setInMoney(oriTxnAmount);
				 if(DroolsConstants.RuleSuperposedEnum.TRUE_SUP.getCode().equals(tr.getIsSuperposed())){ //叠加
					 stackList.add(tr);
				 }else{
					 notStackList.add(tr);
				 }
			 }
		}
	}
	
	public int maxFavourableConvert(List<TransRule> list) {
		if (list != null && list.size() > 1) {
			Comparator<TransRule> comparator = new Comparator<TransRule>() {
				public int compare(TransRule o1, TransRule o2) {
					return o1.getInMoney() > o2.getInMoney() ? 1 : -1;
				}
			};
			Collections.sort(list, comparator);
			return list.get(0).getInMoney();
		} else if (list.size() == 1) {
			return list.get(0).getInMoney();
		} else {
			return 0;
		}
	}
	
}
