package com.cn.thinkx.wecard.customer.module.checkstand.util;

import java.util.concurrent.ConcurrentHashMap;

public class Constants {
	// 存储系统通用值（慎用）
	public static ConcurrentHashMap<String, Object> sessionMap = new ConcurrentHashMap<String, Object>();
	// 定义成功值
	public static final String SUCCESS = "success";
	// 定义失败值
	public static final String FAILED = "failed";
	//货币类型
	public static final String TRANS_CURR_CD = "156";
	
	// 日切允许状态
	public static final String TRANS_FLAG_YES = "1";
	
	// 嘉福平台接口返回
	public static final String JF_TRANS_RESP_SUCCESS = "98";
	public static final String JF_PAY_BACK_TRANS_ERROR = "999";
	public static final String JF_PAY_BACK_REPEAT_DESC = "重复退款";
	public static final String JF_PAY_BACK_SUCCESS_DESC = "退款成功";
	public static final String JF_PAY_BACK_FAILED_DESC = "退款失败";
	
	// 交易核心返回状态：无效卡号包括销毁状态
	public static final String TXN_TRANS_RESP_INVALID_CARD = "14";
	// 调用交易异常
	public static final String TXN_TRANS_EXCEPTION = "96";
	// 微信端websocket链接参数 业务类型：已扫描
	public static final String WX_WEBSOCKET_BIZ_SCANNED = "scanned";
	// 微信端websocket链接参数 业务类型：扫一扫
	public static final String WX_WEBSOCKET_BIZ_SCANCODE = "scanCode";
	// 微信端websocket链接参数 业务类型：付款码
	public static final String WX_WEBSOCKET_BIZ_QRCODE = "qrCode";
	// 微信端websocket链接参数 开始处理交易
	public static final String WX_WEBSOCKET_START_TRANS = "start_trans";
	// 微信端websocket链接参数 需要密码
	public static final String WX_WEBSOCKET_NEED_PASSWORD = "need_password";
	// 微信端websocket用户发送密码信息间隔特殊字符
	public static final String WX_FLAG_SEND_PASS = "@";
	
	// 微信支付API密钥
	public static final String KEY = "68cCSHFD0mAs3H1Dduw0raQMbVlpmVd8";
	
	// 微信端注册 查询密码
	public static final String PWD_PINQUIRY="0000000000000000";
	
	// 微信端管理员权限session名
	public static final String MANAGER_RESOURCES_SESSION="manager_resources_session";
	
	/**
	 * 规则类型信息表  
	 *
	 */
	public enum TransRespEnum {
		SUCCESS("00", "交易成功"),
		REPEAT("11", "重复交易"),
		EXCEPTION("96", "网络异常"),
		ERROR("99", "系统故障");
		
		private String code;
		
		private String name;
		
		TransRespEnum(String code, String name){
			this.code = code;
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
		
		public static String findNameByCode(String code) {
			for (TransRespEnum t : TransRespEnum.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t.getName();
				}
			}
			return null;
		}
	}
	
	/**
	 * 规则类型信息表  
	 *
	 */
	public enum SendMsgTypeEnum{
		msg_01("01","注册"),
		msg_02("02","账户设置"),
		msg_03("03","消费退款"),
		msg_NUll("100","");
		private String code;
		
		private String name;
		
		SendMsgTypeEnum(String code,String name){
			this.code=code;
			this.name=name;
		}
		
	       public static SendMsgTypeEnum findByCode(String code) {
	           for (SendMsgTypeEnum value : SendMsgTypeEnum.values()) {
	               if (value.code.equals(code)) {
	                   return value;
	               }
	           }
	           return SendMsgTypeEnum.msg_NUll;
	       }
		
		public String getCode() {
			return code;
		}
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 规则类型信息表  
	 *
	 */
	public enum RuleTypeEnum{
		RULETYPE_1000("1000","百分比"),
		RULETYPE_2000("2000","万分比");
		
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
	 * 商户账户信息表  账户类型
	 *
	 */
	public enum MchntAcctTypeEnum{
		DEPOSIT_MONEY_ACCT_TYPE("100","沉淀资金账户"),
		WECHAT_ACCT_TYPE("200","微信充值账户"),
		ALIPAY_ACCT_TYPE("300","支付宝充值账户"),
		JIAFU_ACCT_TYPE("400","嘉福平台"),
		E_BANK_ACCT_TYPE("500","网银账户");
		
		private String code;
		
		private String name;
		
		 MchntAcctTypeEnum(String code,String name){
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
	 * 用户信息表  账户类型
	 *
	 */
	public enum UserTypeEnum{
		APP_TYPE("00","APP"),
		WX_TYPE("10","微信"),
		WG_TyPE("20","网关");
		
		private String code;
		
		private String name;
		
		UserTypeEnum(String code,String name){
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
	 * 个人信息表  证件类型
	 *
	 */
	public enum CardTypeEnum{
		CARD_TYPE_00("00","身份证"),
		CARD_TYPE_10("10","军官证"),
		CARD_TYPE_20("20","护照"),
		CARD_TYPE_30("30","学生证");
		private String code;
		
		private String name;
		
		CardTypeEnum(String code,String name){
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
	 * 商户账户信息表 账户状态
	 *
	 */
	public enum MchntAcctStatEnum{
		NORMAL_ACCT_SATA("00","有效"),
		CANCELLATION_ACCT_SATA("10","注销"),
		FROZEN_ACCT_SATA("20","冻结");
		private String code;
		
		private String name;
		
		MchntAcctStatEnum(String code,String name){
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
	 * 公账号分组 0,1,2系统默认 第一次商户关注状态为 0
	 *
	 */
	public enum GroupsIdStatEnum{
		groupdefauls_stat("0");
		
		private String code;
		
		GroupsIdStatEnum(String code){
			this.code=code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * 用户操作菜单状态  00-管理员分享角色
	 *
	 */
	public enum ShareTypeEnum{
		SHARE_MANAGER_ROLE("00");
		
		private String code;
		
		ShareTypeEnum(String code){
			this.code=code;
		}
		public String getCode() {
			return code;
		}
	}
	
	
	/**
	 * 用户操作菜单状态  00-无权限；10-获得权限 
	 *
	 */
	public enum FansStatusEnum{
		Fans_STATUS_00("00"),
		Fans_STATUS_10("10");
		
		private String code;
		
		FansStatusEnum(String code){
			this.code=code;
		}
		public String getCode() {
			return code;
		}
	}
	
	/**
	 * 商户粉丝用户状态 1-可用；0-不可用
	 *
	 */
	public enum AccountFansStatusEnum{
		TRUE_STATUS(1),
		FALSE_STATUS(0);
		
		private Integer code;
		
		AccountFansStatusEnum(Integer code){
			this.code=code;
		}
		public Integer getCode() {
			return code;
		}
	}
	/**
	 * 数据启用标记
	 * @author 13501
	 *
	 */
	public enum DataStatEnum{
		
		TRUE_STATUS("0"),
		FALSE_STATUS("1");
		
		private String code;
		
		DataStatEnum(String code){
			this.code=code;
		}
		public String getCode() {
			return code;
		}
		
	}
	
	  /**
	  *	商户平台角色管理
	  * //000 客户会员 100 商户boss 200 商户财务 300 商户店长 400 商户收银员 500 商户服务员
	  * 
	  */
	 public enum RoleNameEnum{
		 
		 VIP_ROLE_MCHANT("1","000", "客户会员"),
		 BOSS_ROLE_MCHANT("2","100", "商户boss"),
		 FINANCIAL_RORE_MCHANT("3","200", "商户财务"),
		 SHOPMANAGER_ROLE_MCHANT("4","300", "商户店长"),
		 CASHIER_ROLE_MCHANT("5","400", "商户收银员"),
		 WAITER_ROLE_MCHANT("6","500", "商户服务员");

		private String roleId;
		private String roleType; //000 客户会员 100 商户boss 200 商户财务 300 商户店长 400 商户收银员 500 商户服务员
		private String roleName ;

		RoleNameEnum(String roleId,String roleType, String roleName){
	         this.roleId = roleId;
	         this.roleType = roleType;
	         this.roleName = roleName;
	     }

		public String getRoleId() {
			return roleId;
		}

		public String getRoleType() {
			return roleType;
		}

		public String getRoleName() {
			return roleName;
		}
	 }
	 
	 /**
	 *
	 * 交易类型
	 *
	 */
	public enum TransCode {
		MB80("B80", "商户开户"), 
		MB10("B10", "商户提现"), 
		MB20("B20", "商户沉淀资金账户充值"),
		MB30("B30", "商户网站退货交易"),
		CW80("W80", "客户开户"),
		CW81("W81", "密码重置"),
		CW10("W10", "客户消费"),
		CW20("W20", "客户充值"),
		CW71("W71", "快捷支付"),
		CW11("W11", "客户交易撤销"),
		CW74("W74", "快捷支付交易撤销");
		
		private String code;
		
		private String value;
		
		TransCode(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static TransCode findByCode(String code) {
			for (TransCode t : TransCode.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	 
	 /**
	 *
	 * 渠道类型
	 *
	 */
	public enum ChannelCode {
		CHANNEL1("40001010"),// 商户开户、客户开户、密码重置、消费 (从微信公众号发起)
		CHANNEL2("40002001"),// 充值、商户提现  (从微信公众号发起)
		CHANNEL3("40003001"),// 充值、商户提现  (从支付宝发起)
		CHANNEL4("40004001"),// 充值、商户提现  (从嘉福平台发起)
		CHANNEL5("40005001");// 充值、商户提现  (从网银向本系统发起)
		
		private final String value;

		ChannelCode(String value) {
			this.value = value;
		};
		
		@Override
		public String toString() {
			return this.value;
		}
		
		public static ChannelCode findByCode(String code) {
			for (ChannelCode t : ChannelCode.values()) {
				if (t.value.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	};
	
}
