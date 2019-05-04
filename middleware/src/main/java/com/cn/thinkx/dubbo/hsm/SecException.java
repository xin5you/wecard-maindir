package com.cn.thinkx.dubbo.hsm;

public class SecException extends Exception {
	
	static final long serialVersionUID = -2398001218377041315L;
	
	public static final String ERR_SEC_CODE_7000 = "7000";
	public static final String ERR_SEC_CODE_7001 = "7001";
	public static final String ERR_SEC_CODE_7002 = "7002";
	public static final String ERR_SEC_CODE_7003 = "7003";
	public static final String ERR_SEC_CODE_7004 = "7004";
	public static final String ERR_SEC_CODE_7005 = "7005";
	
	public static final String ERR_SEC_CODE_8000 = "8000";
	public static final String ERR_SEC_CODE_8001 = "8001";
	public static final String ERR_SEC_CODE_8002 = "8002";
	public static final String ERR_SEC_CODE_8003 = "8003";
	public static final String ERR_SEC_CODE_8004 = "8004";
	public static final String ERR_SEC_CODE_8005 = "8005";
	
	public static final String ERR_SEC_CODE_9000 = "9000";
	public static final String ERR_SEC_CODE_9001 = "9001";
	
	public static final String ERR_SEC_MSG_7000 = "PIN长度超限";	
	public static final String ERR_SEC_MSG_7001 = "PIN TO PINBLOCK失败";	
	public static final String ERR_SEC_MSG_7002 = "非对称转PIN失败";
	public static final String ERR_SEC_MSG_7003 = "加密机参数错误";
	public static final String ERR_SEC_MSG_7004 = "签名运算,传入私钥数据";
	public static final String ERR_SEC_MSG_7005 = "PIN加密错误";
	
	
	public static final String ERR_SEC_MSG_8000 = "获取加密机联接错误信息失败";
	public static final String ERR_SEC_MSG_8001 = "非对称512位公钥加密失败";
	public static final String ERR_SEC_MSG_8002 = "非对称转PIN失败";
	public static final String ERR_SEC_MSG_8003 = "加密机参数错误";
	public static final String ERR_SEC_MSG_8004 = "签名运算,传入私钥数据";
	public static final String ERR_SEC_MSG_8005 = "非对称公钥证书加密失败";
	
	public static final String ERR_SEC_MSG_9000 = "生产MAC数据失败";
	public static final String ERR_SEC_MSG_9001 = "验证MAC失败";
	
	public SecException() {
	}

	public SecException(String message) {
	    super(message);
	}
}
