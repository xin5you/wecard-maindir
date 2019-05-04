/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: MerchantBatchOrderDemo.java
 * Author:   16031333
 * Date:     2016年5月25日 上午9:42:36
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cn.thinkx.wecard.api.module.withdraw.suning.demo;

import com.cn.thinkx.wecard.api.module.withdraw.suning.dto.WithdrawQueryData;

/**
 * 〈一句话功能简述〉<br> 
 * 〈出款批次查询〉
 *
 * @author 16031333
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class WithdrawQueryDemo {

	public void action() throws Throwable {

		WithdrawQueryData withdrawQueryData = new WithdrawQueryData();
	    String baseUrl = "https://wagtestpre.suning.com/epps-wag/withdrawQuery.htm";
	    try {
		String responseData = withdrawQueryData.batchWithDraw(baseUrl);
		System.out.println("出款批次查询responseData:"+responseData);

	    } catch (Exception e  ) {
	    	System.out.println("调用出款批次查询出错："+e);
	    }
	}
	
	public static void main(String[] args) throws Throwable {
		WithdrawQueryDemo demo = new WithdrawQueryDemo();
		demo.action();
	}
}
