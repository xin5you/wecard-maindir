package com.cn.iboot.diy.api.base.constants;

public class Constants {

	public static final String SESSION_USER = "sessionUser";
	/**手机动态码 session**/
	public static final String SESSION_PHONECODE = "session_phonecode_";
	public static final String SESSION_PHONECODE_TIME = "session_phonecode_time_";
	/** redis**/
	public static final String REDIS_HASH_TABLE_TB_BASE_DICT_KV= "TB_BASE_DICT_KV";
	public static final String REDIS_HASH_TABLE_TB_CHANNEL_SECURITY_INF_KV = "TB_CHANNEL_SECURITY_INF_KV";

	/**
	 * 随机码类型
	 * 
	 * @author xiaomei
	 *
	 */
	public enum RandomCodeType {

		LOGIN(1, "random_code_login", "登录");

		private int id;
		private String code;
		private String name;

		RandomCodeType(int id, String code, String name) {
			this.id = id;
			this.code = code;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}

		public String getCode() {
			return code;
		}

		public static RandomCodeType findByCode(int id) {
			for (RandomCodeType value : RandomCodeType.values()) {
				if (value.id == id) {
					return value;
				}
			}
			return null;
		}
	}

	/**
	 * 正常停用状态
	 * 
	 * @author xiaomei
	 *
	 */
	public enum PrmStat {
		PS0("0", "正常"),
		PS1("1", "停用");

		private String code;
		private String value;

		PrmStat(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static PrmStat findByCode(String code) {
			for (PrmStat t : PrmStat.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}

	/**
	 * 运营数据的数据状态
	 * @author zhupan
	 *
	 */
	public enum DIYStat{
		DIYStat0("0","原始数据"),
		DIYStat1("1","已修改"),
		DIYStat2("2","已归档");

		private String code;
		private String name;

		DIYStat(String code, String name) {
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

		public static DIYStat findByCode(String code) {

			for (DIYStat s : DIYStat.values()) {
				if (s.code.equalsIgnoreCase(code)) {
					return s;
				}
			}
			return null;
		}

	}


	/**
	 * 实时统计时间
	 * @author zyl
	 *
	 */
	public enum DiyTime{

		DiyTime0("00","0:00"),
		DiyTime1("01","6:00"),
		DiyTime2("02","7:30"),
		DiyTime3("03","9:00"),
		DiyTime4("04","10:00"),
		DiyTime5("05","10:30"),
		DiyTime6("06","12:00"),
		DiyTime7("07","14:00"),
		DiyTime8("08","16:30"),
		DiyTime9("09","18:00"),
		DiyTime10("10","19:30"),
		DiyTime11("11","23:00");

		private String code;
		private String name;

		DiyTime(String code, String name) {
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

		public static DiyTime findByCode(String code) {

			for (DiyTime s : DiyTime.values()) {
				if (s.code.equalsIgnoreCase(code)) {
					return s;
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


}
