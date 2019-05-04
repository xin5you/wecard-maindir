package com.cn.thinkx.wecard.centre.module.quartz;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;

/**
 * 微信 accessToken 定时更新 Job
 *
 */
public class AccessTokenBizJob {

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 刷新微信公众号AccountToken
	 * 
	 * @throws Exception
	 */
	public void doRefreshAccessToken() throws Exception {
		List<MpAccount> accountList = null;
		try {
			accountList = WxApiClient.getAllMpAccountList();
			if (accountList != null && accountList.size() > 0) {
				MpAccount mpAccount = null;
				for (int i = 0; i < accountList.size(); i++) {
					mpAccount = accountList.get(i);
					logger.info("refresh {} token", mpAccount.toString());
					WxApiClient.doRefreshAccessToken(mpAccount); // 刷新公众号的accesstoken
				}
			}
		} catch (Exception e) {
			logger.error("## 定时任务doRefreshAccessToken执行异常", e);
		}
	}

}