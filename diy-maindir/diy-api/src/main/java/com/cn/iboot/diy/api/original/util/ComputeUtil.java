package com.cn.iboot.diy.api.original.util;

public class ComputeUtil {

	public static String toCompute(String allMoney,String serviceMoney){
		return String.valueOf(Integer.valueOf(allMoney)-Integer.valueOf(serviceMoney));
	}
	
	public static String toSum(String cardAmt,String quickAmt){
		return String.valueOf(Integer.valueOf(cardAmt)+Integer.valueOf(quickAmt));
	}
}
