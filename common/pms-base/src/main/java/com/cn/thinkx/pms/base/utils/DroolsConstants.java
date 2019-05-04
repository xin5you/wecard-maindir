package com.cn.thinkx.pms.base.utils;

public class DroolsConstants {

	public static final String DROOLS_RULE_NOTSTACK="NOTSTACK";
	
	public static final String DROOLS_RULE_STACK="STACK";
	
	
	
	/**
	 * 规则类型信息表 
	 *
	 */
	public enum RuleTemplateEnum{
		 RuleTemplate_1("10000000","充值策略"),
		 RuleTemplate_2("20000000","消费策略");

		private String code;
		
		private String name;
		
		RuleTemplateEnum(String code,String name){
			this.code=code;
			this.name=name;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 规则类型信息表  是否叠加类型
	 *
	 */
	public enum RuleTypeEnum{
		RULETYPE_1000("10001000","充值送"),
		RULETYPE_2000("10002000","充值享受折扣"),
		RULETYPE_3000("20003000","消费满减"),
		RULETYPE_4000("20004000","消费享受折扣");
		
		private String code;
		
		private String name;
		
		RuleTypeEnum(String code,String name){
			this.code=code;
			this.name=name;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 交易规则信息表  是否叠加类型
	 *
	 */
	public enum RuleSuperposedEnum{
		FALSE_SUP("1","不叠加"),
		TRUE_SUP("0","叠加");
		
		private String code;
		
		private String name;
		
		RuleSuperposedEnum(String code,String name){
			this.code=code;
			this.name=name;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
}
