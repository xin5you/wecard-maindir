package com.cn.thinkx.dubbo.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.util.Constants.RoleNameEnum;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.merchant.service.MerchantManagerService;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.service.wecard.WecardWXService;
import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

import net.sf.json.JSONObject;

@Service("wecardWXService")
public class WecardWXServiceImpl implements WecardWXService {

	Logger logger = LoggerFactory.getLogger(WecardWXServiceImpl.class);

	@Autowired
	private MerchantManagerService merchantManagerService;

	@Override
	public boolean sendCustomTextMsg(String txnId, String txnTime, String mchntCode, String shopCode, String payAmt,
			String giveAmt, String phoneNumLast4) throws Exception {
		MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();
		List<MerchantManager> mngList = merchantManagerService.getMerchantManagerByRoleType(mchntCode, shopCode,
				RoleNameEnum.CASHIER_ROLE_MCHANT.getRoleType());
		String notice = null;

		if (mngList != null && mngList.size() > 0) {
			for (MerchantManager mng : mngList) {
				notice = "【" + mng.getMchntName() + "】收款通知：收到手机尾号<"+ phoneNumLast4 +">客户在（" + mng.getShopName() 
						+ "）的扫码付款 " + NumberUtils.formatMoney(payAmt) + " 元，交易时间：" + txnTime + "，交易流水：" + txnId;
				try {
					JSONObject obj = WxApiClient.sendCustomTextMessage(mng.getMangerName(), notice, mpAccount);
					if (obj == null)
						break;
				} catch (Exception e) {
					logger.error("发送客服消息失败", e);
				}
			}
			return true;
		}
		return false;
	}

}
