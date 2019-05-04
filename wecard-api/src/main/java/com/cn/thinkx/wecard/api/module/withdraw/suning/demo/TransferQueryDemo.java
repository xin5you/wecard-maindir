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

import com.cn.thinkx.wecard.api.module.withdraw.suning.dto.TransferQueryData;

/**
 * 〈一句话功能简述〉<br> 
 * 〈转账批次查询〉
 *
 * @author 16031333
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TransferQueryDemo {

	public void action() throws Throwable {

		TransferQueryData transferQueryData = new TransferQueryData();
	    String baseUrl = "https://wagtestpre.suning.com/epps-twg/transferQuery.htm";
	    try {
		String responseData = transferQueryData.batchWithDraw(baseUrl);
		System.out.println("转账批次查询responseData:"+responseData);

	    } catch (Exception e  ) {
	    	System.out.println("调用转账批次查询出错："+e);
	    }
	}
	
	public static void main(String[] args) throws Throwable {
		TransferQueryDemo demo = new TransferQueryDemo();
		demo.action();
	}
}
