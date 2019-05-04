package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseResp;
/**
 * 客户账户查询
 * @author zqy
 *
 */
public class CustomerAccountQueryITFResp extends BaseResp {

	/**
	 * 开户标志
	 */
	private String accountFlag;
	
	/**
	 * 开卡标志
	 */
	private String cardFlag;
	

	public String getAccountFlag() {
		return accountFlag;
	}

	public void setAccountFlag(String accountFlag) {
		this.accountFlag = accountFlag;
	}

	public String getCardFlag() {
		return cardFlag;
	}

	public void setCardFlag(String cardFlag) {
		this.cardFlag = cardFlag;
	}
	
}
