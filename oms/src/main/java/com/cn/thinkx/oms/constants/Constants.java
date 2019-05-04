
package com.cn.thinkx.oms.constants;

public class Constants {

	public static final String SESSION_USER = "sessionUser";
	
	public static final String SUCCESS_CODE = "00";
    /**
    *
    * 随机码类型
    *
    */
   public enum RandomCodeType {
	   
	   LOGIN(1, "random_code_login", "登陆");
	   
       private int id;

       private String code;
       
       private String name;

       RandomCodeType(int id, String code, String name)
       {
    	   this.id = id;
           this.code = code;
           this.name = name;
       }
       public String getName()
       {
           return name;
       }
       public int getId(){
           return id;
       }
       public String getCode(){
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
	 * 活动状态
	 *
	 */
	public enum CommStatus {
		INVALID("0", "无效"), 
		EFFECTIVE("1", "有效"), 
		PRESELL("2", "预售");

		private String code;
		private String name;

		CommStatus(String code, String name) {
			this.code = code;
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}
	
	/**
	 * 活动状态
	 *
	 */
	public enum ActiveStatus {
		draft("0", "草稿"), 
		inEffect("1", "生效中"), 
		invalid("2", "失效");
		
		private String code;
		private String name;
		
		ActiveStatus(String code, String name) {
			this.code = code;
			this.name = name;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 活动状态
	 *
	 */
	public enum ActiveStatusUsed {
		inEffect("1", "生效中"), 
		invalid("2", "失效");
		
		private String code;
		private String name;
		
		ActiveStatusUsed(String code, String name) {
			this.code = code;
			this.name = name;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getName() {
			return name;
		}
	}
	
	/**
	 * 图片上传应用种类
	 * @author 13501
	 *
	 */
	public enum ImageApplicationEnum{
		
		Application10("10","商户"),
		Application20("20","门店"),
		Application30("30","产品"),
		Application40("40","商城"),
		Application50("50","卡密产品LOGO");
		
	     private String code;
	    
	     private String name;
	     
	     ImageApplicationEnum(String code,String name){
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
	 * 图片上传应用类型
	 * @author 13501
	 *
	 */
	public enum ImageApplicationTypeEnum{
		
		ApplicationType1001("1001","1001-商户logo"),
		ApplicationType1002("1002","1002-组织机构文件代码文件"),
		ApplicationType1003("1003","1003-企业工商营业执照文件"),
		ApplicationType1004("1004","1004-法人身份证正面照片"),
		ApplicationType2001("2001","2001-店面照"),
		ApplicationType2002("2002","2002-店内照"),
		ApplicationType2003("2003","2003-商品实物照"),
		ApplicationType3001("3001","3001-产品卡面"),
		ApplicationType4001("4001","4001-商城logo"),
		ApplicationType4002("4002","4002-商城背景图"),
		ApplicationType5001("5001","5001-卡密产品LOGO");
		
	     private String code;
	    
	     private String name;
	     
	     ImageApplicationTypeEnum(String code,String name){
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
   * 字典P_CODE enum
   *
   */
  public enum DictType {
	   MCHNT_ACCOUNT_STAT(1, "MCHNT_ACCOUNT_STAT", "商户开户状态"),
	   MCHNT_MORTGAGE_FLG(2, "MCHNT_MORTGAGE_FLG", "商户保证金押款标志"),
	   MCHNT_MARGIN_STAT(3, "MARGIN_STAT", "商户追加保证金审核状态");
	   
      private int id;

      private String code;
      
      private String name;

      DictType(int id, String code, String name)
      {
   	   this.id = id;
          this.code = code;
          this.name = name;
      }
      public String getName()
      {
          return name;
      }
      public int getId(){
          return id;
      }
      public String getCode(){
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
	 * 保证金审核状态
	 * @author 13501
	 *
	 */
	public enum MarginStatEnum{
		
		MARGIN_10("10","草稿"),
		MARGIN_20("20","审批中"),
		MARGIN_30("30","审批不通过"),
		MARGIN_40("40","审批通过"),
		MARGIN_50("50","打款完成");
		
		private String code;
		
		private String name;
		
		MarginStatEnum(String code,String name){
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
	 *秘钥信息常量定义
	 * @author 13501
	 *
	 */
	public enum KeyEnum{
		
		PWD("PWD","密码秘钥"),
		BAL("BAL","余额秘钥"),
		CVV("CVV","CVV秘钥"),
		TMK("TMD","TMK秘钥"),
		PIK("PIK","POS密码加密秘钥"),
		POS("POS","POS终端主密钥"),
		MAK("MAK","POSMAC加密秘钥");
		
	     private String code;
	    
	     private String name;
	     
	     KeyEnum(String code,String name){
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
	
	public enum KeyTypeEnum{
		ACTKEY("01","账户主密钥"),
		POSKEY("02","终端主密钥");
		
	     private String code;
	    
	     private String name;
	     
	     KeyTypeEnum(String code,String name){
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

	public enum SettleTypeEnum{
		SETTLESUM("10","汇总结算"), //默认
		SETTLESINGLE("20","逐笔结算");
		
	     private String code;
	    
	     private String name;
	     
	     SettleTypeEnum(String code,String name){
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
	
	public enum SettleCycleEnum{
		SETTLEDAY("1000","日结"), //默认
		SETTLEWEEK("1100","周结"),
		SETTLEMONTH("1200","月结");
		
	     private String code;
	    
	     private String name;
	     
	     SettleCycleEnum(String code,String name){
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
	 * 卡产品是否上下架
	 * 
	 * @author xiaomei
	 *
	 */
	public enum isPutaway {
		P0("0", "上架"),
		P1("1", "下架");
		
		private String code;
		private String value;
		
		isPutaway(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static isPutaway findByCode(String code) {
			for (isPutaway t : isPutaway.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
	/**
	 * 卡密产品单位
	 * 
	 * @author xiaomei
	 *
	 */
	public enum ProductUnit {
		P0("0", "M"),
		P1("1", "G"),
		P2("2", "元"),
		P3("3", "月"),
		P4("4", "年");
		
		private String code;
		private String value;
		
		ProductUnit(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static ProductUnit findByCode(String code) {
			for (ProductUnit t : ProductUnit.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
	/**
	 * 卡券集市（出款订单状态）
	 * 
	 * @author xiaomei
	 *
	 */
	public enum withdrawStat {
		S66("66", "申请代付"),
		S00("00", "新建"),
		S01("01", "已受理"),
		S02("02", "处理中"),
		S03("03", "支付中"),
		S04("04", "支付失败"),
		S05("05", "支付成功"),
		S06("06", "支付异常"),
		S07("07", "处理成功");
		
		private String code;
		private String value;
		
		withdrawStat(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static withdrawStat findByCode(String code) {
			for (withdrawStat t : withdrawStat.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
}

