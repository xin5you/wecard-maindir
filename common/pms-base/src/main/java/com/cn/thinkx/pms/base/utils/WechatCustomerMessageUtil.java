package com.cn.thinkx.pms.base.utils;

public class WechatCustomerMessageUtil {

	// 员工注册成功后发送的消息
	public static final String E_REG_MSG_PARAM3 = "欢迎使用知了企服服务公众号，您已成功注册成为【%s】【%s】【%s】";
	public static final String E_REG_MSG_PARAM2 = "欢迎使用知了企服服务公众号，您已成功注册成为【%s】【%s】";

	public static void main(String[] args) {
		// String
		// content_c=String.format(WxCustomMessageUtil.WECHAT_CUSTOMER_CW10_SUCCESS,"2017年06月05日
		// 17时14分25秒","测试商户","测试门店","10.00");
		// System.out.println(content_c);
	}

	/**
	 * 微信客服消息 客户端 消费成功提醒客户端用户
	 */
	public static final String WECHAT_CUSTOMER_CW10_SUCCESS = "消费提醒：您于%s在【%s-%s】进行了会员卡付款，支付%s元。";
	// /**微信客服消息 商户端 消费成功提醒 商户端用户**/
	public static final String WECHAT_MCHNT_CW10_SUCCESS = "【%s】收款通知：收到手机尾号<%s>客户在（%s）的会员卡付款 %s 元，交易时间：%s，交易流水：%s";

	/** 微信客服消息 客户端 充值提醒 客户端用户 **/
	public static final String WECHAT_CUSTOMER_W20_SUCCESS = "充值提醒：您于%s在【%s】进行充值，成功充值 %s 元。";
	public static final String WECHAT_CUSTOMER_W20_REFUNDORDER_SUCCESS = "充值失败提醒：您于 %s在【 %s】购卡，因为系统订单处理失败，稍后请关注微信支付退款通知";
	public static final String WECHAT_CUSTOMER_W20_REFUNDORDER_FAIl = "充值失败提醒：系统订单处理失败且微信退款失败，请联系系统管理员，订单号：%s";
	/** 微信客服消息 客户端 用户快捷消费提醒 客户端用户 **/
	public static final String WECHAT_CUSTOMER_W71_SUCCESS = "消费提醒：您于%s 在【%s-%s】进行了快捷付款，支付%s元。";

	/** 微信客服消息 客户端 用户快捷消费失败，退款成功提醒 客户端用户 **/
	public static final String WECHAT_CUSTOMER_W71_REFUNDORDER_SUCCESS = "消费提醒：您于%s在【%s-%s】进行了扫码快捷付款，支付 %s元，因为系统订单处理失败，稍后请关注微信支付退款通知";
	/** 微信客服消息 客户端 用户快捷消费失败，退款失败提醒 客户端用户 **/
	public static final String WECHAT_CUSTOMER_W71_REFUNDORDER_FAIL = "消费提醒：系统订单处理失败且微信退款失败，请联系系统管理员，订单号： %s";
	/** 微信客服消息 商户端 用户快捷消费提醒 商户端用户 **/
	public static final String WECHAT_MCHNT_W71_SUCCESS = "【%s】收款通知：收到手机尾号<%s>客户在（%s）的快捷付款%s 元，交易时间：%s，交易流水：%s";
	/** 退款 **/
	public static final String WECHAT_CUSTOMER_REFUNDORDER = "退款提醒：您于%s，收到【%s-%s】的退款，退款金额 %s元，已原路退款到您的支付渠道账户中";
}
