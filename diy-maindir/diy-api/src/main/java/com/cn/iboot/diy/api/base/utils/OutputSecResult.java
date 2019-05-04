package com.cn.iboot.diy.api.base.utils;

public class OutputSecResult {
	public String 	dataStr;
	public byte[] 	dataByte;
	public int 		dataLenInt;
	public byte[] 	dataLenByte;
	public byte[] 	signData;
	
	public void setData(byte[] tData) {
		
		dataByte = new byte[tData.length];		
		System.arraycopy(tData, 0, dataByte, 0, tData.length);
		
		dataStr = new String(tData);
	}
	public void setDataLen(byte[] tDataLen)	{
		
		dataLenByte = new byte[tDataLen.length];
		System.arraycopy(tDataLen, 0, dataLenByte, 0, tDataLen.length);
		
		Integer tIneger = new Integer(Integer.parseInt(new String(tDataLen)));
		dataLenInt = tIneger.intValue();
	}	
}
