package com.cn.thinkx.oms.module.statement.util;

public class ComputeUtil {

	public static Integer toCompute(String allMoney,String serviceMoney){
		return Integer.valueOf(allMoney)-Integer.valueOf(serviceMoney);
	}
}
