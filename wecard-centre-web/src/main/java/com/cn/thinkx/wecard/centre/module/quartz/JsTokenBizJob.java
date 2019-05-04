package com.cn.thinkx.wecard.centre.module.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;

/**
 * 微信 jsToken 定时更新 Job
 *
 */
public class JsTokenBizJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 刷新微信公众号jsToken
	 * 
	 * @throws Exception
	 */
	public void doRefreshJsToken() throws Exception {
//		logger.info("定时任务doRefreshJsToken执行开始，时间[{}]", DateUtil.getCurrentDateTimeStr());
		List<MpAccount> accountList = null;
		try {
			accountList = WxApiClient.getAllMpAccountList();
			if (accountList != null && accountList.size() > 0) {
				MpAccount mpAccount = null;
				for (int i = 0; i < accountList.size(); i++) {
					mpAccount = accountList.get(i);
					WxApiClient.doRefreshJsToken(mpAccount); // 刷新 公众号的jsToken
				}
			}
		} catch (Exception e) {
			logger.error("## 定时任务doRefreshJsToken执行异常", e);
		}
	}

}