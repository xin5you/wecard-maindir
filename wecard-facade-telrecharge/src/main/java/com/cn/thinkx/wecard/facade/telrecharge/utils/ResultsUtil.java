package com.cn.thinkx.wecard.facade.telrecharge.utils;

import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespDomain;

/**
 * controller返回处理工具类
 * 
 * @author zhuqiuyou
 *
 */
public class ResultsUtil {

	/** 操作处理成功返回 带返回对象 */
	public static TeleRespDomain<Object> success(Object obj) {
		TeleRespDomain<Object> res = new TeleRespDomain<Object>();
		res.setCode(TeleRespDomain.SUCCESS_CODE);
		res.setMsg(TeleRespDomain.SUCCESS_MSG);
		res.setData(obj);
		return res;
	}
	
	/** 操作处理成功返回 无返回对象 */
	public static TeleRespDomain<Object> success() {
		return success(null);
	}
	
	/** 操作处理失败返回 */
	public static TeleRespDomain<Object> error(String code, String msg) {
		TeleRespDomain<Object> res = new TeleRespDomain<Object>();
		res.setCode(code);
		res.setMsg(msg);
		return res;
	}
	
}
