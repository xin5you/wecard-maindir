package com.cn.iboot.diy.api.base.utils;

import com.cn.iboot.diy.api.base.domain.BaseResult;

/**
 * controller返回处理工具类
 * 
 */
public class ResultsUtil {

	/** 操作处理成功返回 带返回对象 */
	public static BaseResult<Object> success(Object obj) {
		BaseResult<Object> res = new BaseResult<Object>();
		res.setCode(BaseResult.SUCCESS_CODE);
		res.setMsg(BaseResult.SUCCESS_MSG);
		res.setObject(obj);
		return res;
	}
	
	/** 操作处理成功返回 无返回对象 */
	public static BaseResult<Object> success() {
		return success(null);
	}
	
	/** 操作处理失败返回 */
	public static BaseResult<Object> error(String code, String msg) {
		BaseResult<Object> res = new BaseResult<Object>();
		res.setCode(code);
		res.setMsg(msg);
		return res;
	}
	
}
