package com.cn.thinkx.dubbo.facadeImpl.drools.valid;

import java.math.BigDecimal;

public class RuleUtil {
	
	/**
	 * 两个数据相减
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int subUtil(int v1, int v2) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.subtract(b2).intValue();
	}
	
	/**
	 * 两个数据相加
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int addUtil(int v1, int v2) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.add(b2).intValue();
	}

	/**
	 * 两个数据相乘
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int multiplyUtil(int v1, int v2) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		BigDecimal b3 = BigDecimal.valueOf(10000); //折扣率万分比
		return b1.multiply(b2).divide(b3).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
	}
	
	/**
	 * 两个数据相除
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static int divideUtil(int v1, int v2) {
		BigDecimal b1 = BigDecimal.valueOf(v1);
		BigDecimal b2 = BigDecimal.valueOf(v2);
		return b1.multiply(b2).intValue();
	}
	
	public static void main(String[] args) {
		Integer l=RuleUtil.multiplyUtil(111, 5100);
		System.out.println(l);
	}
}
