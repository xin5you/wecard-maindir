package com.cn.thinkx.pms.base.utils;

/**
 * 聚合支付枚举类
 * 
 * @author xiaomei
 *
 */
public class BaseIntegrationPayConstants {

	// 出现硬件或者网络等其他因素导致的失败交易返回码
	public static final String TXN_TRANS_ERROR = "99";
	public static final String RESPONSE_EXCEPTION_CODE = "96";

	public static final String RESPONSE_EXCEPTION_INFO = "网络异常,请稍后再试";
	public static final String RESPONSE_REPEAT_TRANS = "重复交易";

	// 日切允许状态
	public static final String TRANS_FLAG_YES = "1";
	//货币类型
	public static final String TRANS_CURR_CD = "156";
	// 定义成功值
	public static final String SUCCESS = "success";
	// 定义失败值
	public static final String FAILED = "failed";
	
	//聚合支付消费方法名
	public static final String PAYMENT = "payment";
	
	//聚合支付退款方法名
	public static final String REFUND = "refund";
	
	//聚合支付查询方法名
	public static final String SEARCH = "search";
	
	//申鑫支付成功返回码
	public static final String SX_PAYMENT_SUCCESS = "000000";
	
	//申鑫退款成功返回码
	public static final String SX_REFUND_SUCCESS = "00";
	
	//申鑫退款成功返回码
	public static final String SX_PAYMENT_TYPE = "S";
	
	//薪无忧通道
	public static final String HKB_TERM_CHNL_NO = "101";

	/**
	 *
	 * 交易类型
	 *
	 */
	public enum TransCode {
		CW71("W71", "聚合支付消费"),
		CW74("W74", "聚合支付退款"),
		CW77("W77", "聚合支付查询");
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

	/**
	 *
	 * 核心接口返回码
	 *
	 */
	public enum ITFRespCode {
		CODE00("00", "交易成功"), 
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
	 * 渠道类型
	 *
	 */
	public enum ChannelCode {
		CHANNEL0("10001001"),//管理平台
		CHANNEL1("40001010"),// 商户开户、客户开户、密码重置、消费 (从微信公众号发起)
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
	 *
	 * 发起方
	 *
	 */
	public enum SponsorCode {
		SPONSOR00("00"),// 薪无忧客户微信平台
		SPONSOR10("10"),// 薪无忧商户微信平台
		SPONSOR20("20"),// 扫码盒子会员卡
		SPONSOR30("30"),// 扫码盒子微信
		SPONSOR40("40"),// 扫码盒子支付宝
		SPONSOR50("50");// 扫码盒子云闪付

		private final String value;

		SponsorCode(String value) {
			this.value = value;
		};

		@Override
		public String toString() {
			return this.value;
		}

		public static SponsorCode findByCode(String code) {
			for (SponsorCode t : SponsorCode.values()) {
				if (t.value.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}

	/**
	 * 支付方式
	 * 
	 * @author xiaomei
	 *
	 */
	public enum PaymentType {
		INVALID("INVALID"),
		HKBPAY("HKB"),
		WXPAY("WXPAY"),
		ALIPAY("ALIPAY"),
		UNIONPAY("UNIONPAY");

		private String value;
		PaymentType(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
	}
	
	public enum RefundType{
		INVALID("INVALID"),
		WXPAY("WECHAT"),
		ALIPAY("ALIPAY"),
		UNIONPAY("UNIONPAY");
		
		private String value;
		
		RefundType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}

	/**
	 * 第三方接口枚举
	 * 
	 * @author xiaomei
	 *
	 */
	public enum ChnlNoMethod {
		CM10("101", "薪无忧", ""),
		CM20("102", "申鑫", "com.cn.thinkx.integrationpay.shenxinpay.service.ShenXinPayService"),
		CM30("103", "收钱吧", "");

		private String channelCode;
		private String channelName;
		private String className;

		public String getChannelCode() {
			return channelCode;
		}

		public String getChannelName() {
			return channelName;
		}

		public String getClassName() {
			return className;
		}

		@Override
		public String toString() {
			return this.channelCode;
		}

		public static ChnlNoMethod findChannelCodeByCode(String channelCode) {
			for (ChnlNoMethod t : ChnlNoMethod.values()) {
				if (t.channelCode.equalsIgnoreCase(channelCode)) {
					return t;
				}
			}
			return null;
		}

		ChnlNoMethod(String channelCode, String channelName, String className) {
			this.channelCode = channelCode;
			this.channelName = channelName;
			this.className = className;
		}
	}
	
	public enum ChnlNoType{
		CHANNEL2("40002001","WECHAT"),// 微信
		CHANNEL3("40003001","ALIPAY"),// 支付宝
		CHANNEL5("40005001","UNIONPAY");// 网银
		
		private String code;
		private String value;
		
		ChnlNoType(String code,String value){
			this.code = code;
			this.value = value;
		}
		public static String findChnlNoTypeValue(String code) {
			for (ChnlNoType t : ChnlNoType.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t.value;
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
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	/**
	 * oms中支付通道类型
	 * 
	 * @author kpplg
	 *
	 */
	public enum OMSChannelType{
		ChannelType1("1","会员卡"),
		ChannelType2("2","第三方"),
		ChannelType3("3","其他");
		
		private String code;
		private String value;
		
		OMSChannelType(String code,String value){
			this.code = code;
			this.value = value;
		}
		public static String findOMSChannelTypeByCode(String code) {
			for (OMSChannelType t : OMSChannelType.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t.value;
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

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
		
	}
	public enum OMSChannelCode {
		OMSCHANNEL0("10001001","管理平台"),//管理平台
		OMSCHANNEL1("40001010","薪无忧"),// 商户开户、客户开户、密码重置、消费 (从微信公众号发起)
		OMSCHANNEL2("40002001","微信"),// 充值、商户提现  (从微信公众号发起)
		OMSCHANNEL3("40003001","支付宝"),// 充值、商户提现  (从支付宝发起)
		OMSCHANNEL4("40004001","嘉福平台"),// 充值、商户提现  (从嘉福平台发起)
		OMSCHANNEL5("40005001","云闪付"),// 充值、商户提现  (从网银向本系统发起)
		OMSCHANNEL6("40006001","电商");// all  (从电商端发起)

		private String code;
		private String value;

		OMSChannelCode(String code,String value){
			this.code = code;
			this.value = value;
		}
		public static String findOMSChannelCodeByCode(String code) {
			for (OMSChannelCode t : OMSChannelCode.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t.value;
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

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
