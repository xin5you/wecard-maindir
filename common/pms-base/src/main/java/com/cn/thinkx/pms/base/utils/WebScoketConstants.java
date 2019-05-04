package com.cn.thinkx.pms.base.utils;

public class WebScoketConstants {
	// 微信端websocket链接参数 业务类型：已扫描
	public static final String WX_WEBSOCKET_BIZ_SCANNED = "scanned";
	// 微信端websocket链接参数 业务类型：扫一扫
	public static final String WX_WEBSOCKET_BIZ_SCANCODE = "scanCode";
	// 微信端websocket链接参数 业务类型：付款码
	public static final String WX_WEBSOCKET_BIZ_QRCODE = "qrCode";
	// 微信端websocket链接参数 开始处理交易
	public static final String WX_WEBSOCKET_START_TRANS = "start_trans";
	// 微信端websocket链接参数 需要密码
	public static final String WX_WEBSOCKET_NEED_PASSWORD = "need_password";
	// 微信端websocket用户发送密码信息间隔特殊字符
	public static final String WX_FLAG_SEND_PASS = "@";
}
