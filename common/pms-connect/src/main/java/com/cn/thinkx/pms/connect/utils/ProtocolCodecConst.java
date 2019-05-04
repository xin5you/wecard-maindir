package com.cn.thinkx.pms.connect.utils;

public interface ProtocolCodecConst {
	
	//length type: "N"-number string, "BCD"-number string with bcd compress, "Bi"-binary data
	
	String lenTypeNumStr = "N";
	
	String lenTypeBCD = "BCD";
	
	String lenTypeBinary = "Bi";
	
	int lenFieldLen = 5;
	
	boolean includeLenField = false;

}
