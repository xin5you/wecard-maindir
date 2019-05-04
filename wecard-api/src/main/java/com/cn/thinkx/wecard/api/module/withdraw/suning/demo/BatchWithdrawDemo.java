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

import com.cn.thinkx.wecard.api.module.withdraw.suning.dto.BatchWithdrawData;

/**
 * 〈一句话功能简述〉<br>
 * 〈批量出款到卡〉
 * 
 * @author 16031333
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchWithdrawDemo {

    public void action() throws Throwable {
        BatchWithdrawData batchWithdrawData = new BatchWithdrawData();
        String baseUrl = "https://wagtestpre.suning.com/epps-wag/withdraw.htm";
        try {
            String responseData = batchWithdrawData.batchWithDraw(baseUrl);

            System.out.println("批量出款responseData:" + responseData);

        } catch (Exception e) {
            System.out.println("调用批量出款出错：" + e);
        }
    }

    public static void main(String[] args) throws Throwable {
        BatchWithdrawDemo demo = new BatchWithdrawDemo();
        demo.action();
    }
}
