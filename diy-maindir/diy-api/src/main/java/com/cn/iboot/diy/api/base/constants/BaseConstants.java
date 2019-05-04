package com.cn.iboot.diy.api.base.constants;

public class BaseConstants {
	
	
	// 报文消息头
	public static final String MSG_HEAD = "MSG_HEAD";
	// 通信成功后返回值
	public static final String RESPONSE_SUCCESS_CODE = "00";
	
	public static final String RESPONSE_SUCCESS_INFO = "交易成功";
	// 出现硬件或者网络等其他因素导致的失败交易返回码
	public static final String TXN_TRANS_ERROR = "99";
	public static final String RESPONSE_EXCEPTION_CODE = "96";
	
	public static final String RESPONSE_EXCEPTION_INFO = "网络异常,请稍后再试";
	public static final String RESPONSE_REPEAT_TRANS = "重复交易";
	public static final String RESPONSE_COMMAMOUNT_ERROR = "购卡与实际交易金额不一致";
	// 日切允许状态
	public static final String TRANS_FLAG_YES = "1";
	//货币类型
	public static final String TRANS_CURR_CD = "156";
	// 定义成功值
	public static final String SUCCESS = "success";
	// 定义失败值
	public static final String FAILED = "failed";

	// 交易核心返回状态：成功
	public static final String TXN_TRANS_RESP_SUCCESS = "00";
	
	// 交易核心返回状态：无效卡号包括销毁状态
	public static final String TXN_TRANS_RESP_INVALID_CARD = "14";
	
	
	// 微信端注册 查询密码
	public static final String PWD_PINQUIRY="0000000000000000";
	
	// 微信端管理员权限session名
	public static final String MANAGER_RESOURCES_SESSION="manager_resources_session";
	
	
	/**
	 * 订单异步通知结果
	 *
	 */
	public enum TransOrderResult{
		FAIL("FAIL"),
		SUCCESS("SUCCESS");
		
		private String value;
		
		TransOrderResult(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public enum OMSChannel{
		CHANNEL1001("1001","知了企服","40001001,40002001,50002001"),// 知了企服
		CHANNEL4001("4001","嘉福","40004001,50004001"),// 嘉福
		CHANNEL10001("10001","平台","10001001");// 平台
		private  String code;
		private  String name;
		private  String value;

		OMSChannel(String code, String name, String value) {
			this.code = code;
			this.name = name;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public static OMSChannel findByCode(String code) {
			for (OMSChannel t : OMSChannel.values()) {
				if (t.getCode().equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
		
		public static String contansValue(String value){
			for (OMSChannel t : OMSChannel.values()) {
				if (t.getValue().contains(value)) {
					return t.getName();
				}
			}
			return null;
		}
		
	}
	
	public enum OMSShopType{
		shopTYpe10("10","实体门店"),
		shopTYpe20("20","商城门店"),
		shopTYpe30("30","虚拟门店"),
		shopTYpe99("99","其他");
		private String code;
		private String name;
		
		OMSShopType(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public enum OMSSellCardFlag{
		sellCardFlag0("0","允许售卡"),
		sellCardFlag1("1","不允许售卡");
		
		private String code;
		private String name;
		
		OMSSellCardFlag(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
	}
	
	public enum OMSOrderStat{
		orderStat_10("10","草稿"),
		orderStat_19("19","取消"),
		orderStat_20("20","待处理"),
		orderStat_30("30","处理中"),
		orderStat_40("40","处理成功"),
		orderStat_90("90","部分成功");
		
		private String code;
		private String stat;
		
		OMSOrderStat(String code, String stat) {
			this.code = code;
			this.stat = stat;
		}
		
		public static String findStat(String code){
			for (OMSOrderStat t : OMSOrderStat.values()) {
				if (t.getCode().contains(code)) {
					return t.getStat();
				}
			}
			return null;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getStat() {
			return stat;
		}

		public void setStat(String stat) {
			this.stat = stat;
		}

		
		
	}
	
	public enum DiyInvoiceStat{
		DiyInvoiceStat_0("0","未开票"),
		DiyInvoiceStat_1("1","已开票");
		
		private String code; 
		private String type;
		
		DiyInvoiceStat(String code, String type) {
			this.code = code;
			this.type = type;
		}
		
		public static String findType(String code){
			for (DiyInvoiceStat t : DiyInvoiceStat.values()) {
				if (t.getCode().contains(code)) {
					return t.getType();
				}
			}
			return null;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public enum OMSOrderType{
		orderType_100("100","批量开户"),
		orderType_200("200","批量开卡"),
		orderType_300("300","批量充值");
		
		private String code; 
		private String type;
		
		OMSOrderType(String code, String type) {
			this.code = code;
			this.type = type;
		}
		
		public static String findType(String code){
			for (OMSOrderType t : OMSOrderType.values()) {
				if (t.getCode().contains(code)) {
					return t.getType();
				}
			}
			return null;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public enum OMSOrderListStat{
		orderListStat_0("0","未处理"),
		orderListStat_00("00","处理成功"),
		orderListStat_99("99","处理失败");
		
		private String code;
		private String stat;
		
		OMSOrderListStat(String code, String stat) {
			this.code = code;
			this.stat = stat;
		}
		
		public static String findOrderListStat(String code){
			for (OMSOrderListStat t : OMSOrderListStat.values()) {
				if (t.getCode().contains(code)) {
					return t.getStat();
				}
			}
			return null;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getStat() {
			return stat;
		}

		public void setStat(String stat) {
			this.stat = stat;
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
		
		public static String findNameByCode(String roleType) {
			for (RoleNameEnum t : RoleNameEnum.values()) {
				if (t.roleType.equalsIgnoreCase(roleType)) {
					return t.roleName;
				}
			}
			return null;
		}
	 }
	 
	 /**
	 *
	 * 交易类型
	 *
	 */
	public enum TransCode {
//		CW80("W80", "开户"),
//		CW81("W81", "密码重置"),
		CW10("W10", "消费"),
		CW20("W20", "充值"),
		CW71("W71", "快捷消费"),
		CW11("W11", "退款"),
		CW74("W74", "退款（快捷）"),
		CW30("W30", "退货"),
		CW75("W75", "退货（快捷）");
//		CS20("S20", "返利");
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
				if (t.getCode().equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
	public enum TransType{
		trans1("1","交易成功"),
		trans3("3","已退款"),
		trans17("17","已退货");
		
		private String code;
		private String value;
		
		TransType(String code, String value) {
			this.code = code;
			this.value = value;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
		public static TransType findByCode(String code) {
			for (TransType t : TransType.values()) {
				if (t.getCode().equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
		
	}

		
		
		/**
		 * 账户类型 0:已关闭 1:建立中 2:已链接
		 */
		public enum ACC_TYPE {
			CUSTOMER("100"), MERCHANT("200");
			private final String value;

			ACC_TYPE(String value) {
				this.value = value;
			};

			@Override
			public String toString() {
				return this.value;
			}
		};
	
	/**
	 *
	 * 核心接口返回码
	 *
	 */
	public enum ITFRespCode {
		CODE00("00", "处理成功"), 
		CODE01("01", "查发卡行"), 
		CODE03("03", "无效商户"),
		CODE04("04", "受限商户"),
		CODE06("06", "无效合同"),
		CODE07("07", "终端已经下载过TMK"),
		CODE08("08", "终端未签到"),
		CODE09("09", "文件不存在"),
		CODE10("10", "文件打开错误"),
		CODE12("12", "无效交易"),
		CODE13("13", "无效金额"),
		CODE14("14", "无效卡号包括销毁状态"),
		CODE15("15", "卡未找到"),
		CODE16("16", "卡未激活"),
		CODE17("17", "与原交易卡号不符"),
		CODE20("20", "无效应答"),
		CODE21("21", "账户不匹配"),
		CODE22("22", "怀疑操作有误"),
		CODE23("23", "不可接受的手续费"),
		CODE25("25", "未找到原交易"),
		CODE26("26", "原交易不成功"),
		CODE31("31", "路由失败-机构不支持"),
		CODE34("34", "有作弊嫌疑"),
		CODE36("36", "卡已锁定"),
		CODE39("39", "交易日期有误"),
		CODE40("40", "请求的功能尚不支持"),
		CODE41("41", "卡已挂失"),
		CODE42("42", "无效账户商户合同下未关联账户"),
		CODE44("44", "卡被注销"),
		CODE45("45", "卡被冻结"),
		CODE51("51", "余额不足"),
		CODE54("54", "过期的卡"),
		CODE55("55", "密码错"),
		CODE56("56", "无此卡记录"),
		CODE57("57", "不允许持卡人进行的交易"),
		CODE58("58", "不允许终端进行的交易"),
		CODE61("61", "POS单笔交易金额超限"),
		CODE62("62", "POS当天累计交易金额超限"),
		CODE63("63", "余额不正确"),
		CODE64("64", "与原交易金额不符"),
		CODE65("65", "消费次数超限"),
		CODE66("66", "充值次数超限"),
		CODE67("67", "网上单笔交易金额超限"),
		CODE68("68", "网上当天累计交易金额超限"),
		CODE72("72", "无效IC卡信息"),
		CODE74("74", "cvv2不正确"),
		CODE75("75", "密码错误次数超限"),
		CODE77("77", "pos批次不一致重新签到"),
		CODE80("80", "购卡次数超限"),
		CODE90("90", "系统正在批处理日切中"),
		CODE91("91", "通信失败"),
		CODE92("92", "设施找不到或无法达到"),
		CODE94("94", "重复交易"),
		CODE95("95", "加密失败"),
		CODE96("96", "系统故障"),
		CODE97("97", "无效终端"),
		CODE99("99", "格式错误"),
		CODEA0("A0", "MAC错");

		private String code;

		private String value;

		ITFRespCode(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static ITFRespCode findByCode(String code) {
			for (ITFRespCode itf : ITFRespCode.values()) {
				if (itf.code.equalsIgnoreCase(code)) {
					return itf;
				}
			}
			return null;
		}
	}
	

	
	/**
	 *
	 * 交易类型
	 *
	 */
	public enum TransQueryCode {
//		MB80("B80", "商户开户"), 
//		MB10("B10", "商户提现"), 
//		MB20("B20", "商户沉淀资金账户充值"),
//		MB30("B30", "商户网站退货交易"),
//		CW80("W80", "客户开户"),
//		CW81("W81", "密码重置"),
//		CW10("W10", "消费"),
//		CW20("W20", "充值"),
//		CW71("W71", "快捷消费"),
//		CW11("W11", "退款"),
//		CW74("W74", "退款（快捷）"),
//		CS20("S20", "返利");
		
		CW10("W10", "消费"),
		CW71("W71", "快捷消费"),
		CW11("W11", "退款"),
		CW74("W74", "退款（快捷）"),
		CW30("W30", "退货"),
		CW75("W75", "退货（快捷）");
		
		private String code;
		
		private String value;
		
		TransQueryCode(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static TransQueryCode findByCode(String code) {
			for (TransQueryCode t : TransQueryCode.values()) {
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
		CHANNEL0("10001001"),//管理平台
		CHANNEL1("40001001"),// 商户开户、客户开户、密码重置、消费 (从微信公众号发起)
		CHANNEL2("40002001"),// 充值、商户提现  (从微信公众号发起)
		CHANNEL3("40003001"),// 充值、商户提现  (从支付宝发起)
		CHANNEL4("40004001"),// 充值、商户提现  (从嘉福平台发起)
		CHANNEL5("40005001"),// 充值、商户提现  (从网银向本系统发起)
		CHANNEL6("40006001");// all  (从电商端发起)
		
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
	}
	
	
	/**
	 * 用户信息表  账户类型
	 *
	 */
	public enum UserTypeEnum{
		APP_TYPE("00","APP"),
		WX_TYPE("10","微信"),
		WX_TYPE_JF("11","嘉福微信"),
		WG_TYPE("20","网关"),
		OMS_TYPE("90","管理平台");
		
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
	 * 图片管理表 应用种类
	 *
	 */
	public enum Application {
		APP_MCHNT("10","商户"),
		APP_SHOP("20","门店"),
		APP_PROD("30","产品");
		
		private String code;
		private String name;
		
		Application(String code,String name){
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
	 * 图片管理表 应用类型
	 *
	 */
	public enum AppType {
		A1001("1001","商户LOGO"),
		A1002("1002","组织机构文件代码文件"),
		A1003("1003","企业工商营业执照文件"),
		A1004("1004","法人身份证正面照片"),
		A2001("2001","店铺照"),
		A2002("2002","店内照"),
		A3001("3001","产品卡面");
		
		private String code;
		private String name;
		
		AppType(String code,String name){
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
	 *
	 * 行业类型
	 *
	 */
	public enum IndustryType {
		IT1("1", "美食"), 
		IT2("2", "休闲娱乐"), 
		IT3("3", "生活服务"),
		IT4("4", "运输票务"),
		IT5("5", "电影票"),
		IT6("6", "旅游"),
		IT7("7", "酒店"),
		IT8("8", "购物"),
		IT9("9", "虚拟"),
		IT10("10", "网络传媒");
		
		private String code;
		private String value;
		
		IndustryType(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static IndustryType findByCode(String code) {
			for (IndustryType t : IndustryType.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}

	/**
	 * 支付请求发起平台类型
	 * @author zqy
	 *
	 */
	public enum PayReqTypeEnum{

		REQ_TYPE_A("A"),//API系统
		REQ_TYPE_B("B"),//商户平台
		REQ_TYPE_C("C");//wxp
		
		private String code;
		
		PayReqTypeEnum(String code){
			this.code=code;
		}
	
		public String getCode() {
			return code;
		}

	}

	/**
	 * websocket 微信扫码支付 类型
	 *
	 */
	public enum WSSendTypeEnum{
		
		SEND_TYPE_00("00","保持心跳"),
		SEND_TYPE_10("10","订单支付请求"),
		SEND_TYPE_20("20","请求输入密码"),
		SEND_TYPE_80("80","微信支付 后台处理通知"),
		SEND_TYPE_90("90","扫描支付结果"),
		SEND_TYPE_99("99","websocket异常");
		
		private String code;
		
		private String name;
		
		WSSendTypeEnum(String code,String name){
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
	 * 用户二维码 支付方式类型
	 *
	 */
	public enum PayTypeEnum{
		VIPCARD_PAY("VIPCARD_PAY","会员卡支付"),
		WECHAT_PAY("WECHAT_PAY","微信支付");
		
		private String code;
		
		private String name;
		
		PayTypeEnum(String code,String name){
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
	 * redis 订阅频道
	 * @author zqy
	 *
	 */
	public enum RedisChannelEnum{
		B_SCAN_QR_CODE_PAY("B_SCAN_QR_CODE_PAY","微信扫码支付订阅频道");
		
		private String code;
		
		private String name;
		
		RedisChannelEnum(String code,String name){
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
	 * 密码重置 账户类型 
	 * @author zqy
	 *
	 */
	public enum PWDAcctType{
		
		CUSTOMER_PWD_100("100","会员密码"),
		MERCHANT_PWD_200("200","商户管理员密码");
		
		private String code;
		private String name;
		
		PWDAcctType(String code,String name){
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
	 *
	 * 渠道类型
	 *
	 */
	public enum RechargeSpecialChannelCode {
		
		Channel50002001("50002001","40002001","微信钱包充值"),
		Channel50004001("50004001","40004001","嘉福平台充值");
		
		private String channelCode;
		private String orgChannelCode;
		private String value;
		
		@Override
		public String toString() {
			return this.channelCode;
		}
		
		public static RechargeSpecialChannelCode findChannelCodeByOrgCode(String code) {
			for (RechargeSpecialChannelCode t : RechargeSpecialChannelCode.values()) {
				if (t.orgChannelCode.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
		
		RechargeSpecialChannelCode(String channelCode,String orgChannelCode,String value){
			this.channelCode=channelCode;
			this.orgChannelCode=orgChannelCode;
			this.value=value;
		}

		public String getChannelCode() {
			return channelCode;
		}

		public String getOrgChannelCode() {
			return orgChannelCode;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum providerOrderRechargeState {
		RechargeState0("0","充值中"),
		RechargeState1("1","充值成功"),
		RechargeState3("3","充值失败"),
		RechargeState8("8","待充值"),
		RechargeState9("9","撤销");

		private String code;
		private String value;

		providerOrderRechargeState(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static String findByCode(String code) {
			for (providerOrderRechargeState t : providerOrderRechargeState.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t.getValue();
				}
			}
			return null;
		}
	}
}
