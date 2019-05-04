package com.cn.thinkx.wecard.centre.module.biz.util;

import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.centre.module.biz.service.CardKeysTransLogService;

public class CardKeysFactory {
	
	// 私有构造方法
	private CardKeysFactory() {
		
	}

	private static CardKeysTransLogService cardKeysTransLogService;

	private static CardKeysOrderInfService cardKeysOrderInfService;

	public static CardKeysTransLogService getCardKeysTransLogService() {
		if (cardKeysTransLogService == null) {
			cardKeysTransLogService = SpringContextUtil.getBeanById("cardKeysTransLogService");
		}
		return cardKeysTransLogService;
	}

	public static CardKeysOrderInfService getCardKeysOrderInfService() {
		if (cardKeysOrderInfService == null) {
			cardKeysOrderInfService = SpringContextUtil.getBeanById("cardKeysOrderInfService");
		}
		return cardKeysOrderInfService;
	}
}
